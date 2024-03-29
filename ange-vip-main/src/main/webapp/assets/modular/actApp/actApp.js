layui.use(['table', 'form', 'admin', 'ax', 'notice', 'textool', 'func','dataGrid'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;
    var form = layui.form;
    var dataGrid = layui.dataGrid;
    var notice = layui.notice;
    var textool = layui.textool;

    /**
     * 代理软件表管理
     */
    var AgentApp = {
        tableId: "agentAppTable"
    };

    /**
     * 初始化表格的列
     */
    AgentApp.initColumn = function () {
        return [[
            // {align: 'center', field: 'agentAppId', fixed: 'left', type: 'checkbox'},
            // {field: 'agentPowerId', hide: true},
            // {field: 'appId', hide: true},
            {align: 'center', field: 'appName', title: '应用名称'},
            {align: 'center', field: 'agentAppNo', title: '应用编号'},
            {align: 'center', field: 'pidUserName', title: '上级名称'},
            {align: 'center', field: 'agentGradeName', sort: true, title: '代理等级'},
            // {align: 'center', field: 'agentUserAccount', rowspan: 2, title: '代理账号'},
            {align: 'center', field: 'createTime', sort: true, title: '合作时间'},
            {
                align: 'center', field: 'balance', title: '余额', templet: function (d) {
                    return '<span style="color: #fd6b00">' + '¥' + d.balance + '</span>';
                }
            },
            {
                align: 'center', field: 'status', title: '状态', templet: function (d) {
                    if (d.status == 0) {
                        return '正常';
                    } else {
                        return '冻结';
                    }
                }
            },
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AgentApp.search = function () {
        var queryData = {};
        queryData['appId'] = $("#appId").val();
        table.reload(AgentApp.tableId, {where: queryData});
    };

    /**
     * 点击查询按钮
     */
    AgentApp.addActApp = function () {
        admin.open({
            type: 1,
            title: '申请应用',
            area: '500px',
            url: Feng.ctxPath + '/actApp/add?type=' + Feng.getUrlParam("type"),
            success: function (layero, dIndex) {
                form.render('select');
                form.val('actAppForm', {
                    "balance": 0
                });
                textool.init({
                    // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
                    eleId: null,
                    // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
                    maxlength: 128
                });
                //表单提交事件
                form.on('submit(actAppSubmit)', function (data) {
                    data.field.type = Feng.getUrlParam("type");
                    var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
                    var ajax = new $ax(Feng.ctxPath + "/actExamine/agentApplyAddItem", function (data) {
                        top.layer.close(loading);
                        layer.close(dIndex);
                        notice.msg('申请成功，请等待审批!', {icon: 1});
                        table.reload(AgentApp.tableId);
                        //关掉对话框
                        admin.closeThisDialog();
                    }, function (data) {
                        top.layer.close(loading);
                        notice.msg('申请失败！' + data.responseJSON.message, {icon: 2});
                    });
                    ajax.set(data.field);
                    ajax.start();
                    return false;
                });
                // 禁止弹窗出现滚动条
                $(layero).children('.layui-layer-content').css('overflow', 'visible');
            }
        });
    };


    /**
     * 弹出添加对话框
     */
    AgentApp.openAddDlg = function () {
        // admin.putTempData('formOk', false);
        admin.open({
            type: 1,
            title: '添加代理',
            area: '500px',
            url: Feng.ctxPath + '/agentApp/add',
            success: function (layero, dIndex) {
                form.render('select');
                form.val('agentAppForm', {
                    "balance": 0
                });
                //表单提交事件
                form.on('submit(agentAppSubmit)', function (data) {
                    var loadIndex = top.layer.load(2);
                    admin.btnLoading('.agentAppSubmit');
                    var ajax = new $ax(Feng.ctxPath + "/agentApp/addItem", function (data) {
                        layer.close(loadIndex);
                        admin.removeLoading($content);
                        layer.close(dIndex);
                        layer.msg("添加成功！", {icon: 1});
                        table.reload(AgentApp.tableId);
                        //关掉对话框
                        admin.closeThisDialog();
                    }, function (data) {
                        layer.close(loadIndex);
                        layer.msg("添加失败！" + data.responseJSON.message, {icon: 2});
                    });
                    ajax.set(data.field);
                    ajax.start();
                    return false;
                });
                // 禁止弹窗出现滚动条
                $(layero).children('.layui-layer-content').css('overflow', 'visible');
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AgentApp.exportExcel = function () {
        var checkRows = table.checkStatus(AgentApp.tableId);
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
    AgentApp.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改代理软件表',
            area: '700px',
            content: Feng.ctxPath + '/agentApp/edit?agentAppId=' + data.agentAppId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentApp.tableId);
            }
        });
    };

    /**
     * 点击充值
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.openRechargeDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '代理充值',
            area: '500px',
            content: Feng.ctxPath + '/agentApp/recharge?agentAppId=' + data.agentAppId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentApp.tableId);
            }
        });
    };

    /**
     * 点击权限
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.openPowerDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '权限配置',
            area: '700px',
            content: Feng.ctxPath + '/agentApp/power?agentPowerId=' + data.agentPowerId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentApp.tableId);
            }
        });
    };

    /**
     * 点击卡密配置
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.openCardDlg = function (data) {
        admin.putTempData('formOk', false);
        console.log(data.appId);
        top.layui.admin.open({
            type: 2,
            title: '卡密配置',
            area: '800px',
            content: Feng.ctxPath + '/agentApp/card?agentAppId=' + data.agentAppId + '&appId=' + data.appId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentApp.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentApp/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AgentApp.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("agentAppId", data.agentAppId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 快捷页面
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.openQuickDlg = function (data) {
        admin.putTempData('formOk', false);
        func.open({
            type: 2,
            area: '700px',
            title: '快捷页面',
            content: Feng.ctxPath + '/actApp/quick?agentAppId=' + data.agentAppId,
            endCallback: function () {
                // AgentApp.loadAppInfo();
            }
        });
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AgentApp.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            Feng.error("未选中数据!");
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].agentAppId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentApp/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AgentApp.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定要删除这些数据?", operation);
    };

    // 渲染表格
    // var tableResult = table.render({
    //     elem: '#' + AgentApp.tableId,
    //     url: Feng.ctxPath + '/actApp/list',
    //     page: true,
    //     // toolbar: '#' + AgentApp.tableId + '-toolbar',
    //     // defaultToolbar: [{
    //     //     title: '刷新',
    //     //     layEvent: 'refresh',
    //     //     icon: 'layui-icon-refresh',
    //     // }, 'filter', 'print'],
    //     height: "full-80",
    //     cellMinWidth: 100,
    //     cols: AgentApp.initColumn()
    // });

    AgentApp.loadAppInfo = function (appName) {
        dataGrid.render({
            elem: '#appInfoTable',  // 容器
            templet: '#appInfoItem',  // 模板
            height: '1200',
            defaultToolbar: ['filter'],
            data: Feng.ctxPath + '/actApp/list', // url
            page: {limit: 12, limits: [12, 24, 36, 48, 60]},
            where: {
                'appName':appName
            },
            done:function(data){
                if (data.length === 0){
                    $('#ew-datagrid-page-appInfoTable').hide();
                    $('#appInfoTable').attr("style",'min-height: 650px;position: relative;');
                    $('#appInfoTable').html('<div class="no_result" style="padding: 200px 0;">' +
                        '<p><img width="106" height="130" src="'+Feng.ctxPath+'/assets/expand/images/no_result.png" alt="找不到结果为空相关搜索结果"></p>' +
                        '<p class="words"><span class="line" style="color: #aaa">暂无数据</span>' +
                        '</p>' +
                        '</div>')
                }
            },
            onToolBarClick: function (obj) {  // toolBar事件
                var event = obj.event;
                var data = obj.data;
                if (event == 'quick') {
                    AgentApp.openQuickDlg(data);
                }
            }
        });
    };
    //渲染应用列表
    AgentApp.loadAppInfo();

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AgentApp.search();
    });

    // 搜索按钮点击事件
    $('#addActApp').click(function () {
        AgentApp.addActApp();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AgentApp.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AgentApp.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AgentApp.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AgentApp.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(AgentApp.tableId);
        } else if (obj.event === 'batchRemove') {
            AgentApp.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AgentApp.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AgentApp.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AgentApp.onDeleteItem(data);
        } else if (layEvent === 'recharge') {
            AgentApp.openRechargeDlg(data);
        } else if (layEvent === 'power') {
            AgentApp.openPowerDlg(data);
        } else if (layEvent === 'card') {
            AgentApp.openCardDlg(data);
        } else if (layEvent === 'quick') {
            AgentApp.openQuickDlg(data);
        }
    });
});
