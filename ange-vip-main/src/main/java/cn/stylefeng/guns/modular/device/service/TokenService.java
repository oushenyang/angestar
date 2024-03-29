package cn.stylefeng.guns.modular.device.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import cn.stylefeng.guns.sys.core.exception.apiResult.CardInfoApi;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.model.params.TokenParam;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import cn.stylefeng.guns.sys.core.exception.apiResult.AppInfoApi;
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
    void deleteByCardId(Long token);

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
     * 获取上次登录token
     *
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    TokenResult getLastTokenByCardOrUserId(Long cardOrUserId);

    /**
     * 创建token
     * @param apiManage 接口信息
     * @param cardInfoApi 卡密信息
     * @param appInfoApi 应用信息
     * @param mac mac地址
     * @param model 设备型号
     * @return 接口
     */
    boolean createToken(ApiManageApi apiManage, CardInfoApi cardInfoApi, AppInfoApi appInfoApi, String mac, String model, String holdCheck, Date expireTime);

    /**
     * 获取今日在线人数
     * @param userId 用户id
     * @return
     */
    Integer onlineNum(Long userId);

    /**
     * 获取指定限制分钟内和版本号的在线人数
     *
     * @param appId 应用id
     * @param limit 指定分钟内
     * @param edition 版本号
     * @return 在线人数
     */
    Integer getOnlineNumByRedis(Long appId, String limit, String edition);

    /**
     * 获取卡密的所有token信息
     * @param card 卡密
     * @return token集合
     */
    List<Token> getTokenListByCardAndRedis(String card);
}
