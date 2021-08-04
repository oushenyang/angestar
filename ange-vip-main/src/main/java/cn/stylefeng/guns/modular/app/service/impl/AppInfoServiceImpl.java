package cn.stylefeng.guns.modular.app.service.impl;

import cn.hutool.core.text.StrBuilder;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.core.constant.type.BuyCardType;
import cn.stylefeng.guns.modular.agent.service.AgentAppService;
import cn.stylefeng.guns.modular.demos.service.AsyncService;
import cn.stylefeng.guns.sys.core.constant.state.RedisExpireTime;
import cn.stylefeng.guns.sys.core.constant.state.RedisType;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.mapper.AppInfoMapper;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppInfoResult;
import  cn.stylefeng.guns.modular.app.service.AppInfoService;
import cn.stylefeng.guns.sys.core.auth.util.RedisUtil;
import cn.stylefeng.guns.sys.core.constant.state.UserLogMsg;
import cn.stylefeng.guns.sys.core.constant.state.UserLogType;
import cn.stylefeng.guns.sys.core.exception.apiResult.AppInfoApi;
import cn.stylefeng.guns.sys.core.exception.SystemApiException;
import cn.stylefeng.guns.sys.core.log.LogManager;
import cn.stylefeng.guns.sys.core.log.factory.LogTaskFactory;
import cn.stylefeng.guns.sys.core.util.Contrast;
import cn.stylefeng.guns.sys.core.util.CreateNamePicture;
import cn.stylefeng.guns.sys.core.util.NumToChUtil;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
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

        //应用快捷页面编号
        entity.setAppQuick(wordAndNum("",12));

        //生成加密密匙
//        entity.setWebKey(wordAndNum("",16));

        //生成加密盐
//        entity.setWebSalt(wordAndNum("",8));

        this.save(entity);
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.APP.getType(),userId,
                userId, UserLogMsg.APP_ADD.getLogName(),StrUtil.format(UserLogMsg.APP_ADD.getMessage(), "【"+entity.getAppName()+"】")));
        param.setAppId(entity.getAppId());

        //异步调用插入
        asyncService.insertAppOthers(entity.getAppId(),entity.getAppNum(),LoginContextHolder.getContext().getUserId());
    }

    //生成小写字母+数字,
    public String wordAndNum(String cardTypePrefix,Integer cardTypeLength) {
//        StringBuilder val = new StringBuilder(cardTypePrefix);
        StrBuilder val = StrBuilder.create();
        val.append(cardTypePrefix);
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
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.APP.getType(),userId,
                userId, UserLogMsg.APP_DEL.getLogName(), StrUtil.format(UserLogMsg.APP_DEL.getMessage(), "【"+param.getAppName()+"】")));
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
        if (StringUtils.isNotEmpty(newEntity.getAppName())&& StringUtils.isNotEmpty(oldEntity.getAppName())&&!oldEntity.getAppName().substring(0,1).equals(newEntity.getAppName().substring(0,1))){
            //生成应用头像
            try {
                newEntity.setAppHead(CreateNamePicture.generateImg(newEntity.getAppName()));
            } catch (IOException e) {
                throw new ServiceException(ADD_HEAD_ERROR);
            }
        }
        //插入日志
        Long userId = LoginContextHolder.getContext().getUserId();
        LogManager.me().executeLog(LogTaskFactory.bussinessLog(UserLogType.APP.getType(),userId,
                userId, UserLogMsg.APP_UP.getLogName(), StrUtil.format(UserLogMsg.APP_UP.getMessage(), "【"+newEntity.getAppName()+"】")));
        ToolUtil.copyProperties(newEntity, oldEntity);
        redisUtil.del(RedisType.APP_INFO.getCode() + oldEntity.getAppNum());
        this.updateById(newEntity);
    }

    @Override
    public List<AppInfoResult> findListBySpec(Page page,AppInfoParam param){
        return baseMapper.findListBySpec(page,param);
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
        AppInfoApi appInfoApi = null;
        Object object = redisUtil.get(RedisType.APP_INFO.getCode() + callCode);
        if (ObjectUtil.isNull(object)){
            appInfoApi = baseMapper.findAppInfoApi(callCode);
            if (ObjectUtil.isNotNull(appInfoApi)){
                redisUtil.set(RedisType.APP_INFO.getCode() + callCode , appInfoApi, RedisExpireTime.WEEK.getCode());
            }else {
                //接口错误
                throw new SystemApiException(1, "数据错误","",false);
            }
        }else {
            appInfoApi = (AppInfoApi)object;
        }
        return appInfoApi;
    }

    /**
     * 获取用户的应用数量
     *
     * @param userId 用户id
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    @Override
    public Integer appNum(Long userId) {
        return baseMapper.appNum(userId);
    }

    /**
     * 获取所有应用id
     *
     * @return 应用id集合
     */
    @Override
    public List<Long> getAppIdList() {
        return baseMapper.getAppIdList();
    }
}
