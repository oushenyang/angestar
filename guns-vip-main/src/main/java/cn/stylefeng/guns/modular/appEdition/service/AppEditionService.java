package cn.stylefeng.guns.modular.appEdition.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.appEdition.entity.AppEdition;
import cn.stylefeng.guns.modular.appEdition.model.params.AppEditionParam;
import cn.stylefeng.guns.modular.appEdition.model.result.AppEditionResult;
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
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    void delete(AppEditionParam param);

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
