layui.use(['table', 'form', 'admin', 'ax','element','dropdown'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var form = layui.form;
    var element = layui.element;
    var dropdown = layui.dropdown;

    /**
     * 代理软件表管理
     */
    var actCard = {
        tableId: "actCardTable"
    };

    var agentAppId = $('.layui-this').attr('data-appId');

    //tab事件监听
    element.on('tab(actCardTabBrief)', function(data){
        agentAppId = $(this).attr('data-appId');
        table.render({
            elem: '#' + actCard.tableId + agentAppId,
            url: Feng.ctxPath + '/actCard/list',
            page: true,
            height: "full-158",
            cellMinWidth: 100,
            where:{
                'actCardAppId':$(this).attr('data-appId')
            },
            cols: actCard.initColumn()
        });
        // 行内工具条点击事件
        table.on('tool(' + actCard.tableId + agentAppId+')', function (obj) {
            var data = obj.data;
            var layEvent = obj.event;
            if (layEvent === 'edit') {
                actCard.openEditDlg(data);
            } else if (layEvent === 'delete') {
                actCard.onDeleteItem(data);
            } else if (layEvent === 'recharge') {
                actCard.openRechargeDlg(data);
            } else if (layEvent === 'power') {
                actCard.openPowerDlg(data);
            } else if (layEvent === 'card') {
                actCard.openCardDlg(data);
            } else if (layEvent === 'copy') {
                actCard.copy(data)
            }
        });
    });

    actCard.copy = function (data) {
        var cla = '.cardCode' + data.cardId;
        var clipboard = new ClipboardJS(cla, {
            text: function () {
                return data.cardCode;
            }
        });
        clipboard.on('success', function (e) {
            e.clearSelection();
            Feng.success("已复制到粘贴板");
            return false;
        });
        clipboard.on('error', function (e) {
            e.clearSelection();
            Feng.error("复制失败");
            return false;
        });
    };

    /**
     * 初始化表格的列
     */
    actCard.initColumn = function () {
        return [[
            {align: 'center', field: 'cardId', fixed: 'left', type: 'checkbox'},
            {field: 'appId', hide: true},
            {
                align: 'center', field: 'appName', fixed: 'left', width: 100, title: '所属应用', templet: function (d) {
                    if (!d.appName) {
                        return '通用卡密';
                    } else {
                        return d.appName;
                    }
                }
            },
            {align: 'center', field: 'cardCode', width: 300, title: '卡密', templet: '#cardCodeTpl'},
            {
                align: 'center', field: 'cardTypeName', sort: true, title: '卡类', templet: function (d) {
                    if (!d.cardTypeName) {
                        return '自定义';
                    } else {
                        return d.cardTypeName;
                    }
                }
            },
            {align: 'center', field: 'userName', title: '申请人名称'},
            {align: 'center', field: 'cardStatus', sort: true, title: '状态', templet: '#cardStatusTpl'},
            {align: 'center', field: 'activeTime', sort: true, title: '激活时间'},
            {align: 'center', field: 'expireTime', sort: true, title: '过期时间'},
            {align: 'center', field: 'cardRemark', title: '备注'},
            {align: 'center', toolbar: '#tableBar', width: 125, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    actCard.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(actCard.tableId, {where: queryData});
    };


    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    actCard.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/actCard/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(actCard.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("actCardId", data.actCardId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    actCard.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            Feng.error("未选中数据!");
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].actCardId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/actCard/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(actCard.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定要删除这些数据?", operation);
    };

    /**
     * 弹出添加对话框
     */
    actCard.openAddDlg = function (agentAppId) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '新增卡密',
            area: '700px',
            content: Feng.ctxPath + '/actCard/add?agentAppId='+agentAppId,
            end: function () {
                admin.getTempData('formOk') && table.reload(actCard.tableId+agentAppId);
            }
        });
    };

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        actCard.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        actCard.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        actCard.exportExcel();
    });

    if ($('#agentAppsSize').val()>0){
        // 渲染表格
        var tableResult = table.render({
            elem: '#' + actCard.tableId + agentAppId,
            url: Feng.ctxPath + '/actCard/list',
            page: true,
            height: "full-158",
            cellMinWidth: 100,
            toolbar: '#' + actCard.tableId + '-toolbar',
            defaultToolbar: ['filter', 'print'],
            where:{
                'actCardAppId':$('.layui-this').attr('data-appId')
            },
            cols: actCard.initColumn()
        });
    }
    // 表头工具条点击事件
    table.on('toolbar(' + actCard.tableId + agentAppId+ ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            actCard.openAddDlg(agentAppId);
        } else if (obj.event === 'refresh') {
            table.reload(actCard.tableId);
        } else if (obj.event === 'batchRemove') {
            actCard.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + actCard.tableId + agentAppId+')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            actCard.openEditDlg(data);
        } else if (layEvent === 'delete') {
            actCard.onDeleteItem(data);
        } else if (layEvent === 'recharge') {
            actCard.openRechargeDlg(data);
        } else if (layEvent === 'power') {
            actCard.openPowerDlg(data);
        } else if (layEvent === 'card') {
            actCard.openCardDlg(data);
        } else if (layEvent === 'copy') {
            actCard.copy(data)
        }
    });
});
