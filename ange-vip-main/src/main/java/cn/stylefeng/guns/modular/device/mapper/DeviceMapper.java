package cn.stylefeng.guns.modular.device.mapper;

import cn.stylefeng.guns.modular.device.entity.Device;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 绑定设备信息 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
public interface DeviceMapper extends BaseMapper<Device> {

    /**
     * 更新设备登录次数
     *
     * @param deviceId 设备id
     */
    void updateDeviceLoginNumByDeviceId(@Param("deviceId") Long deviceId);
}
