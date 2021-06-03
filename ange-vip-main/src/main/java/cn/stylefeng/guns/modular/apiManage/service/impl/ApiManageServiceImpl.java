package cn.stylefeng.guns.modular.apiManage.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.mapper.ApiManageMapper;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiManageParam;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageResult;
import  cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.exception.enums.ApiExceptionEnum;
import cn.stylefeng.guns.sys.core.util.IpUtils;
import cn.stylefeng.roses.core.util.HttpContext;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口管理  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-21
 */
@Service
public class ApiManageServiceImpl extends ServiceImpl<ApiManageMapper, ApiManage> implements ApiManageService {

    @Autowired
    private RedisUtil redisUtil;


    @Override
    public void add(ApiManageParam param){
        ApiManage entity = getEntity(param);
        entity.setAppId(1L);
        this.save(entity);
    }

    @Override
    public void delete(ApiManageParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(ApiManageParam param){
        ApiManage oldEntity = getOldEntity(param);
        ApiManage newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        redisUtil.del(RedisType.API_MANAGE.getCode()+ oldEntity.getCallCode());
        this.updateById(newEntity);
    }

    @Override
    public ApiManageResult findBySpec(ApiManageParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, ApiManageParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(ApiManageParam param){
        Page pageContext = getPageContext();
        QueryWrapper<ApiManage> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    /**
     * 从redis中查接口管理
     *
     * @param apiCode 接口编码
     * @param callCode   应用调用码
     * @return 接口信息
     */
    @Override
    public ApiManageApi getApiManageByRedis(String apiCode, String callCode) {
        limit(4,10);
        //是否存在该hash表
        ApiManageApi apiManageApi = (ApiManageApi)redisUtil.hget(RedisType.API_MANAGE.getCode()+ callCode,apiCode);
        if (ObjectUtil.isNull(apiManageApi)) {
            //不存在则查询
            apiManageApi = baseMapper.findApiManageApi(apiCode, callCode);
            if (ObjectUtil.isNull(apiManageApi)) {
                throw new SystemApiException(ApiExceptionEnum.API_BAD.getCode(), ApiExceptionEnum.API_BAD.getMessage(), "", false);
            } else {
                redisUtil.hset(RedisType.API_MANAGE.getCode() + callCode, apiCode, apiManageApi, RedisExpireTime.WEEK.getCode());
            }
        }
        return apiManageApi;
    }

    //限流
    public void limit(int times, int second){
        //根据 IP + API 限流
        String key = IpUtils.getIpAddr(HttpContext.getRequest()) + HttpContext.getRequest().getRequestURI();
        //根据key获取已请求次数
        Integer maxTimes = (Integer) redisUtil.get(key);
        if(maxTimes == null){
            //set时一定要加过期时间
            redisUtil.set(key, 1, second);
        }else if(maxTimes < times){
            redisUtil.set(key, maxTimes+1, second);
        }else{
            throw new SystemApiException(5, "请求过于频繁,请稍后再试","",false);
        }
    }

    private Serializable getKey(ApiManageParam param){
        return param.getApiManageId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ApiManage getOldEntity(ApiManageParam param) {
        return this.getById(getKey(param));
    }

    private ApiManage getEntity(ApiManageParam param) {
        ApiManage entity = new ApiManage();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
