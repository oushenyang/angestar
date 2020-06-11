package cn.stylefeng.guns.modular.remote.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.remote.entity.RemoteData;
import cn.stylefeng.guns.modular.remote.model.params.RemoteDataParam;
import cn.stylefeng.guns.modular.remote.model.result.RemoteDataResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 远程数据 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-12
 */
public interface RemoteDataService extends IService<RemoteData> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    void add(RemoteDataParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    void delete(RemoteDataParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    void update(RemoteDataParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    RemoteDataResult findBySpec(RemoteDataParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
    List<Map<String, Object>> findListBySpec(Page page, RemoteDataParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-12
     */
     LayuiPageInfo findPageBySpec(RemoteDataParam param);

}
