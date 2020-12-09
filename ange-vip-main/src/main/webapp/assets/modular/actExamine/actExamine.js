layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

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
            {align: 'center',field: 'agentExamineId', fixed: 'left',type: 'checkbox'},
            {align: 'center',field: 'appId', sort: true, title: '应用id'},
            {align: 'center',field: 'developerUserId', sort: true, title: '开发者用户id'},
            {align: 'center',field: 'agentUserId', sort: true, title: '代理用户id'},
            {align: 'center',field: 'agentUserName', sort: true, title: '代理用户名称'},
            {align: 'center',field: 'agentUserAccount', sort: true, title: '代理用户账号'},
            {align: 'center',field: 'applyReason', sort: true, title: '申请理由'},
            {align: 'center',field: 'applyType', sort: true, title: '申请类型：1-申请代理；2-邀请代理'},
            {align: 'center',field: 'examineStatus', sort: true, title: '审核状态：1-等待开发者审核；2-等待代理审核；3-开发者拒绝；4-代理拒绝；5-代理成功'},
            {align: 'center',field: 'examineTime', sort: true, title: '审核时间'},
            {align: 'center',field: 'createUser', sort: true, title: '创建人'},
            {align: 'center',field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center',field: 'updateUser', sort: true, title: '更新人'},
            {align: 'center',field: 'updateTime', sort: true, title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AgentExamine.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(AgentExamine.tableId, {where: queryData});
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
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    AgentExamine.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改代理审核表',
            area: '700px',
            content: Feng.ctxPath + '/agentExamine/edit?agentExamineId=' + data.agentExamineId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentExamine.tableId);
            }
        });
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
    AgentExamine.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].agentExamineId+",";
        }
        ids = ids.substr(0,ids.length-1);
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
        url: Feng.ctxPath + '/agentExamine/list',
        page: true,
        toolbar: '#' + AgentExamine.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
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
    table.on('toolbar(' + AgentExamine.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AgentExamine.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(AgentExamine.tableId);
        } else if(obj.event === 'batchRemove'){
            AgentExamine.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AgentExamine.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AgentExamine.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AgentExamine.onDeleteItem(data);
        }
    });
});
