package cn.stylefeng.guns.modular.agent.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppRechargeParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentAppResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理软件表  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
public interface AgentAppService extends IService<AgentApp> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    void add(AgentAppParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    void delete(AgentAppParam param);

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
    void update(AgentAppParam param);

    /**
     * 充值
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    void recharge(AgentAppRechargeParam agentAppRechargeParam);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    AgentAppResult findBySpec(AgentAppParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
    List<Map<String, Object>> findListBySpec(Page page, AgentAppParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-20
     */
     LayuiPageInfo findPageBySpec(AgentAppParam param);

}
