package cn.stylefeng.guns.modular.agent.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.model.params.AgentPowerParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentPowerResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 代理权限 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
public interface AgentPowerService extends IService<AgentPower> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    void add(AgentPowerParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    void delete(AgentPowerParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    void update(AgentPowerParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    AgentPowerResult findBySpec(AgentPowerParam param);

    /**
     * 获取当前登陆代理的下级代理显示权限
     * @param agentAppId 当前登陆代理的下级代理应用id
     * @return
     */
    List<AgentPowerResult> getSubordinateAgentPowerByAgentAppId(Long agentAppId);

    /**
     * 获取当前登陆代理的下级代理显示权限
     * @param agentAppId 当前登陆代理的下级代理应用id
     * @return
     */
    AgentPowerResult getSubordinateAgentPowerShow(Long agentAppId);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    List<AgentPowerResult> findListBySpec(AgentPowerParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
     LayuiPageInfo findPageBySpec(AgentPowerParam param);

    /**
     * 查看是否有权限
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    Boolean checkPower(Long agentAppId, String powerStr);
}
