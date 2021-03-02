package cn.stylefeng.guns.modular.appPower.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.appPower.entity.AppPowerType;
import cn.stylefeng.guns.modular.appPower.mapper.AppPowerTypeMapper;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerTypeParam;
import cn.stylefeng.guns.modular.appPower.model.result.AppPowerTypeResult;
import  cn.stylefeng.guns.modular.appPower.service.AppPowerTypeService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 应用类型表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
@Service
public class AppPowerTypeServiceImpl extends ServiceImpl<AppPowerTypeMapper, AppPowerType> implements AppPowerTypeService {

    @Override
    public void add(AppPowerTypeParam param){
        AppPowerType entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AppPowerTypeParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AppPowerTypeParam param){
        AppPowerType oldEntity = getOldEntity(param);
        AppPowerType newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AppPowerTypeResult findBySpec(AppPowerTypeParam param){
        return null;
    }

    @Override
    public List<AppPowerTypeResult> findListBySpec(AppPowerTypeParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AppPowerTypeParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AppPowerType> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    @Override
    public List<AppPowerTypeResult> getTypeList() {
        return baseMapper.getTypeList();
    }

    private Serializable getKey(AppPowerTypeParam param){
        return param.getAppPowerTypeId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AppPowerType getOldEntity(AppPowerTypeParam param) {
        return this.getById(getKey(param));
    }

    private AppPowerType getEntity(AppPowerTypeParam param) {
        AppPowerType entity = new AppPowerType();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
