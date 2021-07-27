package cn.stylefeng.guns.modular.apiManage.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiManageParam;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口管理  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-21
 */
public interface ApiManageService extends IService<ApiManage> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void add(ApiManageParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void delete(ApiManageParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    void update(ApiManageParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    ApiManageResult findBySpec(ApiManageParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
    List<Map<String, Object>> findListBySpec(Page page, ApiManageParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-21
     */
     LayuiPageInfo findPageBySpec(ApiManageParam param);

    /**
     * 从redis中查接口管理
     * @param apiCode 接口编码
     * @param callCode 应用调用码
     * @return 接口信息
     */
    ApiManageApi getApiManageByRedis(String apiCode, String callCode);

    /**
     * 同步api接口
     */
    void sync();
}
