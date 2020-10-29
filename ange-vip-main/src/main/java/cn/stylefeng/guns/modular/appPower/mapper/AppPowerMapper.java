package cn.stylefeng.guns.modular.appPower.mapper;

import cn.stylefeng.guns.modular.appPower.entity.AppPower;
import cn.stylefeng.guns.modular.appPower.model.params.AppPowerParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用授权表  Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
public interface AppPowerMapper extends BaseMapper<AppPower> {

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") AppPowerParam param);
}
