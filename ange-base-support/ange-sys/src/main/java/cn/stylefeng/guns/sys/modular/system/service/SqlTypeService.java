package cn.stylefeng.guns.sys.modular.system.service;

import cn.stylefeng.guns.base.enums.CommonStatus;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import cn.stylefeng.guns.sys.modular.system.entity.SqlType;
import cn.stylefeng.guns.sys.modular.system.mapper.SqlTypeMapper;
import cn.stylefeng.guns.sys.modular.system.model.params.SqlTypeParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SqlTypeResult;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * sql类型表 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
@Service
public class SqlTypeService extends ServiceImpl<SqlTypeMapper, SqlType> {

    @Autowired
    private SqlService sqlService;

    /**
     * 新增
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public void add(SqlTypeParam param) {

        //判断是否已经存在同编码或同名称sql
        QueryWrapper<SqlType> sqlQueryWrapper = new QueryWrapper<>();
        sqlQueryWrapper.eq("code", param.getCode()).or().eq("name", param.getName());
        List<SqlType> list = this.list(sqlQueryWrapper);
        if (list != null && list.size() > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }

        SqlType entity = getEntity(param);

        //设置状态
        entity.setStatus(CommonStatus.ENABLE.getCode());

        this.save(entity);
    }

    /**
     * 删除
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    @Transactional(rollbackFor = Exception.class)
    public void delete(SqlTypeParam param) {

        if (param == null || param.getSqlTypeId() == null) {
            throw new RequestEmptyException("sql类型id为空");
        }

        //删除sql类型
        this.removeById(getKey(param));

        //删除sql
        this.sqlService.remove(new QueryWrapper<Sql>().eq("sql_type_id", getKey(param)));
    }

    /**
     * 更新
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public void update(SqlTypeParam param) {
        SqlType oldEntity = getOldEntity(param);
        SqlType newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);

        //判断编码是否重复
        QueryWrapper<SqlType> wrapper = new QueryWrapper<SqlType>()
                .and(i -> i.eq("code", newEntity.getCode()).or().eq("name", newEntity.getName()))
                .and(i -> i.ne("sql_type_id", newEntity.getSqlTypeId()));
        int sqls = this.count(wrapper);
        if (sqls > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }

        this.updateById(newEntity);
    }

    /**
     * 查询单条数据，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public SqlTypeResult findBySpec(SqlTypeParam param) {
        return null;
    }

    /**
     * 查询列表，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public List<SqlTypeResult> findListBySpec(SqlTypeParam param) {
        return null;
    }

    /**
     * 查询分页数据，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public LayuiPageInfo findPageBySpec(SqlTypeParam param) {
        Page pageContext = getPageContext();
        QueryWrapper<SqlType> objectQueryWrapper = new QueryWrapper<>();
        if (ToolUtil.isNotEmpty(param.getCondition())) {
            objectQueryWrapper.and(i -> i.eq("code", param.getCondition()).or().eq("name", param.getCondition()));
        }
        if (ToolUtil.isNotEmpty(param.getStatus())) {
            objectQueryWrapper.and(i -> i.eq("status", param.getStatus()));
        }
        if (ToolUtil.isNotEmpty(param.getSystemFlag())) {
            objectQueryWrapper.and(i -> i.eq("system_flag", param.getSystemFlag()));
        }

        pageContext.setAsc("sort");

        IPage page = this.page(pageContext, objectQueryWrapper);
        return LayuiPageFactory.createPageInfo(page);
    }

    private Serializable getKey(SqlTypeParam param) {
        return param.getSqlTypeId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private SqlType getOldEntity(SqlTypeParam param) {
        return this.getById(getKey(param));
    }

    private SqlType getEntity(SqlTypeParam param) {
        SqlType entity = new SqlType();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

}
