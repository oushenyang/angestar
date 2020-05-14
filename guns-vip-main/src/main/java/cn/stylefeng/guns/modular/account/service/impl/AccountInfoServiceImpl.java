package cn.stylefeng.guns.modular.account.service.impl;

import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountInfo;
import cn.stylefeng.guns.modular.account.mapper.AccountInfoMapper;
import cn.stylefeng.guns.modular.account.model.params.AccountInfoParam;
import cn.stylefeng.guns.modular.account.model.result.AccountInfoResult;
import  cn.stylefeng.guns.modular.account.service.AccountInfoService;
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
 * 用户账号表 服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-05-14
 */
@Service
public class AccountInfoServiceImpl extends ServiceImpl<AccountInfoMapper, AccountInfo> implements AccountInfoService {

    @Override
    public void add(AccountInfoParam param){
        AccountInfo entity = getEntity(param);
        this.save(entity);
    }

    @Override
    public void delete(AccountInfoParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AccountInfoParam param){
        AccountInfo oldEntity = getOldEntity(param);
        AccountInfo newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AccountInfoResult findBySpec(AccountInfoParam param){
        return null;
    }

    @Override
    public List<AccountInfoResult> findListBySpec(AccountInfoParam param){
        return null;
    }

    @Override
    public LayuiPageInfo findPageBySpec(AccountInfoParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AccountInfo> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AccountInfoParam param){
        return param.getAccountId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AccountInfo getOldEntity(AccountInfoParam param) {
        return this.getById(getKey(param));
    }

    private AccountInfo getEntity(AccountInfoParam param) {
        AccountInfo entity = new AccountInfo();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
