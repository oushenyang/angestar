package cn.stylefeng.guns.modular.app.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.guns.modular.app.entity.AppException;
import cn.stylefeng.guns.modular.app.model.params.AppExceptionParam;
import cn.stylefeng.guns.modular.app.model.result.AppExceptionResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 应用异常列表 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
public interface AppExceptionService extends IService<AppException> {

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    void delete(AppExceptionParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    void batchRemove(String ids);

    /**
     * 查询分页数据
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    List<AppExceptionResult> findListPage(Page page, AppExceptionParam param);
}
