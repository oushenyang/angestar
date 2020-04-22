layui.use(['table', 'dropdown','admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var dropdown = layui.dropdown;

    /**
     * 卡密表管理
     */
    var CardInfo = {
        tableId: "cardInfoTable"
    };

    /**
     * 初始化表格的列
     */
    CardInfo.initColumn = function () {
        return [[
            // {type: 'checkbox'},
            {align: 'center', field: 'cardId', fixed: 'left', type: 'checkbox'},
            {
                align: 'center', field: 'appName', fixed: 'left', title: '所属应用', templet: function (d) {
                    if (!d.appName) {
                        return '通用卡密';
                    } else {
                        return d.appName;
                    }
                }
            },
            {align: 'center', field: 'cardCode', fixed: 'left', title: '卡密'},
            {align: 'center', field: 'cardTypeName', sort: true, title: '卡类'},
            // {align: 'center',field: 'userId', sort: true, title: '申请人ID'},
            {align: 'center', field: 'userName', title: '申请人名称'},
            // {align: 'center',field: 'isUniversal', sort: true, title: '是否通用 0-否；1-是'},
            // {align: 'center',field: 'isCustomTime', sort: true, title: '是否自定义时间'},
            // {align: 'center',field: 'customTimeNum', sort: true, title: '自定义时间值(天)'},
            {align: 'center', field: 'cardStatus', sort: true, title: '状态', templet: '#cardStatusTpl'},
            // {align: 'center',field: 'cardMac', sort: true, title: '绑定mac'},
            // {align: 'center',field: 'cardIp', sort: true, title: '绑定ip'},
            // {align: 'center',field: 'cardToken', sort: true, title: '卡密token'},
            {align: 'center', field: 'activeTime', sort: true, title: '激活时间'},
            {align: 'center', field: 'expireTime', sort: true, title: '过期时间'},
            // {align: 'center',field: 'cardBindType', sort: true, title: '绑机配置 0-默认；1-关闭；2-MAC；3-IP；4-混合；'},
            // {align: 'center',field: 'cardOpenRange', sort: true, title: '多开开关 0-默认；1-关闭；2-开启'},
            // {align: 'center',field: 'cardOpenNum', sort: true, title: '多开数量'},
            {align: 'center', field: 'cardRemark', title: '备注'},
            // {align: 'center',field: 'prohibitRemark', sort: true, title: '禁用备注'},
            {align: 'center', toolbar: '#tableBar', width: 125, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CardInfo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(CardInfo.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    CardInfo.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加卡密表',
            area: '700px',
            content: Feng.ctxPath + '/cardInfo/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(CardInfo.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    CardInfo.exportExcel = function () {
        var checkRows = table.checkStatus(CardInfo.tableId);
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
    CardInfo.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改卡密表',
            area: '700px',
            content: Feng.ctxPath + '/cardInfo/edit?cardId=' + data.cardId,
            end: function () {
                admin.getTempData('formOk') && table.reload(CardInfo.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CardInfo.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("cardId", data.cardId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    CardInfo.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            Feng.error("未选中数据!");
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].cardId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(CardInfo.tableId);
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
        elem: '#' + CardInfo.tableId,
        url: Feng.ctxPath + '/cardInfo/list',
        page: true,
        toolbar: '#' + CardInfo.tableId + '-toolbar',
        defaultToolbar: [{
            title: '刷新',
            layEvent: 'refresh',
            icon: 'layui-icon-refresh',
        }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: CardInfo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CardInfo.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        CardInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        CardInfo.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + CardInfo.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            CardInfo.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(CardInfo.tableId);
        } else if (obj.event === 'batchRemove') {
            CardInfo.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + CardInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            CardInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            CardInfo.onDeleteItem(data);
        }
    });
});
