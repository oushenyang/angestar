package cn.stylefeng.guns.modular.remote.mapper;

import cn.stylefeng.guns.modular.remote.entity.RemoteData;
import cn.stylefeng.guns.modular.remote.model.params.RemoteDataParam;
import cn.stylefeng.guns.modular.remote.model.result.RemoteDataApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 远程数据 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-12
 */
public interface RemoteDataMapper extends BaseMapper<RemoteData> {

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") RemoteDataParam param);

    RemoteDataApi getRemoteDataByAppIdAndDataCode(@Param("appId") Long appId, @Param("dataCode") String dataCode);
}
