package cn.stylefeng.guns.modular.appPower.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.base.consts.ConstantsContext;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.appPower.entity.AppPower;
import cn.stylefeng.guns.modular.appPower.mapper.AppPowerMapper;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerParam;
import cn.stylefeng.guns.modular.appPower.model.result.AppPowerResult;
import  cn.stylefeng.guns.modular.appPower.service.AppPowerService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 应用授权表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
@Service
public class AppPowerServiceImpl extends ServiceImpl<AppPowerMapper, AppPower> implements AppPowerService {

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public void add(AppPowerParam param){
        AppPower entity = getEntity(param);

        //判断是否已经存在同编码或同名称字典
        QueryWrapper<AppPower> dictQueryWrapper = new QueryWrapper<>();
        dictQueryWrapper
                .and(i -> i.eq("sign", param.getSign()))
                .and(i -> i.eq("app_type_code", param.getAppTypeCode()));
        List<AppPower> list = this.list(dictQueryWrapper);
        if (list != null && list.size() > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }
        this.save(entity);
        redisUtil.hset(RedisType.APP_POWER + "app_type_code:huanyin",param.getSign(),entity,604800);
    }

    @Override
    public void delete(AppPowerParam param){
        AppPower appPower = this.getById(param.getAppPowerId());
        redisUtil.hdel(RedisType.APP_POWER + "app_type_code:huanyin",appPower.getSign());
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AppPowerParam param){
        AppPower oldEntity = getOldEntity(param);
        AppPower newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
//        //判断是否已经存在同编码或同名称字典
//        QueryWrapper<AppPower> dictQueryWrapper = new QueryWrapper<>();
//        dictQueryWrapper
//                .and(i -> i.eq("sign", param.getSign()))
//                .and(i -> i.eq("app_type_code", param.getAppTypeCode()));
//        List<AppPower> list = this.list(dictQueryWrapper);
//        if (list != null && list.size() > 0) {
//            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
//        }
        this.updateById(newEntity);
        redisUtil.hset(RedisType.APP_POWER + "app_type_code:huanyin",param.getSign(),newEntity,604800);
    }

    @Override
    public AppPowerResult findBySpec(AppPowerParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, AppPowerParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(AppPowerParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AppPower> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 判断是否制裁
     *
     * @param sign
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @Override
    public boolean whetherLegal(String sign) {
        Map<Object, Object> objects = redisUtil.hmget(RedisType.APP_POWER + "app_type_code:huanyin");
        List<AppPower> appPowers = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)) {
            for (Map.Entry<Object, Object> m : objects.entrySet()) {
                appPowers.add((AppPower) m.getValue());
            }
        }else {
            List<AppPower> appPowerList = this.list();
            for (AppPower appPower : appPowerList){
                redisUtil.hset(RedisType.APP_POWER + "app_type_code:huanyin",appPower.getSign(),appPower,604800);
            }
            appPowers.addAll(appPowerList);
        }
        boolean isHave = false;
        boolean isLegal = false;
        for (AppPower appPower : appPowers){
            //如果存在
            if (sign.equals(appPower.getSign())){
                if (ConstantsContext.getPirateOpen2()&&appPower.getWhetherSanction()&&!appPower.getWhetherLegal()){
                    isLegal = true;
                }
                isHave = true;
                break;
            }
        }
        if (!isHave){
            AppPower appPower1 = new AppPower();
            appPower1.setSign(sign);
            appPower1.setAppTypeCode("huanyin");
            appPower1.setWhetherLegal(false);
            appPower1.setWhetherSanction(false);
            appPower1.setWhetherShow(false);
            appPower1.setCreateTime(new Date());
            this.save(appPower1);
            redisUtil.hset(RedisType.APP_POWER + "app_type_code:huanyin",sign,appPower1,604800);
        }
        return isLegal;
    }

    /**
     * 判断是否显示
     *
     * @param sign
     * @author shenyang.ou
     * @Date 2020-10-29
     */
    @Override
    public boolean whetherShow(String sign) {
        Map<Object, Object> objects = redisUtil.hmget(RedisType.APP_POWER + "app_type_code:huanyin");
        List<AppPower> appPowers = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)) {
            for (Map.Entry<Object, Object> m : objects.entrySet()) {
                appPowers.add((AppPower) m.getValue());
            }
        }else {
            List<AppPower> appPowerList = this.list();
            for (AppPower appPower : appPowerList){
                redisUtil.hset(RedisType.APP_POWER + "app_type_code:huanyin",appPower.getSign(),appPower,604800);
            }
            appPowers.addAll(appPowerList);
        }
        boolean isHave = false;
        boolean isLegal = false;
        for (AppPower appPower : appPowers){
            //如果存在
            if (sign.equals(appPower.getSign())){
                if (ConstantsContext.getPirateOpen2()&&appPower.getWhetherShow()&&!appPower.getWhetherLegal()){
                    isLegal = true;
                }
                isHave = true;
                break;
            }
        }
        if (!isHave){
            AppPower appPower1 = new AppPower();
            appPower1.setSign(sign);
            appPower1.setAppTypeCode("huanyin");
            appPower1.setWhetherLegal(false);
            appPower1.setWhetherSanction(false);
            appPower1.setWhetherShow(false);
            appPower1.setCreateTime(new Date());
            this.save(appPower1);
            redisUtil.hset(RedisType.APP_POWER + "app_type_code:huanyin",sign,appPower1,604800);
        }
        return isLegal;
    }

    private Serializable getKey(AppPowerParam param){
        return param.getAppPowerId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AppPower getOldEntity(AppPowerParam param) {
        return this.getById(getKey(param));
    }

    private AppPower getEntity(AppPowerParam param) {
        AppPower entity = new AppPower();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
