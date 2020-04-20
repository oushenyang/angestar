package cn.stylefeng.guns.sys.modular.system.service;

import cn.hutool.core.bean.BeanUtil;
import cn.stylefeng.guns.base.enums.CommonStatus;
import cn.stylefeng.guns.base.pojo.node.ZTreeNode;
import cn.stylefeng.guns.base.pojo.page.LayuiPageFactory;
import cn.stylefeng.guns.base.pojo.page.LayuiPageInfo;
import cn.stylefeng.guns.sys.core.exception.enums.BizExceptionEnum;
import cn.stylefeng.guns.sys.modular.system.entity.Sql;
import cn.stylefeng.guns.sys.modular.system.entity.SqlType;
import cn.stylefeng.guns.sys.modular.system.mapper.SqlMapper;
import cn.stylefeng.guns.sys.modular.system.model.params.SqlParam;
import cn.stylefeng.guns.sys.modular.system.model.result.SqlResult;
import cn.stylefeng.roses.core.util.ToolUtil;
import cn.stylefeng.roses.kernel.model.exception.RequestEmptyException;
import cn.stylefeng.roses.kernel.model.exception.ServiceException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 基础字典 服务实现类
 * </p>
 *
 * @author stylefeng
 * @since 2019-03-13
 */
@Service
public class SqlService extends ServiceImpl<SqlMapper, Sql> {

    @Autowired
    private SqlTypeService sqlTypeService;

    /**
     * 新增
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public void add(SqlParam param) {

        //判断是否已经存在同编码或同名称字典
        QueryWrapper<Sql> sqlQueryWrapper = new QueryWrapper<>();
        sqlQueryWrapper
                .and(i -> i.eq("code", param.getCode()).or().eq("name", param.getName()))
                .and(i -> i.eq("sql_type_id", param.getSqlTypeId()));
        List<Sql> list = this.list(sqlQueryWrapper);
        if (list != null && list.size() > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }

        Sql entity = getEntity(param);

        //设置pids
        sqlSetPids(entity);

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
    public void delete(SqlParam param) {

        //删除字典的所有子级
        List<Long> subIds = getSubIds(param.getSqlId());
        if (subIds.size() > 0) {
            this.removeByIds(subIds);
        }

        this.removeById(getKey(param));
    }

    /**
     * 更新
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public void update(SqlParam param) {
        Sql oldEntity = getOldEntity(param);
        Sql newEntity = getEntity(param);
        ToolUtil.copyProperties(newEntity, oldEntity);

        //判断编码是否重复
        QueryWrapper<Sql> wrapper = new QueryWrapper<Sql>()
                .and(i -> i.eq("code", newEntity.getCode()).or().eq("name", newEntity.getName()))
                .and(i -> i.ne("sql_id", newEntity.getSqlId()))
                .and(i -> i.eq("sql_type_id", param.getSqlTypeId()));
        int sqls = this.count(wrapper);
        if (sqls > 0) {
            throw new ServiceException(BizExceptionEnum.DICT_EXISTED);
        }

        //设置pids
        sqlSetPids(newEntity);

        this.updateById(newEntity);
    }

    /**
     * 查询单条数据，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public SqlResult findBySpec(SqlParam param) {
        return null;
    }

    /**
     * 查询列表，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public List<SqlResult> findListBySpec(SqlParam param) {
        return null;
    }

    /**
     * 查询分页数据，Specification模式
     *
     * @author stylefeng
     * @Date 2019-03-13
     */
    public LayuiPageInfo findPageBySpec(SqlParam param) {
        QueryWrapper<Sql> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sql_type_id", param.getSqlTypeId());

        if (ToolUtil.isNotEmpty(param.getCondition())) {
            objectQueryWrapper.and(i -> i.eq("code", param.getCondition()).or().eq("name", param.getCondition()));
        }

        objectQueryWrapper.orderByAsc("sort");

        List<Sql> list = this.list(objectQueryWrapper);

        //去除根节点为0的
        if (list.size() > 0) {
            for (Sql sql : list) {
                if (sql.getParentId() != null && sql.getParentId().equals(0L)) {
                    sql.setParentId(null);
                }
            }
        }

        LayuiPageInfo result = new LayuiPageInfo();
        result.setData(list);

        return result;
    }

    /**
     * 获取字典的树形列表（ztree结构）
     *
     * @author fengshuonan
     * @Date 2019/3/14 3:40 PM
     */
    public List<ZTreeNode> sqlTreeList(Long sqlTypeId, Long sqlId) {
        if (sqlTypeId == null) {
            throw new RequestEmptyException();
        }

        List<ZTreeNode> tree = this.baseMapper.sqlTree(sqlTypeId);

        //获取sql的所有子节点
        List<Long> subIds = getSubIds(sqlId);

        //如果传了sqlId，则在返回结果里去掉
        List<ZTreeNode> resultTree = new ArrayList<>();
        for (ZTreeNode zTreeNode : tree) {

            //如果sqlId等于树节点的某个id则去除
            if (ToolUtil.isNotEmpty(sqlId) && sqlId.equals(zTreeNode.getId())) {
                continue;
            }
            if (subIds.contains(zTreeNode.getId())) {
                continue;
            }
            resultTree.add(zTreeNode);
        }

        resultTree.add(ZTreeNode.createParent());

        return resultTree;
    }

    /**
     * 查看sql的详情
     *
     * @author fengshuonan
     * @Date 2019/3/14 5:22 PM
     */
    public SqlResult sqlDetail(Long sqlId) {
        if (ToolUtil.isEmpty(sqlId)) {
            throw new RequestEmptyException();
        }

        SqlResult sqlResult = new SqlResult();

        //查询字典
        Sql detail = this.getById(sqlId);
        if (detail == null) {
            throw new RequestEmptyException();
        }

        //查询父级字典
        if (ToolUtil.isNotEmpty(detail.getParentId())) {
            Long parentId = detail.getParentId();
            Sql sqlType = this.getById(parentId);
            if (sqlType != null) {
                sqlResult.setParentName(sqlType.getName());
            } else {
                sqlResult.setParentName("无父级");
            }
        }

        ToolUtil.copyProperties(detail, sqlResult);

        return sqlResult;
    }

    /**
     * 查询字典列表，通过字典类型
     *
     * @author fengshuonan
     * @Date 2019-06-20 15:14
     */
    public List<Sql> listSqls(Long sqlTypeId) {

        QueryWrapper<Sql> objectQueryWrapper = new QueryWrapper<>();
        objectQueryWrapper.eq("sql_type_id", sqlTypeId);

        List<Sql> list = this.list(objectQueryWrapper);

        if (list == null) {
            return new ArrayList<>();
        } else {
            return list;
        }

    }

    /**
     * 查询字典列表，通过字典类型code
     *
     * @author fengshuonan
     * @Date 2019-06-20 15:14
     */
    public List<Sql> listSqlsByCode(String sqlTypeCode) {

        QueryWrapper<SqlType> wrapper = new QueryWrapper<>();
        wrapper.eq("code", sqlTypeCode);

        SqlType one = this.sqlTypeService.getOne(wrapper);
        return listSqls(one.getSqlTypeId());
    }

    /**
     * 查询字典列表，通过字典类型code
     *
     * @author fengshuonan
     * @Date 2019-06-20 15:14
     */
    public List<Map<String, Object>> getSqlsByCodes(List<String> sqlCodes) {

        QueryWrapper<Sql> wrapper = new QueryWrapper<>();
        wrapper.in("code", sqlCodes).orderByAsc("sort");

        ArrayList<Map<String, Object>> results = new ArrayList<>();

        //转成map
        List<Sql> list = this.list(wrapper);
        for (Sql sql : list) {
            Map<String, Object> map = BeanUtil.beanToMap(sql);
            results.add(map);
        }

        return results;
    }

    private Serializable getKey(SqlParam param) {
        return param.getSqlId();
    }

    private Page getPageContext() {
        return LayuiPageFactory.defaultPage();
    }

    private Sql getOldEntity(SqlParam param) {
        return this.getById(getKey(param));
    }

    private Sql getEntity(SqlParam param) {
        Sql entity = new Sql();
        ToolUtil.copyProperties(param, entity);
        return entity;
    }

    private List<Long> getSubIds(Long sqlId) {

        ArrayList<Long> longs = new ArrayList<>();

        if (ToolUtil.isEmpty(sqlId)) {
            return longs;
        } else {
            List<Sql> list = this.baseMapper.likeParentIds(sqlId);
            for (Sql sql : list) {
                longs.add(sql.getSqlId());
            }
            return longs;
        }
    }

    private void sqlSetPids(Sql param) {
        if (param.getParentId().equals(0L)) {
            param.setParentIds("[0]");
        } else {
            //获取父级的pids
            Long parentId = param.getParentId();
            Sql parent = this.getById(parentId);
            if (parent == null) {
                param.setParentIds("[0]");
            } else {
                param.setParentIds(parent.getParentIds() + "," + "[" + parentId + "]");
            }
        }
    }

}
