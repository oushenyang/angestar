package cn.stylefeng.guns.modular.remote.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.remote.entity.RemoteData;
import cn.stylefeng.guns.modular.remote.mapper.RemoteDataMapper;
import cn.stylefeng.guns.modular.remote.model.params.RemoteDataParam;
import cn.stylefeng.guns.modular.remote.model.result.RemoteDataResult;
import  cn.stylefeng.guns.modular.remote.service.RemoteDataService;
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
 * 远程数据 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-12
 */
@Service
public class RemoteDataServiceImpl extends ServiceImpl<RemoteDataMapper, RemoteData> implements RemoteDataService {

    @Override
    public void add(RemoteDataParam param){
        RemoteData entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(RemoteDataParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(RemoteDataParam param){
        RemoteData oldEntity = getOldEntity(param);
        RemoteData newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public RemoteDataResult findBySpec(RemoteDataParam param){
        return null;
    }

    @Override
    public List<RemoteDataResult> findListBySpec(RemoteDataParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(RemoteDataParam param){
        Page pageContext = getPageContext();
        QueryWrapper<RemoteData> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(RemoteDataParam param){
        return param.getDataId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private RemoteData getOldEntity(RemoteDataParam param) {
        return this.getById(getKey(param));
    }

    private RemoteData getEntity(RemoteDataParam param) {
        RemoteData entity = new RemoteData();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
