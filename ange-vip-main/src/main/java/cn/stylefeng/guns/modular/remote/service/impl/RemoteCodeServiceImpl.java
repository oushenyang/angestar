package cn.stylefeng.guns.modular.remote.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.remote.entity.RemoteCode;
import cn.stylefeng.guns.modular.remote.mapper.RemoteCodeMapper;
import cn.stylefeng.guns.modular.remote.model.params.RemoteCodeParam;
import cn.stylefeng.guns.modular.remote.model.result.RemoteCodeResult;
import  cn.stylefeng.guns.modular.remote.service.RemoteCodeService;
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
 * 远程代码 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-19
 */
@Service
public class RemoteCodeServiceImpl extends ServiceImpl<RemoteCodeMapper, RemoteCode> implements RemoteCodeService {

    @Override
    public void add(RemoteCodeParam param){
        RemoteCode entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(RemoteCodeParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(RemoteCodeParam param){
        RemoteCode oldEntity = getOldEntity(param);
        RemoteCode newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public RemoteCodeResult findBySpec(RemoteCodeParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page, RemoteCodeParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(RemoteCodeParam param){
        Page pageContext = getPageContext();
        QueryWrapper<RemoteCode> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(RemoteCodeParam param){
        return param.getCodeId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private RemoteCode getOldEntity(RemoteCodeParam param) {
        return this.getById(getKey(param));
    }

    private RemoteCode getEntity(RemoteCodeParam param) {
        RemoteCode entity = new RemoteCode();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
