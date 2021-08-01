package cn.stylefeng.guns.modular.apiManage.mapper;

import cn.stylefeng.guns.modular.apiManage.entity.ApiManage;
import cn.stylefeng.guns.modular.apiManage.model.params.ApiManageParam;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiManageApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 接口管理  Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-21
 */
public interface ApiManageMapper extends BaseMapper<ApiManage> {

    /**
     * 分页查询接口
     * @param page 分页数据
     * @param param 查询条件
     * @return 结果
     */
    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") ApiManageParam param);

    /**
     * api获取接口管理
     * @param apiCode 编码
     * @param callCode 调用码
     * @return 结果
     */
    ApiManageApi findApiManageApi(@Param("apiCode") String apiCode, @Param("callCode") String callCode);

    List<ApiManageApi> findApiManageApiListByCallCode(@Param("callCode") String callCode);
}
