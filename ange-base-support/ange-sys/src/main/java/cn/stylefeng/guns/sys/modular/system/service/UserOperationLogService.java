package cn.stylefeng.guns.sys.modular.system.service;

import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.sys.modular.system.entity.OperationLog;
import cn.stylefeng.guns.sys.modular.system.entity.User;
import cn.stylefeng.guns.sys.modular.system.entity.UserOperationLog;
import cn.stylefeng.guns.sys.modular.system.mapper.OperationLogMapper;
import cn.stylefeng.guns.sys.modular.system.mapper.UserOperationLogMapper;
import cn.stylefeng.guns.sys.modular.system.model.result.UserOperationLogResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 操作日志 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2018-12-07
 */
public interface UserOperationLogService extends IService<UserOperationLog>{

    List<UserOperationLogResult> getLogListByUserId(Long userId);
}
