layui.use(['table', 'admin', 'ax', 'notice'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var notice = layui.notice;

    /**
     * 代理审核表管理
     */
    var AgentExamine = {
        tableId: "agentExamineTable"
    };

    /**
     * 初始化表格的列
     */
    AgentExamine.initColumn = function () {
        return [[
            // {align: 'center', field: 'agentExamineId', fixed: 'left', type: 'checkbox'},
            {align: 'center', field: 'appName', title: '应用名称'},
            {align: 'center', field: 'pidUserName',title: '上级名称'},
            {align: 'center', field: 'agentUserName', title: '代理名称'},
            {align: 'center', field: 'agentUserAccount',title: '代理账号'},
            {
                align: 'center', field: 'applyType', title: '申请类型', templet: function (d) {
                    if (d.applyType === 1) {
                        return '申请代理';
                    } else if (d.applyType === 2){
                        return '邀请代理';
                    }
                }
            },
            {align: 'center', field: 'applyReason', title: '申请理由'},
            {
                align: 'center',
                field: 'examineStatus',
                title: '审核状态',templet:'#examineStatusTpl'
            },
            {align: 'center', field: 'createTime', sort: true, title: '申请时间'},
            {align: 'center', field: 'examineTime', sort: true, title: '审核时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AgentExamine.search = function () {
        var queryData = {};
        queryData['appId'] = $("#appId").val();
        queryData['type'] = $("#type").val();
        table.reload(AgentExamine.tableId, {page:{curr:1},where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AgentExamine.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加代理审核表',
            area: '700px',
            content: Feng.ctxPath + '/agentExamine/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentExamine.tableId);
            }
        });
    };

    /**
     * 代理点击同意
     *
     * @param data 点击按钮时候的行数据
     */
    AgentExamine.agree = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/actExamine/actAgree", function (data) {
                notice.msg("代理成功!", {icon: 1});
                parent.layer.closeAll();
                table.reload(AgentExamine.tableId);
            }, function (data) {
                notice.msg("代理失败!" + data.responseJSON.message, {icon: 2});
                parent.layer.closeAll();
            });
            ajax.set("agentExamineId", data.agentExamineId);
            ajax.set("type", $('#type').val());
            ajax.start();
        };
        Feng.confirm("是否同意代理?", operation);
    };

    /**
     * 代理点击拒绝
     *
     * @param data 点击按钮时候的行数据
     */
    AgentExamine.refuse = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/actExamine/actRefuse", function (data) {
                notice.msg("拒绝成功!", {icon: 1});
                parent.layer.closeAll();
                table.reload(AgentExamine.tableId);
            }, function (data) {
                notice.msg("拒绝失败!" + data.responseJSON.message, {icon: 2});
                parent.layer.closeAll();
            });
            ajax.set("agentExamineId", data.agentExamineId);
            ajax.set("type", $('#type').val());
            ajax.start();
        };
        Feng.confirm("是否拒绝代理?", operation);
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AgentExamine.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentExamine/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AgentExamine.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("agentExamineId", data.agentExamineId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AgentExamine.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            Feng.error("未选中数据!");
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].agentExamineId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentExamine/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AgentExamine.tableId);
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
        elem: '#' + AgentExamine.tableId,
        url: Feng.ctxPath + '/actExamine/list',
        // page: true,
        defaultToolbar: ['filter'],
        height: "full-80",
        page: {limit: 15, limits: [15, 30, 45, 60, 75, 90, 105, 120, 200]},
        cellMinWidth: 100,
        where:{
            type:$("#type").val()
        },
        cols: AgentExamine.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AgentExamine.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AgentExamine.openAddDlg();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AgentExamine.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AgentExamine.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(AgentExamine.tableId);
        } else if (obj.event === 'batchRemove') {
            AgentExamine.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AgentExamine.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'agree') {
            AgentExamine.agree(data);
        } else if (layEvent === 'refuse') {
            AgentExamine.refuse(data);
        }
    });
});
