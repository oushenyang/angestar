package cn.stylefeng.guns.modular.agent.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentExamineResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

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
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
    void add(AgentExamineParam param);

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
    List<AgentExamineResult> findListBySpec(AgentExamineParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-12-09
     */
     LayuiPageInfo findPageBySpec(AgentExamineParam param);

}
