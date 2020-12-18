package cn.stylefeng.guns.modular.agent.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.mapper.AgentPowerMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentPowerParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentPowerResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import  cn.stylefeng.guns.modular.agent.service.AgentPowerService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
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
//    @Autowired
//    AgentAppService agentAppService;

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
    @Transactional
    public void update(AgentPowerParam param){
        AgentPower oldEntity = getOldEntity(param);
        AgentPower newEntity = getEntity(param);
        //查找下级代理的所有权限信息
        List<AgentPowerResult> agentPowerResults = this.getSubordinateAgentPowerByAgentAppId(param.getAgentAppId());
        if (CollectionUtil.isNotEmpty(agentPowerResults)){
            agentPowerResults.forEach(agentPowerResult -> {
                if (!newEntity.getCardCreate()){
                    agentPowerResult.setCardCreate(false);
                }
                if (!newEntity.getCardDisable()){
                    agentPowerResult.setCardDisable(false);
                }
                if (!newEntity.getCardLook()){
                    agentPowerResult.setCardLook(false);
                }
                if (!newEntity.getCardData()){
                    agentPowerResult.setCardData(false);
                }
                if (!newEntity.getCardTime()){
                    agentPowerResult.setCardTime(false);
                }
                if (!newEntity.getCardDelete()){
                    agentPowerResult.setCardDelete(false);
                }
                if (!newEntity.getAccountCreate()){
                    agentPowerResult.setAccountCreate(false);
                }
                if (!newEntity.getAccountDisable()){
                    agentPowerResult.setAccountDisable(false);
                }
                if (!newEntity.getAccountEditPassword()){
                    agentPowerResult.setAccountEditPassword(false);
                }
                if (!newEntity.getAccountDelete()){
                    agentPowerResult.setAccountDelete(false);
                }
                if (!newEntity.getAccountTime()){
                    agentPowerResult.setAccountTime(false);
                }
                if (!newEntity.getAccountData()){
                    agentPowerResult.setAccountData(false);
                }
                if (!newEntity.getAccountQuery()){
                    agentPowerResult.setAccountQuery(false);
                }
            });
        }
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
        //更新下级代理的所有权限信息
        this.updateBatchById(getEntityList(agentPowerResults));
    }

    @Override
    public AgentPowerResult findBySpec(AgentPowerParam param){
        return null;
    }

    /**
     * 查找下级代理的所有权限信息
     *
     * @param agentAppId 当前登陆代理的下级代理应用id
     * @return
     */
    @Override
    public List<AgentPowerResult> getSubordinateAgentPowerByAgentAppId(Long agentAppId) {
        return baseMapper.getSubordinateAgentPowerByAgentAppId(agentAppId);
    }

    /**
     * 获取当前登陆代理的下级代理显示权限
     *
     * @param agentAppId 当前登陆代理的下级代理应用id
     * @return
     */
    @Override
    public AgentPowerResult getSubordinateAgentPowerShow(Long agentAppId) {
        return baseMapper.getSubordinateAgentPowerShow(agentAppId);
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

    public List<AgentPower> getEntityList(List<AgentPowerResult> params) {
        List<AgentPower> agentPowers = new ArrayList<>();
        params.forEach(agentPowerResult -> {
            AgentPower entity = new AgentPower();
            ToolUtil.copyProperties(agentPowerResult, entity);
            agentPowers.add(entity);
        });
        return agentPowers;
    }

}
