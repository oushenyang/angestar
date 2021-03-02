package cn.stylefeng.guns.modular.agent.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.auth.exception.OperationException;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.type.BuyCardType;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.entity.AgentBuyCard;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.mapper.AgentAppMapper;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppRechargeParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentBuyCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentAppResult;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentBuyCardService;
import cn.stylefeng.guns.modular.agent.service.AgentExamineService;
import cn.stylefeng.guns.modular.agent.service.AgentPowerService;
import cn.stylefeng.guns.sys.core.util.NumToChUtil;
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
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.*;

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
    private final AgentBuyCardService agentBuyCardService;

    public AgentAppServiceImpl(UserService userService, AgentPowerService agentPowerService, AgentBuyCardService agentBuyCardService) {
        this.userService = userService;
        this.agentPowerService = agentPowerService;
        this.agentBuyCardService = agentBuyCardService;
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
        agentApp.setAgentUserId(entity.getAgentUserId());
        agentApp.setAgentGrade(1);
        agentApp.setBalance(new BigDecimal(BigInteger.ZERO));
        agentApp.setAgentAppIdPid(0L);
        agentApp.setAgentAppIdPids("[0],");
        agentApp.setPid(entity.getDeveloperUserId());
        agentApp.setPids(entity.getPids());
        this.save(agentApp);
        AgentPower agentPower = new AgentPower();
        agentPower.setAgentAppId(agentApp.getAgentAppId());
        agentPower.setAppId(agentApp.getAppId());
        agentPower.setCreateTime(new Date());
        agentPower.setCreateUser(LoginContextHolder.getContext().getUserId());
        agentPowerService.save(agentPower);
    }

    /**
     * 新增下级代理
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    @Override
    public void addSubordinateAgent(AgentExamine entity) {
        //查找上级代理
        AgentApp superiorAgentApp = baseMapper.selectOne(new QueryWrapper<AgentApp>()
                .eq("app_id",entity.getAppId())
                .eq("agent_grade",entity.getAgentGrade()-1)
                .eq("developer_user_id",entity.getDeveloperUserId())
                .eq("agent_user_id",entity.getPid()));
        AgentApp agentApp = new AgentApp();
        agentApp.setAppId(entity.getAppId());
        agentApp.setDeveloperUserId(entity.getDeveloperUserId());
        agentApp.setAgentUserId(entity.getAgentUserId());
        agentApp.setAgentGrade(entity.getAgentGrade());
        agentApp.setBalance(new BigDecimal(BigInteger.ZERO));
        agentApp.setAgentAppIdPid(superiorAgentApp.getAgentAppId());
        agentApp.setAgentAppIdPids(superiorAgentApp.getAgentAppIdPids()+"["+superiorAgentApp.getAgentAppId()+"],");
        agentApp.setPid(entity.getPid());
        agentApp.setPids(entity.getPids());
        this.save(agentApp);
        AgentPower agentPower = new AgentPower();
        agentPower.setAgentAppId(agentApp.getAgentAppId());
        agentPower.setAppId(agentApp.getAppId());
        agentPower.setCreateTime(new Date());
        agentPower.setCreateUser(LoginContextHolder.getContext().getUserId());
        agentPowerService.save(agentPower);
    }

    @Override
    @Transactional
    public void delete(AgentAppParam param) {
        //代理权限
        agentPowerService.remove(new QueryWrapper<AgentPower>().eq("agent_app_id", param.getAgentAppId()));
        //代理审核记录
//        agentExamineService.remove(new QueryWrapper<AgentExamine>().eq("agent_app_id", param.getAgentAppId()));
//        //代理购卡记录
//        agentBuyCardService.remove(new QueryWrapper<AgentBuyCard>().eq("agent_app_id", param.getAgentAppId()));
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
    @Transactional
    public void recharge(AgentAppRechargeParam agentAppRechargeParam) {
        AgentApp agentApp =this.getById(agentAppRechargeParam.getAgentAppId());
        agentApp.setAgentAppId(agentAppRechargeParam.getAgentAppId());
        AgentBuyCardParam param = new AgentBuyCardParam();
        //充值
        if (agentAppRechargeParam.getRechargeType() == 0) {
            agentApp.setBalance(agentAppRechargeParam.getBalance().add(agentAppRechargeParam.getRechargeBalance()));
            param.setAmount(agentAppRechargeParam.getRechargeBalance());
            //设置明细
            if (agentApp.getAgentGrade()>1){
                AgentApp superiorAgentApp =this.getById(agentApp.getAgentAppIdPid());
                //上级代理扣款
                if (superiorAgentApp.getBalance().subtract(agentAppRechargeParam.getRechargeBalance()).signum()==-1){
                    throw new OperationException(SUBORDINATE_INSUFFICIENT_BALANCE_AGENT);
                }
                superiorAgentApp.setBalance(superiorAgentApp.getBalance().subtract(agentAppRechargeParam.getRechargeBalance()));
                this.updateById(superiorAgentApp);
                param.setDetailed(StrUtil.format(BuyCardType.SUBORDINATE_AGENT_RECHARGE.getDetailed(),
                        NumToChUtil.to(agentApp.getAgentGrade()),
                        agentAppRechargeParam.getRechargeBalance(),
                        agentAppRechargeParam.getRechargeBalance()));
            }else {
                param.setDetailed(StrUtil.format(BuyCardType.PRIMARY_AGENT_RECHARGE.getDetailed(),
                        agentAppRechargeParam.getRechargeBalance()));
            }
        }else {
            //上级代理扣款
            if (agentAppRechargeParam.getBalance().subtract(agentAppRechargeParam.getRechargeBalance()).signum()==-1){
                throw new OperationException(GREATER_THAN_SUBORDINATE_INSUFFICIENT_BALANCE_AGENT);
            }
            agentApp.setBalance(agentAppRechargeParam.getBalance().subtract(agentAppRechargeParam.getRechargeBalance()));
            param.setAmount(new BigDecimal(BigInteger.ZERO).subtract(agentAppRechargeParam.getRechargeBalance()));
            //设置明细
            if (agentApp.getAgentGrade()>1){
                AgentApp superiorAgentApp =this.getById(agentApp.getAgentAppIdPid());
                //上级代理充值
                superiorAgentApp.setBalance(superiorAgentApp.getBalance().add(agentAppRechargeParam.getRechargeBalance()));
                this.updateById(superiorAgentApp);
                param.setDetailed(StrUtil.format(BuyCardType.SUBORDINATE_AGENT_DEDUCT.getDetailed(),
                        NumToChUtil.to(agentApp.getAgentGrade()),
                        agentAppRechargeParam.getRechargeBalance(),
                        agentAppRechargeParam.getRechargeBalance()));
            }else {
                param.setDetailed(StrUtil.format(BuyCardType.PRIMARY_AGENT_DEDUCT.getDetailed(), agentAppRechargeParam.getRechargeBalance()));
            }
        }
        this.updateById(agentApp);
        //生成充值
        param.setAgentAppId(agentApp.getAgentAppId());
        param.setBuyCardType(BuyCardType.PRIMARY_AGENT_RECHARGE.getCode());
        param.setCreateTime(new Date());
        param.setCreateUser(LoginContextHolder.getContext().getUserId());
        agentBuyCardService.add(param);
    }

    @Override
    public AgentAppResult findBySpec(AgentAppParam param) {
        return null;
    }

    /**
     * 根据id获取代理应用详情
     *
     * @param agentAppId 代理应用id
     * @return
     */
    @Override
    public AgentAppResult getDetailById(Long agentAppId) {
        return baseMapper.getDetailById(agentAppId);
    }

    @Override
    public List<AgentAppResult> findListBySpec(Page page, AgentAppParam param) {
        List<AgentAppResult> list = baseMapper.findListBySpec(page,param);
        list.forEach(agentAppResult -> {
            agentAppResult.setAgentGradeName(StrUtil.format("{}级代理",NumToChUtil.to(agentAppResult.getAgentGrade())));
        });
        return list;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AgentAppParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<AgentApp> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 获取代理数量
     *
     * @param userId 用户id
     * @return 数量
     */
    @Override
    public Integer agentNum(Long userId) {
        return baseMapper.agentNum(userId);
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
