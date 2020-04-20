package cn.stylefeng.guns.modular.card.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.card.entity.CardInfo;
import cn.stylefeng.guns.modular.card.mapper.CardInfoMapper;
import cn.stylefeng.guns.modular.card.model.params.CardInfoParam;
import cn.stylefeng.guns.modular.card.model.result.CardInfoResult;
import  cn.stylefeng.guns.modular.card.service.CardInfoService;
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
 * 卡密表 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-20
 */
@Service
public class CardInfoServiceImpl extends ServiceImpl<CardInfoMapper, CardInfo> implements CardInfoService {

    @Override
    public void add(CardInfoParam param){
        CardInfo entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CardInfoParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(CardInfoParam param){
        CardInfo oldEntity = getOldEntity(param);
        CardInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CardInfoResult findBySpec(CardInfoParam param){
        return null;
    }

    @Override
    public List<CardInfoResult> findListBySpec(CardInfoParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(CardInfoParam param){
        Page pageContext = getPageContext();
        QueryWrapper<CardInfo> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(CardInfoParam param){
        return param.getCardId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private CardInfo getOldEntity(CardInfoParam param) {
        return this.getById(getKey(param));
    }

    private CardInfo getEntity(CardInfoParam param) {
        CardInfo entity = new CardInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
