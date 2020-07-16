package cn.stylefeng.guns.webApi.huanying.mapper;

import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.model.result.HyAppResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 幻影破解 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-16
 */
public interface HyAppMapper extends BaseMapper<HyApp> {

    List<HyAppResult> findListBySpec(@Param("utDid") String utDid);
}
