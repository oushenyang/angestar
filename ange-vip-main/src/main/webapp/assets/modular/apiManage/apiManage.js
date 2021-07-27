layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 接口管理管理
     */
    var ApiManage = {
        tableId: "apiManageTable"
    };

    /**
     * 初始化表格的列
     */
    ApiManage.initColumn = function () {
        return [[
            // {align: 'center',field: 'apiManageId', type: 'checkbox'},
            {align: 'center',field: 'apiTypeName', title: '分类'},
            {align: 'center',field: 'apiName', title: '接口名称'},
            {align: 'center',field: 'apiCode', title: '接口编码'},
            {align: 'center',field: 'parameterNum', title: '参数数量'},
            {align: 'center',field: 'sort', title: '排序'},
            {align: 'center',field: 'createTime', title: '创建时间'},
            {align: 'center',field: 'updateTime', title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ApiManage.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ApiManage.tableId, {page:{curr:1},where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    ApiManage.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加接口管理',
            area: '1000px',
            content: Feng.ctxPath + '/apiManage/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(ApiManage.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    ApiManage.exportExcel = function () {
        var checkRows = table.checkStatus(ApiManage.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    ApiManage.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改接口管理',
            area: '1000px',
            content: Feng.ctxPath + '/apiManage/edit?apiManageId=' + data.apiManageId,
            end: function () {
                admin.getTempData('formOk') && table.reload(ApiManage.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ApiManage.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/apiManage/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ApiManage.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("apiManageId", data.apiManageId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    ApiManage.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].apiManageId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/apiManage/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(ApiManage.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定要删除这些数据?", operation);
    };

    /**
     * 同步
     *
     * @param obj 选择的行数据
     */
    ApiManage.sync = function(obj){
        var operation = function () {
            var loading = top.layer.msg('处理中', {icon: 16, shade:[0.1, '#000'], time: false});
            var ajax = new $ax(Feng.ctxPath + "/apiManage/sync", function (data) {
                top.layer.close(loading);
                Feng.success("同步成功!");
                table.reload(ApiManage.tableId);
            }, function (data) {
                top.layer.close(loading);
                Feng.error("同步失败!" + data.responseJSON.message + "!");
            },true);
            // ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定同步?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + ApiManage.tableId,
        url: Feng.ctxPath + '/apiManage/list',
        toolbar: '#' + ApiManage.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-80",
        page: {limit: 50, limits: [50, 100, 150, 200, 250, 300]},
        where:{
            type:$('#type').val()
        },
        cellMinWidth: 100,
        cols: ApiManage.initColumn(),
        done : function(res, curr, count) {
            var data = res.data;
            console.log(res.data)
            var mergeIndex = 0;//定位需要添加合并属性的行数
            var mark = 1; //这里涉及到简单的运算，mark是计算每次需要合并的格子数
            var columsName = ['apiTypeName'];//需要合并的列名称
            var columsIndex = [0];//需要合并的列索引值

            for (var k = 0; k < columsName.length; k++) { //这里循环所有要合并的列
                var trArr = $(".layui-table-body>.layui-table").find("tr");//所有行
                for (var i = 1; i < res.data.length; i++) { //这里循环表格当前的数据
                    var tdCurArr = trArr.eq(i).find("td").eq(columsIndex[k]);//获取当前行的当前列
                    var tdPreArr = trArr.eq(mergeIndex).find("td").eq(columsIndex[k]);//获取相同列的第一列

                    if (data[i][columsName[k]] === data[i-1][columsName[k]]) { //后一行的值与前一行的值做比较，相同就需要合并
                        mark += 1;
                        tdPreArr.each(function () {//相同列的第一列增加rowspan属性
                            $(this).attr("rowspan", mark);
                        });
                        tdCurArr.each(function () {//当前行隐藏
                            $(this).css("display", "none");
                        });
                    }else {
                        mergeIndex = i;
                        mark = 1;//一旦前后两行的值不一样了，那么需要合并的格子数mark就需要重新计算
                    }
                }
                mergeIndex = 0;
                mark = 1;
            }
        }
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ApiManage.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ApiManage.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ApiManage.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + ApiManage.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            ApiManage.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(ApiManage.tableId);
        } else if(obj.event === 'batchRemove'){
            ApiManage.batchRemove(obj)
        } else if(obj.event === 'sync'){
            ApiManage.sync(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + ApiManage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ApiManage.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ApiManage.onDeleteItem(data);
        }
    });
});
