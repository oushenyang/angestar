layui.use(['table', 'form', 'admin', 'ax', 'notice'], function () {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var $ax = layui.ax;
    var admin = layui.admin;
    var notice = layui.notice;

    /**
     * 注册码卡类列表管理
     */
    var AccountCardType = {
        tableId: "accountCardTypeTable"
    };

    /**
     * 初始化表格的列
     */
    AccountCardType.initColumn = function () {
        return [[
            // {field: 'accountCardTypeId', type: 'checkbox'},
            {
                field: 'appId',  title: '来源', templet: function (d) {
                    if (d.appId==0){
                        return '默认生成';
                    }else {
                        return '自定义';
                    }
                }
            },
            {field: 'cardTypeName', title: '卡类名称'},
            {
                field: 'cardTimeType', title: '时间类型', templet: function (d) {
                    switch (d.cardTimeType) {
                        case 0:
                            return '分';
                        case 1:
                            return '时';
                        case 2:
                            return '天';
                        case 3:
                            return '周';
                        case 4:
                            return '月';
                        case 5:
                            return '年';
                        default:
                            return '无'
                    }
                }
            },
            {field: 'cardTypeData', title: '卡值'},
            {field: 'cardTypePrefix', title: '卡密前缀'},
            {
                field: 'cardTypeRule', title: '卡密规则', templet: function (d) {
                    switch (d.cardTypeRule) {
                        case 0:
                            return '大写字母+数字';
                        case 1:
                            return '小写字母+数字';
                        case 2:
                            return '全大写字母';
                        case 3:
                            return '全小写字母';
                        case 4:
                            return '全数字';
                        default:
                            return '无'
                    }
                }
            },
            {field: 'cardTypeLength', title: '卡密长度'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'status',  templet: '#statusTpl', title: '状态'},
            {field: 'sort', title: '排序'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AccountCardType.search = function () {
        var queryData = {};
        queryData['appId'] = $("#appId").val();
        queryData['cardTypeName'] = $("#cardTypeName").val();
        $('.toolbar').reset();
        layui.form.render();
        table.reload(AccountCardType.tableId, {page:{curr:1},where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AccountCardType.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加注册码卡类列表',
            area: '700px',
            content: Feng.ctxPath + '/accountCardType/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AccountCardType.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AccountCardType.exportExcel = function () {
        var checkRows = table.checkStatus(AccountCardType.tableId);
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
    AccountCardType.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改注册码卡类列表',
            area: '700px',
            content: Feng.ctxPath + '/accountCardType/edit?accountCardTypeId=' + data.accountCardTypeId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AccountCardType.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AccountCardType.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/accountCardType/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AccountCardType.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("accountCardTypeId", data.accountCardTypeId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 修改卡密类型状态
     *
     * @param userId 用户id
     * @param checked 是否选中（true,false），选中就是解锁用户，未选中就是锁定用户
     */
    AccountCardType.changeCardTypeStatus = function (accountCardTypeId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/accountCardType/unfreeze", function (data) {
                notice.msg('解除冻结成功!', {icon: 1});
            }, function (data) {
                notice.msg("解除冻结失败!" + data.responseJSON.message + "!", {icon: 2});
                table.reload(AccountCardType.tableId);
            });
            ajax.set("accountCardTypeId", accountCardTypeId);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/accountCardType/freeze", function (data) {
                notice.msg('冻结成功!', {icon: 1});
            }, function (data) {
                notice.msg("冻结失败!" + data.responseJSON.message + "!", {icon: 2});
                table.reload(AccountCardType.tableId);
            });
            ajax.set("accountCardTypeId", accountCardTypeId);
            ajax.start();
        }
    };
    // 修改卡密类型状态
    form.on('switch(status)', function (obj) {
        var accountCardTypeId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;
        AccountCardType.changeCardTypeStatus(accountCardTypeId, checked);
    });

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AccountCardType.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            notice.msg("未选中数据!", {icon: 2});
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].accountCardTypeId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/accountCardType/batchRemove", function (data) {
                notice.msg("删除成功!", {icon: 1});
                table.reload(AccountCardType.tableId);
            }, function (data) {
                notice.msg("删除失败!" + data.responseJSON.message + "!", {icon: 2});
            });
            ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定要删除这些数据?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AccountCardType.tableId,
        url: Feng.ctxPath + '/accountCardType/list',
        page: true,
        toolbar: '#' + AccountCardType.tableId + '-toolbar',
        // defaultToolbar: [{
        //     title: '刷新',
        //     layEvent: 'refresh',
        //     icon: 'layui-icon-refresh',
        // }, 'filter', 'print'],
        height: "full-115",
        cellMinWidth: 100,
        cols: AccountCardType.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AccountCardType.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AccountCardType.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AccountCardType.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AccountCardType.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AccountCardType.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(AccountCardType.tableId);
        } else if (obj.event === 'batchRemove') {
            AccountCardType.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AccountCardType.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AccountCardType.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AccountCardType.onDeleteItem(data);
        }
    });
});
