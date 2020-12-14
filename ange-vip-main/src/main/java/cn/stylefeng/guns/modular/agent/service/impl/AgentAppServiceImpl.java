package cn.stylefeng.guns.modular.agent.service.impl;

import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.exception.OperationException;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.mapper.AgentAppMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppRechargeParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentAppResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentPowerService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.UN_FIND_CARD;
import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.USER_NOT_EXISTED;

/**
 * <p>
 * 代理软件表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
@Service
public class AgentAppServiceImpl extends ServiceImpl<AgentAppMapper, AgentApp> implements AgentAppService {
    private final UserService userService;
    private final AgentPowerService agentPowerService;

    public AgentAppServiceImpl(UserService userService,AgentPowerService agentPowerService) {
        this.userService = userService;
        this.agentPowerService = agentPowerService;
    }

    @Override
    public void add(AgentAppParam param) {
        param.setDeveloperUserId(LoginContextHolder.getContext().getUserId());
        param.setPid(LoginContextHolder.getContext().getUserId());
        User user = userService.getByAccount(param.getAgentUserAccount());
        if (user == null) {
            throw new OperationException(USER_NOT_EXISTED);
        }
        param.setAgentUserId(user.getUserId());
        param.setAgentUserName(user.getName());
        param.setAgentUserAccount(user.getAccount());
        param.setAgentGrade(1);
        param.setPids("[" + LoginContextHolder.getContext().getUserId() + "]," + "[" + user.getUserId() + "],");
        AgentApp entity = getEntity(param);
        this.save(entity);
        AgentPower agentPower = new AgentPower();
        agentPower.setAgentAppId(entity.getAgentAppId());
        agentPower.setCreateTime(new Date());
        agentPower.setCreateUser(LoginContextHolder.getContext().getUserId());
        agentPowerService.save(agentPower);
    }

    /**
     * 新增一级代理
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @Override
    public void addAgent(AgentExamine entity) {
        AgentApp agentApp = new AgentApp();
        agentApp.setAppId(entity.getAppId());
        agentApp.setDeveloperUserId(entity.getDeveloperUserId());
        agentApp.setPid(entity.getDeveloperUserId());
        agentApp.setAgentUserId(entity.getAgentUserId());
        agentApp.setAgentUserName(entity.getAgentUserName());
        agentApp.setAgentUserAccount(entity.getAgentUserAccount());
        agentApp.setAgentGrade(1);
        agentApp.setBalance(new BigDecimal(BigInteger.ZERO));
        agentApp.setPids("[" + entity.getDeveloperUserId() + "]," + "[" + entity.getAgentUserId() + "],");
        this.save(agentApp);
        AgentPower agentPower = new AgentPower();
        agentPower.setAgentAppId(agentApp.getAgentAppId());
        agentPower.setAppId(agentApp.getAppId());
        agentPower.setCreateTime(new Date());
        agentPower.setCreateUser(LoginContextHolder.getContext().getUserId());
        agentPowerService.save(agentPower);
    }

    @Override
    public void delete(AgentAppParam param) {
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids) {
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AgentAppParam param) {
        AgentApp oldEntity = getOldEntity(param);
        AgentApp newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public void recharge(AgentAppRechargeParam agentAppRechargeParam) {
        AgentApp agentApp = new AgentApp();
        agentApp.setAgentAppId(agentAppRechargeParam.getAgentAppId());
        //充值
        if (agentAppRechargeParam.getRechargeType() == 0) {
            agentApp.setBalance(agentAppRechargeParam.getBalance().add(agentAppRechargeParam.getRechargeBalance()));
        }else {
            agentApp.setBalance(agentAppRechargeParam.getBalance().subtract(agentAppRechargeParam.getRechargeBalance()));
        }
        this.updateById(agentApp);
    }

    @Override
    public AgentAppResult findBySpec(AgentAppParam param) {
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, AgentAppParam param) {
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentAppParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<AgentApp> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AgentAppParam param) {
        return param.getAgentAppId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AgentApp getOldEntity(AgentAppParam param) {
        return this.getById(getKey(param));
    }

    private AgentApp getEntity(AgentAppParam param) {
        AgentApp entity = new AgentApp();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
