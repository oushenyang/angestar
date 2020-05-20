package cn.stylefeng.guns.modular.remote.mapper;

import cn.stylefeng.guns.modular.remote.entity.RemoteCode;
import cn.stylefeng.guns.modular.remote.model.params.RemoteCodeParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 远程代码 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-19
 */
public interface RemoteCodeMapper extends BaseMapper<RemoteCode> {

    /**
     * 获取远程代码分页列表
     * @param page 分页
     * @param param 查询条件
     * @return 结果
     */
    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") RemoteCodeParam param);
}
