package cn.stylefeng.guns.modular.app.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.app.entity.AppFeedback;
import cn.stylefeng.guns.modular.app.mapper.AppFeedbackMapper;
import cn.stylefeng.guns.modular.app.model.params.AppFeedbackParam;
import cn.stylefeng.guns.modular.app.model.result.AppFeedbackResult;
import  cn.stylefeng.guns.modular.app.service.AppFeedbackService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * 应用反馈列表 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2021-08-06
 */
@Service
public class AppFeedbackServiceImpl extends ServiceImpl<AppFeedbackMapper, AppFeedback> implements AppFeedbackService {
    /**
     * 删除
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @Override
    public void delete(AppFeedbackParam param){
        this.removeById(getKey(param));
    }

    /**
     * 批量删除
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    /**
     * 查询分页数据
     *
     * @author shenyang.ou
     * @Date 2021-08-06
     */
    @Override
    public List<AppFeedbackResult> findListPage(Page page, AppFeedbackParam param){
        return baseMapper.findListPage(page,param);
    }

    private Serializable getKey(AppFeedbackParam param){
        return param.getFeedbackId();
    }

}
