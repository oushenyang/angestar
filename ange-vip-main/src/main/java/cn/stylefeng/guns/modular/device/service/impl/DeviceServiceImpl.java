package cn.stylefeng.guns.modular.device.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.device.entity.Device;
import cn.stylefeng.guns.modular.device.mapper.DeviceMapper;
import cn.stylefeng.guns.modular.device.model.params.DeviceParam;
import cn.stylefeng.guns.modular.device.model.result.DeviceResult;
import  cn.stylefeng.guns.modular.device.service.DeviceService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.util.CardDateUtil;
import cn.stylefeng.guns.sys.core.util.IpUtils;
import cn.stylefeng.guns.sys.core.util.ip2region.IpToRegionUtil;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.catalina.connector.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
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

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private AsyncService asyncService;

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
        objectQueryWrapper.eq("card_type",param.getCardType());
        objectQueryWrapper.eq("card_or_user_id",param.getCardOrUserId());
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public boolean getDeviceApiAndHandleByCardOrUserId(Long appId,Long cardId,String cardCode,Integer cardBindType,Integer cardBindNum,String mac,String model,Date expireTime) {
        Object object = redisUtil.hget(RedisType.CARD_INFO.getCode() + cardCode,RedisType.DEVICE.getCode());
        List<Device> deviceApis = new ArrayList<>();
        if (ObjectUtil.isNotNull(object)) {
            List<Device> deviceList = JSON.parseArray(object.toString(),Device.class);
            deviceApis.addAll(deviceList);
        }

        //如果空
        if (CollectionUtils.isEmpty(deviceApis)){
            List<Device> deviceApiList = new ArrayList<>();
            insertDevice(deviceApiList,appId,cardId, mac,model,cardCode);
            return true;
        }else {
            boolean isHave = false;
            //mac
            if (cardBindType == 1){
                for (Device deviceApi : deviceApis){
                    if (deviceApi.getMac().equals(mac)) {
                        deviceApi.setLoginNum(deviceApi.getLoginNum()+1);
                        asyncService.updateDeviceAndCardLoginNum(deviceApi.getDeviceId(),deviceApi.getCardOrUserId());
                        isHave = true;
                        break;
                    }
                }
                //ip
            }else if(cardBindType == 2){
                for (Device deviceApi : deviceApis){
                    if (deviceApi.getIp().equals(getIp())){
                        //异步调用更新
                        deviceApi.setLoginNum(deviceApi.getLoginNum()+1);
                        asyncService.updateDeviceAndCardLoginNum(deviceApi.getDeviceId(),deviceApi.getCardOrUserId());
                        isHave = true;
                        break;
                    }
                }
                //混合
            }else if(cardBindType == 3){
                for (Device deviceApi : deviceApis){
                    if (deviceApi.getMac().equals(mac)&&deviceApi.getIp().equals(getIp())){
                        deviceApi.setLoginNum(deviceApi.getLoginNum()+1);
                        asyncService.updateDeviceAndCardLoginNum(deviceApi.getDeviceId(),deviceApi.getCardOrUserId());
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
                    insertDevice(deviceApis,appId,cardId, mac,model,cardCode);
                    return true;
                }else {
                    //返回错误,不在常用设备登录
                    return false;
                }
            }
        }
    }

    /**
     * 更新设备登录次数
     *
     * @param deviceId 设备id
     */
    @Override
    public void updateDeviceLoginNumByDeviceId(Long deviceId) {
        baseMapper.updateDeviceLoginNumByDeviceId(deviceId);
    }

    private void insertDevice(List<Device> deviceApiList,Long appId,Long cardId,String mac,String model,String cardCode){
        Date date = new Date();
        Device device = new Device();
        device.setAppId(appId);
        device.setCardOrUserId(cardId);
        device.setCardType(1);
        device.setMac(mac);
        device.setIp(IpUtils.getIpAddr(HttpContext.getRequest()));
        device.setModel(model);
//        device.setIpAddress(IpToRegionUtil.ipToRegion(getIp()));
        device.setLoginNum(1);
        device.setCreateTime(date);
//        baseMapper.insert(device);
        //异步调用插入
        asyncService.insertDevice(device);
        deviceApiList.add(device);
        redisUtil.hset(RedisType.CARD_INFO.getCode() + cardCode,RedisType.DEVICE.getCode(),JSON.toJSONString(deviceApiList), RedisExpireTime.DAY.getCode());
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
