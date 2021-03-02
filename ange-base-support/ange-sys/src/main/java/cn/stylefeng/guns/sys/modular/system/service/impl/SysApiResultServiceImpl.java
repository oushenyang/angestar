package cn.stylefeng.guns.sys.modular.system.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.sys.modular.system.mapper.SysApiResultMapper;
import cn.stylefeng.guns.sys.modular.system.model.params.ApiResultParam;
import cn.stylefeng.guns.sys.modular.system.model.result.ApiResultApi;
import cn.stylefeng.guns.sys.modular.system.model.result.ApiResultResult;
import cn.stylefeng.guns.sys.modular.system.service.SysApiResultService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口自定义返回 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-31
 */
@Service
public class SysApiResultServiceImpl extends ServiceImpl<SysApiResultMapper, ApiResult> implements SysApiResultService {

    @Override
    public void add(ApiResultParam param){
        ApiResult entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(ApiResultParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(ApiResultParam param){
        ApiResult oldEntity = getOldEntity(param);
        ApiResult newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public ApiResultResult findBySpec(ApiResultParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, ApiResultParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(ApiResultParam param){
        Page pageContext = getPageContext();
        QueryWrapper<ApiResult> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public ApiResultApi findApiResultApi(Long appId, Integer resultCode) {
        return baseMapper.findApiResultApi(appId,resultCode);
    }

    private Serializable getKey(ApiResultParam param){
        return param.getApiResultId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private ApiResult getOldEntity(ApiResultParam param) {
        return this.getById(getKey(param));
    }

    private ApiResult getEntity(ApiResultParam param) {
        ApiResult entity = new ApiResult();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
