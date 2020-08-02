package cn.stylefeng.guns.modular.device.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.device.entity.Token;
import cn.stylefeng.guns.modular.device.mapper.TokenMapper;
import cn.stylefeng.guns.modular.device.model.params.TokenParam;
import cn.stylefeng.guns.modular.device.model.result.TokenResult;
import  cn.stylefeng.guns.modular.device.service.TokenService;
import cn.stylefeng.roses.core.util.ToolUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 登录信息 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-08-02
 */
@Service
public class TokenServiceImpl extends ServiceImpl<TokenMapper, Token> implements TokenService {

    @Override
    public void add(TokenParam param){
        Token entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(TokenParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void update(TokenParam param){
        Token oldEntity = getOldEntity(param);
        Token newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public TokenResult findBySpec(TokenParam param){
        return null;
    }

    @Override
    public List<TokenResult> findListBySpec(TokenParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(TokenParam param){
        Page pageContext = getPageContext();
        QueryWrapper<Token> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(TokenParam param){
        return param.getTokenId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Token getOldEntity(TokenParam param) {
        return this.getById(getKey(param));
    }

    private Token getEntity(TokenParam param) {
        Token entity = new Token();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
