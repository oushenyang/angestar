package cn.stylefeng.guns.modular.agent.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentBuyCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentBuyCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentBuyCardResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 代理购卡记录 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-11
 */
public interface AgentBuyCardService extends IService<AgentBuyCard> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    void add(AgentBuyCardParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    void delete(AgentBuyCardParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    void update(AgentBuyCardParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    AgentBuyCardResult findBySpec(AgentBuyCardParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
    List<AgentBuyCardResult> findListBySpec(AgentBuyCardParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-12-11
     */
     LayuiPageInfo findPageBySpec(AgentBuyCardParam param);

}
