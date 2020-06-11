package cn.stylefeng.guns.modular.account.service.impl;

import cn.hutool.core.date.DateUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.core.constant.state.CardTimeType;
import cn.stylefeng.guns.core.constant.state.CardTypeRule;
import cn.stylefeng.guns.modular.account.entity.SerialInfo;
import cn.stylefeng.guns.modular.account.mapper.SerialInfoMapper;
import cn.stylefeng.guns.modular.account.model.params.SerialInfoParam;
import cn.stylefeng.guns.modular.account.model.result.SerialInfoResult;
import  cn.stylefeng.guns.modular.account.service.SerialInfoService;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
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
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 注册码表 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-29
 */
@Service
public class SerialInfoServiceImpl extends ServiceImpl<SerialInfoMapper, SerialInfo> implements SerialInfoService {

    public final CodeCardTypeService codeCardTypeService;
    public final AppInfoService appInfoService;

    public SerialInfoServiceImpl(CodeCardTypeService codeCardTypeService, AppInfoService appInfoService) {
        this.codeCardTypeService = codeCardTypeService;
        this.appInfoService = appInfoService;
    }

    @Override
    public List<String> add(SerialInfoParam param){
        //通用应用
        if (param.getAppId()==0){
            param.setIsUniversal(true);
        }else {
            //获取应用信息
            AppInfo appInfo = appInfoService.getById(param.getAppId());
            appInfo.setCardNum(appInfo.getCardNum()+param.getAddNum());
            appInfoService.updateById(appInfo);
        }
        //当前时间
        Date date = DateUtil.date();
        param.setUserId(LoginContextHolder.getContext().getUserId());
        param.setCreateUser(LoginContextHolder.getContext().getUserId());
        param.setCreateTime(date);
        param.setUserName(LoginContextHolder.getContext().getUserName());
        param.setCardStatus(CardStatus.NOT_ACTIVE.getCode());
        List<SerialInfo> serialInfos = new ArrayList<>();
        List<String> cards = new ArrayList<>();
        //获取卡类信息
        CodeCardType codeCardType = codeCardTypeService.getById(param.getCardTypeId());
        //自定义时间
        if (param.getIsCustomTime()){
            param.setCardTypeId(0L);
            if (codeCardType.getCardTypeRule()== CardTypeRule.CAPITAL_AND_NUM.getCode()||codeCardType.getCardTypeRule()==CardTypeRule.CAPITAL.getCode()){
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
            param.setSerialId(IdWorker.getId());
            param.setSerialCode(card);
            SerialInfo entity = getEntity(param);
            serialInfos.add(entity);
            cards.add(card);
        }
        baseMapper.saveSerialBatch(serialInfos);
        return cards;
    }

    @Override
    public void delete(SerialInfoParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(SerialInfoParam param){
        SerialInfo oldEntity = getOldEntity(param);
        SerialInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public SerialInfoResult findBySpec(SerialInfoParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, SerialInfoParam param){
        return baseMapper.findListBySpec(page, param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(SerialInfoParam param){
        Page pageContext = getPageContext();
        QueryWrapper<SerialInfo> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(SerialInfoParam param){
        return param.getSerialId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private SerialInfo getOldEntity(SerialInfoParam param) {
        return this.getById(getKey(param));
    }

    private SerialInfo getEntity(SerialInfoParam param) {
        SerialInfo entity = new SerialInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
