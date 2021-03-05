package cn.stylefeng.guns.modular.trial.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.trial.entity.Trial;
import cn.stylefeng.guns.modular.trial.mapper.TrialMapper;
import cn.stylefeng.guns.modular.trial.model.params.TrialParam;
import cn.stylefeng.guns.modular.trial.model.result.TrialResult;
import  cn.stylefeng.guns.modular.trial.service.TrialService;
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
 * 试用信息 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-03-04
 */
@Service
public class TrialServiceImpl extends ServiceImpl<TrialMapper, Trial> implements TrialService {

    @Override
    public void add(TrialParam param){
        Trial entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TrialParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(TrialParam param){
        Trial oldEntity = getOldEntity(param);
        Trial newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TrialResult findBySpec(TrialParam param){
        return null;
    }

    @Override
    public List<TrialResult> findListBySpec(Page page, TrialParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(TrialParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Trial> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(TrialParam param){
        return param.getTrialId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Trial getOldEntity(TrialParam param) {
        return this.getById(getKey(param));
    }

    private Trial getEntity(TrialParam param) {
        Trial entity = new Trial();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
