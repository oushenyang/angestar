package cn.stylefeng.guns.modular.agent.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.exception.OperationException;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.state.ExamineStatus;
import cn.stylefeng.guns.core.constant.type.ApplyType;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.mapper.AgentExamineMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentExamineResult;
import  cn.stylefeng.guns.modular.agent.service.AgentExamineService;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.service.UserService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.INVITED_AGENT;
import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.USER_NOT_EXISTED;

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

    public AgentExamineServiceImpl(UserService userService) {
        this.userService = userService;
    }

    /**
     * 开发者端新增代理审核记录
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    @Override
    public void developerAddItem(AgentExamineParam param){
        User user = userService.getByAccount(param.getAgentUserAccount());
        if (user == null) {
            throw new OperationException(USER_NOT_EXISTED);
        }
        AgentExamine agentExamine = baseMapper.selectOne(new QueryWrapper<AgentExamine>()
                .eq("app_id",param.getAppId())
                .eq("developer_user_id",LoginContextHolder.getContext().getUserId())
                .eq("agent_user_id",user.getUserId())
                .eq("examine_status",ExamineStatus.WAITING_AGENT_REVIEW.getCode()));
        if (ObjectUtil.isNotNull(agentExamine)){
            throw new OperationException(INVITED_AGENT);
        }
        param.setDeveloperUserId(LoginContextHolder.getContext().getUserId());
        param.setAgentUserId(user.getUserId());
        param.setAgentUserName(user.getName());
        param.setAgentUserAccount(user.getAccount());
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
