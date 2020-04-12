package cn.stylefeng.guns.modular.app.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.mapper.AppInfoMapper;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppInfoResult;
import  cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 软件表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
@Service
public class AppInfoServiceImpl extends ServiceImpl<AppInfoMapper, AppInfo> implements AppInfoService {

    @Override
    public void add(AppInfoParam param) throws IOException {
        AppInfo entity = getEntity(param);
        //生成应用头像
        entity.setAppHead(CreateNamePicture.generateImg(entity.getAppName()));
        this.save(entity);
    }

    @Override
    public void delete(AppInfoParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(AppInfoParam param){
        AppInfo oldEntity = getOldEntity(param);
        AppInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AppInfoResult findBySpec(AppInfoParam param){
        return null;
    }

    @Override
    public List<AppInfoResult> findListBySpec(AppInfoParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AppInfoParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AppInfo> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AppInfoParam param){
        return param.getAppId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AppInfo getOldEntity(AppInfoParam param) {
        return this.getById(getKey(param));
    }

    private AppInfo getEntity(AppInfoParam param) {
        AppInfo entity = new AppInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    @Override
    public List<AppInfoParam> getAppInfoList(Long userId) {
        return baseMapper.findAppInfoList(userId);
    }
}
