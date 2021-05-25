package cn.stylefeng.guns.modular.card.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.stylefeng.guns.base.auth.context.LoginContextHolder;
import cn.stylefeng.guns.base.pojo.node.MenuNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.modular.agent.entity.AgentCard;
import cn.stylefeng.guns.modular.agent.model.params.AgentCardParam;
import cn.stylefeng.guns.modular.agent.model.result.AgentCardResult;
import cn.stylefeng.guns.modular.agent.service.AgentCardService;
import cn.stylefeng.guns.modular.card.entity.CodeCardType;
import cn.stylefeng.guns.modular.card.mapper.CodeCardTypeMapper;
import cn.stylefeng.guns.modular.card.model.params.CodeCardTypeParam;
import cn.stylefeng.guns.modular.card.model.result.CodeCardTypeResult;
import  cn.stylefeng.guns.modular.card.service.CodeCardTypeService;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.entity.Dict;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;

import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.SQL_ERROR;
import static cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum.UPDATE_APPEDITION_ERROR;

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

    @Autowired
    AgentCardService agentCardService;

    @Override
    public void add(CodeCardTypeParam param){
        CodeCardType entity = getEntity(param);
        //判断是否已经存在同名称
        List<CodeCardType> list = this.list(new QueryWrapper<CodeCardType>().eq("card_type_name",param.getCardTypeName()).eq("create_user",LoginContextHolder.getContext().getUserId()));
        if (CollectionUtil.isNotEmpty(list)){
            throw new ServiceException(BizExceptionEnum.CARD_TYPE_NAME_EXISTED);
        }
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

    /**
     * 根据用户id获取卡类信息
     *
     * @param userId 用户id
     * @return 卡类信息
     */
    @Override
    public List<CodeCardType> getCardTypeByUserId(Long userId) {
        return baseMapper.findCardTypeByUserId(userId);
    }

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param cardTypeIds 已经存在的卡密类型id集合
     * @param cardType 卡密类型
     * @param userId 用户id
     * @return 卡类信息
     */
    @Override
    public List<CodeCardType> getCardTypeByAppIdAndCardTypeIds(List<Long> cardTypeIds,Integer cardType,Long userId){
        return baseMapper.findCardTypeByAppIdAndCardTypeIds(cardTypeIds,cardType,userId);
    }

    @Override
    public List<AgentCardResult> getAgentCardResultByAppIdAndCardTypeIds(Long userId) {
        return baseMapper.findAgentCardResultByAppIdAndCardTypeIds(userId);
    }

    /**
     * 排除已经存在的卡类获取剩余卡类信息
     * @param agentAppId 代理应用id
     * @param cardType 卡类类型 0-单码卡密；1-通用卡密；2-注册卡密
     * @return 卡类信息
     */
    @Override
    public List<CodeCardType> getCardTypeByAgentAppIdAndCardType(Long agentAppId,Integer cardType) {
        QueryWrapper<AgentCard> wrapper = new QueryWrapper<>();
        wrapper = wrapper.eq("agent_app_id", agentAppId);
        wrapper = wrapper.eq("card_type", cardType);
        List<AgentCard> agentCards = agentCardService.list(wrapper);
        //已经存在的卡类集合
        List<Long> cardTypeIds = new ArrayList<>();
        agentCards.forEach(agentCard->{
            cardTypeIds.add(agentCard.getCardTypeId());
        });
        return this.getCardTypeByAppIdAndCardTypeIds(cardTypeIds,cardType, LoginContextHolder.getContext().getUserId());
    }

    /**
     * 根据应用id创建卡类信息
     *
     * @param sqls  sql
     * @return 卡类信息
     */
    @Transactional
    @Override
    public List<CodeCardType> addCardTypeBySql(List<Sql> sqls,Long appId) {
        List<CodeCardType> codeCardTypes = new ArrayList<>();
        for (Sql sql : sqls){
            Long cardTypeId = IdWorker.getId();
            Map<String, Object> map = new HashMap<>();
            map.put("sqlStr",sql.getDescription());
            map.put("cardTypeId",cardTypeId);
            map.put("appId",0L);
            map.put("createUser",LoginContextHolder.getContext().getUserId());
            map.put("createTime",new Date());
            try{
                baseMapper.addCardTypeBySql(map);
                CodeCardType codeCardType = new CodeCardType();
                codeCardType.setCardTypeId(cardTypeId);
                codeCardType.setCardTypeName(sql.getName());
                codeCardTypes.add(codeCardType);
            }catch (Exception e){
                throw new ServiceException(SQL_ERROR);
            }
        }
        return codeCardTypes;
    }

    /**
     * 根据用户id和卡密类型和时间值查询卡密类型ID
     * @param userId 用户id
     * @param cardTimeType 卡密类型
     * @param cardTypeData 时间值
     * @return 卡密类型ID
     */
    @Override
    public Long findByCardTimeTypeAndCardTypeData(Long userId,Integer cardTimeType, Integer cardTypeData) {
        return baseMapper.findByCardTimeTypeAndCardTypeData(userId,cardTimeType,cardTypeData);
    }
}
