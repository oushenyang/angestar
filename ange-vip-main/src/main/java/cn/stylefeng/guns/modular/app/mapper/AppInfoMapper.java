package cn.stylefeng.guns.modular.app.mapper;

import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import cn.stylefeng.guns.modular.app.model.result.AppInfoApi;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 软件表  Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-01
 */
public interface AppInfoMapper extends BaseMapper<AppInfo> {

    /**
     * 查找当前用户所有软件列表
     *
     * @param userId 用户id
     * @author angedata
     * @Date 2019-07-24
     */
    List<AppInfoParam> findAppInfoList(@Param("userId") Long userId);

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") AppInfoParam param);

    AppInfoApi findAppInfoApi(@Param("appId") Long appId);
}
