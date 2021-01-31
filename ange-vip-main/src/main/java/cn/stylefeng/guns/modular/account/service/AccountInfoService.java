package cn.stylefeng.guns.modular.account.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountInfo;
import cn.stylefeng.guns.modular.account.model.params.AccountInfoParam;
import cn.stylefeng.guns.modular.account.model.result.AccountInfoResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

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
    List<Map<String, Object>> findListBySpec(Page page, AccountInfoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-05-14
     */
     LayuiPageInfo findPageBySpec(AccountInfoParam param);

    /**
     * 新增判断账号是否存在
     * @param appId 应用id
     * @param account 账号
     * @return 是否
     */
    boolean addAccountWhetherAlready(Long appId, String account);

    /**
     * 获取账号数量
     * @param userId 用户id
     * @return 数量
     */
    Integer accountNum(Long userId);
}
