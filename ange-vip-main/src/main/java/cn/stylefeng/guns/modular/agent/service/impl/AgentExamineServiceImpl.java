package cn.stylefeng.guns.modular.agent.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.mapper.AgentExamineMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentExamineResult;
import  cn.stylefeng.guns.modular.agent.service.AgentExamineService;
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
 * 代理审核表 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-09
 */
@Service
public class AgentExamineServiceImpl extends ServiceImpl<AgentExamineMapper, AgentExamine> implements AgentExamineService {

    @Override
    public void add(AgentExamineParam param){
        AgentExamine entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public AgentExamineResult findBySpec(AgentExamineParam param){
        return null;
    }

    @Override
    public List<AgentExamineResult> findListBySpec(AgentExamineParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentExamineParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AgentExamine> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AgentExamineParam param){
        return param.getAgentExamineId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AgentExamine getOldEntity(AgentExamineParam param) {
        return this.getById(getKey(param));
    }

    private AgentExamine getEntity(AgentExamineParam param) {
        AgentExamine entity = new AgentExamine();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
