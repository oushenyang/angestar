package cn.stylefeng.guns.modular.apiManage.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiResultParam;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiResultApi;
import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.sys.modular.system.model.result.ApiResultResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口自定义返回 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-31
 */
public interface ApiResultService extends IService<ApiResult> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    void add(ApiResultParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    void delete(ApiResultParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    void update(ApiResultParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    ApiResultResult findBySpec(ApiResultParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
    List<Map<String, Object>> findListBySpec(Page page, ApiResultParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-07-31
     */
     LayuiPageInfo findPageBySpec(ApiResultParam param);

    ApiResultApi findApiResultApi(Long appId, Integer resultCode);
}
