layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 应用类型表管理
     */
    var AppPowerType = {
        tableId: "appPowerTypeTable"
    };

    /**
     * 初始化表格的列
     */
    AppPowerType.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {align: 'center',field: 'appPowerTypeId', fixed: 'left',type: 'checkbox'},
            {align: 'center',field: 'appName', sort: true, title: '分类应用名'},
            {align: 'center',field: 'appCode', sort: true, title: '分类编码'},
            {align: 'center',field: 'customData', sort: true, title: '应用自定义数据'},
            {align: 'center',field: 'createUser', sort: true, title: '创建人'},
            {align: 'center',field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center',field: 'updateUser', sort: true, title: '更新人'},
            {align: 'center',field: 'updateTime', sort: true, title: '更新时间'},
            {align: 'center',align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AppPowerType.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(AppPowerType.tableId, {page:{curr:1},where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AppPowerType.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加应用类型表',
            area: '700px',
            content: Feng.ctxPath + '/appPowerType/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AppPowerType.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AppPowerType.exportExcel = function () {
        var checkRows = table.checkStatus(AppPowerType.tableId);
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
    AppPowerType.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改应用类型表',
            area: '700px',
            content: Feng.ctxPath + '/appPowerType/edit?appPowerTypeId=' + data.appPowerTypeId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AppPowerType.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AppPowerType.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appPowerType/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AppPowerType.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("appPowerTypeId", data.appPowerTypeId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AppPowerType.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].appPowerTypeId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appPowerType/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AppPowerType.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定要删除这些数据?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AppPowerType.tableId,
        url: Feng.ctxPath + '/appPowerType/list',
        page: true,
        toolbar: '#' + AppPowerType.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: AppPowerType.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AppPowerType.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AppPowerType.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AppPowerType.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AppPowerType.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AppPowerType.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(AppPowerType.tableId);
        } else if(obj.event === 'batchRemove'){
            AppPowerType.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AppPowerType.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AppPowerType.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AppPowerType.onDeleteItem(data);
        }
    });
});
