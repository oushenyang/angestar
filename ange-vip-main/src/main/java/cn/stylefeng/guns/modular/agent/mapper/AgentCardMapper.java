package cn.stylefeng.guns.modular.agent.mapper;

import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理卡密 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-22
 */
public interface AgentCardMapper extends BaseMapper<AgentCard> {

    /**
     * 获取代理卡密分页列表
     * @param page 分页数据
     * @param param 查询参数
     * @return 结果
     */
    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") AgentCardParam param);
}
