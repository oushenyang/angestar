package cn.stylefeng.guns.modular.account.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountCardType;
import cn.stylefeng.guns.modular.account.model.params.AccountCardTypeParam;
import cn.stylefeng.guns.modular.account.model.result.AccountCardTypeResult;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 账号卡类列表  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-16
 */
public interface AccountCardTypeService extends IService<AccountCardType> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    void add(AccountCardTypeParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    void delete(AccountCardTypeParam param);

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
     * @Date 2020-04-16
     */
    void update(AccountCardTypeParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    AccountCardTypeResult findBySpec(AccountCardTypeParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    List<Map<String, Object>> findListBySpec(Page page, AccountCardTypeParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
     LayuiPageInfo findPageBySpec(AccountCardTypeParam param);

    /**
     * 根据用户id获取卡类信息
     * @param userId 用户id
     * @return 卡类信息
     */
    List<AccountCardType> getCardTypeByUserId(Long userId);

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param accountCardTypeIds 已经存在的卡密类型id集合
     * @param cardType 卡密类型
     * @param userId 用户id
     * @return 卡类信息
     */
    List<AccountCardType> getCardTypeByAppIdAndCardTypeIds(List<Long> accountCardTypeIds, Integer cardType, Long userId);

    /**
     * 根据应用id创建卡类信息
     * @param sqls sql
     * @return 卡类信息
     */
    List<AccountCardType> addCardTypeBySql(List<Sql> sqls, Long appId);
}
