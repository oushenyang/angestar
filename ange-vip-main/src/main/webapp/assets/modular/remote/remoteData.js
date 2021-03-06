layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 远程数据管理
     */
    var RemoteData = {
        tableId: "remoteDataTable"
    };

    /**
     * 初始化表格的列
     */
    RemoteData.initColumn = function () {
        return [[
            {align: 'center', field: 'dataId', type: 'checkbox'},
            {align: 'center', field: 'appName', width: 100, title: '所属应用'},
            {align: 'center', field: 'dataCode',title: '数据编码'},
            {align: 'center', field: 'dataValue', title: '数据值'},
            {
                align: 'center', field: 'createType', sort: true, title: '创建类型', templet: function (d) {
                    if (d.createType==0) {
                        return '手动添加';
                    } else {
                        return '接口生成';
                    }
                }
            },
            {align: 'center', field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center', field: 'updateTime', sort: true, title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    RemoteData.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(RemoteData.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    RemoteData.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加远程数据',
            area: '700px',
            content: Feng.ctxPath + '/remoteData/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(RemoteData.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    RemoteData.exportExcel = function () {
        var checkRows = table.checkStatus(RemoteData.tableId);
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
    RemoteData.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改远程数据',
            area: '700px',
            content: Feng.ctxPath + '/remoteData/edit?dataId=' + data.dataId,
            end: function () {
                admin.getTempData('formOk') && table.reload(RemoteData.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    RemoteData.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/remoteData/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(RemoteData.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("dataId", data.dataId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    RemoteData.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            Feng.error("未选中数据!");
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].dataId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/remoteData/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(RemoteData.tableId);
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
        elem: '#' + RemoteData.tableId,
        url: Feng.ctxPath + '/remoteData/list',
        // page: true,
        toolbar: '#' + RemoteData.tableId + '-toolbar',
        defaultToolbar: ['filter'],
        height: "full-80",
        page: {limit: 15, limits: [15, 30, 45, 60, 75, 90, 105, 120, 200]},
        cellMinWidth: 100,
        cols: RemoteData.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        RemoteData.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        RemoteData.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        RemoteData.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + RemoteData.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            RemoteData.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(RemoteData.tableId);
        } else if (obj.event === 'batchRemove') {
            RemoteData.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + RemoteData.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            RemoteData.openEditDlg(data);
        } else if (layEvent === 'delete') {
            RemoteData.onDeleteItem(data);
        }
    });
});
