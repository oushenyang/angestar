package cn.stylefeng.guns.modular.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.Editor;
import cn.hutool.core.util.IdUtil;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.app.model.result.AppInfoApi;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.mapper.TokenMapper;
import cn.stylefeng.guns.modular.device.model.params.TokenParam;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.CardLoginException;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mchange.v1.util.ListUtils;
import io.swagger.models.auth.In;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

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

    private final RedisUtil redisUtil;

    public TokenServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void add(TokenParam param) {
        Token entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TokenParam param) {
        this.removeById(getKey(param));
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
    public boolean createToken(ApiManageApi apiManage, CardInfoApi cardInfoApi, AppInfoApi appInfoApi, String mac, String model,Date expireTime) {
        Map<Object, Object> objects = redisUtil.hmget(RedisType.TOKEN + String.valueOf(cardInfoApi.getCardId()));
        List<Token> tokens = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)) {
            for (Map.Entry<Object, Object> m : objects.entrySet()) {
                tokens.add((Token) m.getValue());
            }
        }
        switch (cardInfoApi.getCardOpenRange()) {
            case 0:
                //默认，从应用设置里取
                if (appInfoApi.getCodeOpenRange() == 0) {
                    if (CollectionUtils.isEmpty(tokens)) {
                        List<Token> tokenList = new ArrayList<>();
                        String tokenStr = insertToken(tokenList, apiManage.getAppId(), cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model);
                        throw new CardLoginException(200, apiManage.getAppId(),tokenStr,expireTime,true);
                    } else {
                        //顶号登录，直接生成新的
                        if (appInfoApi.getAccountSignType() == 1) {
                            String tokenStr = delAndInsertToken(tokens, 1,apiManage.getAppId(), cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model);
                            throw new CardLoginException(200, apiManage.getAppId(),tokenStr,expireTime,true);
                            //非顶号，提示
                        } else {
                            throw new CardLoginException(-207, apiManage.getAppId(),"卡密超过最大登录数,如果确定已经下线,请等60分钟后重试！",new Date(), false);
                        }
                    }
                } else {
                    //开启
                    String tokenStr = updateAndInsertToken(tokens, appInfoApi.getCodeSignType(), appInfoApi.getCodeOpenNum(), apiManage.getAppId(), cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model);
                    throw new CardLoginException(200, apiManage.getAppId(),tokenStr,expireTime,true);
                }
            case 1:
                //关闭，只允许一个
                if (CollectionUtils.isEmpty(tokens)) {
                    List<Token> tokenList = new ArrayList<>();
                    String tokenStr = insertToken(tokenList, apiManage.getAppId(), cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model);
                    throw new CardLoginException(200, apiManage.getAppId(),tokenStr,expireTime,true);
                } else {
                    //顶号登录，直接生成新的
                    if (appInfoApi.getAccountSignType() == 1) {
                        String tokenStr = delAndInsertToken(tokens,1, apiManage.getAppId(), cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model);
                        throw new CardLoginException(200, apiManage.getAppId(),tokenStr,expireTime,true);
                        //非顶号，提示
                    } else {
                        throw new CardLoginException(-207, apiManage.getAppId(),"卡密超过最大登录数,如果确定已经下线,请等60分钟后重试！",new Date(), false);
                    }
                }
            case 2:
                //开启
                String tokenStr = updateAndInsertToken(tokens, cardInfoApi.getCardSignType(), cardInfoApi.getCardOpenNum(), apiManage.getAppId(), cardInfoApi.getCardId(), cardInfoApi.getCardCode(), mac, model);
                throw new CardLoginException(200, apiManage.getAppId(),tokenStr,expireTime,true);
        }
        return true;
    }

    private String insertToken(List<Token> tokenList, Long appId, Long cardId, String cardCode, String mac, String model) {
        String tokenStr = IdUtil.simpleUUID();
        Date date = new Date();
        Token token = new Token();
        token.setAppId(appId);
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
        baseMapper.insert(token);
        tokenList.add(token);
        redisUtil.hset(RedisType.TOKEN + String.valueOf(cardId),tokenStr,token,3600);
        return tokenStr;
    }

    /**
     * 先删除指定数量再新增一个
     * @param delNum 删除的数量
     * @param tokenList token集合
     * @param appId 应用id
     * @param cardId 卡密id
     * @param cardCode 卡密
     * @param mac mac
     * @param model 设备型号
     */
    private String delAndInsertToken(List<Token> tokenList, Integer delNum,Long appId, Long cardId, String cardCode, String mac, String model) {
        //先删除然后新增一个
        for (int i = 0; i < delNum; i++) {
            redisUtil.hdel(RedisType.TOKEN + String.valueOf(cardId), tokenList.get(i).getToken());
            baseMapper.deleteById(tokenList.get(i));
        }
        String tokenStr = IdUtil.simpleUUID();
        Date date = new Date();
        Token token = new Token();
        token.setAppId(appId);
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
        baseMapper.insert(token);
        tokenList.add(token);
        redisUtil.hset(RedisType.TOKEN + String.valueOf(cardId),tokenStr,token,3600);
        return tokenStr;
    }

    private String updateAndInsertToken(List<Token> tokenList, Integer cardSignType, Integer codeOpenNumLong, Long appId, Long cardId, String cardCode, String mac, String model) {
        String tokenStr = null;
        //顶号登录
        if (cardSignType == 1) {
            if (CollectionUtil.isNotEmpty(tokenList)) {
                //如果不等于空且小于多开数量
                if (tokenList.size() < codeOpenNumLong) {
                    tokenStr = insertToken(tokenList, appId, cardId, cardCode, mac, model);
                } else if (tokenList.size() == codeOpenNumLong) {
                    tokenStr = delAndInsertToken(tokenList,1, appId, cardId, cardCode, mac, model);
                } else if (tokenList.size() > codeOpenNumLong) {
                    tokenStr = delAndInsertToken(tokenList, (tokenList.size() - codeOpenNumLong) - 1,appId, cardId, cardCode, mac, model);
                }
            } else {
                tokenStr = insertToken(tokenList, appId, cardId, cardCode, mac, model);
            }
        } else {
            //如果不等于空且小于多开数量
            if (CollectionUtil.isNotEmpty(tokenList)) {
                if (tokenList.size() < codeOpenNumLong) {
                    tokenStr = insertToken(tokenList, appId, cardId, cardCode, mac, model);
                } else if (tokenList.size() >= codeOpenNumLong) {
                    throw new SystemApiException(-207, "卡密超过最大登录数,如果确定已经下线,请等60分钟后重试！", "", false);
                }
            } else {
                tokenStr = insertToken(tokenList, appId, cardId, cardCode, mac, model);
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
