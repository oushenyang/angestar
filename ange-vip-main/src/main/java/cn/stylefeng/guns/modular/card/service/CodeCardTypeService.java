package cn.stylefeng.guns.modular.card.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.core.constant.state.CardTimeType;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import cn.stylefeng.guns.modular.card.model.result.CodeCardTypeResult;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单码卡类列表  服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-16
 */
public interface CodeCardTypeService extends IService<CodeCardType> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    void add(CodeCardTypeParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    void delete(CodeCardTypeParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-04-12
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    void update(CodeCardTypeParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    CodeCardTypeResult findBySpec(CodeCardTypeParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
    List<Map<String, Object>> findListBySpec(Page page, CodeCardTypeParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-16
     */
     LayuiPageInfo findPageBySpec(CodeCardTypeParam param);

    /**
     * 根据用户id获取卡类信息
     * @param userId 用户id
     * @return 卡类信息
     */
    List<CodeCardType> getCardTypeByUserId(Long userId);

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param cardTypeIds 已经存在的卡密类型id集合
     * @param cardType 卡密类型
     * @param userId 用户id
     * @return 卡类信息
     */
    List<CodeCardType> getCardTypeByAppIdAndCardTypeIds(List<Long> cardTypeIds,Integer cardType,Long userId);

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param userId 用户id
     * @return 卡类信息
     */
    List<AgentCardResult> getAgentCardResultByAppIdAndCardTypeIds(Long userId);

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param agentAppId 代理应用id
     * @param cardType 卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     * @return 卡类信息
     */
    List<CodeCardType> getCardTypeByAgentAppIdAndCardType(Long agentAppId,Integer cardType);

    /**
     * 根据用户id和卡密类型和时间值查询卡密类型ID
     * @param userId 用户id
     * @param cardTimeType 卡密类型
     * @param cardTypeData 时间值
     * @return 卡密类型ID
     */
    Long findByCardTimeTypeAndCardTypeData(Long userId, Integer cardTimeType, Integer cardTypeData);
}
