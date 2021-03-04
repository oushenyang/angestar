package cn.stylefeng.guns.modular.trial.mapper;

import cn.stylefeng.guns.modular.trial.entity.Trial;
import cn.stylefeng.guns.modular.trial.model.params.TrialParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 试用信息 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-03-04
 */
public interface TrialMapper extends BaseMapper<Trial> {

    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") TrialParam param);
}
