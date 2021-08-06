package cn.stylefeng.guns.sys.modular.system.service.impl;

import cn.stylefeng.guns.sys.modular.system.entity.UserOperationLog;
import cn.stylefeng.guns.sys.modular.system.mapper.UserOperationLogMapper;
import cn.stylefeng.guns.sys.modular.system.model.result.UserOperationLogResult;
import cn.stylefeng.guns.sys.modular.system.service.UserOperationLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户操作日志表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-06-27
 */
@Service
public class UserOperationLogServiceImpl extends ServiceImpl<UserOperationLogMapper, UserOperationLog> implements UserOperationLogService {


    @Override
    public List<UserOperationLogResult> getLogListByUserId(Long userId) {
        return baseMapper.getLogListByUserId(userId);
    }
}
