layui.use(['table', 'form', 'admin', 'ax', 'notice', 'textool','dropdown'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var form = layui.form;
    var notice = layui.notice;
    var textool = layui.textool;
    var dropdown = layui.dropdown;

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
        return [
            [
                {align: 'center', field: 'agentAppId', rowspan: 2, type: 'checkbox'},
                {field: 'agentPowerId', hide: true},
                {field: 'appId', hide: true},
                {align: 'center', field: 'appName', rowspan: 2, title: '所属应用'},
                {
                    align: 'center', field: 'status', title: '状态', rowspan: 2, templet: function (d) {
                        if (d.status == 0) {
                            return '正常';
                        } else {
                            return '冻结';
                        }
                    }
                },
                {align: 'center', field: 'agentUserName', rowspan: 2, title: '代理名称'},
                {align: 'center', field: 'agentUserAccount', rowspan: 2, title: '代理账号'},
                {align: 'center', field: 'agentUserAccount', colspan: 3, title: '注册码'},
                {align: 'center', field: 'agentUserAccount', colspan: 3, title: '单码'},
                // {align: 'center',field: 'createUser', sort: true, title: '创建人'},
                {align: 'center', field: 'createTime', sort: true, rowspan: 2, title: '合作时间'},
                {
                    align: 'center', field: 'balance', rowspan: 2, title: '余额', templet: function (d) {
                            return '<span style="color: orange">'+'￥'+d.balance+'</span>';
                    }
                },
                // {align: 'center',field: 'updateUser', sort: true, title: '更新人'},
                // {align: 'center',field: 'updateTime', sort: true, title: '更新时间'},
                {align: 'center', toolbar: '#tableBar', width: 250, rowspan: 2, fixed: 'right', title: '操作'}
            ],
            [

                // {align: 'center',field: 'agentUserId', sort: true, title: '代理用户id'},

                // {align: 'center',field: 'agentGrade', sort: true, title: '代理等级'},
                // {align: 'center',field: 'pid', sort: true, title: '父代理应用id'},
                // {align: 'center',field: 'pids', sort: true, title: '父级ids'},

                {align: 'center', field: 'balance', title: '未使用'},
                {align: 'center', field: 'balance', title: '已使用'},
                {align: 'center', field: 'balance', title: '总卡量'},
                {align: 'center', field: 'balance', title: '未使用'},
                {align: 'center', field: 'balance', title: '已使用'},
                {align: 'center', field: 'balance', title: '总卡量'},


            ]

        ];
    };

    /**
     * 点击查询按钮
     */
    AgentApp.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        queryData['type'] = Feng.getUrlParam("type");
        table.reload(AgentApp.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AgentApp.openAddDlg = function () {
        admin.open({
            type: 1,
            title: '添加代理',
            area: '500px',
            url: Feng.ctxPath + '/agentApp/add?type='+Feng.getUrlParam("type"),
            success: function (layero, dIndex) {
                form.render('select');
                form.val('agentAppForm', {
                    "balance": 0
                });
                textool.init({
                    // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
                    eleId: null,
                    // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
                    maxlength: 128
                });
                //表单提交事件
                form.on('submit(agentAppSubmit)', function (data) {
                    data.field.type = Feng.getUrlParam("type");
                    data.field.agentAppId = $("select[name=appId] option:selected").attr("data-agentAppId");
                    data.field.developerUserId = $("select[name=appId] option:selected").attr("data-developerUserId");
                    var loadIndex = layer.load(2);
                    var ajax = new $ax(Feng.ctxPath + "/actExamine/developerAddItem", function (data) {
                        layer.close(loadIndex);
                        layer.close(dIndex);
                        notice.msg('申请成功，请等待代理审批!', {icon: 1});
                        table.reload(AgentApp.tableId);
                        //关掉对话框
                        admin.closeThisDialog();
                    }, function (data) {
                        layer.close(loadIndex);
                        notice.msg('申请失败！'+ data.responseJSON.message, {icon: 2});
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
            // area: ['800px', '1000px'], //宽高
            content: Feng.ctxPath + '/agentApp/card?agentAppId=' + data.agentAppId + '&appId=' + data.appId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AgentApp.tableId);
            }
        });
    };

    /**
     * 设置总代
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.setRose = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentApp/setRose", function (data) {
                notice.msg("设置成功!", {icon: 1});
                parent.layer.closeAll();
                table.reload(AgentApp.tableId);
            }, function (data) {
                notice.msg("设置失败!" + data.responseJSON.message + "!", {icon: 2});
                parent.layer.closeAll();
            });
            ajax.set("agentAppId", data.agentAppId);
            ajax.start();
        };
        Feng.confirm("是否设置总代?", operation);
    };

    /**
     * 取消总代
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.cancelRose = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentApp/cancelRose", function (data) {
                notice.msg("取消成功!", {icon: 1});
                parent.layer.closeAll();
                table.reload(AgentApp.tableId);
            }, function (data) {
                notice.msg("取消失败!" + data.responseJSON.message + "!", {icon: 2});
                parent.layer.closeAll();
            });
            ajax.set("agentAppId", data.agentAppId);
            ajax.start();
        };
        Feng.confirm("是否取消总代?", operation);
    };

    /**
     * 冻结代理
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.disable = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentApp/disable", function (data) {
                notice.msg("冻结成功!", {icon: 1});
                parent.layer.closeAll();
                table.reload(AgentApp.tableId);
            }, function (data) {
                notice.msg("冻结失败!" + data.responseJSON.message + "!", {icon: 2});
                parent.layer.closeAll();
            });
            ajax.set("agentAppId", data.agentAppId);
            ajax.start();
        };
        Feng.confirm("是否冻结代理?", operation);
    };

    /**
     * 取消总代
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.cancelDisable = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentApp/cancelDisable", function (data) {
                notice.msg("解冻代理成功!", {icon: 1});
                parent.layer.closeAll();
                table.reload(AgentApp.tableId);
            }, function (data) {
                notice.msg("解冻失败!" + data.responseJSON.message + "!", {icon: 2});
                parent.layer.closeAll();
            });
            ajax.set("agentAppId", data.agentAppId);
            ajax.start();
        };
        Feng.confirm("是否解冻代理?", operation);
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentApp/delete", function (data) {
                notice.msg("删除成功!", {icon: 1});
                parent.layer.closeAll();
                table.reload(AgentApp.tableId);
            }, function (data) {
                notice.msg("删除失败!" + data.responseJSON.message + "!", {icon: 2});
                parent.layer.closeAll();
            });
            ajax.set("agentAppId", data.agentAppId);
            ajax.start();
        };
        Feng.confirm("是否删除代理?", operation);
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
    var tableResult = table.render({
        elem: '#' + AgentApp.tableId,
        url: Feng.ctxPath + '/agentApp/list',
        page: true,
        toolbar: '#' + AgentApp.tableId + '-toolbar',
        where: {
            'type': Feng.getUrlParam("type")
        },
        defaultToolbar: [{
            title: '刷新',
            layEvent: 'refresh',
            icon: 'layui-icon-refresh',
        }, 'filter', 'print'],
        height: "full-115",
        cellMinWidth: 100,
        cols: AgentApp.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AgentApp.search();
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
        } else if (layEvent === 'setRose') {
            AgentApp.setRose(data);
        } else if (layEvent === 'cancelRose') {
            AgentApp.cancelRose(data);
        } else if (layEvent === 'disable') {
            AgentApp.disable(data);
        } else if (layEvent === 'cancelDisable') {
            AgentApp.cancelDisable(data);
        }
    });
});
