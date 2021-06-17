package cn.stylefeng.guns.modular.account.mapper;

import cn.stylefeng.guns.modular.account.entity.AccountInfo;
import cn.stylefeng.guns.modular.account.model.params.AccountInfoParam;
import cn.stylefeng.guns.modular.account.model.result.AccountMonth;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户账号表 Mapper 接口
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-14
 */
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {

    /**
     * 账号分页列表
     * @param page 分页数据
     * @param param 查询条件
     * @return 结果
     */
    List<Map<String, Object>> findListBySpec(@Param("page") Page page, @Param("param") AccountInfoParam param);

    /**
     * 获取账号数量
     * @param userId 用户id
     * @return 数量
     */
    Integer accountNum(@Param("userId") Long userId);

    List<AccountMonth> getAccountMonth(@Param("userId") Long userId, @Param("date") String format,@Param("countArr")  String[] countArr);
}
