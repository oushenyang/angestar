package cn.stylefeng.guns.modular.apiManage.mapper;

import cn.stylefeng.guns.sys.modular.system.entity.ApiResult;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiResultParam;
import cn.stylefeng.guns.modular.apiManage.model.result.ApiResultApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") ApiResultParam param);
}
