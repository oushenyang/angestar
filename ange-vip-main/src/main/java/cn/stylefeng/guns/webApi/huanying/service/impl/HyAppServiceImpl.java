package cn.stylefeng.guns.webApi.huanying.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.mapper.HyAppMapper;
import cn.stylefeng.guns.webApi.huanying.model.params.HyAppParam;
import cn.stylefeng.guns.webApi.huanying.model.result.HyAppResult;
import  cn.stylefeng.guns.webApi.huanying.service.HyAppService;
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
 * 幻影破解 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-16
 */
@Service
public class HyAppServiceImpl extends ServiceImpl<HyAppMapper, HyApp> implements HyAppService {

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
    public List<HyAppResult> findListBySpec(String utDid){
        return baseMapper.findListBySpec(utDid);
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
