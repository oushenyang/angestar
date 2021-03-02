package cn.stylefeng.guns.modular.account.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.account.entity.AccountCardType;
import cn.stylefeng.guns.modular.account.mapper.AccountCardTypeMapper;
import cn.stylefeng.guns.modular.account.model.params.AccountCardTypeParam;
import cn.stylefeng.guns.modular.account.model.result.AccountCardTypeResult;
import cn.stylefeng.guns.modular.account.service.AccountCardTypeService;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.SQL_ERROR;

/**
 * <p>
 * 单码卡类列表  服务实现类
 * </p>
 *
 * @author shenyang.ou
 * @since 2020-04-16
 */
@Service
public class AccountCardTypeServiceImpl extends ServiceImpl<AccountCardTypeMapper, AccountCardType> implements AccountCardTypeService {

    @Override
    public void add(AccountCardTypeParam param){
        AccountCardType entity = getEntity(param);
        //判断是否已经存在同名称
        List<AccountCardType> list = this.list(new QueryWrapper<AccountCardType>().eq("card_type_name",param.getCardTypeName()).eq("create_user",LoginContextHolder.getContext().getUserId()));
        if (CollectionUtil.isNotEmpty(list)){
            throw new ServiceException(BizExceptionEnum.CARD_TYPE_NAME_EXISTED);
        }
        this.save(entity);
    }

    @Override
    public void delete(AccountCardTypeParam param){
        this.removeById(getKey(param));
    }

    @Override
    public void batchRemove(String ids){
        List<String> idList = Arrays.asList(ids.split(","));
        this.removeByIds(idList);
    }

    @Override
    public void update(AccountCardTypeParam param){
        AccountCardType oldEntity = getOldEntity(param);
        AccountCardType newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);
        this.updateById(newEntity);
    }

    @Override
    public AccountCardTypeResult findBySpec(AccountCardTypeParam param){
        return null;
    }

    @Override
    public List<Map<String, Object>> findListBySpec(Page page,AccountCardTypeParam param){
        return baseMapper.findListBySpec(page,param);
    }

    @Override
    public LayuiPageInfo findPageBySpec(AccountCardTypeParam param){
        Page pageContext = getPageContext();
        QueryWrapper<AccountCardType> objectQueryWrapper = new QueryWrapper<>();
        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(AccountCardTypeParam param){
        return param.getAccountCardTypeId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private AccountCardType getOldEntity(AccountCardTypeParam param) {
        return this.getById(getKey(param));
    }

    private AccountCardType getEntity(AccountCardTypeParam param) {
        AccountCardType entity = new AccountCardType();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    /**
     * 根据用户id获取卡类信息
     *
     * @param userId 用户id
     * @return 卡类信息
     */
    @Override
    public List<AccountCardType> getCardTypeByUserId(Long userId) {
        return baseMapper.findCardTypeByUserId(userId);
    }

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param accountCardTypeIds 已经存在的卡密类型id集合
     * @param cardType 卡密类型
     * @param userId 用户id
     * @return 卡类信息
     */
    @Override
    public List<AccountCardType> getCardTypeByAppIdAndCardTypeIds(List<Long> accountCardTypeIds,Integer cardType,Long userId){
        return baseMapper.findCardTypeByAppIdAndCardTypeIds(accountCardTypeIds,cardType,userId);
    }

    /**
     * 根据应用id创建卡类信息
     *
     * @param sqls  sql
     * @return 卡类信息
     */
    @Transactional
    @Override
    public List<AccountCardType> addCardTypeBySql(List<Sql> sqls,Long appId) {
        List<AccountCardType> accountCardTypes = new ArrayList<>();
        for (Sql sql : sqls){
            Long accountCardTypeId = IdWorker.getId();
            Map<String, Object> map = new HashMap<>();
            map.put("sqlStr",sql.getDescription());
            map.put("accountCardTypeId",accountCardTypeId);
            map.put("appId",0L);
            map.put("createUser",LoginContextHolder.getContext().getUserId());
            map.put("createTime",new Date());
            try{
                baseMapper.addCardTypeBySql(map);
                AccountCardType accountCardType = new AccountCardType();
                accountCardType.setAccountCardTypeId(accountCardTypeId);
                accountCardType.setCardTypeName(sql.getName());
                accountCardTypes.add(accountCardType);
            }catch (Exception e){
                throw new ServiceException(SQL_ERROR);
            }
        }
        return accountCardTypes;
    }

}
