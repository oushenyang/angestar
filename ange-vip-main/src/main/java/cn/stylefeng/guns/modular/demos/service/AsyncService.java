package cn.stylefeng.guns.modular.demos.service;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.http.HttpUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.db.entity.DatabaseInfo;
import cn.stylefeng.guns.modular.account.entity.AccountCardType;
import cn.stylefeng.guns.modular.account.service.AccountCardTypeService;
import cn.stylefeng.guns.modular.agent.entity.*;
import cn.stylefeng.guns.modular.agent.service.*;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.apiManage.service.ApiResultService;
import cn.stylefeng.guns.modular.app.entity.AppEdition;
import cn.stylefeng.guns.modular.app.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.app.service.AppEditionService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.sys.core.util.SpringUtil;
import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import net.bytebuddy.implementation.bytecode.Throw;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * <p></p>
 *
 * @author shenyang.ou
 * @version 1.0
 * @date 2020/11/13
 * @since JDK 1.8
 */
@Service
public class AsyncService {
    @Async
    public void insertTokenToSql(Token token){
        TokenService tokenService = SpringUtil.getBean(TokenService.class);
        tokenService.save(token);
    }

    /**
     * 先删除在新增token
     * @param codeOpenNumLong 保留数量
     * @param cardId 卡密id
     */
    @Async
    public void delAndInsertToken(Integer codeOpenNumLong, Long cardId){
        TokenService tokenService = SpringUtil.getBean(TokenService.class);
        List<Token> tokenList = tokenService.list(new QueryWrapper<Token>().eq("card_or_user_id", cardId));
        if (tokenList.size()>codeOpenNumLong){
            //先删除然后新增一个
            for (int i = 0; i < tokenList.size()-codeOpenNumLong; i++) {
                if (ObjectUtil.isNotNull(tokenList.get(i))){
                    tokenService.deleteByCardId(tokenList.get(i).getCardOrUserId());
                }
            }
        }
        tokenList.clear();
    }

    @Async
    public void insertDevice(Device device) {
        DeviceService deviceService = SpringUtil.getBean(DeviceService.class);
        deviceService.save(device);
    }

    @Async
    public void updateDeviceAndCardLoginNum(Long deviceId,Long cardId) {
        DeviceService deviceService = SpringUtil.getBean(DeviceService.class);
        CardInfoService cardInfoService = SpringUtil.getBean(CardInfoService.class);
        deviceService.updateDeviceLoginNumByDeviceId(deviceId);
        cardInfoService.updateCardLoginNumByCardId(cardId);
    }

    /**
     * 更新token和缓存
     * @param singleCode 卡密
     * @param token token
     */
    @Async
    public void updateTokenAndRedis(String singleCode, Token token) {
        TokenService tokenService = SpringUtil.getBean(TokenService.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        redisUtil.expire(RedisType.CARD_INFO.getCode() + singleCode, RedisExpireTime.DAY.getCode());
        tokenService.updateById(token);
    }

    /**
     * 生成应用相关数据
     * @param appId appId
     * @param appNum appNum
     */
    @Async
    public void insertAppOthers(Long appId, String appNum, Long userId) {
        ApiManageService apiManageService = SpringUtil.getBean(ApiManageService.class);
        ApiResultService apiResultService = SpringUtil.getBean(ApiResultService.class);
        CodeCardTypeService codeCardTypeService = SpringUtil.getBean(CodeCardTypeService.class);
        AccountCardTypeService accountCardTypeService = SpringUtil.getBean(AccountCardTypeService.class);
        AppEditionService appEditionService = SpringUtil.getBean(AppEditionService.class);
        //生成api接口
        List<ApiManage> apiManages = apiManageService.list(new QueryWrapper<ApiManage>().eq("app_id", 1L));
        apiManages.forEach(apiManage -> {
            apiManage.setApiManageId(null);
            apiManage.setAppId(appId);
            apiManage.setCallCode(appNum);
            apiManage.setCreateTime(new Date());
            apiManage.setCreateUser(userId);
            apiManage.setUpdateTime(null);
            apiManage.setUpdateUser(null);
        });
        apiManageService.saveBatch(apiManages);

        //生成api自定义返回
        List<ApiResult> apiResults = apiResultService.list(new QueryWrapper<ApiResult>().eq("app_id", 1L));
        apiResults.forEach(apiResult1 -> {
            apiResult1.setApiResultId(null);
            apiResult1.setAppId(appId);
            apiResult1.setCreateTime(new Date());
            apiResult1.setCreateUser(userId);
            apiResult1.setUpdateTime(null);
            apiResult1.setUpdateUser(null);
        });
        apiResultService.saveBatch(apiResults);

        //生成卡密类型
        //先判断有没有
        List<CodeCardType> codeCardTypeList = codeCardTypeService.list(new QueryWrapper<CodeCardType>().eq("app_id", 0L).eq("create_user",userId));
        if (CollectionUtil.isEmpty(codeCardTypeList)){
            List<CodeCardType> codeCardTypes = codeCardTypeService.list(new QueryWrapper<CodeCardType>().eq("create_user",0L));
            codeCardTypes.forEach(codeCardType2 -> {
                codeCardType2.setCardTypeId(null);
                codeCardType2.setCreateTime(new Date());
                codeCardType2.setCreateUser(userId);
                codeCardType2.setUpdateTime(null);
                codeCardType2.setUpdateUser(null);
            });
            codeCardTypeService.saveBatch(codeCardTypes);
        }

        //生成注册码卡密类型
        //先判断有没有
        List<AccountCardType> accountCardTypeList = accountCardTypeService.list(new QueryWrapper<AccountCardType>().eq("app_id", 0L).eq("create_user",userId));
        if (CollectionUtil.isEmpty(accountCardTypeList)){
            List<AccountCardType> accountCardTypes = accountCardTypeService.list(new QueryWrapper<AccountCardType>().eq("create_user",0L));
            accountCardTypes.forEach(accountCardType -> {
                accountCardType.setAccountCardTypeId(null);
                accountCardType.setCreateTime(new Date());
                accountCardType.setCreateUser(userId);
                accountCardType.setUpdateTime(null);
                accountCardType.setUpdateUser(null);
            });
            accountCardTypeService.saveBatch(accountCardTypes);
        }

        //生成版本号
        AppEditionParam appEditionParam = new AppEditionParam();
        appEditionParam.setAppId(appId);
        appEditionParam.setEditionName("1.0");
        appEditionParam.setEditionNum("1.0");
        appEditionParam.setCreateUser(userId);
        appEditionService.add(appEditionParam);
    }

    /**
     * 删除应用相关数据
     * @param appId appId
     */
    @Async
    public void deleteAppOthers(Long appId) {
        AppEditionService appEditionService = SpringUtil.getBean(AppEditionService.class);
        ApiManageService apiManageService = SpringUtil.getBean(ApiManageService.class);
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        ApiResultService apiResultService = SpringUtil.getBean(ApiResultService.class);
        CardInfoService cardInfoService = SpringUtil.getBean(CardInfoService.class);
        DeviceService deviceService = SpringUtil.getBean(DeviceService.class);
        TokenService tokenService = SpringUtil.getBean(TokenService.class);
        AgentAppService agentAppService = SpringUtil.getBean(AgentAppService.class);
        AgentCardService agentCardService = SpringUtil.getBean(AgentCardService.class);
        AgentPowerService agentPowerService = SpringUtil.getBean(AgentPowerService.class);
        AgentExamineService agentExamineService = SpringUtil.getBean(AgentExamineService.class);
        AgentBuyCardService agentBuyCardService = SpringUtil.getBean(AgentBuyCardService.class);
        //删除版本
        appEditionService.remove(new QueryWrapper<AppEdition>().eq("app_id", appId));
        //删除api接口
        apiManageService.remove(new QueryWrapper<ApiManage>().eq("app_id", appId));
        List<ApiManage> apiManages = apiManageService.list(new QueryWrapper<ApiManage>().eq("app_id", appId));
        apiManages.forEach(apiManage1 -> {
            //清除api接口缓存
            redisUtil.del(RedisType.API_MANAGE.getCode() + apiManage1.getApiCode() + "-" +  apiManage1.getCallCode());
        });
        //删除api返回
        apiResultService.remove(new QueryWrapper<ApiResult>().eq("app_id", appId));
        List<ApiResult> apiResults = apiResultService.list(new QueryWrapper<ApiResult>().eq("app_id", appId));
        apiResults.forEach(apiResult1 -> {
            //清除api接口缓存
            redisUtil.del(RedisType.API_RESULT.getCode() + apiResult1.getAppId() + "-" +  apiResult1.getResultCode());
        });
        //删除卡密
        cardInfoService.remove(new QueryWrapper<CardInfo>().eq("app_id", appId));
        //删除绑定设备
        deviceService.remove(new QueryWrapper<Device>().eq("app_id", appId));
        //删除token
        tokenService.remove(new QueryWrapper<Token>().eq("app_id", appId));
        //删除代理
        agentAppService.remove(new QueryWrapper<AgentApp>().eq("app_id", appId));
        //代理卡密
        agentCardService.remove(new QueryWrapper<AgentCard>().eq("app_id", appId));
        //代理权限
        agentPowerService.remove(new QueryWrapper<AgentPower>().eq("app_id", appId));
        //代理审核记录
        agentExamineService.remove(new QueryWrapper<AgentExamine>().eq("app_id", appId));
        //代理购卡记录
        agentBuyCardService.remove(new QueryWrapper<AgentBuyCard>().eq("app_id", appId));
    }

    /**
     * 更新卡密信息
     * @param cardInfo 卡密信息
     */
    @Async
    public void updateCard(CardInfo cardInfo) {
        CardInfoService cardInfoService = SpringUtil.getBean(CardInfoService.class);
        cardInfoService.updateById(cardInfo);
    }

    /**
     * 更新卡密信息和缓存
     * @param cardInfo 卡密信息
     */
    @Async
    public void updateCardAndRedis(Long appId, CardInfo cardInfo, String singleCode) {
        RedisUtil redisUtil = SpringUtil.getBean(RedisUtil.class);
        CardInfoService cardInfoService = SpringUtil.getBean(CardInfoService.class);
        redisUtil.del(RedisType.CARD_INFO.getCode() + singleCode);
        cardInfoService.updateById(cardInfo);
    }

    /**
     * 激活易游卡密
     * @param cardLoginUrl 单码登录地址
     * @param singleCode 单码
     */
    @Async
    public void activationYyCard(String cardLoginUrl, String singleCode) {
        HashMap<String, Object> paramMap = new HashMap<>();
        paramMap.put("SingleCode", singleCode);
        paramMap.put("Ver", "1.0");
        paramMap.put("Mac", "11111");
        HttpUtil.post(cardLoginUrl, paramMap);
    }
}
