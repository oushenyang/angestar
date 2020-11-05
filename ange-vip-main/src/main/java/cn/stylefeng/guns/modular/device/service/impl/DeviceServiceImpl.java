package cn.stylefeng.guns.modular.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.mapper.DeviceMapper;
import cn.stylefeng.guns.modular.device.model.params.DeviceParam;
import cn.stylefeng.guns.modular.device.model.result.DeviceResult;
import  cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

import static cn.stylefeng.roses.core.util.HttpContext.getIp;

/**
 * <p>
 * 绑定设备信息 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<DeviceMapper, Device> implements DeviceService {

    private final RedisUtil redisUtil;

    public DeviceServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void add(DeviceParam param){
        Device entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(DeviceParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(DeviceParam param){
        Device oldEntity = getOldEntity(param);
        Device newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public DeviceResult findBySpec(DeviceParam param){
        return null;
    }

    @Override
    public List<DeviceResult> findListBySpec(DeviceParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(DeviceParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Device> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public boolean getDeviceApiAndHandleByCardOrUserId(Long appId,Long cardId,Integer cardBindType,Integer cardBindNum,String mac,String model) {
//        List<Object> objects = redisUtil.lGet(RedisType.DEVICE.getCode() + String.valueOf(cardId),0,-1);
//        List<Device> deviceApis = new ArrayList<>();
//        if (CollectionUtil.isNotEmpty(objects)){
//            deviceApis = JSON.parseArray(objects.get(0).toString(),Device.class);
//        }
//        if (CollectionUtils.isEmpty(deviceApis)){
//            deviceApis = baseMapper.selectList(new QueryWrapper<Device>().eq("card_id", cardId));
//            if (CollectionUtils.isEmpty(deviceApis)){
//                redisUtil.lSet(RedisType.CARD_INFO.getCode() + String.valueOf(cardId), deviceApis,604800);
//            }
//        }

        Map<Object, Object> objects = redisUtil.hmget(RedisType.DEVICE.getCode() + cardId);
        List<Device> deviceApis = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)) {
            for (Map.Entry<Object, Object> m : objects.entrySet()) {
                deviceApis.add((Device) m.getValue());
            }
        }

        //如果空
        if (CollectionUtils.isEmpty(deviceApis)){
            List<Device> deviceApiList = new ArrayList<>();
            insertDevice(deviceApiList,appId,cardId, mac);
            return true;
        }else {
            boolean isHave = false;
            //mac
            if (cardBindType == 1){
                for (Device deviceApi : deviceApis){
                    if (deviceApi.getMac().equals(mac)) {
                        isHave = true;
                        break;
                    }
                }
                //ip
            }else if(cardBindType == 2){
                for (Device deviceApi : deviceApis){
                    if (deviceApi.getIp().equals(getIp())){
                        isHave = true;
                        break;
                    }
                }
                //混合
            }else if(cardBindType == 3){
                for (Device deviceApi : deviceApis){
                    if (deviceApi.getMac().equals(mac)&&deviceApi.getIp().equals(getIp())){
                        isHave = true;
                        break;
                    }
                }
            }

            //直接返回成功
            if (isHave){
                 return true;
            }else {
                //如果还没达到上限,直接插入并返回成功
                if (deviceApis.size()<cardBindNum){
                    insertDevice(deviceApis,appId,cardId, mac);
                    return true;
                }else {
                    //返回错误,不在常用设备登录
                    return false;
                }
            }
        }
    }

    private void insertDevice(List<Device> deviceApiList,Long appId,Long cardId,String mac){
        Date date = new Date();
        Device device = new Device();
        device.setAppId(appId);
        device.setCardOrUserId(cardId);
        device.setCardType(1);
        device.setMac(mac);
        device.setIp(getIp());
        device.setCreateTime(date);
        baseMapper.insert(device);
        deviceApiList.add(device);
        redisUtil.hset(RedisType.DEVICE.getCode() + cardId,mac+getIp(),device,604800);
    }

    private Serializable getKey(DeviceParam param){
        return param.getDeviceId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Device getOldEntity(DeviceParam param) {
        return this.getById(getKey(param));
    }

    private Device getEntity(DeviceParam param) {
        Device entity = new Device();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
