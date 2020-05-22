package cn.stylefeng.guns.modular.agent.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.mapper.AgentCardMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import  cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.service.SqlService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    private final CodeCardTypeService codeCardTypeService;

    public AgentCardServiceImpl(CodeCardTypeService codeCardTypeService) {
        this.codeCardTypeService = codeCardTypeService;
    }

    @Override
    public void add(AgentCardParam param){
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
    public List<Map<String, Object>> findListBySpec(Page page,AgentCardParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentCardParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AgentCard> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 初始化
     *
     * @param agentCardParam 参数
     */
    @Override
    public void initializeItem(AgentCardParam agentCardParam) {
        QueryWrapper<AgentCard> wrapper = new QueryWrapper<>();
        wrapper = wrapper.eq("agent_app_id", agentCardParam.getAgentAppId());
        wrapper = wrapper.eq("app_id", agentCardParam.getAppId());
        wrapper = wrapper.eq("card_type", agentCardParam.getCardType());
        List<AgentCard> agentCards = baseMapper.selectList(wrapper);
        //已经存在的卡类集合
        List<Long> cardTypeIds = new ArrayList<>();
        agentCards.forEach(agentCard->{
            cardTypeIds.add(agentCard.getCardTypeId());
        });
        List<CodeCardType> codeCardTypes = codeCardTypeService.getCardTypeByAppIdAndCardTypeIds(cardTypeIds,agentCardParam.getCardType(),agentCardParam.getAppId(), LoginContextHolder.getContext().getUserId());

//        baseMapper.selectList()

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
