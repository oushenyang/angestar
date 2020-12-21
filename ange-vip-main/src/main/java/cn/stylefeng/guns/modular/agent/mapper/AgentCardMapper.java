package cn.stylefeng.guns.modular.agent.mapper;

import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理卡密 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-22
 */
public interface AgentCardMapper extends BaseMapper<AgentCard> {

    /**
     * 获取代理单码卡类分页列表
     * @param page 分页数据
     * @param param 查询参数
     * @return 结果
     */
    List<Map<String, Object>> findCodeCardTypeListBySpec(@Param("page") Page page, @Param("param") AgentCardParam param);

    /**
     * 获取代理注册码卡类分页列表
     * @param page 分页数据
     * @param param 查询参数
     * @return 结果
     */
    List<Map<String, Object>> findAccountCardTypeListBySpec(@Param("page") Page page, @Param("param") AgentCardParam param);

    /**
     * 通过应用Id和代理应用id查找卡密类型
     *
     * @param appId      应用id
     * @param agentAppId 代理应用id
     * @return 卡密列表
     */
    List<AgentCardResult> selectCardTypeByAppIdAndAgentAppId(@Param("appId") Long appId, @Param("agentAppId") Long agentAppId, @Param("cardType") Integer cardType);

    /**
     * 查找上级代理的卡类权限信息
     *
     * @param agentAppId 当前代理应用id
     * @param cardType   卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     * @return 卡类信息
     */
    List<AgentCardResult> getSuperiorCardTypeByAgentAppIdAndCardType(@Param("agentAppId") Long agentAppId, @Param("cardType") Integer cardType);

    /**
     * 查找当前代理的卡类权限信息
     *
     * @param agentAppId 当前代理应用id
     * @param cardType   卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     * @return 卡类信息
     */
    List<AgentCardResult> getCardTypeByAgentAppIdAndCardType(@Param("agentAppId") Long agentAppId, @Param("cardType") Integer cardType);
}
