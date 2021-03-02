package cn.stylefeng.guns.modular.card.mapper;

import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单码卡类列表  Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-16
 */
public interface CodeCardTypeMapper extends BaseMapper<CodeCardType> {

    List<Map<String, Object>> findListBySpec(@Param("page")Page page, @Param("param")CodeCardTypeParam param);

    /**
     * 根据用户id获取卡类信息
     * @return 卡类信息
     */
    List<CodeCardType> findCardTypeByUserId(@Param("userId")Long userId);

    @Transactional
    void addCardTypeBySql(Map map);

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param cardTypeIds 已经存在的卡密类型id集合
     * @param cardType 卡密类型
     * @param userId 用户id
     * @return 卡类信息
     */
    List<CodeCardType> findCardTypeByAppIdAndCardTypeIds(@Param("cardTypeIds")List<Long> cardTypeIds, @Param("cardType")Integer cardType, @Param("userId")Long userId);

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param userId 用户id
     * @return 卡类信息
     */
    List<AgentCardResult> findAgentCardResultByAppIdAndCardTypeIds(@Param("userId")Long userId);
}
