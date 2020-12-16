package cn.stylefeng.guns.modular.app.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.db.entity.DatabaseInfo;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.app.entity.AppEdition;
import cn.stylefeng.guns.modular.app.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.app.service.AppEditionService;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.service.CardInfoService;
import cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.service.ApiManageService;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.mapper.AppInfoMapper;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppInfoApi;
import cn.stylefeng.guns.modular.app.model.result.AppInfoResult;
import  cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.modular.apiManage.service.ApiResultService;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.management.Agent;

import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.ADD_HEAD_ERROR;

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
    @Autowired
    private AsyncService asyncService;
    @Autowired
    private AgentAppService agentAppService;
    @Autowired
    private RedisUtil redisUtil;

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

        //异步调用插入
        asyncService.insertAppOthers(entity.getAppId(),entity.getAppNum(),LoginContextHolder.getContext().getUserId());
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
        //异步调用删除
        asyncService.deleteAppOthers(param.getAppId());
        //清除应用缓存
        redisUtil.del(RedisType.APP_INFO.getCode() + param.getAppNum());
        this.removeById(getKey(param));
    }

    @Override
    public void update(AppInfoParam param){
        AppInfo oldEntity = getOldEntity(param);
        AppInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        redisUtil.del(RedisType.APP_INFO.getCode() + newEntity.getAppNum());
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

    /**
     * 查找当前一级代理用户所有拥有总代权限软件列表
     *
     * @param agentUserId 用户id
     * @author angedata
     * @Date 2019-07-24
     */
    @Override
    public List<AppInfoParam> getAgentAppInfoList(Long agentUserId) {
        return baseMapper.getAgentAppInfoList(agentUserId);
    }

    @Override
    public AppInfoApi getAppInfoByRedis(String callCode) {
        AppInfoApi appInfoApi = (AppInfoApi) redisUtil.get(RedisType.APP_INFO.getCode() + callCode);
        if (ObjectUtil.isNull(appInfoApi)){
            appInfoApi = baseMapper.findAppInfoApi(callCode);
            if (ObjectUtil.isNotNull(appInfoApi)){
                redisUtil.set(RedisType.APP_INFO.getCode() + callCode , appInfoApi, RedisExpireTime.MONTH.getCode());
            }else {
                //接口错误
                throw new SystemApiException(-1, "数据错误","",false);
            }
        }
        return appInfoApi;
    }
}
