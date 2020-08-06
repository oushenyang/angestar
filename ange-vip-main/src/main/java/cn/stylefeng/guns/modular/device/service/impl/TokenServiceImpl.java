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
import  cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mchange.v1.util.ListUtils;
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
    public void add(TokenParam param){
        Token entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TokenParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TokenParam param){
        Token oldEntity = getOldEntity(param);
        Token newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TokenResult findBySpec(TokenParam param){
        return null;
    }

    @Override
    public List<TokenResult> findListBySpec(TokenParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TokenParam param){
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
    public boolean createToken(ApiManageApi apiManage, CardInfoApi cardInfoApi, AppInfoApi appInfoApi, String mac, String model) {
        List<Token> tokens = (List<Token>) redisUtil.lGetObject(RedisType.TOKEN + String.valueOf(cardInfoApi.getCardId()),0,-1);
        if (CollectionUtils.isEmpty(tokens)){
            tokens = baseMapper.selectList(new QueryWrapper<Token>().eq("card_id", cardInfoApi.getCardId()));
            if (CollectionUtils.isEmpty(tokens)){
                redisUtil.set(RedisType.TOKEN + String.valueOf(cardInfoApi.getCardId()), tokens);
            }
        }
        switch (cardInfoApi.getCardOpenRange()){
            case 0:
                //关闭，只允许一个
                if (appInfoApi.getCodeOpenRange()==0){
                    if (CollectionUtils.isEmpty(tokens)){
                        List<Token> tokenList = new ArrayList<>();
                        insertToken(tokenList,apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardCode(), mac,model);
                        return true;
                    }else {
                        List<Token> tokenList = new ArrayList<>();
                        //更新
                    }
                }else {
                    //开启
                }
                break;
            case 1:
                //关闭，只允许一个
                if (CollectionUtils.isEmpty(tokens)){
                    List<Token> tokenList = new ArrayList<>();
                    insertToken(tokenList,apiManage.getAppId(),cardInfoApi.getCardId(),cardInfoApi.getCardCode(), mac,model);
                    return true;
                }
                break;
            case 2:
                //开启
                break;
        }
        return true;
    }

    private void insertToken(List<Token> tokenList,Long appId,Long cardId,String cardCode,String mac,String model){
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
        token.setToken(IdUtil.simpleUUID());
        token.setLoginTime(date);
        token.setCheckTime(date);
        token.setCreateTime(date);
        baseMapper.insert(token);
        tokenList.add(token);
        redisUtil.set(RedisType.TOKEN + String.valueOf(cardId), tokenList);
    }

    private Serializable getKey(TokenParam param){
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
