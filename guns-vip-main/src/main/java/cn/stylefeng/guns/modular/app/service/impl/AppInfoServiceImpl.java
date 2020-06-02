package cn.stylefeng.guns.modular.app.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.mapper.AppInfoMapper;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppInfoResult;
import  cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.util.CardStringRandom;
import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.guns.sys.modular.system.entity.DictType;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.ADD_HEAD_ERROR;
import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.UPDATE_APPEDITION_ERROR;

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
    @Transactional(rollbackFor = Exception.class)
    public void add(AppInfoParam param){
        AppInfo entity = getEntity(param);
        //生成应用头像
        try {
            entity.setAppHead(CreateNamePicture.generateImg(entity.getAppName()));
        } catch (IOException e) {
            throw new ServiceException(ADD_HEAD_ERROR);
        }
        //生成应用编码
        entity.setAppNum(wordAndNum("",12));
        this.save(entity);
        param.setAppId(entity.getAppId());
    }

    //生成小写字母+数字,
    public String wordAndNum(String cardTypePrefix,Integer cardTypeLength) {
        StringBuilder val = new StringBuilder(cardTypePrefix);
        String base = "abcdefghkmnpqrstuvwxyz123456789";
        Random random = new Random();
        for ( int i = 0; i < cardTypeLength; i++ )
        {
            int number = random.nextInt( base.length() );
            val.append( base.charAt( number ) );
        }
        AppInfo temp = new AppInfo();
        temp.setAppNum(val.toString());
        QueryWrapper<AppInfo> queryWrapper = new QueryWrapper<>(temp);
        //递归算法，如果判断编码重复调用自身方法重新生成
        if (baseMapper.selectOne(queryWrapper)!=null){
            wordAndNum("",12);
        }
        return val.toString();
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
    public List<Map<String, Object>> findListBySpec(Page page,AppInfoParam param){
        return baseMapper.findListBySpec(page,param);
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
