package cn.stylefeng.guns.modular.app.mapper;

import cn.stylefeng.guns.modular.app.entity.AppFeedback;
import cn.stylefeng.guns.modular.app.model.params.AppFeedbackParam;
import cn.stylefeng.guns.modular.app.model.result.AppFeedbackResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 应用反馈列表 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
public interface AppFeedbackMapper extends BaseMapper<AppFeedback> {

    List<AppFeedbackResult> findListPage(@Param("page") Page page,@Param("param") AppFeedbackParam param);
}
