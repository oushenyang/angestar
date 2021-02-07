layui.use(['layer', 'table', 'ax', 'admin', 'func', 'laydate'], function () {
    var $ = layui.$;
    var $ax = layui.ax;
    var layer = layui.layer;
    var table = layui.table;
    var laydate = layui.laydate;
    var func = layui.func;
    var admin = layui.admin;

    /**
     * 系统管理--定时任务
     */
    var Job = {
        tableId: "jobTable",    //表格id
        condition: {
            name: "",
            deptId: "",
            timeLimit: ""
        }
    };

    /**
     * 初始化表格的列
     */
    Job.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'jobId', hide: true, fixed:'left',title: 'ID'},
            {field: 'jobName', title: '任务名称',minWidth: 180, align: 'left'},
            {field: 'cronExpression', title: 'cron表达式', minWidth: 150, align: 'center'},
            {
                field: 'nextTime', title: '下次触发时间', minWidth: 200, align: 'center'
                // , templet: function (d) {
                //     if (!d.nextTime && d.nextTime != -1) {
                //         return commonTime(parseInt(d.nextTime));
                //     } else {
                //         return "";
                //     }
                // }
            },
            {
                field: 'prevTime', title: '上次执行时间', align: 'center'
                // , templet: function (d) {
                //     if (!d.prevTime && d.prevTime != -1) {
                //         return commonTime(parseInt(d.prevTime));
                //     } else {
                //         return "";
                //     }
                // }
            },
            {field: 'suspende', title: '执行状态', align: 'center', templet: '#suspendeTpl'},
            {field: 'status', title: '任务状态', align: 'center', templet: '#statusTpl'},
            // {
            //     field: 'remark',
            //     title: '备注',
            //     align: 'left',
            //     formatter: function (v, r) {
            //         return "<span title='" + v + "'>" + v + "</span>";
            //     }
            // },
            // {
            //     field: 'create_time',
            //     title: '创建时间',
            //     align: 'center',
            //     formatter: function (v, r) {
            //         if (v != null && v != undefined && v != '') {
            //             return v;
            //         }
            //         return "";
            //     }
            // },
            // {
            //     field: 'filesUrl',
            //     title: '运行日志',
            //     align: 'center',
            //     formatter: function (v, r) {
            //         if (v != null && v != undefined && v != '') {
            //             return "<a href='javascript:void(0);' target='_parent'>查看当天运行日志</a>";
            //         }
            //         return "-";
            //
            //     }
            // },
            {
                field: ' ',
                title: '操作',
                align: 'center',
                toolbar: '#tableBar',
                fixed:'right',
                minWidth: 420
            }
        ]];
    };

    /**
     * 点击查询按钮
     */
    Job.search = function () {
        var queryData = {};
        queryData['jobName'] = $("#jobName").val();
        table.reload(Job.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加Job对话框
     */
    Job.openAddJob = function () {
        top.layui.admin.open({
            type: 2,
            title: '新增定时任务',
            area: '700px',
            content: Feng.ctxPath + '/taskJob/job_add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Job.tableId);
            }
        });
    };

    /**
     * 点击日志按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    Job.onLogJob = function (data) {
        // top.layui.admin.open({
        //     type: 2,
        //     title: data.jobName+'日志',
        //     area: '900px',
        //     content: Feng.ctxPath + '/taskJob/job_log?jobId=' + data.jobId
        // });

        func.open({
            title:  data.jobName+'日志',
            content: Feng.ctxPath + '/taskJob/job_log?jobId=' + data.jobId,
            tableId: Job.tableId
        });
    };

    /**
     * 点击编辑用户按钮时
     *
     * @param data 点击按钮时候的行数据
     */
    Job.onEditJob = function (data) {
        top.layui.admin.open({
            type: 2,
            title: '编辑定时任务',
            area: '700px',
            content: Feng.ctxPath + '/taskJob/job_edit?jobId=' + data.jobId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Job.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Job.exportExcel = function () {
        var checkRows = table.checkStatus(Job.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 点击删除用户按钮
     *
     * @param data 点击按钮时候的行数据
     */
    Job.onDeleteUser = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/taskJob/delete", function () {
                table.reload(Job.tableId);
                Feng.success("删除成功!");
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("userId", data.userId);
            ajax.start();
        };
        Feng.confirm("是否删除用户" + data.account + "?", operation);
    };


    // 渲染表格
    var tableResult = table.render({
        elem: '#' + Job.tableId,
        url: Feng.ctxPath + '/taskJob/list',
        page: true,
        height: "full-98",
        cellMinWidth: 100,
        cols: Job.initColumn()
    });

    //渲染时间选择框
    laydate.render({
        elem: '#timeLimit',
        range: true,
        max: Feng.currentDate()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Job.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Job.openAddJob();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Job.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + Job.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Job.onEditJob(data);
        } else if (layEvent === 'start') {
            Job.onChenageType(data.jobId, 3, data.suspende, data.status);
        } else if (layEvent === 'stop') {
            Job.onChenageType(data.jobId, 0, data.suspende, data.status);
        } else if (layEvent === 'execution') {
            Job.onChenageType(data.jobId, 1, data.suspende, data.status);
        } else if (layEvent === 'recovery') {
            Job.onChenageType(data.jobId, 2, data.suspende, data.status);
        }else if (layEvent === 'log') {
            Job.onLogJob(data);
        }
    });

    Job.onChenageType = function (id, type, suspende, status) {
        var title = "";

        if (type != 3 && status == 1) {
            Feng.error("请先启动该任务!");
            return;
        }

        if (type == 0) {
            title = "终止 该任务";
        }
        if (type == 1) {
            title = "立即执行 该任务";
        }
        if (type == 2) {
            if (suspende == 1) {
                suspende = 2;
            } else {
                suspende = 1;
            }
            title = "暂停/恢复 该任务";
        }
        if (type == 3) {
            title = "激活 该任务";
        }
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/taskJob/runJob", function () {
                table.reload(Job.tableId);
                Feng.success(title + "成功!");
            }, function (data) {
                Feng.error(title + "失败!" + data.responseJSON.message + "!");
            });
            ajax.set("jobId", id);
            ajax.set("type", type);
            ajax.set("suspende", suspende);
            ajax.start();
        };
        Feng.confirm("是否确认 " + title + "？", operation);
    };
});