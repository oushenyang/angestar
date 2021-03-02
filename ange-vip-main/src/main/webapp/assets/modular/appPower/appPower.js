layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 应用授权表管理
     */
    var AppPower = {
        tableId: "appPowerTable"
    };

    /**
     * 初始化表格的列
     */
    AppPower.initColumn = function () {
        return [[
            {align: 'center',field: 'appPowerId', fixed: 'left',type: 'checkbox'},
            {align: 'center',field: 'appName', title: '应用名'},
            {align: 'center',field: 'num', title: '用户数量'},
            {align: 'center',field: 'appTypeCode', title: '应用分类编码'},
            {align: 'center',field: 'sign', title: '签名验证'},
            {align: 'center',field: 'applicationName', title: '应用入口'},
            {align: 'center',field: 'whetherLegal', title: '是否授权', templet: '#whetherLegalTpl'},
            {align: 'center',field: 'whetherShow', title: '是否显示', templet: '#whetherShow'},
            {align: 'center',field: 'whetherSanction', title: '是否制裁',templet: '#whetherSanctionTpl'},
            // {align: 'center',field: 'customData', title: '应用自定义数据'},
            {align: 'center',field: 'sanctionTime', sort: true, title: '制裁时间'},
            {align: 'center',field: 'createTime', title: '创建时间'},
            {align: 'center',toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AppPower.search = function () {
        var queryData = {};
        queryData['appCode'] = $("#appCode").val();
        queryData['appName'] = $("#appName").val();
        table.reload(AppPower.tableId, {page:{curr:1},where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AppPower.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加应用授权表',
            area: '700px',
            content: Feng.ctxPath + '/appPower/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AppPower.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AppPower.exportExcel = function () {
        var checkRows = table.checkStatus(AppPower.tableId);
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
    AppPower.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改应用授权表',
            area: '700px',
            content: Feng.ctxPath + '/appPower/edit?appPowerId=' + data.appPowerId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AppPower.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AppPower.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appPower/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AppPower.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("appPowerId", data.appPowerId);
            // ajax.set("sign", data.sign);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AppPower.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].appPowerId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appPower/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AppPower.tableId);
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
        elem: '#' + AppPower.tableId,
        url: Feng.ctxPath + '/appPower/list',
        page: true,
        // toolbar: '#' + AppPower.tableId + '-toolbar',
        //         defaultToolbar: [{
        //             title:'刷新',
        //             layEvent: 'refresh',
        //             icon: 'layui-icon-refresh',
        //         }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: AppPower.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AppPower.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AppPower.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AppPower.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AppPower.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AppPower.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(AppPower.tableId);
        } else if(obj.event === 'batchRemove'){
            AppPower.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AppPower.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AppPower.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AppPower.onDeleteItem(data);
        }
    });
});
