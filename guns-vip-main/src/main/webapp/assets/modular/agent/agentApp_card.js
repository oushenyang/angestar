/**
 * 详情对话框
 */
var AgentAppInfoDlg = {
    data: {
        appId: "",
        developerUserId: "",
        agentUserId: "",
        agentUserName: "",
        agentUserAccount: "",
        agentGrade: "",
        pid: "",
        pids: "",
        balance: "",
        status: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['table', 'form', 'formX', 'admin', 'ax', 'element'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var element = layui.element;
    var table = layui.table;


    /**
     * 代理软件表管理
     */
    var AgentApp = {
        card: "card",
        currentCard: "current_card",
        account: "account"
    };

    //一些事件监听
    element.on('tab(demo)', function (data) {
        console.log(data);
    });
    /**
     * 初始化表格的列
     */
    AgentApp.initColumn = function () {
        return [[
            {field: 'cardTypeId', type: 'checkbox', fixed: 'left'},
            // {
            //     field: 'appName', fixed: 'left', title: '所属应用', templet: function (d) {
            //         if (!d.appName) {
            //             return '通用卡类';
            //         } else {
            //             return d.appName;
            //         }
            //     }
            // },
            {field: 'cardTypeName', title: '卡类名称'},
            {field: 'cardTypeName', title: '市场价'},
            {field: 'cardTypeName', title: '代理价'},
            {field: 'createTime',width: 160,  sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AgentApp.card,
        url: Feng.ctxPath + '/agentApp/list',
        page: true,
        toolbar: '#' + AgentApp.card + '-toolbar',
        defaultToolbar: [],
        height: "full-158",
        cellMinWidth: 100,
        cols: AgentApp.initColumn()
    });
    //让当前iframe弹层高度适应
    admin.iframeAuto();
    // //获取详情信息，填充表单
    // var ajax = new $ax(Feng.ctxPath + "/agentPower/detail?agentPowerId=" + Feng.getUrlParam("agentPowerId"));
    // var result = ajax.start();
    // form.val('agentAppForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/agentPower/editItem", function (data) {
            Feng.success("更新成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("更新失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});