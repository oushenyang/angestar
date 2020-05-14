package cn.stylefeng.guns.modular.account.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountInfo;
import cn.stylefeng.guns.modular.account.model.params.AccountInfoParam;
import cn.stylefeng.guns.modular.account.model.result.AccountInfoResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 用户账号表 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-14
 */
public interface AccountInfoService extends IService<AccountInfo> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    void add(AccountInfoParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    void delete(AccountInfoParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    void update(AccountInfoParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    AccountInfoResult findBySpec(AccountInfoParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
    List<AccountInfoResult> findListBySpec(AccountInfoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
     LayuiPageInfo findPageBySpec(AccountInfoParam param);

}
