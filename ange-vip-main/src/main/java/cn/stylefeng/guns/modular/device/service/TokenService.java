package cn.stylefeng.guns.modular.device.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.model.params.TokenParam;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import com.baomidou.mybatisplus.extension.service.IService;

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

}
