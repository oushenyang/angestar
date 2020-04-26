package cn.stylefeng.guns.modular.card.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.core.constant.state.CardTimeType;
import cn.stylefeng.guns.core.constant.state.CardTypeRule;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.mapper.CardInfoMapper;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.model.result.CardInfoResult;
import  cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.sys.core.util.CardStringRandom;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 卡密表 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
@Service
public class CardInfoServiceImpl extends ServiceImpl<CardInfoMapper, CardInfo> implements CardInfoService {
    @Autowired
    public CodeCardTypeService codeCardTypeService;

    @Override
    public  List<String> add(CardInfoParam param){
        //通用应用
        if (param.getAppId()==0){
            param.setIsUniversal(true);
        }
        //当前时间
        Date date = DateUtil.date();
        param.setUserId(LoginContextHolder.getContext().getUserId());
        param.setCreateUser(LoginContextHolder.getContext().getUserId());
        param.setCreateTime(date);
        param.setUserName(LoginContextHolder.getContext().getUserName());
        param.setCardStatus(CardStatus.NOT_ACTIVE.getCode());
        List<CardInfo> cardInfos = new ArrayList<>();
        List<String> cards = new ArrayList<>();
        //获取卡类信息
        CodeCardType codeCardType = codeCardTypeService.getById(param.getCardTypeId());
        //自定义时间
        if (param.getIsCustomTime()){
            param.setCardTypeId(0L);
            if (codeCardType.getCardTypeRule()==CardTypeRule.CAPITAL_AND_NUM.getCode()||codeCardType.getCardTypeRule()==CardTypeRule.CAPITAL.getCode()){
                codeCardType.setCardTypePrefix("Z");
            }else if (codeCardType.getCardTypeRule()==CardTypeRule.LOWER_CASE_AND_NUM.getCode()||codeCardType.getCardTypeRule()==CardTypeRule.LOWER_CASE.getCode()){
                codeCardType.setCardTypePrefix("z");
            }else {
                codeCardType.setCardTypePrefix("0");
            }
            codeCardType.setCardTimeType(CardTimeType.DAY.getCode());
            codeCardType.setCardTypeData(param.getCustomTimeNum());
        }
        //卡密前缀
        if (StringUtils.isNotEmpty(param.getCardTypePrefix())){
            codeCardType.setCardTypePrefix(param.getCardTypePrefix());
        }
        //如果激活
        if (param.getIsActivation()){
            param.setCardStatus(CardStatus.ACTIVATED.getCode());
            param.setActiveTime(date);
            param.setExpireTime(CardDateUtil.getExpireTime(date,codeCardType.getCardTimeType(),codeCardType.getCardTypeData()));
        }
        for (int i=0; i<param.getAddNum(); i++){
            String card = CardStringRandom.create(codeCardType.getCardTypePrefix(),codeCardType.getCardTypeRule(),codeCardType.getCardTypeLength());
            param.setCardId(IdWorker.getId());
            param.setCardCode(card);
            CardInfo entity = getEntity(param);
            cardInfos.add(entity);
            cards.add(card);
        }
//        this.saveBatch(cardInfos);
        baseMapper.saveCardBatch(cardInfos);
        return cards;
    }

    @Override
    public void delete(CardInfoParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(CardInfoParam param){
        CardInfo oldEntity = getOldEntity(param);
        CardInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CardInfoResult findBySpec(CardInfoParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, CardInfoParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(CardInfoParam param){
        Page pageContext = getPageContext();
        QueryWrapper<CardInfo> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(CardInfoParam param){
        return param.getCardId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private CardInfo getOldEntity(CardInfoParam param) {
        return this.getById(getKey(param));
    }

    private CardInfo getEntity(CardInfoParam param) {
        CardInfo entity = new CardInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
