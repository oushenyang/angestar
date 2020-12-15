layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 代理购卡记录管理
     */
    var AgentBuyCard = {
        tableId: "agentBuyCardTable"
    };

    /**
     * 初始化表格的列
     */
    AgentBuyCard.initColumn = function () {
        return [[
            // {align: 'center', width:60,field: 'agentBuyCardId', fixed: 'left', type: 'checkbox'},
            {align: 'center', maxWidth:150,field: 'appName', title: '应用名称'},
            {align: 'center', maxWidth:150,field: 'developerUserName', title: '开发者名称'},
            {align: 'center', maxWidth:150,field: 'agentUserName', title: '代理名称'},
            {align: 'center', maxWidth:150,field: 'agentUserAccount', title: '代理账号'},
            // {align: 'center', maxWidth:150,field: 'buyNum', title: '购买数量'},
            // {align: 'center', maxWidth:150,field: 'cardTypeName', title: '卡类名称'},
            // {align: 'center', maxWidth:150,field: 'cardType', title: '卡密类型',
            //     templet: function (d) {
            //         if (d.cardType == 0) {
            //             return '单码卡密';
            //         } else if (d.cardType == 1) {
            //             return '通用卡密';
            //         } else if (d.cardType == 2) {
            //             return '注册卡密';
            //         }
            // }},
            {align: 'center', maxWidth:120,field: 'amount', title: '金额', templet: function (d) {
                    if (d.buyCardType == 2) {
                        return '<span style="color: red">'+'-'+d.amount+'</span>';
                    }else if (d.buyCardType == 1||d.buyCardType == 3){
                        if (d.amount>0){
                            return '<span style="color: orange">'+'+'+d.amount+'</span>';
                        }else {
                            return '<span style="color: red">'+d.amount+'</span>';
                        }
                    }else {
                        return '<span style="color: orange">'+'+'+d.amount+'</span>';
                    }

                }},
            {align: 'center', minWidth:330, field: 'detailed', title: '明细'},
            {align: 'center', maxWidth:250,field: 'remark', title: '备注'},
            {align: 'center', minWidth:170,field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AgentBuyCard.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(AgentBuyCard.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AgentBuyCard.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加代理购卡记录',
            area: '700px',
            content: Feng.ctxPath + '/agentBuyCard/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentBuyCard.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AgentBuyCard.exportExcel = function () {
        var checkRows = table.checkStatus(AgentBuyCard.tableId);
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
    AgentBuyCard.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改代理购卡记录',
            area: '700px',
            content: Feng.ctxPath + '/agentBuyCard/edit?agentBuyCardId=' + data.agentBuyCardId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentBuyCard.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AgentBuyCard.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentBuyCard/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AgentBuyCard.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("agentBuyCardId", data.agentBuyCardId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AgentBuyCard.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            Feng.error("未选中数据!");
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].agentBuyCardId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentBuyCard/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AgentBuyCard.tableId);
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
        elem: '#' + AgentBuyCard.tableId,
        url: Feng.ctxPath + '/agentBuyCard/list',
        page: true,
        height: "full-158",
        cellMinWidth: 100,
        cols: AgentBuyCard.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AgentBuyCard.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AgentBuyCard.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AgentBuyCard.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AgentBuyCard.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AgentBuyCard.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(AgentBuyCard.tableId);
        } else if (obj.event === 'batchRemove') {
            AgentBuyCard.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AgentBuyCard.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AgentBuyCard.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AgentBuyCard.onDeleteItem(data);
        }
    });
});
