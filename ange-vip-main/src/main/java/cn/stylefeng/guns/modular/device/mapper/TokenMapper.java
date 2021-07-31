package cn.stylefeng.guns.modular.device.mapper;

import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 登录信息 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
public interface TokenMapper extends BaseMapper<Token> {

    void deleteByCardId(@Param("cardId") Long cardId);

    /**
     * 获取上次登录token
     *
     * @param cardOrUserId
     * @author shenyang.ou
     * @Date 2020-08-02
     */
    TokenResult getLastTokenByCardOrUserId(@Param("cardOrUserId") Long cardOrUserId);

    /**
     * 获取今日在线人数
     *
     * @param userId 用户id
     * @return
     */
    Integer onlineNum(@Param("userId") Long userId);

    /**
     * 获取指定限制分钟内和版本号的在线人数
     *
     * @param appId 应用id
     * @param limit 指定分钟内
     * @param edition 版本号
     * @return 在线人数
     */
    Integer getOnlineNum(@Param("appId") Long appId,@Param("limit") Integer limit,@Param("edition") String edition);
}
