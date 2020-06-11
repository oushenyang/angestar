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
    element.on('tab(cardTabBrief)', function (data) {
        if (data.index==0){
            // 渲染表格
            table.render({
                elem: '#' + AgentApp.card,
                url: Feng.ctxPath + '/agentCard/list',
                page: true,
                toolbar: '#' + AgentApp.card + '-toolbar',
                defaultToolbar: [],
                height: "full-158",
                cellMinWidth: 100,
                where:{
                    'cardType':0,
                    'agentAppId':Feng.getUrlParam("agentAppId")
                },
                cols: AgentApp.initColumn()
            });
        }else if(data.index==1){
            // 渲染表格
             table.render({
                elem: '#' + AgentApp.currentCard,
                url: Feng.ctxPath + '/agentCard/list',
                page: true,
                toolbar: '#' + AgentApp.currentCard + '-toolbar',
                defaultToolbar: [],
                height: "full-158",
                cellMinWidth: 100,
                 where:{
                     'cardType':1,
                     'agentAppId':Feng.getUrlParam("agentAppId")
                 },
                cols: AgentApp.initColumn()
            });
        }else {
            // 渲染表格
            table.render({
                elem: '#' + AgentApp.account,
                url: Feng.ctxPath + '/agentCard/list',
                page: true,
                toolbar: '#' + AgentApp.account + '-toolbar',
                defaultToolbar: [],
                height: "full-158",
                cellMinWidth: 100,
                where:{
                    'cardType':2,
                    'agentAppId':Feng.getUrlParam("agentAppId")
                },
                cols: AgentApp.initColumn()
            });
        }
    });
    /**
     * 初始化表格的列
     */
    AgentApp.initColumn = function () {
        return [[
            {field: 'cardTypeId', type: 'checkbox', fixed: 'left'},
            {
                field: 'appName', fixed: 'left', title: '所属应用', templet: function (d) {
                    if (!d.appName) {
                        return '通用卡类';
                    } else {
                        return d.appName;
                    }
                }
            },
            {field: 'cardTypeName', title: '卡类名称'},
            {field: 'marketPrice', title: '市场价'},
            {field: 'agentPrice', title: '代理价'},
            {field: 'createTime',width: 160,  sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };
    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AgentApp.card,
        url: Feng.ctxPath + '/agentCard/list',
        page: true,
        toolbar: '#' + AgentApp.card + '-toolbar',
        defaultToolbar: [],
        height: "full-158",
        cellMinWidth: 100,
        where:{
            'cardType':0,
            'agentAppId':Feng.getUrlParam("agentAppId")
        },
        cols: AgentApp.initColumn()
    });
    //让当前iframe弹层高度适应
    admin.iframeAuto();

    /**
     * 初始化
     */
    AgentApp.initializeItem = function (cardType) {
        console.log(Feng.getUrlParam("appId"));
        var initializeItem = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentCard/initializeItem", function (data) {
                Feng.success("初始化成功!");
                if (cardType==0){
                    table.reload(AgentApp.card);
                }else if (cardType==1){
                    table.reload(AgentApp.currentCard);
                }else {
                    table.reload(AgentApp.account);
                }

            }, function (data) {
                Feng.error("初始化失败!" + data.responseJSON.message + "!");
            });
            ajax.set("cardType", cardType);
            ajax.set("agentAppId", Feng.getUrlParam("agentAppId"));
            ajax.set("appId",Feng.getUrlParam("appId"));
            ajax.start();
        };
        Feng.confirm("是否初始化?", initializeItem);
    };
    // 单码卡密表头工具条点击事件
    table.on('toolbar(' + AgentApp.card + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AgentApp.openAddDlg();
        } else if(obj.event === 'initialize'){
            AgentApp.initializeItem(0);
        }
    });
    // 通用卡密表头工具条点击事件
    table.on('toolbar(' + AgentApp.currentCard + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AgentApp.openAddDlg();
        } else if(obj.event === 'initialize'){
            AgentApp.initializeItem(1);
        }
    });
    // 账号卡密表头工具条点击事件
    table.on('toolbar(' + AgentApp.account + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AgentApp.openAddDlg();
        } else if(obj.event === 'initialize'){
            AgentApp.initializeItem(2);
        }
    });

    // //表单提交事件
    // form.on('submit(btnSubmit)', function (data) {
    //     var ajax = new $ax(Feng.ctxPath + "/agentPower/editItem", function (data) {
    //         Feng.success("更新成功！");
    //
    //         //传给上个页面，刷新table用
    //         admin.putTempData('formOk', true);
    //
    //         //关掉对话框
    //         admin.closeThisDialog();
    //     }, function (data) {
    //         Feng.error("更新失败！" + data.responseJSON.message)
    //     });
    //     ajax.set(data.field);
    //     ajax.start();
    //     return false;
    // });
});