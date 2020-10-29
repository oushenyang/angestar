package cn.stylefeng.guns.modular.appPower.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.appPower.entity.AppPower;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerParam;
import cn.stylefeng.guns.modular.appPower.model.result.AppPowerResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用授权表  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
public interface AppPowerService extends IService<AppPower> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    void add(AppPowerParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    void delete(AppPowerParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    void update(AppPowerParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    AppPowerResult findBySpec(AppPowerParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    List<Map<String, Object>> findListBySpec(Page page, AppPowerParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
     LayuiPageInfo findPageBySpec(AppPowerParam param);

}
