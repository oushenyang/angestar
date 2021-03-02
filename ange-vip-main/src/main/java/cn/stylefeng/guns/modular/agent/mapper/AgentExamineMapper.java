package cn.stylefeng.guns.modular.agent.mapper;

import cn.stylefeng.guns.modular.agent.entity.AgentExamine;
import cn.stylefeng.guns.modular.agent.model.params.AgentExamineParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理审核表 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-09
 */
public interface AgentExamineMapper extends BaseMapper<AgentExamine> {

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") AgentExamineParam param);
}
