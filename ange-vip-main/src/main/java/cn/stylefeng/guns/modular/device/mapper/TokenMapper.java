package cn.stylefeng.guns.modular.device.mapper;

import cn.stylefeng.guns.modular.device.entity.Token;
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
}
