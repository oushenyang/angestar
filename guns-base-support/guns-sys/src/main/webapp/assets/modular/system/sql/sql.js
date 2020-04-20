layui.use(['ax', 'treeTable', 'func'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var treeTable = layui.treeTable;
    var func = layui.func;

    //table的初始化实例
    var insTb;

    /**
     * 基础sql管理
     */
    var Sql = {
        tableId: "sqlTable"
    };

    /**
     * 初始化表格的列
     */
    Sql.initColumn = function () {
        return [
            {type: 'checkbox'},
            {field: 'name', align: "center", title: 'sql名称'},
            {field: 'code', align: "center", title: 'sql编码'},
            {field: 'description', align: "center", title: 'sql的描述'},
            {
                field: 'status', align: "center", title: '状态', templet: function (d) {
                    if (d.status === 'ENABLE') {
                        return "启用";
                    } else {
                        return "禁用";
                    }
                }
            },
            {field: 'createTime', align: "center", sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ];
    };

    /**
     * 点击查询按钮
     */
    Sql.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        Sql.initTable(Sql.tableId, queryData);
    };

    /**
     * 弹出添加对话框
     */
    Sql.openAddDlg = function () {
        func.open({
            height: 650,
            title: '添加sql',
            content: Feng.ctxPath + '/sql/add?sqlTypeId=' + $("#sqlTypeId").val(),
            tableId: Sql.tableId,
            endCallback: function () {
                Sql.initTable(Sql.tableId);
            }
        });
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    Sql.openEditDlg = function (data) {
        func.open({
            height: 650,
            title: '修改sql',
            content: Feng.ctxPath + '/sql/edit?sqlId=' + data.sqlId,
            tableId: Sql.tableId,
            endCallback: function () {
                Sql.initTable(Sql.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Sql.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/sql/delete", function (data) {
                Feng.success("删除成功!");
                Sql.search();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("sqlId", data.sqlId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 渲染表格
     */
    Sql.initTable = function (sqlId, data) {
        return treeTable.render({
            elem: '#' + sqlId,
            tree: {
                iconIndex: 1,           // 折叠图标显示在第几列
                idName: 'sqlId',         // 自定义id字段的名称
                pidName: 'parentId',       // 自定义标识是否还有子节点的字段名称
                haveChildName: 'haveChild',  // 自定义标识是否还有子节点的字段名称
                isPidData: true         // 是否是pid形式数据
            },
            height: "full-98",
            cols: Sql.initColumn(),
            reqData: function (data, callback) {
                var ajax = new $ax(Feng.ctxPath + '/sql/list?sqlTypeId=' + $("#sqlTypeId").val(), function (res) {
                    callback(res.data);
                }, function (res) {
                    Feng.error("删除失败!" + res.responseJSON.message + "!");
                });
                ajax.start();
            }
        });
    };

    insTb = Sql.initTable(Sql.tableId);

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Sql.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Sql.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Sql.exportExcel();
    });

    // 关闭页面
    $('#btnBack').click(function () {
        window.location.href = Feng.ctxPath + "/sqlType";
    });

    // 工具条点击事件
    treeTable.on('tool(' + Sql.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Sql.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Sql.onDeleteItem(data);
        }
    });
});
