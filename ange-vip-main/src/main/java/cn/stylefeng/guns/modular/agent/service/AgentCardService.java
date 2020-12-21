package cn.stylefeng.guns.modular.agent.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理卡密 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-22
 */
public interface AgentCardService extends IService<AgentCard> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    void add(AgentCardParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    void delete(AgentCardParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    void update(AgentCardParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    AgentCardResult findBySpec(AgentCardParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    List<Map<String, Object>> findCodeCardTypeListBySpec(Page page,AgentCardParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
    List<Map<String, Object>> findAccountCardTypeListBySpec(Page page,AgentCardParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
     LayuiPageInfo findPageBySpec(AgentCardParam param);

    /**
     * 初始化单码卡类
     * @param agentCardParam
     */
    void initializeItemCodeCard(AgentCardParam agentCardParam);

    void initializeItemAccountCard(AgentCardParam agentCardParam);


    /**
     * 通过应用Id和代理应用id查找卡密类型
     * @param appId 应用id
     * @param agentAppId 代理应用id
     * @return 卡密列表
     */
    List<AgentCardResult> findCardTypeByAppIdAndAgentAppId(Long appId, Long agentAppId,Integer cardType);

    /**
     * 排除已经存在的上级卡类获取剩余卡类信息
     * @param agentAppId 当前代理应用id
     * @param cardType 卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     * @return 卡类信息
     */
    List<AgentCardResult> getCardTypeByAgentAppIdAndCardType(Integer type,Long agentAppId, Integer cardType);
}
