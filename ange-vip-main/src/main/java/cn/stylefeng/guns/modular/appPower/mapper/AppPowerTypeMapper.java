package cn.stylefeng.guns.modular.appPower.mapper;

import cn.stylefeng.guns.modular.appPower.entity.AppPowerType;
import cn.stylefeng.guns.modular.appPower.model.result.AppPowerTypeResult;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 应用类型表  Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-10-29
 */
public interface AppPowerTypeMapper extends BaseMapper<AppPowerType> {


    List<AppPowerTypeResult> getTypeList();
}
