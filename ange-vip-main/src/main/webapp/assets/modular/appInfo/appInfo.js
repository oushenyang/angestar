layui.use(['table','dataGrid','admin', 'ax', 'element', 'dropdown','func'], function () {
    var $ = layui.$;
    var table = layui.table;
    var dataGrid = layui.dataGrid;
    var $ax = layui.ax;
    var admin = layui.admin;
    var func = layui.func;

    /**
     * 软件表管理
     */
    var AppInfo = {
        tableId: "appInfoTable"
    };

    /**
     * 点击查询按钮
     */
    AppInfo.search = function () {
        AppInfo.loadAppInfo($("#appName").val());
    };

    /**
     * 弹出添加对话框
     */
    AppInfo.openAddDlg = function () {
        admin.putTempData('formOk', false);
        func.open({
            type: 2,
            area: '700px',
            title: '新增应用',
            content: Feng.ctxPath + '/appInfo/add',
            endCallback: function () {
                AppInfo.loadAppInfo();
            }
        });
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    AppInfo.openEditDlg = function (data,title,event) {
        admin.putTempData('formOk', false);
        func.open({
            type: 2,
            area: '700px',
            title: title,
            content: Feng.ctxPath + '/appInfo/edit?appId=' + data.appId +'&event='+event+'&appNum='+data.appNum,
            endCallback: function () {
                AppInfo.loadAppInfo();
            }
        });
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    AppInfo.openQuickDlg = function (data) {
        admin.putTempData('formOk', false);
        func.open({
            type: 2,
            area: '700px',
            title: '快捷页面',
            content: Feng.ctxPath + '/appInfo/quick?appId=' + data.appId +'&appNum='+data.appNum,
            endCallback: function () {
                AppInfo.loadAppInfo();
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AppInfo.onDeleteItem = function (data) {
        var operation = function () {
            var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
            var ajax = new $ax(Feng.ctxPath + "/appInfo/delete", function (data) {
                layer.close(loading);
                Feng.success("删除成功!");
                AppInfo.loadAppInfo();
            }, function (data) {
                layer.close(loading);
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("appId", data.appId);
            ajax.set("appNum", data.appNum);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    AppInfo.loadAppInfo = function (appName) {
        dataGrid.render({
            elem: '#appInfoTable',  // 容器
            templet: '#appInfoItem',  // 模板
            height: '1200',
            defaultToolbar: ['filter'],
            data: Feng.ctxPath + '/appInfo/list', // url
            page: {limit: 12, limits: [12, 24, 36, 48, 60]},
            where: {
                'appName':appName
            },
            done:function(data){
                if (data.length === 0){
                    $('#ew-datagrid-page-appInfoTable').hide();
                    $('#appInfoTable').attr("style",'min-height: 650px;position: relative;');
                    $('#appInfoTable').html('<div class="no_result">' +
                        '<p><img width="106" height="130" src="'+Feng.ctxPath+'/assets/expand/images/no_result.png" alt="找不到结果为空相关搜索结果"></p>' +
                        '<p class="words"><span class="line">没有找到合适的应用</span>' +
                        '</p>' +
                        '</div>')
                }
            },
            onToolBarClick: function (obj) {  // toolBar事件
                var event = obj.event;
                var data = obj.data;
                if (event == 'download') {
                } else if (event == 'edit') {
                    AppInfo.openEditDlg(data,'基本信息',event);
                } else if (event == 'bind') {
                    AppInfo.openEditDlg(data,'绑机配置',event);
                } else if (event == 'open') {
                    AppInfo.openEditDlg(data,'多开配置',event);
                } else if (event == 'trial') {
                    AppInfo.openEditDlg(data,'试用注册',event);
                } else if (event == 'password') {
                    AppInfo.openEditDlg(data,'密匙配置',event);
                } else if (event == 'quick') {
                    AppInfo.openQuickDlg(data);
                } else if (event == 'otherSign') {
                    AppInfo.openEditDlg(data,'外部验证对接',event);
                } else if (event == 'delete') {
                    AppInfo.onDeleteItem(data);
                }
            }
        });
    };
    //渲染应用列表
    AppInfo.loadAppInfo();

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AppInfo.search();
    });

    // 重置按钮点击事件
    $('#btnReset').click(function () {
        $("#appName").val("");
        AppInfo.loadAppInfo();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AppInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AppInfo.exportExcel();
    });

    // 工具条点击事件
    table.on('tool(' + AppInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AppInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AppInfo.onDeleteItem(data);
        }
    });
});
