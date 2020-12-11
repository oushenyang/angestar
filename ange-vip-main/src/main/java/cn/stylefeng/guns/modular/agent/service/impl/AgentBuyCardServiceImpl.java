package cn.stylefeng.guns.modular.agent.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentBuyCard;
import cn.stylefeng.guns.modular.agent.mapper.AgentBuyCardMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentBuyCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentBuyCardResult;
import  cn.stylefeng.guns.modular.agent.service.AgentBuyCardService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 代理购卡记录 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-11
 */
@Service
public class AgentBuyCardServiceImpl extends ServiceImpl<AgentBuyCardMapper, AgentBuyCard> implements AgentBuyCardService {

    @Override
    public void add(AgentBuyCardParam param){
        AgentBuyCard entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AgentBuyCardParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AgentBuyCardParam param){
        AgentBuyCard oldEntity = getOldEntity(param);
        AgentBuyCard newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AgentBuyCardResult findBySpec(AgentBuyCardParam param){
        return null;
    }

    @Override
    public List<AgentBuyCardResult> findListBySpec(AgentBuyCardParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentBuyCardParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AgentBuyCard> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AgentBuyCardParam param){
        return param.getAgentBuyCardId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AgentBuyCard getOldEntity(AgentBuyCardParam param) {
        return this.getById(getKey(param));
    }

    private AgentBuyCard getEntity(AgentBuyCardParam param) {
        AgentBuyCard entity = new AgentBuyCard();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
