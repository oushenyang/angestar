package cn.stylefeng.guns.modular.apiManage.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiManageParam;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 接口管理  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-21
 */
public interface ApiManageService extends IService<ApiManage> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void add(ApiManageParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void delete(ApiManageParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void update(ApiManageParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    ApiManageResult findBySpec(ApiManageParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    List<ApiManageResult> findListBySpec(ApiManageParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
     LayuiPageInfo findPageBySpec(ApiManageParam param);

}
