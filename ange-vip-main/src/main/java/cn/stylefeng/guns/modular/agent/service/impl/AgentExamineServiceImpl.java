package cn.stylefeng.guns.modular.agent.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.exception.OperationException;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.state.ExamineStatus;
import cn.stylefeng.guns.core.constant.type.ApplyType;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.mapper.AgentExamineMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentExamineResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import  cn.stylefeng.guns.modular.agent.service.AgentExamineService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.rmi.ServerException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.*;

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

    private final UserService userService;
    private final AgentAppService agentAppService;

    public AgentExamineServiceImpl(UserService userService, AgentAppService agentAppService) {
        this.userService = userService;
        this.agentAppService = agentAppService;
    }

    /**
     * 开发者端新增代理审核记录
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    @Override
    @Transactional
    public void developerAddItem(AgentExamineParam param){
        User user = userService.getAgentByAccount(param.getAgentUserAccount());
        if (user == null) {
            throw new OperationException(USER_NOT_EXISTED);
        }
        AgentExamine agentExamine = baseMapper.selectOne(new QueryWrapper<AgentExamine>()
                .eq("app_id",param.getAppId())
                .eq("developer_user_id",LoginContextHolder.getContext().getUserId())
                .eq("agent_user_id",user.getUserId())
//                .eq("agent_grade",1)
                .eq("examine_status",ExamineStatus.WAITING_AGENT_REVIEW.getCode()));
        if (ObjectUtil.isNotNull(agentExamine)){
            if (agentExamine.getPid().equals(LoginContextHolder.getContext().getUserId())){
                throw new OperationException(INVITED_AGENT);
            }else {
                throw new OperationException(INVITED_OTHER_AGENT);
            }

        }
        AgentApp agentApp = agentAppService.getOne(new QueryWrapper<AgentApp>()
                .eq("app_id",param.getAppId())
                .eq("agent_user_id",user.getUserId()));
        if (ObjectUtil.isNotNull(agentApp)){
            if (agentApp.getPid().equals(LoginContextHolder.getContext().getUserId())){
                //该用户已经是您的代理，请勿重复操作
                throw new OperationException(ALREADY_AGENT);
            }else {
                //该用户已经代理过该软件，请勿重复操作
                throw new OperationException(ALREADY_AGENT_APP);
            }
        }
        param.setDeveloperUserId(LoginContextHolder.getContext().getUserId());
        param.setAgentUserId(user.getUserId());
        param.setAgentGrade(1);
        param.setPid(LoginContextHolder.getContext().getUserId());
        param.setPids("[" + LoginContextHolder.getContext().getUserId() + "]," + "[" + user.getUserId() + "],");
        //申请类型（邀请代理）
        param.setApplyType(ApplyType.INVITE_AGENT.getCode());
        //审核状态（等待代理审核）
        param.setExamineStatus(ExamineStatus.WAITING_AGENT_REVIEW.getCode());
        AgentExamine entity = getEntity(param);
        this.save(entity);
    }

    /**
     * 代理端端新增下级代理接口
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    @Override
    @Transactional
    public void agentAddItem(AgentExamineParam param){
        User user = userService.getAgentByAccount(param.getAgentUserAccount());
        if (user == null) {
            throw new OperationException(USER_NOT_EXISTED);
        }
        //不能添加自己为代理
        if (user.getUserId().equals(LoginContextHolder.getContext().getUserId())){
            throw new OperationException(NO_SELF_AGENT);
        }
        AgentExamine agentExamine = baseMapper.selectOne(new QueryWrapper<AgentExamine>()
                .eq("app_id",param.getAppId())
                .eq("developer_user_id",param.getDeveloperUserId())
                .eq("agent_user_id",user.getUserId())
//                .eq("pid",LoginContextHolder.getContext().getUserId())
                .eq("examine_status",ExamineStatus.WAITING_AGENT_REVIEW.getCode()));
        if (ObjectUtil.isNotNull(agentExamine)){
            if (agentExamine.getPid().equals(LoginContextHolder.getContext().getUserId())){
                throw new OperationException(INVITED_AGENT);
            }else {
                throw new OperationException(INVITED_OTHER_AGENT);
            }

        }
        AgentApp agentApp = agentAppService.getOne(new QueryWrapper<AgentApp>()
                .eq("app_id",param.getAppId())
                .eq("agent_user_id",user.getUserId()));
        if (ObjectUtil.isNotNull(agentApp)){
            if (agentApp.getPid().equals(LoginContextHolder.getContext().getUserId())){
                //该用户已经是您的代理，请勿重复操作
                throw new OperationException(ALREADY_AGENT);
            }else {
                //该用户已经代理过该软件，请勿重复操作
                throw new OperationException(ALREADY_AGENT_APP);
            }
        }
        //获取当前代理的代理信息
        AgentApp agentApp1 = agentAppService.getById(param.getAgentAppId());
        param.setDeveloperUserId(param.getDeveloperUserId());
        param.setAgentUserId(user.getUserId());
        param.setAgentGrade(agentApp1.getAgentGrade()+1);
        param.setPid(LoginContextHolder.getContext().getUserId());
        param.setPids(agentApp1.getPids()+ "[" + user.getUserId() + "],");
        //申请类型（邀请代理）
        param.setApplyType(ApplyType.INVITE_AGENT.getCode());
        //审核状态（等待代理审核）
        param.setExamineStatus(ExamineStatus.WAITING_AGENT_REVIEW.getCode());
        AgentExamine entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public AgentExamineResult findBySpec(AgentExamineParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page,AgentExamineParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentExamineParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AgentExamine> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 代理同意代理
     *
     * @param agentExamineParam
     */
    @Override
    @Transactional
    public void agree(AgentExamineParam agentExamineParam) {
        AgentExamine entity = this.getById(agentExamineParam.getAgentExamineId());
        entity.setExamineStatus(ExamineStatus.AGENT_SUCCESS.getCode());
        entity.setExamineTime(new Date());
        entity.setUpdateTime(new Date());
        this.updateById(entity);
        if (entity.getAgentGrade()>1){
            AgentApp agentApp = agentAppService.getOne(new QueryWrapper<AgentApp>()
                    .eq("app_id",entity.getAppId())
                    .eq("agent_grade",entity.getAgentGrade())
                    .eq("pid",entity.getPid())
                    .eq("developer_user_id",entity.getDeveloperUserId())
                    .eq("agent_user_id",entity.getAgentUserId()));
            if (ObjectUtil.isNotNull(agentApp)){
                throw new OperationException(AGREED_AGENT);
            }
            agentAppService.addSubordinateAgent(entity);
        }else {
            AgentApp agentApp = agentAppService.getOne(new QueryWrapper<AgentApp>()
                    .eq("app_id",entity.getAppId())
                    .eq("agent_grade",1)
                    .eq("developer_user_id",entity.getDeveloperUserId())
                    .eq("agent_user_id",entity.getAgentUserId()));
            if (ObjectUtil.isNotNull(agentApp)){
                throw new OperationException(AGREED_AGENT);
            }
            agentAppService.addAgent(entity);
        }

    }

    /**
     * 代理拒绝代理
     *
     * @param agentExamineParam
     */
    @Override
    public void actRefuse(AgentExamineParam agentExamineParam) {
        agentExamineParam.setExamineStatus(ExamineStatus.AGENT_REFUSE.getCode());
        agentExamineParam.setExamineTime(new Date());
        agentExamineParam.setUpdateTime(new Date());
        AgentExamine entity = getEntity(agentExamineParam);
        this.updateById(entity);
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
