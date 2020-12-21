package cn.stylefeng.guns.modular.card.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.exception.OperationException;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.state.CardStatus;
import cn.stylefeng.guns.core.constant.state.CardTimeType;
import cn.stylefeng.guns.core.constant.state.CardTypeRule;
import cn.stylefeng.guns.core.constant.type.BuyCardType;
import cn.stylefeng.guns.core.constant.type.CardType;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.model.params.AgentBuyCardParam;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentBuyCardService;
import cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.agent.service.AgentPowerService;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.mapper.CardInfoMapper;
import cn.stylefeng.guns.modular.card.model.params.BatchCardInfoParam;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.card.model.result.CardInfoResult;
import  cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.sys.core.util.CardStringRandom;
import cn.stylefeng.guns.sys.core.util.NumToChUtil;
import cn.stylefeng.guns.sys.core.util.SnowflakeUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.*;

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
    @Autowired
    public AppInfoService appInfoService;
    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private AgentAppService agentAppService;
    @Autowired
    private AgentCardService agentCardService;
    @Autowired
    private AgentBuyCardService agentBuyCardService;
    @Autowired
    private AgentPowerService agentPowerService;


    @Override
    @Transactional
    public  List<String> add(CardInfoParam param){
        //通用应用
        if (param.getAppId()==0){
            param.setIsUniversal(true);
        }else {
            param.setIsUniversal(false);
            //获取应用信息
            AppInfo appInfo = appInfoService.getById(param.getAppId());
            appInfo.setCardNum(appInfo.getCardNum()+param.getAddNum());
            appInfoService.updateById(appInfo);
        }
        //当前时间
        Date date = DateUtil.date();
//        param.setUserId(LoginContextHolder.getContext().getUserId());
//        param.setCreateUser(LoginContextHolder.getContext().getUserId());
        param.setCreateTime(date);
//        param.setUserName(LoginContextHolder.getContext().getUserName());
        param.setCardStatus(CardStatus.NOT_ACTIVE.getCode());
        param.setCardBindType(0);
        param.setCardSignType(1);
        param.setCardOpenRange(0);
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

    /**
     * 外部创建卡密
     *
     * @param param
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @Override
    public void createCard(CardInfo param) {
        baseMapper.insert(param);
    }

    @Override
    public void delete(CardInfoParam param){
        redisUtil.hdel(RedisType.CARD_INFO.getCode() + param.getAppId(),param.getCardCode());
        redisUtil.del(RedisType.TOKEN.getCode() + param.getCardId());
        redisUtil.del(RedisType.DEVICE.getCode() + param.getCardId());
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        List<CardInfo> cardInfos = this.listByIds(idList);
        cardInfos.forEach(cardInfo->{
            redisUtil.hdel(RedisType.CARD_INFO.getCode() + cardInfo.getAppId(),cardInfo.getCardCode());
            redisUtil.hdel(RedisType.TOKEN.getCode() + cardInfo.getCardId());
            redisUtil.hdel(RedisType.DEVICE.getCode() + cardInfo.getCardId());
            if (cardInfo.getAppId()!=0){
                //获取应用信息
                AppInfo appInfo = appInfoService.getById(cardInfo.getAppId());
                appInfo.setCardNum(appInfo.getCardNum()-1);
                appInfoService.updateById(appInfo);
            }
        });
        this.removeByIds(idList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void BatchEdit(BatchCardInfoParam param){
        if (param.getOperateFlag()==0&&StringUtils.isEmpty(param.getIds())){
            throw new ServiceException(UN_SELECT_CARD);
        }
        List<CardInfo> cardInfos = new ArrayList<>();
        if (param.getOperateFlag()==0){
            List<String> idList = Arrays.asList(param.getIds().split(","));
            cardInfos = this.listByIds(idList);
        } else {
            param.setCreateUser(LoginContextHolder.getContext().getUserId());
            cardInfos = baseMapper.selectByBatchCardInfo(param);
        }
        if (CollectionUtils.isEmpty(cardInfos)){
            throw new ServiceException(UN_FIND_CARD);
        }
        editCardInfos(cardInfos, param);
        baseMapper.BachUpdateCardInfo(cardInfos);
//        this.updateById(newEntity);
    }

    private List<CardInfo> editCardInfos(List<CardInfo> cardInfos,BatchCardInfoParam param){
        cardInfos.forEach(cardInfo->{
            redisUtil.hdel(RedisType.CARD_INFO.getCode() + cardInfo.getAppId(),cardInfo.getCardCode());
            switch (param.getEvent()){
                case "prohibition":
                    cardInfo.setProhibitRemark(param.getProhibitRemark());
                    cardInfo.setCardStatus(CardStatus.DISABLED.getCode());
                    return;
                case "unsealing":
                    if (cardInfo.getExpireTime()!=null){
                        //过期
                        if (cardInfo.getExpireTime().before(new Date())){
                            cardInfo.setCardStatus(CardStatus.EXPIRED.getCode());
                        }else {
                            cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                        }
                    }else {
                        cardInfo.setCardStatus(CardStatus.NOT_ACTIVE.getCode());
                    }
                    return;
                case "overtime":
                    //如果未激活
                    if (cardInfo.getCardStatus()==CardStatus.NOT_ACTIVE.getCode()){
                        if (param.getAddTime()==null){
                            cardInfo.setAddDayNum(param.getAddDayNum());
                            cardInfo.setAddHourNum(param.getAddHourNum());
                            cardInfo.setAddMinuteNum(param.getAddMinuteNum());
                            cardInfo.setAddTime(null);
                        }else {
                            cardInfo.setAddDayNum(null);
                            cardInfo.setAddHourNum(null);
                            cardInfo.setAddMinuteNum(null);
                            cardInfo.setAddTime(param.getAddTime());
                        }
                    }else if (cardInfo.getCardStatus()==CardStatus.ACTIVATED.getCode()||cardInfo.getCardStatus()==CardStatus.EXPIRED.getCode()){
                        if (param.getAddTime()==null){
                            cardInfo.setAddDayNum(param.getAddDayNum());
                            cardInfo.setAddHourNum(param.getAddHourNum());
                            cardInfo.setAddMinuteNum(param.getAddMinuteNum());
                            cardInfo.setAddTime(null);
                            Date addExpireTime = CardDateUtil.getAddExpireTime(cardInfo.getExpireTime(),param.getAddDayNum(),param.getAddHourNum(),param.getAddMinuteNum());
                            cardInfo.setExpireTime(addExpireTime);
                            //如果已激活，同时处理后的到期时间小于当前时间，则已过期
                            if (cardInfo.getCardStatus()==CardStatus.ACTIVATED.getCode()&&addExpireTime.before(new Date())){
                                cardInfo.setCardStatus(CardStatus.EXPIRED.getCode());
                                //如果已过期，同时处理后的到期时间大于当前时间，则已激活
                            }else if (cardInfo.getCardStatus()==CardStatus.EXPIRED.getCode()&&!addExpireTime.before(new Date())){
                                cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                            }
                        }else {
                            cardInfo.setAddDayNum(null);
                            cardInfo.setAddHourNum(null);
                            cardInfo.setAddMinuteNum(null);
                            cardInfo.setAddTime(param.getAddTime());
                            cardInfo.setExpireTime(param.getAddTime());
                            //如果已激活，同时处理后的到期时间小于当前时间，则已过期
                            if (cardInfo.getCardStatus()==CardStatus.ACTIVATED.getCode()&&param.getAddTime().before(new Date())){
                                cardInfo.setCardStatus(CardStatus.EXPIRED.getCode());
                                //如果已过期，同时处理后的到期时间大于当前时间，则已激活
                            }else if (cardInfo.getCardStatus()==CardStatus.EXPIRED.getCode()&&!param.getAddTime().before(new Date())){
                                cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                            }
                        }
                    }else if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()){
                        if (param.getAddTime()==null){
                            if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()&&cardInfo.getExpireTime()==null){
                                cardInfo.setAddDayNum(param.getAddDayNum());
                                cardInfo.setAddHourNum(param.getAddHourNum());
                                cardInfo.setAddMinuteNum(param.getAddMinuteNum());
                                cardInfo.setAddTime(null);
                                cardInfo.setExpireTime(null);
                            }else if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()&&cardInfo.getExpireTime()!=null){
                                cardInfo.setAddDayNum(param.getAddDayNum());
                                cardInfo.setAddHourNum(param.getAddHourNum());
                                cardInfo.setAddMinuteNum(param.getAddMinuteNum());
                                cardInfo.setAddTime(null);
                                cardInfo.setExpireTime(CardDateUtil.getAddExpireTime(cardInfo.getExpireTime(),param.getAddDayNum(),param.getAddHourNum(),param.getAddMinuteNum()));
                            }
                        }else {
                            if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()&&cardInfo.getExpireTime()==null){
                                cardInfo.setAddDayNum(null);
                                cardInfo.setAddHourNum(null);
                                cardInfo.setAddMinuteNum(null);
                                cardInfo.setAddTime(param.getAddTime());
                                cardInfo.setExpireTime(null);
                            }else if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()&&cardInfo.getExpireTime()!=null){
                                cardInfo.setAddDayNum(null);
                                cardInfo.setAddHourNum(null);
                                cardInfo.setAddMinuteNum(null);
                                cardInfo.setAddTime(param.getAddTime());
                                cardInfo.setExpireTime(param.getAddTime());
                            }
                        }
                    }
                    return;
                case "untying":
                    return;
                case "editRemark":
                    cardInfo.setCardRemark(param.getCardRemark());
                    return;
                case "batchRemove":
                    return;
            }
        });
        return cardInfos;
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

    /**
     * 通过应用id和卡密查找卡密信息
     * @param appId 应用id
     * @param singleCode 卡密
     * @return 卡密信息
     */
    @Override
    public CardInfoApi getCardInfoApiByAppIdAndCardCode(Long appId, String singleCode) {
        CardInfoApi cardInfoApi;
        //是否存在该hash表
        boolean isHave = redisUtil.hHasKey(RedisType.CARD_INFO.getCode()+ appId,singleCode);
        if (isHave){
            cardInfoApi = (CardInfoApi)redisUtil.hget(RedisType.CARD_INFO.getCode()+ appId,singleCode);
        }else {
            //不存在则数据库查
            cardInfoApi = baseMapper.getCardInfoApiByAppIdAndCardCode(appId,singleCode);
            if (ObjectUtil.isNotNull(cardInfoApi)){
                //如果通用
                if (cardInfoApi.getIsUniversal()){
                    cardInfoApi.setAppId(appId);
                    cardInfoApi.setIsUniversal(false);
                    CardInfo cardInfo = new CardInfo();
                    cardInfo.setCardId(cardInfoApi.getCardId());
                    cardInfo.setAppId(appId);
                    cardInfo.setUniversal(false);
                    //异步调用更新卡密信息
                    asyncService.updateCard(cardInfo);
                }
                redisUtil.hset(RedisType.CARD_INFO.getCode()+ appId,singleCode,cardInfoApi, RedisExpireTime.WEEK.getCode());
            }
        }
        return cardInfoApi;
    }

    @Override
    public void updateCardAndRedis(Long appId, CardInfo cardInfo, String singleCode) {
        redisUtil.hdel(RedisType.CARD_INFO.getCode() + appId,singleCode);
        baseMapper.updateById(cardInfo);
    }

    /**
     * 一级代理新增卡密
     *
     * @param cardInfoParam
     */
    @Override
    @Transactional
    public List<String> actAddItem(CardInfoParam cardInfoParam) {
        cardInfoParam.setUserId(LoginContextHolder.getContext().getUserId());
        cardInfoParam.setCreateUser(cardInfoParam.getDeveloperUserId());
        cardInfoParam.setUserName(LoginContextHolder.getContext().getUserName());
        //先检查有没有新增权限
        AgentPower agentPower = agentPowerService.getOne(new QueryWrapper<AgentPower>()
                .eq("agent_app_id",cardInfoParam.getAgentAppId()));
        if (!agentPower.getCardCreate()){
            throw new OperationException(NOT_CARD_CREATE);
        }
        //再检查余额够不够
        AgentApp agentApp = agentAppService.getById(cardInfoParam.getAgentAppId());
        //代理卡密价格信息
        AgentCard agentCard = agentCardService.getOne(new QueryWrapper<AgentCard>()
                .eq("card_type_id",cardInfoParam.getCardTypeId())
                .eq("agent_app_id",agentApp.getAgentAppId()));
        //卡类信息
        CodeCardType cardType = codeCardTypeService.getById(cardInfoParam.getCardTypeId());
        if (ObjectUtil.isNull(agentApp)){
            throw new OperationException(NOT_AGENT);
        }
        if (agentApp.getStatus()==1){
            throw new OperationException(DISABLE_AGENT);
        }
        //代理余额
        BigDecimal balance = agentApp.getBalance();
        //此次扣除金额
        BigDecimal deductionAmount = agentCard.getAgentPrice().multiply(BigDecimal.valueOf(cardInfoParam.getAddNum()));
        //如果扣除余额大于代理余额
        if (deductionAmount.compareTo(balance)>0){
            //代理余额不足
            throw new OperationException(INSUFFICIENT_BALANCE_AGENT);
        }
        //设置余额
        agentApp.setBalance(balance.subtract(deductionAmount));
        //更新代理余额
        agentAppService.updateById(agentApp);
        //新增购卡记录
        String batchNo = SnowflakeUtil.getInstance().nextIdStr();
        AgentBuyCardParam param = new AgentBuyCardParam();
        param.setAgentAppId(agentApp.getAgentAppId());
        param.setAgentPrice(agentCard.getAgentPrice());
        param.setBuyNum(cardInfoParam.getAddNum());
        param.setBatchNo(batchNo);
        param.setCardTypeId(cardInfoParam.getCardTypeId());
        param.setCardType(CardType.SINGLE_CARD.getCode());
        param.setAmount(deductionAmount);
        //设置明细
        if (agentApp.getAgentGrade()>1){
            //上级代理卡密价格信息
            AgentCard superiorAgentCard = agentCardService.getOne(new QueryWrapper<AgentCard>()
                    .eq("card_type_id",cardInfoParam.getCardTypeId())
                    .eq("agent_app_id",agentApp.getAgentAppIdPid()));
            //查找上级代理应用信息
            AgentApp superiorAgentApp = agentAppService.getById(agentApp.getAgentAppIdPid());
            //返差价
            BigDecimal priceDifference = (agentCard.getAgentPrice().subtract(superiorAgentCard.getAgentPrice())).multiply(BigDecimal.valueOf(cardInfoParam.getAddNum()));
            //设置上级代理余额
            superiorAgentApp.setBalance(superiorAgentApp.getBalance().add(priceDifference));
            agentAppService.updateById(superiorAgentApp);
            param.setBuyCardType(BuyCardType.SUBORDINATE_AGENT_CARD_REBATE.getCode());
            param.setDetailed(StrUtil.format(BuyCardType.SUBORDINATE_AGENT_CARD_REBATE.getDetailed(),
                    NumToChUtil.to(agentApp.getAgentGrade()),
                    cardInfoParam.getAddNum(),
                    cardType.getCardTypeName(),
                    agentCard.getAgentPrice(),
                    superiorAgentCard.getAgentPrice(),
                    priceDifference));
        }else {
            param.setBuyCardType(BuyCardType.PRIMARY_AGENT_CARD_PURCHASING.getCode());
            param.setDetailed(StrUtil.format(BuyCardType.PRIMARY_AGENT_CARD_PURCHASING.getDetailed(),
                    cardInfoParam.getAddNum(),
                    cardType.getCardTypeName(),
                    agentCard.getAgentPrice(),
                    deductionAmount));
        }
        param.setCreateTime(new Date());
        param.setCreateUser(LoginContextHolder.getContext().getUserId());
        agentBuyCardService.add(param);
        //生成批次号
        cardInfoParam.setBatchNo(batchNo);
        List<String> cardInfos = this.add(cardInfoParam);
        return cardInfos;
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

    private CardInfo getEntityByBatch(BatchCardInfoParam param) {
        CardInfo entity = new CardInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
