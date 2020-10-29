package cn.stylefeng.guns.modular.appPower.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.appPower.entity.AppPower;
import cn.stylefeng.guns.modular.appPower.mapper.AppPowerMapper;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerParam;
import cn.stylefeng.guns.modular.appPower.model.result.AppPowerResult;
import  cn.stylefeng.guns.modular.appPower.service.AppPowerService;
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
 * 应用授权表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
@Service
public class AppPowerServiceImpl extends ServiceImpl<AppPowerMapper, AppPower> implements AppPowerService {

    @Override
    public void add(AppPowerParam param){
        AppPower entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AppPowerParam param){
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
        this.updateById(newEntity);
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
