package cn.stylefeng.guns.modular.app.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.entity.AppEdition;
import cn.stylefeng.guns.modular.app.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.app.model.result.AppEditionResult;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiAppEdition;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用版本表  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-12
 */
public interface AppEditionService extends IService<AppEdition> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    void add(AppEditionParam param);

    /**
     * 新增判断版本号是否存在
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    boolean addIsAlreadyAppEdition(Long appId, String editionNum);

    /**
     * 获取最新版本信息
     *
     * @param appId 应用id
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    ApiAppEdition getNewestAppEditionByRedis(Long appId);

    /**
     * 编辑判断版本号是否存在
     *
     * @param appId 应用id
     * @param editionId 版本id
     * @param editionNum 版本号
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    boolean editIsAlreadyAppEdition(Long appId, Long editionId, String editionNum);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    void delete(AppEditionParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    void update(AppEditionParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    AppEditionResult findBySpec(AppEditionParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    List<AppEditionResult> findListBySpec(AppEditionParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
     LayuiPageInfo findPageBySpec(AppEditionParam param);

    /**
     * 查询版本列表
     *
     * @param page        分页参数
     * @param appId       软件ID
     * @param editionName 版本名称
     * @param userId      用户Id
     * @return 版本列表
     */
    List<Map<String, Object>> getAppEditions(Page page, Long appId, String editionName, Long userId);
}
