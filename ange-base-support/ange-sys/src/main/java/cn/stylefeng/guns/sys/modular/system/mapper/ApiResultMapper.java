package cn.stylefeng.guns.sys.modular.system.mapper;

import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.sys.modular.system.model.result.ApiResultApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 接口自定义返回 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-07-31
 */
public interface ApiResultMapper extends BaseMapper<ApiResult> {

    ApiResultApi findApiResultApi(@Param("appId") Long appId, @Param("resultCode") Integer resultCode);
}
