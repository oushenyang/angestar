package cn.stylefeng.guns.modular.app.mapper;

import cn.stylefeng.guns.modular.app.entity.AppInfo;
import cn.stylefeng.guns.modular.app.model.params.AppInfoParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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
}
