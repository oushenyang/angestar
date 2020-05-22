package cn.stylefeng.guns.modular.agent.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
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
    List<Map<String, Object>> findListBySpec(Page page,AgentCardParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-22
     */
     LayuiPageInfo findPageBySpec(AgentCardParam param);

    /**
     * 初始化
     * @param agentCardParam
     */
    void initializeItem(AgentCardParam agentCardParam);
}
