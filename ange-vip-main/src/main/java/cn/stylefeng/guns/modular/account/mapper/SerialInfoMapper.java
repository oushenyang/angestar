package cn.stylefeng.guns.modular.account.mapper;

import cn.stylefeng.guns.modular.account.entity.SerialInfo;
import cn.stylefeng.guns.modular.account.model.params.SerialInfoParam;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 注册码表 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-29
 */
public interface SerialInfoMapper extends BaseMapper<SerialInfo> {

    void saveSerialBatch(@Param("serialInfos") List<SerialInfo> serialInfos);

    /**
     * 注册码列表分页查询
     * @param page 分页数据
     * @param param 查询条件
     * @return
     */
    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") SerialInfoParam param);
}
