package cn.stylefeng.guns.modular.card.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.mapper.CodeCardTypeMapper;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import cn.stylefeng.guns.modular.card.model.result.CodeCardTypeResult;
import  cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 单码卡类列表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-16
 */
@Service
public class CodeCardTypeServiceImpl extends ServiceImpl<CodeCardTypeMapper, CodeCardType> implements CodeCardTypeService {

    @Override
    public void add(CodeCardTypeParam param){
        CodeCardType entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(CodeCardTypeParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(CodeCardTypeParam param){
        CodeCardType oldEntity = getOldEntity(param);
        CodeCardType newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public CodeCardTypeResult findBySpec(CodeCardTypeParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page,CodeCardTypeParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(CodeCardTypeParam param){
        Page pageContext = getPageContext();
        QueryWrapper<CodeCardType> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(CodeCardTypeParam param){
        return param.getCardTypeId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private CodeCardType getOldEntity(CodeCardTypeParam param) {
        return this.getById(getKey(param));
    }

    private CodeCardType getEntity(CodeCardTypeParam param) {
        CodeCardType entity = new CodeCardType();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
