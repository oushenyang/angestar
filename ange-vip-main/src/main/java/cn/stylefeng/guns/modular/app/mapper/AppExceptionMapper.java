package cn.stylefeng.guns.modular.app.mapper;

import cn.stylefeng.guns.modular.app.entity.AppException;
import cn.stylefeng.guns.modular.app.model.params.AppExceptionParam;
import cn.stylefeng.guns.modular.app.model.result.AppExceptionResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 应用异常列表 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
public interface AppExceptionMapper extends BaseMapper<AppException> {

    List<AppExceptionResult> findListPage(@Param("page") Page page, @Param("param") AppExceptionParam param);
}
