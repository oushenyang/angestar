package cn.stylefeng.guns.modular.card.mapper;

import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

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
     * 根据应用id获取卡类信息
     *
     * @param appId 应用id
     * @return 卡类信息
     */
    List<CodeCardType> findCardTypeByAppId(@Param("appId")Long appId,@Param("userId")Long userId);

    void addCardTypeBySql(@Param("sqlStr") String sqlStr);
}
