package cn.stylefeng.guns.modular.app.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import cn.stylefeng.guns.modular.app.entity.AppFeedback;
import cn.stylefeng.guns.modular.app.model.params.AppFeedbackParam;
import cn.stylefeng.guns.modular.app.model.result.AppFeedbackResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 应用反馈列表 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
public interface AppFeedbackService extends IService<AppFeedback> {
    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    void delete(AppFeedbackParam param);

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
    List<AppFeedbackResult> findListPage(Page page, AppFeedbackParam param);
}
