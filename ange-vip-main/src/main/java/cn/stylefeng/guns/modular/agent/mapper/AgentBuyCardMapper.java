package cn.stylefeng.guns.modular.agent.mapper;

import cn.stylefeng.guns.modular.agent.entity.AgentBuyCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentBuyCardParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 代理购卡记录 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-12-11
 */
public interface AgentBuyCardMapper extends BaseMapper<AgentBuyCard> {

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") AgentBuyCardParam param);
}
