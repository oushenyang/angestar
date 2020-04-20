layui.use(['table','ax', 'func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var func = layui.func;

    /**
     * sql类型表管理
     */
    var SqlType = {
        tableId: "sqlTypeTable"
    };

    /**
     * 初始化表格的列
     */
    SqlType.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'sqlTypeId', hide: true, title: 'sql类型id'},
            {
                field: 'name', align: "center", sort: true, title: '类型名称', templet: function (d) {
                    var url = Feng.ctxPath + '/sql?sqlTypeId=' + d.sqlTypeId;
                    return '<a style="color: #01AAED;" href="' + url + '">' + d.name + '</a>';
                }
            },
            {
                field: 'code', align: "center", sort: true, title: '类型编码', minWidth: 166, templet: function (d) {
                    var url = Feng.ctxPath + '/sql?sqlTypeId=' + d.sqlTypeId;
                    return '<a style="color: #01AAED;" href="' + url + '">' + d.code + '</a>';
                }
            },
            {
                field: 'systemFlag', align: "center", sort: true, title: '是否是系统sql', templet: function (d) {
                    if (d.systemFlag === 'Y') {
                        return "是";
                    } else {
                        return "否";
                    }
                }
            },
            {field: 'description', align: "center", sort: true, title: 'sql语句'},
            {
                field: 'status', sort: true, align: "center", title: '状态', templet: function (d) {
                    if (d.status === 'ENABLE') {
                        return "启用";
                    } else {
                        return "禁用";
                    }
                }
            },
            {field: 'createTime', align: "center", sort: true, title: '添加时间', minWidth: 161},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SqlType.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        queryData['systemFlag'] = $("#systemFlag").val();
        queryData['status'] = $("#status").val();
        table.reload(SqlType.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    SqlType.openAddDlg = function () {
        func.open({
            height: 630,
            title: '添加sql类型',
            content: Feng.ctxPath + '/sqlType/add',
            tableId: SqlType.tableId
        });
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    SqlType.openEditDlg = function (data) {
        func.open({
            height: 630,
            title: '修改sql类型',
            content: Feng.ctxPath + '/sqlType/edit?sqlTypeId=' + data.sqlTypeId,
            tableId: SqlType.tableId
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    SqlType.onDeleteItem = function (data) {

        if (data.systemFlag === 'Y') {
            Feng.error("系统sql无法删除");
            return;
        }

        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/sqlType/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(SqlType.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("sqlTypeId", data.sqlTypeId);
            ajax.start();
        };

        Feng.confirm("是否删除?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + SqlType.tableId,
        url: Feng.ctxPath + '/sqlType/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: SqlType.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SqlType.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        SqlType.openAddDlg();
    });

    // 工具条点击事件
    table.on('tool(' + SqlType.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        console.log(obj)
        console.log(data)

        if (layEvent === 'edit') {
            SqlType.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SqlType.onDeleteItem(data);
        }
    });
});
