package cn.stylefeng.guns.modular.device.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiManageApi;
import cn.stylefeng.guns.modular.app.model.result.AppInfoApi;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.model.params.TokenParam;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 登录信息 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
public interface TokenService extends IService<Token> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    void add(TokenParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    void delete(TokenParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    void deleteByToken(String token);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    void update(TokenParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    TokenResult findBySpec(TokenParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    List<TokenResult> findListBySpec(TokenParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
     LayuiPageInfo findPageBySpec(TokenParam param);

    /**
     * 创建token
     * @param apiManage 接口信息
     * @param cardInfoApi 卡密信息
     * @param appInfoApi 应用信息
     * @param mac mac地址
     * @param model 设备型号
     * @return 接口
     */
    boolean createToken(ApiManageApi apiManage, CardInfoApi cardInfoApi, AppInfoApi appInfoApi, String mac, String model, String holdCheck,Date expireTime);
}
