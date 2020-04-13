package cn.stylefeng.guns.modular.appEdition.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.appEdition.entity.AppEdition;
import cn.stylefeng.guns.modular.appEdition.mapper.AppEditionMapper;
import cn.stylefeng.guns.modular.appEdition.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.appEdition.model.result.AppEditionResult;
import  cn.stylefeng.guns.modular.appEdition.service.AppEditionService;
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
 * 应用版本表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-12
 */
@Service
public class AppEditionServiceImpl extends ServiceImpl<AppEditionMapper, AppEdition> implements AppEditionService {

    @Override
    public void add(AppEditionParam param){
        AppEdition entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AppEditionParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AppEditionParam param){
        AppEdition oldEntity = getOldEntity(param);
        AppEdition newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AppEditionResult findBySpec(AppEditionParam param){
        return null;
    }

    @Override
    public List<AppEditionResult> findListBySpec(AppEditionParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AppEditionParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AppEdition> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AppEditionParam param){
        return param.getEditionId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AppEdition getOldEntity(AppEditionParam param) {
        return this.getById(getKey(param));
    }

    private AppEdition getEntity(AppEditionParam param) {
        AppEdition entity = new AppEdition();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    /**
     * 查询版本列表
     *
     * @param page        分页参数
     * @param appId       软件ID
     * @param editionName 版本名称版本名称
     * @param userId      用户ID
     * @return 版本列表
     */
    @Override
    public List<Map<String, Object>> getAppEditions(Page page, Long appId, String editionName, Long userId) {
        return baseMapper.findAppEditions(page,appId,editionName,userId);
    }

}
