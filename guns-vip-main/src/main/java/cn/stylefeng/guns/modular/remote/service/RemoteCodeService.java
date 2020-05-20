package cn.stylefeng.guns.modular.remote.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.remote.entity.RemoteCode;
import cn.stylefeng.guns.modular.remote.model.params.RemoteCodeParam;
import cn.stylefeng.guns.modular.remote.model.result.RemoteCodeResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 远程代码 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-19
 */
public interface RemoteCodeService extends IService<RemoteCode> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    void add(RemoteCodeParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    void delete(RemoteCodeParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    void update(RemoteCodeParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    RemoteCodeResult findBySpec(RemoteCodeParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
    List<Map<String, Object>> findListBySpec(Page page,RemoteCodeParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-19
     */
     LayuiPageInfo findPageBySpec(RemoteCodeParam param);

}
