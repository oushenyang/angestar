package cn.stylefeng.guns.sys.modular.system.mapper;

import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 基sql Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
public interface SqlMapper extends BaseMapper<Sql> {

    /**
     * 获取ztree的节点列表
     */
    List<ZTreeNode> sqlTree(@Param("sqlTypeId") Long sqlTypeId);

    /**
     * where parentIds like ''
     */
    List<Sql> likeParentIds(@Param("sqlId") Long sqlId);
}
