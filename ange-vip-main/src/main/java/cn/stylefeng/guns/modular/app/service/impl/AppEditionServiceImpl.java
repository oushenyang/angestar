package cn.stylefeng.guns.modular.app.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.modular.app.entity.AppEdition;
import cn.stylefeng.guns.modular.app.mapper.AppEditionMapper;
import cn.stylefeng.guns.modular.app.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.app.model.result.AppEditionResult;
import  cn.stylefeng.guns.modular.app.service.AppEditionService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.*;

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
    @Autowired
    private AppInfoService appInfoService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(AppEditionParam param){
        AppEdition entity = getEntity(param);
        this.save(entity);
        //更新应用最新版本id
        if (!updateAppEditionId(param.getAppId())){
            throw new ServiceException(UPDATE_APPEDITION_ERROR);
        }
    }

    /**
     * 判断版本号是否存在
     *
     * @param appId
     * @param editionNum
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @Override
    public boolean addIsAlreadyAppEdition(Long appId, String editionNum) {
        AppEdition appEdition = new AppEdition();
        appEdition.setEditionNum(editionNum);
        appEdition.setAppId(appId);
        List<AppEdition> list = this.list(new QueryWrapper<>(appEdition));
        return CollectionUtils.isNotEmpty(list);
    }

    /**
     * 编辑判断版本号是否存在
     *
     * @param appId 应用id
     * @param editionId 版本id
     * @param editionNum 版本号
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    @Override
    public boolean editIsAlreadyAppEdition(Long appId, Long editionId, String editionNum) {
        List<AppEdition> appEditions = baseMapper.editIsAlreadyAppEdition(appId,editionId,editionNum);
        if (appEditions != null && appEditions.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 更新软件版本号为最新
     * @param appId
     * @return
     */
    public boolean updateAppEditionId(Long appId){
        AppEdition appEdition = new AppEdition();
        appEdition.setAppId(appId);
        appEdition.setEditionStatus(0);
        List<AppEdition> list = this.list(new QueryWrapper<>(appEdition));
        float num = 0;
        Long editionId = 0l;
        for (AppEdition appEdition1 : list){
            if (Float.parseFloat(appEdition1.getEditionNum())>num){
                num = Float.parseFloat(appEdition1.getEditionNum());
                editionId = appEdition1.getEditionId();
            }
        }
        AppInfo appInfo = new AppInfo();
        appInfo.setAppId(appId);
        appInfo.setVersionNum(editionId);
        return appInfoService.updateById(appInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(AppEditionParam param){
        this.removeById(getKey(param));
        //更新应用最新版本id
        if (!updateAppEditionId(param.getAppId())){
            throw new ServiceException(UPDATE_APPEDITION_ERROR);
        }
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
        //更新应用最新版本id
        if (!updateAppEditionId(param.getAppId())){
            throw new ServiceException(UPDATE_APPEDITION_ERROR);
        }
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
