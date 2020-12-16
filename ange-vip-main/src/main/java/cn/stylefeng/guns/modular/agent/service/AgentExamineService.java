package cn.stylefeng.guns.modular.agent.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentExamineResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理审核表 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-09
 */
public interface AgentExamineService extends IService<AgentExamine> {

    /**
     * 开发者端新增代理审核记录
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    void developerAddItem(AgentExamineParam param);

    /**
     * 代理端端新增二级代理接口
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    void agentAddItem(AgentExamineParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    AgentExamineResult findBySpec(AgentExamineParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    List<Map<String, Object>> findListBySpec(Page page, AgentExamineParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
     LayuiPageInfo findPageBySpec(AgentExamineParam param);

    /**
     * 代理同意代理
     * @param agentExamineParam
     */
    void agree(AgentExamineParam agentExamineParam);

    /**
     * 代理拒绝代理
     * @param agentExamineParam
     */
    void actRefuse(AgentExamineParam agentExamineParam);
}
