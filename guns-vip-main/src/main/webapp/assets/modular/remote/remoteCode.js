layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 远程代码管理
     */
    var RemoteCode = {
        tableId: "remoteCodeTable"
    };

    /**
     * 初始化表格的列
     */
    RemoteCode.initColumn = function () {
        return [[
            {align: 'center',field: 'codeId', fixed: 'left',type: 'checkbox'},
            {align: 'center',field: 'appId', fixed: 'left', width: 100,title: '应用ID'},
            {align: 'center',field: 'codeCode', sort: true, title: '代码编码'},
            {align: 'center',field: 'codeValue', sort: true, title: '代码值'},
            {align: 'center',field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center',field: 'updateTime', sort: true, title: '更新时间'},
            {align: 'center',align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    RemoteCode.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(RemoteCode.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    RemoteCode.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加远程代码',
            area: '700px',
            content: Feng.ctxPath + '/remoteCode/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(RemoteCode.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    RemoteCode.exportExcel = function () {
        var checkRows = table.checkStatus(RemoteCode.tableId);
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
    RemoteCode.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改远程代码',
            area: '700px',
            content: Feng.ctxPath + '/remoteCode/edit?codeId=' + data.codeId,
            end: function () {
                admin.getTempData('formOk') && table.reload(RemoteCode.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    RemoteCode.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/remoteCode/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(RemoteCode.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("codeId", data.codeId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    RemoteCode.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].codeId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/remoteCode/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(RemoteCode.tableId);
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
        elem: '#' + RemoteCode.tableId,
        url: Feng.ctxPath + '/remoteCode/list',
        page: true,
        toolbar: '#' + RemoteCode.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: RemoteCode.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        RemoteCode.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        RemoteCode.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        RemoteCode.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + RemoteCode.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            RemoteCode.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(RemoteCode.tableId);
        } else if(obj.event === 'batchRemove'){
            RemoteCode.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + RemoteCode.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            RemoteCode.openEditDlg(data);
        } else if (layEvent === 'delete') {
            RemoteCode.onDeleteItem(data);
        }
    });
});
