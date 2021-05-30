package cn.stylefeng.guns.modular.app.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppInfoResult;
import cn.stylefeng.guns.sys.core.exception.AppInfoApi;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 软件表  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
public interface AppInfoService extends IService<AppInfo> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    void add(AppInfoParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    void delete(AppInfoParam param);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    void update(AppInfoParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    AppInfoResult findBySpec(AppInfoParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    List<AppInfoResult> findListBySpec(Page page,AppInfoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
     LayuiPageInfo findPageBySpec(AppInfoParam param);

    /**
     * 查找当前用户所有软件列表
     *
     * @param userId 用户id
     * @author angedata
     * @Date 2019-07-24
     */
    List<AppInfoParam> getAppInfoList(Long userId);

    /**
     * 查找当前一级代理用户所有拥有总代权限软件列表
     *
     * @param agentUserId 用户id
     * @author angedata
     * @Date 2019-07-24
     */
    List<AppInfoParam> getAgentAppInfoList(Long agentUserId);

    /**
     * 从redis获取软件信息
     * @param callCode 应用调用码
     * @return 应用信息
     */
    AppInfoApi getAppInfoByRedis(String callCode);

    /**
     * 获取用户的应用数量
     *
     * @author shenyang.ou
     * @Date 2020-04-01
     */
    Integer appNum(Long userId);

    /**
     * 获取所有应用id
     * @return 应用id集合
     */
    List<Long> getAppIdList();
}
