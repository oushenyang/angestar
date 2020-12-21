package cn.stylefeng.guns.modular.agent.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountCardType;
import cn.stylefeng.guns.modular.account.service.AccountCardTypeService;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.mapper.AgentCardMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import  cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 代理卡密 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-22
 */
@Service
public class AgentCardServiceImpl extends ServiceImpl<AgentCardMapper, AgentCard> implements AgentCardService {

    @Autowired
    private CodeCardTypeService codeCardTypeService;

    @Autowired
    private AccountCardTypeService accountCardTypeService;

    @Override
    public void add(AgentCardParam param){
        AgentCardParam param1 = new AgentCardParam();

        AgentCard entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AgentCardParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AgentCardParam param){
        AgentCard oldEntity = getOldEntity(param);
        AgentCard newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AgentCardResult findBySpec(AgentCardParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findCodeCardTypeListBySpec(Page page,AgentCardParam param){
        return baseMapper.findCodeCardTypeListBySpec(page,param);
    }

    @Override
    public List<Map<String, Object>> findAccountCardTypeListBySpec(Page page,AgentCardParam param){
        return baseMapper.findAccountCardTypeListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentCardParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AgentCard> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 初始化单码卡类
     *
     * @param agentCardParam 参数
     */
    @Override
    public void initializeItemCodeCard(AgentCardParam agentCardParam) {
        QueryWrapper<AgentCard> wrapper = new QueryWrapper<>();
        wrapper = wrapper.eq("agent_app_id", agentCardParam.getAgentAppId());
        wrapper = wrapper.eq("app_id", agentCardParam.getAppId());
        wrapper = wrapper.eq("card_type", agentCardParam.getCardType());
        List<AgentCard> agentCards = baseMapper.selectList(wrapper);
        //已经存在的卡类集合
        List<Long> cardTypeIds = new ArrayList<>();
        List<AgentCard> agentCardsList = new ArrayList<>();
        agentCards.forEach(agentCard->{
            cardTypeIds.add(agentCard.getCardTypeId());
        });
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByAppIdAndCardTypeIds(cardTypeIds,agentCardParam.getCardType(), LoginContextHolder.getContext().getUserId());
        if (CollectionUtils.isNotEmpty(codeCardTypes)){
            codeCardTypes.forEach(codeCardType -> {
                AgentCard agentCard = new AgentCard();
                agentCard.setAgentAppId(agentCardParam.getAgentAppId());
                agentCard.setAppId(agentCardParam.getAppId());
                agentCard.setCardTypeId(codeCardType.getCardTypeId());
                agentCard.setCardTypeName(codeCardType.getCardTypeName());
                agentCard.setCardType(agentCardParam.getCardType());
                agentCard.setMarketPrice(codeCardType.getCardTypePrice());
                agentCard.setAgentPrice(codeCardType.getCardTypeAgentPrice());
                agentCard.setCreateUser(LoginContextHolder.getContext().getUserId());
                agentCard.setCreateTime(new Date());
                agentCardsList.add(agentCard);
            });
        }
        this.saveBatch(agentCardsList);
    }

    /**
     * 初始化注册码卡类
     *
     * @param agentCardParam 参数
     */
    @Override
    public void initializeItemAccountCard(AgentCardParam agentCardParam) {
        QueryWrapper<AgentCard> wrapper = new QueryWrapper<>();
        wrapper = wrapper.eq("agent_app_id", agentCardParam.getAgentAppId());
        wrapper = wrapper.eq("app_id", agentCardParam.getAppId());
        wrapper = wrapper.eq("card_type", agentCardParam.getCardType());
        List<AgentCard> agentCards = baseMapper.selectList(wrapper);
        //已经存在的卡类集合
        List<Long> cardTypeIds = new ArrayList<>();
        List<AgentCard> agentCardsList = new ArrayList<>();
        agentCards.forEach(agentCard->{
            cardTypeIds.add(agentCard.getCardTypeId());
        });
        List<AccountCardType> accountCardTypes = accountCardTypeService.getCardTypeByAppIdAndCardTypeIds(cardTypeIds,agentCardParam.getCardType(), LoginContextHolder.getContext().getUserId());
        if (CollectionUtils.isNotEmpty(accountCardTypes)){
            accountCardTypes.forEach(accountCardType -> {
                AgentCard agentCard = new AgentCard();
                agentCard.setAgentAppId(agentCardParam.getAgentAppId());
                agentCard.setAppId(agentCardParam.getAppId());
                agentCard.setCardTypeId(accountCardType.getAccountCardTypeId());
                agentCard.setCardTypeName(accountCardType.getCardTypeName());
                agentCard.setCardType(agentCardParam.getCardType());
                agentCard.setMarketPrice(accountCardType.getCardTypePrice());
                agentCard.setAgentPrice(accountCardType.getCardTypeAgentPrice());
                agentCard.setCreateUser(LoginContextHolder.getContext().getUserId());
                agentCard.setCreateTime(new Date());
                agentCardsList.add(agentCard);
            });
        }
        this.saveBatch(agentCardsList);
    }

    /**
     * 通过应用Id和代理应用id查找卡密类型
     *
     * @param appId      应用id
     * @param agentAppId 代理应用id
     * @return 卡密列表
     */
    @Override
    public List<AgentCardResult> findCardTypeByAppIdAndAgentAppId(Long appId, Long agentAppId,Integer cardType) {
        return baseMapper.selectCardTypeByAppIdAndAgentAppId(appId,agentAppId, cardType);
    }

    /**
     * 排除已经存在的上级卡类获取剩余卡类信息
     *
     * @param agentAppId 当前代理应用id
     * @param cardType   卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     * @return 卡类信息
     */
    @Override
    public List<AgentCardResult> getCardTypeByAgentAppIdAndCardType(Long agentAppId, Integer cardType) {
        List<AgentCardResult> superiorCodeCardTypes = baseMapper.getSuperiorCardTypeByAgentAppIdAndCardType(agentAppId, cardType);
        List<AgentCardResult> codeCardTypes = baseMapper.getCardTypeByAgentAppIdAndCardType(agentAppId, cardType);
        List<AgentCardResult> newCodeCardTypes = new ArrayList<>(superiorCodeCardTypes);
        for (AgentCardResult superiorAgentCardResult : superiorCodeCardTypes){
            for (AgentCardResult agentCardResult : codeCardTypes){
                if (agentCardResult.getCardTypeId().equals(superiorAgentCardResult.getCardTypeId())){
                    newCodeCardTypes.remove(superiorAgentCardResult);
                }
            }
        }
        return newCodeCardTypes;
    }

    private Serializable getKey(AgentCardParam param){
        return param.getAgentCardId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AgentCard getOldEntity(AgentCardParam param) {
        return this.getById(getKey(param));
    }

    private AgentCard getEntity(AgentCardParam param) {
        AgentCard entity = new AgentCard();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
