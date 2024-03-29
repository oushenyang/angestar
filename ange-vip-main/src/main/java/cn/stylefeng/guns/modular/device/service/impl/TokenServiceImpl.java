package cn.stylefeng.guns.modular.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.mapper.TokenMapper;
import cn.stylefeng.guns.modular.device.model.params.TokenParam;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.apiResult.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.CardLoginException;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * <p>
 * 登录信息 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AsyncService asyncService;


    @Override
    public void add(TokenParam param) {
        Token entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TokenParam param) {
        this.removeById(getKey(param));
    }

    /**
     * 删除
     *
     * @param cardId
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @Override
    public void deleteByCardId(Long cardId) {
        baseMapper.deleteByCardId(cardId);
    }

    @Override
    public void update(TokenParam param) {
        Token oldEntity = getOldEntity(param);
        Token newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TokenResult findBySpec(TokenParam param) {
        return null;
    }

    @Override
    public List<TokenResult> findListBySpec(TokenParam param) {
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TokenParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<Token> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 获取上次登录token
     *
     * @param cardOrUserId
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    @Override
    public TokenResult getLastTokenByCardOrUserId(Long cardOrUserId) {
        return baseMapper.getLastTokenByCardOrUserId(cardOrUserId);
    }

    /**
     * 创建token
     *
     * @param apiManage   接口信息
     * @param cardInfoApi 卡密信息
     * @param appInfoApi  应用信息
     * @param mac         mac地址
     * @param model       设备型号
     * @return 接口
     */
    @Override
    public boolean createToken(ApiManageApi apiManage, CardInfoApi cardInfoApi, AppInfoApi appInfoApi, String mac, String model, String holdCheck, Date expireTime) {
        Object object = redisUtil.hget(RedisType.CARD_INFO.getCode() + cardInfoApi.getCardCode(),RedisType.TOKEN.getCode());
        List<Token> tokens = new ArrayList<>();
        if (ObjectUtil.isNotNull(object)) {
            List<Token> tokenList = JSON.parseArray(object.toString(),Token.class);
            //去除过期token
            if (CollectionUtil.isNotEmpty(tokenList)){
                tokenList.forEach(token -> {
                    //如果未到期
                    if (DateUtil.offsetMinute(token.getCreateTime(), CardDateUtil.getClearSpace(appInfoApi.getCodeClearSpace())).compareTo(DateUtil.date())>=0) {
                        tokens.add(token);
                    }
                });
            }

        }
        switch (cardInfoApi.getCardOpenRange()) {
            case 0:
//                //默认，从应用设置里取
//                if (appInfoApi.getCodeOpenRange() == 0) {
//                    if (CollectionUtils.isEmpty(tokens)) {
//                        List<Token> tokenList = new ArrayList<>();
//                        String tokenStr = insertToken(tokenList, appInfoApi, cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model,expireTime);
//                        throw new CardLoginException(2000, apiManage.getAppId(),tokenStr,expireTime,holdCheck,true);
//                    } else {
//                        //顶号登录，直接生成新的
//                        if (appInfoApi.getCodeSignType() == 1) {
//                            String tokenStr = delAndInsertToken(tokens, 1,appInfoApi, cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model,expireTime);
//                            throw new CardLoginException(2000, apiManage.getAppId(),tokenStr,expireTime,holdCheck,true);
//                            //非顶号，提示
//                        } else {
//                            throw new CardLoginException(2008, apiManage.getAppId(),"卡密超过最大登录数,如果确定已经下线,请等60分钟后重试！",new Date(), holdCheck,false);
//                        }
//                    }
//                } else {
                    //开启
                    String tokenStr1 = updateAndInsertToken(apiManage,tokens, appInfoApi.getCodeSignType(), appInfoApi.getCodeOpenNum(), appInfoApi, cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model,expireTime);
                    throw new CardLoginException(2000, apiManage.getAppId(),tokenStr1,expireTime,holdCheck,apiManage,true);
//                }
            case 1:
                //关闭，只允许一个
                if (CollectionUtils.isEmpty(tokens)) {
                    List<Token> tokenList = new ArrayList<>();
                    String tokenStr = insertToken(tokenList, appInfoApi, cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model,expireTime);
                    throw new CardLoginException(2000, apiManage.getAppId(),tokenStr,expireTime,holdCheck,apiManage,true);
                } else {
                    //顶号登录，直接生成新的
                    if (appInfoApi.getCodeSignType() == 1) {
                        String tokenStr = delAndInsertToken(tokens,1, 1,appInfoApi, cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model,expireTime);
                        throw new CardLoginException(2000, apiManage.getAppId(),tokenStr,expireTime,holdCheck,apiManage,true);
                        //非顶号，提示
                    } else {
                        throw new CardLoginException(2008, apiManage.getAppId(),"卡密超过最大登录数,如果确定已经下线,请等60分钟后重试！",new Date(), holdCheck,apiManage,false);
                    }
                }
            case 2:
                //开启
                String tokenStr = updateAndInsertToken(apiManage,tokens, cardInfoApi.getCardSignType(), cardInfoApi.getCardOpenNum(), appInfoApi, cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model,expireTime);
                throw new CardLoginException(2000, apiManage.getAppId(),tokenStr,expireTime,holdCheck,apiManage,true);
        }
        return true;
    }

    /**
     * 获取今日在线人数
     *
     * @param userId 用户id
     * @return
     */
    @Override
    public Integer onlineNum(Long userId) {
        return baseMapper.onlineNum(userId);
    }

    /**
     * 获取指定限制分钟内和版本号的在线人数
     *
     * @param appId   应用id
     * @param limit   指定分钟内
     * @param edition 版本号
     * @return 在线人数
     */
    @Override
    public Integer getOnlineNumByRedis(Long appId, String limit, String edition) {
        Integer onlineNum = (Integer) redisUtil.get(RedisType.ONLINE_NUM.getCode() + appId + limit);
        if (onlineNum==null){
            onlineNum = baseMapper.getOnlineNum(appId, Integer.valueOf(limit),edition);
            redisUtil.set(RedisType.ONLINE_NUM.getCode() + appId + limit, onlineNum, RedisType.ONLINE_NUM.getTime());
        }
        return onlineNum;
    }

    /**
     * 获取卡密的所有token信息
     *
     * @param card 卡密
     * @return token集合
     */
    @Override
    public List<Token> getTokenListByCardAndRedis(String card) {
        Object object = redisUtil.hget(RedisType.CARD_INFO.getCode() + card,RedisType.TOKEN.getCode());
        List<Token> tokens = new ArrayList<>();
        if (ObjectUtil.isNotNull(object)) {
            List<Token> tokenList = JSON.parseArray(object.toString(),Token.class);
            tokens.addAll(tokenList);
        }else {
            tokens = baseMapper.selectList(new QueryWrapper<Token>().eq("card_or_user_code", card));
        }
        return tokens;
    }

    private String insertToken(List<Token> tokenList, AppInfoApi appInfoApi, Long cardId, String cardCode, String mac, String model,Date expireTime) {
        String tokenStr = IdUtil.simpleUUID();
        Date date = new Date();
        Token token = new Token();
        token.setTokenId(IdWorker.getId());
        token.setAppId(appInfoApi.getAppId());
        token.setCardOrUserId(cardId);
        token.setCardOrUserCode(cardCode);
        token.setCardType(1);
        token.setMac(mac);
        token.setModel(model);
        token.setIp(getIp());
        token.setLoginNum(1);
        token.setToken(tokenStr);
        token.setLoginTime(date);
        token.setCheckTime(date);
        token.setCreateTime(date);
        token.setCreateUser(appInfoApi.getCreateUser());
        token.setUpdateTime(date);
        //异步调用插入
        asyncService.insertTokenToSql(token,appInfoApi);
//        baseMapper.insert(token);
        tokenList.add(token);
        redisUtil.hset(RedisType.CARD_INFO.getCode() + cardCode,RedisType.TOKEN.getCode(),JSON.toJSONString(tokenList), RedisExpireTime.DAY.getCode());
        tokenList.clear();
        return tokenStr;
    }

    /**
     * 先删除指定数量再新增一个
     * @param delNum 删除的数量
     * @param tokenList token集合
     * @param appInfoApi 应用
     * @param cardId 卡密id
     * @param cardCode 卡密
     * @param mac mac
     * @param model 设备型号
     */
    private String delAndInsertToken(List<Token> tokenList, Integer delNum,Integer codeOpenNumLong,AppInfoApi appInfoApi, Long cardId, String cardCode, String mac, String model,Date expireTime) {
//        //先删除然后新增一个
        List<Token> removeTokenList = new ArrayList<>();
        for (int i = 0; i < delNum; i++) {
            if (ObjectUtil.isNotNull(tokenList.get(i))){
                removeTokenList.add(tokenList.get(i));
            }
        }
        tokenList.removeAll(removeTokenList);
        removeTokenList.clear();
        //异步调用先删除
        asyncService.delAndInsertToken(codeOpenNumLong-1,cardId,appInfoApi);
        String tokenStr = IdUtil.simpleUUID();
        Date date = new Date();
        Token token = new Token();
        token.setTokenId(IdWorker.getId());
        token.setAppId(appInfoApi.getAppId());
        token.setCardOrUserId(cardId);
        token.setCardOrUserCode(cardCode);
        token.setCardType(1);
        token.setMac(mac);
        token.setModel(model);
        token.setIp(getIp());
        token.setLoginNum(1);
        token.setToken(tokenStr);
        token.setLoginTime(date);
        token.setCheckTime(date);
        token.setCreateUser(appInfoApi.getCreateUser());
        token.setCreateTime(date);
        token.setUpdateTime(date);
//        baseMapper.insert(token);
        //异步调用插入
        asyncService.insertTokenToSql(token,appInfoApi);
        tokenList.add(token);
        redisUtil.hset(RedisType.CARD_INFO.getCode() + cardCode,RedisType.TOKEN.getCode(),JSON.toJSONString(tokenList), RedisExpireTime.DAY.getCode());
        tokenList.clear();
        return tokenStr;
    }

    private String updateAndInsertToken(ApiManageApi apiManage,List<Token> tokenList, Integer cardSignType, Integer codeOpenNumLong, AppInfoApi appInfoApi, Long cardId, String cardCode, String mac, String model,Date expireTime) {
        String tokenStr = null;
        //顶号登录
        if (cardSignType == 1) {
            if (CollectionUtil.isNotEmpty(tokenList)) {
                //如果不等于空且小于多开数量
                if (tokenList.size() < codeOpenNumLong) {
                    tokenStr = insertToken(tokenList, appInfoApi, cardId, cardCode, mac, model,expireTime);
                } else if (tokenList.size() == codeOpenNumLong) {
                    tokenStr = delAndInsertToken(tokenList,1, codeOpenNumLong,appInfoApi, cardId, cardCode, mac, model,expireTime);
                } else if (tokenList.size() > codeOpenNumLong) {
                    tokenStr = delAndInsertToken(tokenList, (tokenList.size() - codeOpenNumLong)+1,codeOpenNumLong,appInfoApi, cardId, cardCode, mac, model,expireTime);
                }
            } else {
                tokenStr = insertToken(tokenList, appInfoApi, cardId, cardCode, mac, model,expireTime);
            }
        } else {
            //如果不等于空且小于多开数量
            if (CollectionUtil.isNotEmpty(tokenList)) {
                if (tokenList.size() < codeOpenNumLong) {
                    tokenStr = insertToken(tokenList, appInfoApi, cardId, cardCode, mac, model,expireTime);
                } else if (tokenList.size() >= codeOpenNumLong) {
                    throw new CardLoginException(2008,appInfoApi.getAppId(), "卡密超过最大登录数,如果确定已经下线,请等60分钟后重试！", new Date(),"", apiManage,false);
                }
            } else {
                tokenStr = insertToken(tokenList, appInfoApi, cardId, cardCode, mac, model,expireTime);
            }
        }
        return tokenStr;
    }

    private Serializable getKey(TokenParam param) {
        return param.getTokenId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Token getOldEntity(TokenParam param) {
        return this.getById(getKey(param));
    }

    private Token getEntity(TokenParam param) {
        Token entity = new Token();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
