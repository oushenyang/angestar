package cn.stylefeng.guns.webApi.huanying.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.model.params.HyAppParam;
import cn.stylefeng.guns.webApi.huanying.model.result.HyAppResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 幻影破解 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-16
 */
public interface HyAppService extends IService<HyApp> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-07-16
     */
    void add(HyAppParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-07-16
     */
    void delete(HyAppParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-07-16
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-07-16
     */
    void update(HyAppParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-07-16
     */
    HyAppResult findBySpec(HyAppParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-07-16
     */
    List<HyAppResult> findListBySpec(String utDid,String sign);

    List<HyAppResult> findListByModelAndSignAndAppName(String utDid,String sign,String appName);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-07-16
     */
     LayuiPageInfo findPageBySpec(HyAppParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-10-28
     */
    List<Map<String, Object>> findListByPage(Page page, HyAppParam param,List<String> signList);

}
