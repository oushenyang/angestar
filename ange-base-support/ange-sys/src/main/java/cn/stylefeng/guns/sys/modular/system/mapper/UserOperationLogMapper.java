package cn.stylefeng.guns.sys.modular.system.mapper;

import cn.stylefeng.guns.sys.modular.system.entity.OperationLog;
import cn.stylefeng.guns.sys.modular.system.entity.UserOperationLog;
import cn.stylefeng.guns.sys.modular.system.model.result.UserOperationLogResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface UserOperationLogMapper extends BaseMapper<UserOperationLog> {
    List<UserOperationLogResult> getLogListByUserId(@Param("userId") Long userId);
}
