package cn.stylefeng.guns.modular.app.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.db.entity.DatabaseInfo;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.app.entity.AppEdition;
import cn.stylefeng.guns.modular.app.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.app.service.AppEditionService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.mapper.AppInfoMapper;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppInfoApi;
import cn.stylefeng.guns.modular.app.model.result.AppInfoResult;
import  cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.modular.apiManage.service.ApiResultService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.ADD_HEAD_ERROR;

/**
 * <p>
 * 软件表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {

    @Autowired
    private ApiManageService apiManageService;
    @Autowired
    private ApiResultService apiResultService;
    @Autowired
    private AppEditionService appEditionService;
    @Autowired
    private CardInfoService cardInfoService;
    @Autowired
    private AgentAppService agentAppService;
    @Autowired
    private AgentCardService agentCardService;
    @Autowired
    private RedisUtil redisUtil;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AppInfoParam param){
        AppInfo entity = getEntity(param);
        //生成应用头像
        try {
            entity.setAppHead(CreateNamePicture.generateImg(entity.getAppName()));
        } catch (IOException e) {
            throw new ServiceException(ADD_HEAD_ERROR);
        }
        //生成应用编码
        entity.setAppNum(wordAndNum("",12));
        this.save(entity);
        //生成api接口
        ApiManage temp = new ApiManage();
        temp.setAppId(1L);
        QueryWrapper<ApiManage> queryWrapper = new QueryWrapper<>(temp);
        List<ApiManage> apiManages = apiManageService.list(queryWrapper);
        apiManages.forEach(apiManage -> {
            apiManage.setApiManageId(null);
            apiManage.setAppId(entity.getAppId());
            apiManage.setCallCode(entity.getAppNum());
            apiManage.setCreateTime(new Date());
            apiManage.setCreateUser(LoginContextHolder.getContext().getUserId());
            apiManage.setUpdateTime(null);
            apiManage.setUpdateUser(null);
        });
        apiManageService.saveBatch(apiManages);

        //生成api自定义返回
        ApiResult apiResult = new ApiResult();
        apiResult.setAppId(1L);
        QueryWrapper<ApiResult> apiResultQueryWrapper = new QueryWrapper<>(apiResult);
        List<ApiResult> apiResults = apiResultService.list(apiResultQueryWrapper);
        apiResults.forEach(apiResult1 -> {
            apiResult1.setApiResultId(null);
            apiResult1.setAppId(entity.getAppId());
            apiResult1.setCreateTime(new Date());
            apiResult1.setCreateUser(LoginContextHolder.getContext().getUserId());
            apiResult1.setUpdateTime(null);
            apiResult1.setUpdateUser(null);
        });
        apiResultService.saveBatch(apiResults);
        param.setAppId(entity.getAppId());

        //生成版本号
        AppEditionParam appEditionParam = new AppEditionParam();
        appEditionParam.setAppId(entity.getAppId());
        appEditionParam.setEditionName("1.0");
        appEditionParam.setEditionNum("1.0");
        appEditionService.add(appEditionParam);
    }

    //生成小写字母+数字,
    public String wordAndNum(String cardTypePrefix,Integer cardTypeLength) {
        StringBuilder val = new StringBuilder(cardTypePrefix);
        String base = "abcdefghkmnpqrstuvwxyz123456789";
        Random random = new Random();
        for ( int i = 0; i < cardTypeLength; i++ )
        {
            int number = random.nextInt( base.length() );
            val.append( base.charAt( number ) );
        }
        AppInfo temp = new AppInfo();
        temp.setAppNum(val.toString());
        QueryWrapper<AppInfo> queryWrapper = new QueryWrapper<>(temp);
        //递归算法，如果判断编码重复调用自身方法重新生成
        if (baseMapper.selectOne(queryWrapper)!=null){
            wordAndNum("",12);
        }
        return val.toString();
    }

    @Override
    public void delete(AppInfoParam param){
        //清除应用缓存
        redisUtil.del(RedisType.APP_INFO.getCode() + param.getAppId());

        //删除版本
        AppEdition appEdition = new AppEdition();
        appEdition.setAppId(param.getAppId());
        appEditionService.remove(new QueryWrapper<>(appEdition));
        //删除api接口
        ApiManage apiManage = new ApiManage();
        apiManage.setAppId(param.getAppId());
        apiManageService.remove(new QueryWrapper<>(apiManage));
        List<ApiManage> apiManages = apiManageService.list(new QueryWrapper<ApiManage>().eq("app_id", param.getAppId()));
        apiManages.forEach(apiManage1 -> {
            //清除api接口缓存
            redisUtil.del(RedisType.API_MANAGE.getCode() + apiManage1.getApiCode() + "-" +  apiManage1.getCallCode());
        });
        //删除api返回
        ApiResult apiResult = new ApiResult();
        apiResult.setAppId(param.getAppId());
        apiResultService.remove(new QueryWrapper<>(apiResult));
        List<ApiResult> apiResults = apiResultService.list(new QueryWrapper<ApiResult>().eq("app_id", param.getAppId()));
        apiResults.forEach(apiResult1 -> {
            //清除api接口缓存
            redisUtil.del(RedisType.API_RESULT.getCode() + apiResult1.getAppId() + "-" +  apiResult1.getResultCode());
        });
        //删除卡密
        CardInfo cardInfo = new CardInfo();
        cardInfo.setAppId(param.getAppId());
        cardInfoService.remove(new QueryWrapper<>(cardInfo));
        //删除代理
        AgentApp agentApp = new AgentApp();
        agentApp.setAppId(param.getAppId());
        agentAppService.remove(new QueryWrapper<>(agentApp));
        //代理卡密
        AgentCard agentCard = new AgentCard();
        agentCard.setAppId(param.getAppId());
        agentCardService.remove(new QueryWrapper<>(agentCard));

        this.removeById(getKey(param));
    }

    @Override
    public void update(AppInfoParam param){
        AppInfo oldEntity = getOldEntity(param);
        AppInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        redisUtil.del(RedisType.APP_INFO.getCode() + newEntity.getAppId());
        this.updateById(newEntity);
    }

    @Override
    public AppInfoResult findBySpec(AppInfoParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page,AppInfoParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(AppInfoParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AppInfo> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AppInfoParam param){
        return param.getAppId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AppInfo getOldEntity(AppInfoParam param) {
        return this.getById(getKey(param));
    }

    private AppInfo getEntity(AppInfoParam param) {
        AppInfo entity = new AppInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public List<AppInfoParam> getAppInfoList(Long userId) {
        return baseMapper.findAppInfoList(userId);
    }

    @Override
    public AppInfoApi getAppInfoByRedis(Long appId) {
        AppInfoApi appInfoApi = (AppInfoApi) redisUtil.get(RedisType.APP_INFO.getCode() + appId);
        if (ObjectUtil.isNull(appInfoApi)){
            appInfoApi = baseMapper.findAppInfoApi(appId);
            if (ObjectUtil.isNotNull(appInfoApi)){
                redisUtil.set(RedisType.APP_INFO.getCode() + appId , appInfoApi,604800);
            }else {
                //接口错误
                throw new SystemApiException(-1, "数据错误","",false);
            }
        }
        return appInfoApi;
    }
}
