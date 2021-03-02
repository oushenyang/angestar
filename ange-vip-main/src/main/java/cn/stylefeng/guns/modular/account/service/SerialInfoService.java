package cn.stylefeng.guns.modular.account.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.SerialInfo;
import cn.stylefeng.guns.modular.account.model.params.SerialInfoParam;
import cn.stylefeng.guns.modular.account.model.result.SerialInfoResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 注册码表 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-29
 */
public interface SerialInfoService extends IService<SerialInfo> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    List<String> add(SerialInfoParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    void delete(SerialInfoParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    void update(SerialInfoParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    SerialInfoResult findBySpec(SerialInfoParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
    List<Map<String, Object>> findListBySpec(Page page, SerialInfoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-29
     */
     LayuiPageInfo findPageBySpec(SerialInfoParam param);

}
