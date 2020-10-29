package cn.stylefeng.guns.webApi.huanying.mapper;

import cn.stylefeng.guns.webApi.huanying.entity.HyApp;
import cn.stylefeng.guns.webApi.huanying.model.params.HyAppParam;
import cn.stylefeng.guns.webApi.huanying.model.result.HyAppResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 幻影破解 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-16
 */
public interface HyAppMapper extends BaseMapper<HyApp> {

    List<HyAppResult> findListBySpec(@Param("utDid") String utDid,@Param("sign") String sign);

    List<Map<String, Object>> findListByPage(@Param("page") Page page, @Param("param") HyAppParam param,@Param("signList") List<String> signList);
}
