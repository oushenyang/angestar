package cn.stylefeng.guns.modular.card.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.model.params.BatchCardInfoParam;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.model.result.CardInfoApi;
import cn.stylefeng.guns.modular.card.model.result.CardInfoResult;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 卡密表 服务类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
public interface CardInfoService extends IService<CardInfo> {

    /**
     * 新增
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    List<String> add(CardInfoParam param);

    /**
     * 删除
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    void delete(CardInfoParam param);

    /**
     * 批量删除
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    void batchRemove(String ids);

    /**
     * 更新
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    @Transactional(rollbackFor = Exception.class)
    void BatchEdit(BatchCardInfoParam param);

    /**
     * 查询单条数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    CardInfoResult findBySpec(CardInfoParam param);

    /**
     * 查询列表，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
    List<Map<String, Object>> findListBySpec(Page page, CardInfoParam param);

    /**
     * 查询分页数据，Specification模式
     *
     * @author shenyang.ou
     * @Date 2020-04-20
     */
     LayuiPageInfo findPageBySpec(CardInfoParam param);

    /**
     * 通过应用id和卡密查找卡密信息
     * @param appId 应用id
     * @param singleCode 卡密
     * @return 卡密信息
     */
    CardInfoApi getCardInfoApiByAppIdAndCardCode(Long appId, String singleCode);
}
