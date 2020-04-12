package cn.stylefeng.guns.modular.appEdition.mapper;

import cn.stylefeng.guns.modular.appEdition.entity.AppEdition;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 应用版本表  Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-12
 */
public interface AppEditionMapper extends BaseMapper<AppEdition> {

    /**
     * 查询版本列表
     *
     * @param page        分页参数
     * @param appId       软件ID
     * @param editionName 版本名称
     * @return 版本列表
     */
    List<Map<String, Object>> findAppEditions(@Param("page")Page page, @Param("appId")Long appId, @Param("editionName")String editionName, @Param("userId")Long userId);

}
