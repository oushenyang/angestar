package cn.stylefeng.guns.modular.card.service;

import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import cn.stylefeng.guns.modular.card.model.result.CodeCardTypeResult;
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

}
