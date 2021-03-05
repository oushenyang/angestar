package cn.stylefeng.guns.modular.trial.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.trial.entity.Trial;
import cn.stylefeng.guns.modular.trial.model.params.TrialParam;
import cn.stylefeng.guns.modular.trial.model.result.TrialResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试用信息 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-03-04
 */
public interface TrialService extends IService<Trial> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    void add(TrialParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    void delete(TrialParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    void update(TrialParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    TrialResult findBySpec(TrialParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
    List<TrialResult> findListBySpec(Page page, TrialParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2021-03-04
     */
     LayuiPageInfo findPageBySpec(TrialParam param);

}
