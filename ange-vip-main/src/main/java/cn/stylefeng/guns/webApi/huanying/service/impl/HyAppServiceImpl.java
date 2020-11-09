package cn.stylefeng.guns.webApi.huanying.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.util.CustomEnAndDe;
import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.mapper.HyAppMapper;
import cn.stylefeng.guns.webApi.huanying.model.params.HyAppParam;
import cn.stylefeng.guns.webApi.huanying.model.result.HyAppResult;
import  cn.stylefeng.guns.webApi.huanying.service.HyAppService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 幻影破解 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-16
 */
@Service
public class HyAppServiceImpl extends ServiceImpl<HyAppMapper, HyApp> implements HyAppService {

    private final RedisUtil redisUtil;

    public HyAppServiceImpl(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public void add(HyAppParam param){
        HyApp entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(HyAppParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(HyAppParam param){
        HyApp oldEntity = getOldEntity(param);
        HyApp newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public HyAppResult findBySpec(HyAppParam param){
        return null;
    }

    @Override
    public List<HyAppResult> findListBySpec(String utDid,String sign){
        List<Object> objects = redisUtil.lGet(RedisType.HUANYIN + utDid + sign,0,-1);
        List<HyAppResult> hyAppResults = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)){
            hyAppResults = JSON.parseArray(objects.get(0).toString(),HyAppResult.class);
        }
        if (CollectionUtils.isEmpty(hyAppResults)){
            hyAppResults = baseMapper.findListBySpec(utDid,sign);
            if (CollectionUtils.isNotEmpty(hyAppResults)){
                redisUtil.lSet(RedisType.HUANYIN + utDid + sign, hyAppResults);
            }
        }
        return hyAppResults;
    }

    @Override
    public List<HyAppResult> findListByModelAndSignAndAppName(String utDid,String sign,String appCode){
        List<Object> objects = redisUtil.lGet(RedisType.HUANYIN.getCode() + utDid +"-"+ sign +"-"+ appCode,0,-1);
        List<HyAppResult> hyAppResults = new ArrayList<>();
        if (CollectionUtil.isNotEmpty(objects)){
            hyAppResults = JSON.parseArray(objects.get(0).toString(),HyAppResult.class);
        }
        if (CollectionUtils.isEmpty(hyAppResults)){
            hyAppResults = baseMapper.findListByModelAndSignAndAppName(utDid,sign, CustomEnAndDe.deCrypto(appCode));
            if (CollectionUtils.isNotEmpty(hyAppResults)){
                redisUtil.lSet(RedisType.HUANYIN.getCode() + utDid +"-"+ sign +"-"+ appCode, hyAppResults, RedisExpireTime.MONTH.getCode());
            }
        }
        return hyAppResults;
    }

    @Override
    public List<Map<String, Object>> findListByPage(Page page, HyAppParam param,List<String> signList){
        return baseMapper.findListByPage(page,param,signList);
    }

    @Override
    public LayuiPageInfo findPageBySpec(HyAppParam param){
        Page pageContext = getPageContext();
        QueryWrapper<HyApp> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(HyAppParam param){
        return param.getAppinfoid();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private HyApp getOldEntity(HyAppParam param) {
        return this.getById(getKey(param));
    }

    private HyApp getEntity(HyAppParam param) {
        HyApp entity = new HyApp();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }


}
