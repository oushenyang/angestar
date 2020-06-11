package cn.stylefeng.guns.modular.agent.mapper;

import cn.stylefeng.guns.modular.agent.entity.AgentApp;
import cn.stylefeng.guns.modular.agent.model.params.AgentAppParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理软件表  Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-20
 */
public interface AgentAppMapper extends BaseMapper<AgentApp> {

    /**
     * 查询代理分页列表
     * @param page 分页参数
     * @param param 查询参数
     * @return 结果
     */
    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") AgentAppParam param);
}
