package cn.stylefeng.guns.modular.agent.mapper;

import cn.stylefeng.guns.modular.agent.entity.AgentPower;
import cn.stylefeng.guns.modular.agent.model.result.AgentPowerResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 代理权限 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
public interface AgentPowerMapper extends BaseMapper<AgentPower> {

    /**
     * 获取当前登陆代理的下级代理显示权限
     *
     * @param agentAppId 当前登陆代理的下级代理应用id
     * @return
     */
    AgentPowerResult getSubordinateAgentPowerShow(@Param("agentAppId") Long agentAppId);

    /**
     * 查找下级代理的所有权限信息
     *
     * @param agentAppId 当前登陆代理的下级代理应用id
     * @return
     */
    List<AgentPowerResult> getSubordinateAgentPowerByAgentAppId(@Param("agentAppId") Long agentAppId);
}
