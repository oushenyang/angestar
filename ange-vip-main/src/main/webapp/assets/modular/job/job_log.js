layui.use(['layer', 'table', 'ax', 'laydate'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;

    /**
     * 系统管理--登陆日志
     */
    var LoginLog = {
        tableId: "loginLogTable"   //表格id
    };

    /**
     * 初始化表格的列
     */
    LoginLog.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'jobId',  hide: true, sort: true, title: 'id'},
            // {field: 'logName', align: "center", sort: true, title: '日志名称'},
            {field: 'creatTime', align: "center", sort: true, title: '时间'},
            {field: 'status', align: "center", sort: true, title: '状态', templet: '#statusTpl'},
            {field: 'msg', align: "center", sort: true, title: '具体消息'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    LoginLog.search = function () {
        var queryData = {};
        queryData['beginTime'] = $("#beginTime").val();
        queryData['endTime'] = $("#endTime").val();
        queryData['status'] = $("#status").val();
        table.reload(LoginLog.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 日志详情
     */
    LoginLog.logDetail = function (param) {
        var ajax = new $ax(Feng.ctxPath + "/taskJob/logDetail?id=" + param.id + '&jobKey=' + param.jobKey);
        var result = ajax.start();
        Feng.infoDetail("日志详情", result.data);
    };

    // 工具条点击事件
    table.on('tool(' + LoginLog.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'detail') {
            LoginLog.logDetail(data);
        }
    });

    //清空日志
    LoginLog.cleanLog = function () {
        Feng.confirm("是否清空所有日志?", function () {
            var ajax = new $ax(Feng.ctxPath + "/taskJob/delLog?jobId="+ Feng.getUrlParam("jobId"), function (data) {
                Feng.success("清空日志成功!");
                LoginLog.search();
            }, function (data) {
                Feng.error("清空日志失败!");
            });
            ajax.start();
        });
    };

    //渲染时间选择框
    laydate.render({
        elem: '#beginTime'
    });

    //渲染时间选择框
    laydate.render({
        elem: '#endTime'
    });

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + LoginLog.tableId,
        url: Feng.ctxPath + '/taskJob/logList?jobId='+ Feng.getUrlParam("jobId"),
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: LoginLog.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        LoginLog.search();
    });

    // 搜索按钮点击事件
    $('#btnClean').click(function () {
        LoginLog.cleanLog();
    });
});
