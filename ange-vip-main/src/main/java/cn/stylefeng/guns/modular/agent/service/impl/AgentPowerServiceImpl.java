package cn.stylefeng.guns.modular.agent.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.mapper.AgentPowerMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentPowerParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentPowerResult;
import  cn.stylefeng.guns.modular.agent.service.AgentPowerService;
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
 * 代理权限 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
@Service
public class AgentPowerServiceImpl extends ServiceImpl<AgentPowerMapper, AgentPower> implements AgentPowerService {

    @Override
    public void add(AgentPowerParam param){
        AgentPower entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AgentPowerParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AgentPowerParam param){
        AgentPower oldEntity = getOldEntity(param);
        AgentPower newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AgentPowerResult findBySpec(AgentPowerParam param){
        return null;
    }

    @Override
    public List<AgentPowerResult> findListBySpec(AgentPowerParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentPowerParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AgentPower> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AgentPowerParam param){
        return param.getAgentPowerId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AgentPower getOldEntity(AgentPowerParam param) {
        return this.getById(getKey(param));
    }

    private AgentPower getEntity(AgentPowerParam param) {
        AgentPower entity = new AgentPower();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
