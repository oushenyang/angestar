layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 用户账号表管理
     */
    var AccountInfo = {
        tableId: "accountInfoTable"
    };

    /**
     * 初始化表格的列
     */
    AccountInfo.initColumn = function () {
        return [[
            {align: 'center',field: 'accountId', fixed: 'left',type: 'checkbox'},
            {align: 'center',field: 'appName',fixed: 'left', width: 100, title: '所属应用'},
            {align: 'center',field: 'account', title: '账号'},
            {align: 'center',field: 'accountStatus', sort: true, title: '状态',templet: '#accountStatusTpl'},
            {align: 'center',field: 'registrationTime', sort: true, title: '注册时间'},
            {align: 'center',field: 'expireTime', sort: true, title: '到期时间'},
            {align: 'center',field: 'accountPoint', title: '用户点数'},
            // {align: 'center',field: 'surplusPoint', title: '剩余点数'},
            {align: 'center',field: 'referrer', title: '推荐人'},
            {align: 'center',field: 'referrerCode',title: '推荐码'},
            {align: 'center',field: 'remark', title: '备注'},
            {align: 'center',toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AccountInfo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(AccountInfo.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AccountInfo.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加用户账号表',
            area: '700px',
            content: Feng.ctxPath + '/accountInfo/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AccountInfo.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AccountInfo.exportExcel = function () {
        var checkRows = table.checkStatus(AccountInfo.tableId);
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
    AccountInfo.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改用户账号表',
            area: '700px',
            content: Feng.ctxPath + '/accountInfo/edit?accountId=' + data.accountId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AccountInfo.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AccountInfo.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/accountInfo/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AccountInfo.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("accountId", data.accountId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AccountInfo.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].accountId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/accountInfo/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AccountInfo.tableId);
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
        elem: '#' + AccountInfo.tableId,
        url: Feng.ctxPath + '/accountInfo/list',
        page: true,
        toolbar: '#' + AccountInfo.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-115",
        cellMinWidth: 100,
        cols: AccountInfo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AccountInfo.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AccountInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AccountInfo.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AccountInfo.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AccountInfo.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(AccountInfo.tableId);
        } else if(obj.event === 'batchRemove'){
            AccountInfo.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AccountInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AccountInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AccountInfo.onDeleteItem(data);
        }
    });
});
