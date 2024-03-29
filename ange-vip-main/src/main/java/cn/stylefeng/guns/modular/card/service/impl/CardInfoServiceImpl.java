package cn.stylefeng.guns.modular.card.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
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
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.card.model.result.*;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.mapper.CardInfoMapper;
import cn.stylefeng.guns.modular.card.model.params.BatchCardInfoParam;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import  cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.UserLogMsg;
import cn.stylefeng.guns.sys.core.constant.state.UserLogType;
import cn.stylefeng.guns.sys.core.exception.CommonException;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.core.log.LogManager;
import cn.stylefeng.guns.sys.core.log.factory.LogTaskFactory;
import cn.stylefeng.guns.sys.core.util.*;
import cn.stylefeng.guns.sys.modular.system.service.ExcelService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

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
    @Autowired
    private ExcelService excelService;
    @Autowired
    private DeviceService deviceService;


    @Override
    @Transactional
    public  List<String> add(CardInfoParam param){
        //通用应用
        if (param.getAppId()==0){
            param.setIsUniversal(true);
        }else {
            param.setIsUniversal(false);
            //获取应用信息
//            AppInfo appInfo = appInfoService.getById(param.getAppId());
//            appInfo.setCardNum(appInfo.getCardNum()+param.getAddNum());
//            appInfoService.updateById(appInfo);
        }
        //当前时间
        Date date = DateUtil.date();
//        param.setUserId(LoginContextHolder.getContext().getUserId());
//        param.setCreateUser(LoginContextHolder.getContext().getUserId());
        param.setCreateTime(date);
//        param.setUserName(LoginContextHolder.getContext().getUserName());
        param.setCardStatus(CardStatus.NOT_ACTIVE.getCode());
        if (StringUtils.isNotEmpty(param.getBatchNo())){
            param.setBatchNo(param.getBatchNo());
        }else {
            param.setBatchNo(SnowflakeUtil.getInstance().nextIdStr());
        }
        param.setCardBindType(0);
        param.setCardSignType(1);
        param.setCardOpenRange(0);
        List<CardInfo> cardInfos = new ArrayList<>();
        List<String> cards = new ArrayList<>();
        //获取卡类信息
        CodeCardType codeCardType = codeCardTypeService.getById(param.getCardTypeId());
        //如果卡类价格为空
        if (null==codeCardType.getCardTypePrice()||null==codeCardType.getCardTypeAgentPrice()){
            throw new OperationException(BizExceptionEnum.CARD_TYPE_PRICE_NULL);
        }
        //获取卡密未激活数量
        List<CardInfo> cardInfoList = baseMapper.selectList(new QueryWrapper<CardInfo>().eq("create_user",param.getCreateUser()).eq("user_id",param.getUserId()).eq("card_status",CardStatus.NOT_ACTIVE.getCode()));
        //如果卡密未激活数量超过2000
        if (cardInfoList.size()+param.getAddNum()>2000){
            throw new OperationException(BizExceptionEnum.CARD_MORE_TAN);
        }
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
    @Transactional(rollbackFor = Exception.class)
    public void delete(CardInfoParam param){
        CardInfo oldEntity = getOldEntity(param);
        redisUtil.del(RedisType.CARD_INFO.getCode() + param.getCardCode());
        this.removeById(getKey(param));
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                oldEntity.getCreateUser(), UserLogMsg.CARD_ONE_DEL.getLogName(), StrUtil.format(UserLogMsg.CARD_ONE_DEL.getMessage(), "【"+oldEntity.getCardCode()+"】")));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        List<CardInfo> cardInfos = this.listByIds(idList);
        cardInfos.forEach(cardInfo->{
            redisUtil.del(RedisType.CARD_INFO.getCode() + cardInfo.getAppId());
//            if (cardInfo.getAppId()!=0){
//                //获取应用信息
//                AppInfo appInfo = appInfoService.getById(cardInfo.getAppId());
//                appInfo.setCardNum(appInfo.getCardNum()-1);
//                appInfoService.updateById(appInfo);
//            }
        });
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                userId, UserLogMsg.CARD_MORE_DEL.getLogName(), StrUtil.format(UserLogMsg.CARD_MORE_DEL.getMessage(), idList.size())));
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
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        String logName = null;
        String message = null;
        if (param.getEvent().equals("prohibition")){
            if (cardInfos.size()==1){
                logName = UserLogMsg.CARD_ONE_PROHIBITION.getLogName();
                message = UserLogMsg.CARD_ONE_PROHIBITION.getMessage();
            }else {
                logName = UserLogMsg.CARD_MORE_PROHIBITION.getLogName();
                message = UserLogMsg.CARD_MORE_PROHIBITION.getMessage();
            }
        }else if (param.getEvent().equals("unsealing")){
            if (cardInfos.size()==1){
                logName = UserLogMsg.CARD_ONE_UNSEALING.getLogName();
                message = UserLogMsg.CARD_ONE_UNSEALING.getMessage();
            }else {
                logName = UserLogMsg.CARD_MORE_UNSEALING.getLogName();
                message = UserLogMsg.CARD_MORE_UNSEALING.getMessage();
            }
        }else if (param.getEvent().equals("overtime")){
            if (cardInfos.size()==1){
                logName = UserLogMsg.CARD_ONE_OVERTIME.getLogName();
                message = UserLogMsg.CARD_ONE_OVERTIME.getMessage();
            }else {
                logName = UserLogMsg.CARD_MORE_OVERTIME.getLogName();
                message = UserLogMsg.CARD_MORE_OVERTIME.getMessage();
            }
        }else if (param.getEvent().equals("untying")){
            if (cardInfos.size()==1){
                logName = UserLogMsg.CARD_ONE_UNTYING.getLogName();
                message = UserLogMsg.CARD_ONE_UNTYING.getMessage();
            }else {
                logName = UserLogMsg.CARD_MORE_UNTYING.getLogName();
                message = UserLogMsg.CARD_MORE_UNTYING.getMessage();
            }
        }else if (param.getEvent().equals("editRemark")){
            if (cardInfos.size()==1){
                logName = UserLogMsg.CARD_ONE_REMARK.getLogName();
                message = UserLogMsg.CARD_ONE_REMARK.getMessage();
            }else {
                logName = UserLogMsg.CARD_MORE_REMARK.getLogName();
                message = UserLogMsg.CARD_MORE_REMARK.getMessage();
            }
        }else if (param.getEvent().equals("data")){
            if (cardInfos.size()==1){
                logName = UserLogMsg.CARD_ONE_DATA.getLogName();
                message = UserLogMsg.CARD_ONE_DATA.getMessage();
            }else {
                logName = UserLogMsg.CARD_MORE_DATA.getLogName();
                message = UserLogMsg.CARD_MORE_DATA.getMessage();
            }
        }
        if (cardInfos.size()==1){
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                    cardInfos.get(0).getCreateUser(), logName, StrUtil.format(message, "【"+cardInfos.get(0).getCardCode()+"】")));
        }else {
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                    cardInfos.get(0).getCreateUser(), logName, StrUtil.format(message, cardInfos.size())));
        }
        baseMapper.BachUpdateCardInfo(cardInfos);
    }

    /**
     * 卡密配置更新
     *
     * @param param
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @Override
    public void editConfigItem(CardInfoParam param) {
        CardInfo oldEntity = getOldEntity(param);
        CardInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
        redisUtil.del(RedisType.CARD_INFO.getCode() + param.getCardCode());
    }

    private List<CardInfo> editCardInfos(List<CardInfo> cardInfos,BatchCardInfoParam param){
        cardInfos.forEach(cardInfo->{
            redisUtil.del(RedisType.CARD_INFO.getCode() + cardInfo.getCardCode());
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
                            cardInfo.setWhetherAddTime(true);
                        }else {
                            cardInfo.setAddDayNum(null);
                            cardInfo.setAddHourNum(null);
                            cardInfo.setAddMinuteNum(null);
                            cardInfo.setWhetherAddTime(true);
                            cardInfo.setAddTime(param.getAddTime());
                        }
                    }else if (cardInfo.getCardStatus()==CardStatus.ACTIVATED.getCode()||cardInfo.getCardStatus()==CardStatus.EXPIRED.getCode()){
                        if (param.getAddTime()==null){
                            cardInfo.setAddDayNum(param.getAddDayNum());
                            cardInfo.setAddHourNum(param.getAddHourNum());
                            cardInfo.setAddMinuteNum(param.getAddMinuteNum());
                            cardInfo.setAddTime(null);
                            cardInfo.setWhetherAddTime(true);
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
                            cardInfo.setWhetherAddTime(true);
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
                                cardInfo.setWhetherAddTime(true);
                            }else if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()&&cardInfo.getExpireTime()!=null){
                                cardInfo.setAddDayNum(param.getAddDayNum());
                                cardInfo.setAddHourNum(param.getAddHourNum());
                                cardInfo.setAddMinuteNum(param.getAddMinuteNum());
                                cardInfo.setAddTime(null);
                                cardInfo.setWhetherAddTime(true);
                                cardInfo.setExpireTime(CardDateUtil.getAddExpireTime(cardInfo.getExpireTime(),param.getAddDayNum(),param.getAddHourNum(),param.getAddMinuteNum()));
                            }
                        }else {
                            if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()&&cardInfo.getExpireTime()==null){
                                cardInfo.setAddDayNum(null);
                                cardInfo.setAddHourNum(null);
                                cardInfo.setAddMinuteNum(null);
                                cardInfo.setAddTime(param.getAddTime());
                                cardInfo.setExpireTime(null);
                                cardInfo.setWhetherAddTime(true);
                            }else if (cardInfo.getCardStatus()==CardStatus.DISABLED.getCode()&&cardInfo.getExpireTime()!=null){
                                cardInfo.setAddDayNum(null);
                                cardInfo.setAddHourNum(null);
                                cardInfo.setAddMinuteNum(null);
                                cardInfo.setAddTime(param.getAddTime());
                                cardInfo.setExpireTime(param.getAddTime());
                                cardInfo.setWhetherAddTime(true);
                            }
                        }
                    }
                    return;
                    //解绑
                case "untying":
                    if (cardInfo.getCardStatus()==CardStatus.ACTIVATED.getCode()){
                        AppInfo appInfo = appInfoService.getById(cardInfo.getAppId());
                        //如果限制解绑次数且该卡密已经超过限制次数
                        if (appInfo.getCodeAfreshBindNum()>1&&cardInfo.getUnbindNum()>=appInfo.getCodeAfreshBindNum()){
                            throw new OperationException(BizExceptionEnum.CARD_MORE_TAN_UNBIND_NUM);
                        }
                        //如果限制解绑次数且该卡密没有超过限制次数
                        if (appInfo.getCodeAfreshBindNum()>1&&cardInfo.getUnbindNum()<appInfo.getCodeAfreshBindNum()){
                            List<Device> deviceList = deviceService.list(new QueryWrapper<Device>().eq("card_or_user_id", cardInfo.getCardId()));
                            if (CollectionUtil.isNotEmpty(deviceList)){
                                //更新卡密重绑次数
                                cardInfo.setUnbindNum(cardInfo.getUnbindNum()+1);
                                //更新卡密扣时
                                cardInfo.setUnbindBuckleTime(cardInfo.getUnbindBuckleTime()+appInfo.getCodeAfreshBindTime());
                                //扣时后的到期时间
                                if (appInfo.getCodeAfreshBindTime()>0){
                                    DateTime expireTime = CardDateUtil.getAddExpireTime(cardInfo.getExpireTime(),null,null,-appInfo.getCodeAfreshBindTime());
                                    cardInfo.setExpireTime(expireTime);
                                    if (expireTime.before(new Date())){
                                        cardInfo.setCardStatus(CardStatus.EXPIRED.getCode());
                                    }
                                }
                            }
                        }
                        deviceService.remove(new QueryWrapper<Device>().eq("card_or_user_id", cardInfo.getCardId()));
                    }
                    return;
                case "editRemark":
                    cardInfo.setCardRemark(param.getCardRemark());
                    return;
                case "data":
                    cardInfo.setCardData(param.getCardData());
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
     * @param timestamp 时间戳
     * @param apiManageApi 接口信息
     * @param cardLogin 是否为卡密登录
     * @return 卡密信息
     */
    @Override
    public CardInfoApi getCardInfoApiByAppIdAndCardCode(Long appId, String singleCode, String timestamp, ApiManageApi apiManageApi,Boolean cardLogin) {
        CardInfoApi cardInfoApi;
        //是否存在该hash表
        boolean isHave = redisUtil.hHasKey(RedisType.CARD_INFO.getCode()+ singleCode,singleCode);
        if (isHave){
            cardInfoApi = (CardInfoApi)redisUtil.hget(RedisType.CARD_INFO.getCode()+ singleCode,singleCode);
            if (!cardInfoApi.getAppId().equals(appId)){
                return null;
            }
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
                cardInfoApi.setRedisTime(new Date());
                redisUtil.hset(RedisType.CARD_INFO.getCode()+ singleCode,singleCode,cardInfoApi, RedisType.CARD_INFO.getTime());
            }
        }
        //如果卡密查不到且不是登录接口则校验
        if (ObjectUtil.isNull(cardInfoApi)&&!cardLogin){
            //卡密不存在
            throw new CommonException(ApiExceptionEnum.CARD_NO.getCode(),timestamp,apiManageApi,false);
        }
        if (ObjectUtil.isNotNull(cardInfoApi)&&!cardLogin&&cardInfoApi.getCardStatus()==2){
            //卡密已过期
            throw new CommonException(ApiExceptionEnum.CARD_EXPIRE.getCode(),timestamp,apiManageApi,false);
        }
        if (ObjectUtil.isNotNull(cardInfoApi)&&!cardLogin&&cardInfoApi.getCardStatus()==3){
            //卡密已被禁用
            throw new CommonException(ApiExceptionEnum.CARD_DISABLED.getCode(),timestamp,apiManageApi,false);
        }
        return cardInfoApi;
    }

    @Override
    public void updateCardAndRedis(Long appId, CardInfo cardInfo, String singleCode) {
        redisUtil.del(RedisType.CARD_INFO.getCode() + singleCode);
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
        if (cardInfoParam.getUserId()==null){
            cardInfoParam.setUserId(LoginContextHolder.getContext().getUserId());
            cardInfoParam.setCreateUser(cardInfoParam.getDeveloperUserId());
            cardInfoParam.setUserName(LoginContextHolder.getContext().getUserName());
        }

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
        //插入日志
//        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),cardInfoParam.getUserId(),
                cardInfoParam.getDeveloperUserId(), UserLogMsg.CARD_ADD.getLogName(), StrUtil.format(UserLogMsg.CARD_ADD.getMessage(), cardInfoParam.getAddNum(),cardType.getCardTypeName())));
        param.setCreateTime(new Date());
        param.setCreateUser(cardInfoParam.getUserId());
        agentBuyCardService.add(param);
        //生成批次号
        cardInfoParam.setBatchNo(batchNo);
        List<String> cardInfos = this.add(cardInfoParam);
        return cardInfos;
    }

    /**
     * 获取该用户所有卡密数量
     *
     * @param userId 用户id
     * @return 数量
     */
    @Override
    public Integer allCardNum(Long userId) {
        return baseMapper.allCardNum(userId);
    }

    /**
     * 获取该用户所有过期卡密数量
     *
     * @param userId 用户id
     * @return 数量
     */
    @Override
    public Integer expireCardNum(Long userId) {
        return baseMapper.expireCardNum(userId);
    }

    /**
     * 获取该用户卡密月统计
     *
     * @param userId 用户id
     * @return 结果
     */
    @Override
    public List<CardMonth> getCardMonth(Long userId,String date,String[] countArr) {
        return baseMapper.getCardMonth(userId, date,countArr);
    }

    /**
     * 获取该用户收入统计
     *
     * @param userId   用户id
     * @param date
     * @param countArr
     * @return 结果
     */
    @Override
    public List<IncomeStatistics> getIncomeStatistics(Long userId, String date, String[] countArr) {
        return baseMapper.getIncomeStatistics(userId, date,countArr);
    }

    /**
     * 删除redis卡密缓存
     *
     * @return 刪除卡密信息
     */
    @Override
    @Transactional
    public List<String> deleteRedisCard() {
        //获取所有应用id
        List<Long> appIdList = appInfoService.getAppIdList();
        List<CardInfoApi> cardInfoApiList = new ArrayList<>();
        List<String> deleteCardCodeList = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(appIdList)){
            appIdList.forEach(appId -> {
                boolean isHave = redisUtil.hasKey(RedisType.CARD_INFO.getCode()+ appId);
                if (isHave){
                    Map<Object, Object> objects = redisUtil.hmget(RedisType.CARD_INFO.getCode() + appId);
                    if (CollectionUtil.isNotEmpty(objects)) {
                        for (Map.Entry<Object, Object> m : objects.entrySet()) {
                            cardInfoApiList.add((CardInfoApi) m.getValue());
                        }
                    }
                }
            });
        }
        if (CollectionUtil.isNotEmpty(cardInfoApiList)){
            for (CardInfoApi cardInfoApi : cardInfoApiList){
                if (DateUtil.between(new Date(),cardInfoApi.getRedisTime(), DateUnit.SECOND)>=604800){
                    deleteCardCodeList.add(cardInfoApi.getCardCode());
                    redisUtil.del(RedisType.CARD_INFO.getCode()+ cardInfoApi.getCardCode());
                }
            }
        }
        return deleteCardCodeList;
    }

    /**
     * 更新卡密登录次数
     *
     * @param cardId 卡密id
     */
    @Override
    public void updateCardLoginNumByCardId(Long cardId) {
        baseMapper.updateCardLoginNumByCardId(cardId);
    }

    /**
     * 导出card
     *
     * @param response
     * @param param
     */
    @Override
    public void exportCard(HttpServletRequest request, HttpServletResponse response, BatchCardInfoParam param) {
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
        List<String> cardList = new ArrayList<>();
        if (param.getExportFlag()==0){
            cardInfos.forEach(cardInfo -> {
                if (param.getExportField()==1){
                    cardList.add(cardInfo.getCardCode());
                }else if (param.getExportField()==2){
                    cardList.add(cardInfo.getCardCode()
                            +param.getSplitSymbol() + StringUtils.trimToEmpty(DateUtil.format(cardInfo.getActiveTime(), "yyyy-MM-dd HH:mm:ss"))
                            +param.getSplitSymbol()+StringUtils.trimToEmpty(DateUtil.format(cardInfo.getExpireTime(), "yyyy-MM-dd HH:mm:ss")));
                }else {
                    cardList.add(cardInfo.getCardCode()
                            +param.getSplitSymbol()+cardInfo.getCardTypeName()
                            +param.getSplitSymbol()+StringUtils.trimToEmpty(DateUtil.format(cardInfo.getActiveTime(), "yyyy-MM-dd HH:mm:ss"))
                            +param.getSplitSymbol()+StringUtils.trimToEmpty(DateUtil.format(cardInfo.getExpireTime(), "yyyy-MM-dd HH:mm:ss")));
                }
            });
            ExportTextUtil.writeToTxt(response,cardList, String.valueOf(DateUtil.date()));
        }else {
            if (param.getExportField()==1){
                List<CardInfoExport1> cardInfoExport1s = new ArrayList<>();
                cardInfos.forEach(cardInfo -> {
                    CardInfoExport1 cardInfoExport1 = new CardInfoExport1();
                    cardInfoExport1.setCardCode(cardInfo.getCardCode());
                    cardInfoExport1s.add(cardInfoExport1);
                });
                excelService.exportExcel(cardInfoExport1s, CardInfoExport1.class,"卡密导出","report-1",response);
            }else if (param.getExportField()==2){
                List<CardInfoExport2> cardInfoExport2s = new ArrayList<>();
                cardInfos.forEach(cardInfo -> {
                    CardInfoExport2 cardInfoExport2 = new CardInfoExport2();
                    cardInfoExport2.setCardCode(cardInfo.getCardCode());
                    cardInfoExport2.setActiveTime(cardInfo.getActiveTime());
                    cardInfoExport2.setExpireTime(cardInfo.getExpireTime());
                    cardInfoExport2s.add(cardInfoExport2);
                });
                excelService.exportExcel(cardInfoExport2s, CardInfoExport2.class,"卡密导出","report-1",response);
            }else {
                List<CardInfoExport3> cardInfoExport3s = new ArrayList<>();
                cardInfos.forEach(cardInfo -> {
                    CardInfoExport3 cardInfoExport3 = new CardInfoExport3();
                    cardInfoExport3.setCardCode(cardInfo.getCardCode());
                    cardInfoExport3.setCardTypeName(cardInfo.getCardTypeName());
                    cardInfoExport3.setActiveTime(cardInfo.getActiveTime());
                    cardInfoExport3.setExpireTime(cardInfo.getExpireTime());
                    cardInfoExport3s.add(cardInfoExport3);
                });
                excelService.exportExcel(cardInfoExport3s, CardInfoExport3.class,"卡密导出","report-1",response);
            }
        }
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                cardInfos.get(0).getCreateUser(), UserLogMsg.CARD_EXPORT.getLogName(), StrUtil.format(UserLogMsg.CARD_EXPORT.getMessage(), cardInfos.size())));
    }

    @Override
    public void yyImportItem(Long appId, String yyCardAddress, String txtFileName) {
        List<String> idList = Arrays.asList(txtFileName.split("\n"));

        for (String singleCode : idList){
            CardInfo cardInfo1 = this.getOne(new QueryWrapper<CardInfo>().eq("app_id",appId).eq("card_code",singleCode));
            if (ObjectUtil.isNull(cardInfo1)){
//                Map<String, String> paramMap = new HashMap<>();
//                paramMap.put("k", singleCode);
//                String result= HttpClientUtil.postParams(yyCardAddress, paramMap);

                HashMap<String, Object> paramMap = new HashMap<>();
                paramMap.put("k", singleCode);
                String result= HttpUtil.post(yyCardAddress, paramMap);
                if(StringUtils.contains(result, "无此卡密")){
                    continue;
                }
                if (StringUtils.isEmpty(result)){
                    continue;
                }
                Document doc = Jsoup.parse(result);
                Elements rows = doc.select("form").get(0).select("span");
                //说明已激活
                if(StringUtils.contains(result, "到期时间")){
                    //卡密类型
                    String cardTypeName = rows.select("span").get(1).text();
                    //激活时间
                    String activeTime = rows.select("span").get(2).text();
                    //到期时间
                    String expireTime = rows.select("span").get(3).text();
                    //先创建卡密
                    CardInfo cardInfo = new CardInfo();
                    cardInfo.setAppId(appId);
                    setCardTypeId(cardTypeName,cardInfo,LoginContextHolder.getContext().getUserId());
                    cardInfo.setCardTypeName(cardTypeName);
                    cardInfo.setUserId(LoginContextHolder.getContext().getUserId());
                    cardInfo.setCreateUser(LoginContextHolder.getContext().getUserId());
                    cardInfo.setUserName("易游");
                    cardInfo.setBatchNo(SnowflakeUtil.getInstance().nextIdStr());
                    cardInfo.setCreateTime(DateUtil.date());
                    cardInfo.setCardCode(singleCode);
                    cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                    cardInfo.setCardBindType(0);
                    cardInfo.setCardSignType(1);
                    cardInfo.setCardOpenRange(0);
                    cardInfo.setActiveTime(DateUtil.parse(activeTime));
                    cardInfo.setExpireTime(DateUtil.parse(expireTime));
                    cardInfo.setCardRemark("手动从易游导入");
                    //已经过期
                    if (DateUtil.parse(expireTime).compareTo(DateUtil.date())<0) {
                        cardInfo.setCardStatus(CardStatus.EXPIRED.getCode());
                        this.save(cardInfo);
                    }
                    this.save(cardInfo);
                    System.out.println("111111");
                }
                //说明未激活
                if(StringUtils.contains(result, "未激活")){
                    //卡密类型
                    String cardTypeName = rows.select("span").get(1).text();
                    Date date = DateUtil.date();
                    //到期时间
                    Date expireTime = CardDateUtil.getExpireTimeByCardTypeName(cardTypeName,date);
                    //先创建卡密
                    CardInfo cardInfo = new CardInfo();
                    cardInfo.setAppId(appId);
                    setCardTypeId(cardTypeName,cardInfo,LoginContextHolder.getContext().getUserId());
                    cardInfo.setCardTypeName(cardTypeName);
                    cardInfo.setUserId(LoginContextHolder.getContext().getUserId());
                    cardInfo.setUserName("易游");
                    cardInfo.setCreateUser(LoginContextHolder.getContext().getUserId());
                    cardInfo.setCreateTime(DateUtil.date());
                    cardInfo.setCardCode(singleCode);
                    cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                    cardInfo.setCardBindType(0);
                    cardInfo.setCardSignType(1);
                    cardInfo.setCardOpenRange(0);
                    cardInfo.setActiveTime(date);
                    cardInfo.setExpireTime(expireTime);
                    cardInfo.setCardRemark("手动从易游导入");
                    this.save(cardInfo);
                    System.out.println("111111");
                }
            }
        }
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                userId, UserLogMsg.CARD_IMPORT.getLogName(), StrUtil.format(UserLogMsg.CARD_IMPORT.getMessage(), idList.size())));
    }

    @Override
    public void customImportItem(CardInfoParam param) {
        Long userId = LoginContextHolder.getContext().getUserId();
        if (param.getOperateFlag()==0){
            CardInfo cardInfo1 = this.getOne(new QueryWrapper<CardInfo>().eq("app_id",param.getAppId()).eq("card_code",param.getCardCode()));
            if (ObjectUtil.isNotNull(cardInfo1)){
                throw new OperationException(BizExceptionEnum.FIND_CARD_EXISTED);
            }
            param.setIsUniversal(false);
            Date date = DateUtil.date();
            param.setUserId(LoginContextHolder.getContext().getUserId());
            param.setCreateUser(LoginContextHolder.getContext().getUserId());
            param.setCreateTime(date);
            param.setUserName(LoginContextHolder.getContext().getUserName());
            CodeCardType codeCardType = codeCardTypeService.getById(param.getCardTypeId());
            if (param.getActiveTime()==null){
                param.setCardStatus(CardStatus.NOT_ACTIVE.getCode());
            }else {
                param.setCardStatus(CardStatus.ACTIVATED.getCode());
                param.setExpireTime(CardDateUtil.getExpireTime(param.getActiveTime(),codeCardType.getCardTimeType(),codeCardType.getCardTypeData()));
            }
            param.setBatchNo(SnowflakeUtil.getInstance().nextIdStr());
            param.setCardBindType(0);
            param.setCardSignType(1);
            param.setCardOpenRange(0);
            param.setCardRemark("自定义单个卡密导入");
            CardInfo entity = getEntity(param);
            baseMapper.insert(entity);
            //插入日志
            LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                    userId, UserLogMsg.CARD_IMPORT.getLogName(), StrUtil.format(UserLogMsg.CARD_IMPORT.getMessage(), 1)));
        }else {
            List<String> cardStrList = Arrays.asList(param.getTxtMoreCard().split("\n"));
            if (CollectionUtil.isNotEmpty(cardStrList)){
                int index = 1;
                for (String cardStr : cardStrList){
                    List<String> str = Arrays.asList(cardStr.split("#"));
                    if (str.size()<4){
                        throw new OperationException(StrUtil.format("第{}行数据格式不正确,请检查！", index));
                    }
                    if (str.get(0).length()<10||str.get(0).length()>40){
                        throw new OperationException(StrUtil.format("第{}行卡密长度不正确,请检查！", index));
                    }
                    CardInfo cardInfo = new CardInfo();
                    setCardTypeId(str.get(1),cardInfo,LoginContextHolder.getContext().getUserId());
                    if (cardInfo.getCardTypeId()==null){
                        throw new OperationException(StrUtil.format("第{}行未找到该卡密类型,请检查！", index));
                    }
                    if ("0".equals(str.get(2))||"0".equals(str.get(3))){
                        cardInfo.setCardStatus(CardStatus.NOT_ACTIVE.getCode());
                    }else {
                        cardInfo.setCardStatus(CardStatus.ACTIVATED.getCode());
                        String[] parsePatterns = {"yyyy-MM-dd HH:mm:ss"};
                        try {
                            cardInfo.setActiveTime(DateUtils.parseDate(str.get(2), parsePatterns));
                            cardInfo.setExpireTime(DateUtils.parseDate(str.get(3), parsePatterns));
                        } catch (ParseException e) {
                            throw new OperationException(StrUtil.format("第{}行时间格式不正确,请检查！", index));
                        }
                    }
                    CardInfo cardInfo1 = this.getOne(new QueryWrapper<CardInfo>().eq("app_id",param.getAppId()).eq("card_code",str.get(0)));
                    if (ObjectUtil.isNotNull(cardInfo1)){
                        continue;
                    }
                    Date date = DateUtil.date();
                    cardInfo.setCardCode(str.get(0));
                    cardInfo.setAppId(param.getAppId());
                    cardInfo.setUserId(LoginContextHolder.getContext().getUserId());
                    cardInfo.setCreateUser(LoginContextHolder.getContext().getUserId());
                    cardInfo.setCreateTime(date);
                    cardInfo.setUserName(LoginContextHolder.getContext().getUserName());
                    cardInfo.setBatchNo(SnowflakeUtil.getInstance().nextIdStr());
                    cardInfo.setCardBindType(0);
                    cardInfo.setCardSignType(1);
                    cardInfo.setCardOpenRange(0);
                    cardInfo.setCardRemark("自定义多个卡密导入");
                    baseMapper.insert(cardInfo);
                    index += 1;
                }
                LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.CARD.getType(),userId,
                        userId, UserLogMsg.CARD_IMPORT.getLogName(), StrUtil.format(UserLogMsg.CARD_IMPORT.getMessage(), cardStrList.size())));
            }
        }
    }

    /**
     * 检查到期卡密并设置过期
     */
    @Override
    public List<CardInfo> checkCardExpireAndSet() {
        List<CardInfo> cardInfos = baseMapper.checkCardExpire();
        cardInfos.forEach(cardInfo -> {
            cardInfo.setCardStatus(CardStatus.EXPIRED.getCode());
            baseMapper.updateById(cardInfo);
            //删除redis缓存
//            redisUtil.del(RedisType.CARD_INFO.getCode() + cardInfo.getCardCode());
        });
        return cardInfos;
    }

    public void setCardTypeId(String cardTypeName, CardInfo cardInfo,Long createUser){
        if ("小时卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.HOUR.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("六时卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.HOUR.getCode(),6);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("天卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.DAY.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("周卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.WEEK.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("半月卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.DAY.getCode(),15);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("月卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.MONTH.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("季卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.MONTH.getCode(),3);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("半年卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.MONTH.getCode(),6);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("年卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.YEAR.getCode(),1);
            cardInfo.setCardTypeId(cardTypeId);
        }else if ("永久卡".equals(cardTypeName)){
            Long cardTypeId = codeCardTypeService.findByCardTimeTypeAndCardTypeData(createUser,CardTimeType.YEAR.getCode(),99);
            cardInfo.setCardTypeId(cardTypeId);
        }
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
