package cn.stylefeng.guns.modular.card.mapper;

import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 卡密表 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
public interface CardInfoMapper extends BaseMapper<CardInfo> {

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param")CardInfoParam param);

    void saveCardBatch(@Param("cardInfos") List<CardInfo> cardInfos);
}
