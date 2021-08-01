package cn.stylefeng.guns.modular.app.mapper;

import cn.stylefeng.guns.modular.app.entity.AppEdition;
import cn.stylefeng.guns.sys.core.exception.apiResult.ApiAppEdition;
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

    /**
     * 编辑判断版本号是否存在
     *
     * @param appId 应用id
     * @param editionId 版本id
     * @param editionNum 版本号
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    List<AppEdition> editIsAlreadyAppEdition(@Param("appId")Long appId, @Param("editionId")Long editionId, @Param("editionNum")String editionNum);

    /**
     * 获取最新版本信息
     *
     * @param appId 应用id
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    ApiAppEdition getNewestAppEdition(@Param("appId")Long appId);
}
