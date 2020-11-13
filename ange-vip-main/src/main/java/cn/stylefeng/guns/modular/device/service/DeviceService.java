package cn.stylefeng.guns.modular.device.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.model.params.DeviceParam;
import cn.stylefeng.guns.modular.device.model.result.DeviceApi;
import cn.stylefeng.guns.modular.device.model.result.DeviceResult;
import com.baomidou.mybatisplus.extension.service.IService;
import io.swagger.models.auth.In;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 绑定设备信息 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
public interface DeviceService extends IService<Device> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    void add(DeviceParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    void delete(DeviceParam param);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    void update(DeviceParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    DeviceResult findBySpec(DeviceParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    List<DeviceResult> findListBySpec(DeviceParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
     LayuiPageInfo findPageBySpec(DeviceParam param);

    /**
     * 获取并处理绑定设备
     * @param cardId 卡密id
     * @return 返回设备
     */
    boolean getDeviceApiAndHandleByCardOrUserId(Long appId, Long cardId, Integer cardBindType, Integer cardBindNum, String mac, String model, Date expireTime);
}
