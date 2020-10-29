package cn.stylefeng.guns.modular.appPower.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.appPower.entity.AppPowerType;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerTypeParam;
import cn.stylefeng.guns.modular.appPower.model.result.AppPowerTypeResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 应用类型表  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
public interface AppPowerTypeService extends IService<AppPowerType> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    void add(AppPowerTypeParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    void delete(AppPowerTypeParam param);

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
    void update(AppPowerTypeParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    AppPowerTypeResult findBySpec(AppPowerTypeParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    List<AppPowerTypeResult> findListBySpec(AppPowerTypeParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-10-29
     */
     LayuiPageInfo findPageBySpec(AppPowerTypeParam param);

     //获取授权类型列表
    List<AppPowerTypeResult> getTypeList();
}
