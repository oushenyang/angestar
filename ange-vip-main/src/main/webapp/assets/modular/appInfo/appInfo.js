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
     * 初始化表格的列
     */
    AppInfo.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'appId', hide: true, title: '软件id'},
            {field: 'appNum', sort: true, title: '应用编号'},
            {field: 'userId', sort: true, title: '开发者用户id'},
            {field: 'appName', sort: true, title: '应用名'},
            {field: 'cydiaFlag', sort: true, title: '运行状态 0-收费；1-免费；2-关闭'},
            {field: 'appNotice', sort: true, title: '软件公告'},
            {field: 'customData1', sort: true, title: '应用自定义数据1'},
            {field: 'customData2', sort: true, title: '应用自定义数据2'},
            {field: 'customData3', sort: true, title: '应用自定义数据2'},
            {field: 'codeBindType', sort: true, title: '单码绑机策略 0-关闭；1-MAC；2-IP；3-混合'},
            {field: 'codeBindOption', sort: true, title: '单码绑机选项 0-每天；1-永久；'},
            {field: 'codeBindNum', sort: true, title: '单码重绑次数'},
            {field: 'codeBindTime', sort: true, title: '单码重绑扣时'},
            {field: 'accountBindType', sort: true, title: '账号绑机策略 0-关闭；1-MAC；2-IP；3-混合'},
            {field: 'accountBindOption', sort: true, title: '账号绑机选项 0-每天；1-永久；'},
            {field: 'accountBindNum', sort: true, title: '账号重绑次数'},
            {field: 'accountBindTime', sort: true, title: '账号重绑扣时'},
            {field: 'codeOpenRange', sort: true, title: '单码多开范围 0-关闭；1-单设备；2-单IP；3-所有设备'},
            {field: 'codeSignType', sort: true, title: '单码登录方式 0-非顶号；1-顶号；'},
            {field: 'codeOpenNum', sort: true, title: '单码多开数量'},
            {field: 'accountOpenRange', sort: true, title: '账号多开范围 0-关闭；1-单设备；2-单IP；3-所有设备'},
            {field: 'accountSignType', sort: true, title: '账号登录方式 0-非顶号；1-顶号；'},
            {field: 'accountOpenNum', sort: true, title: '账号多开数量'},
            {field: 'codeTryType', sort: true, title: '单码试用策略 0-关闭；1-时间；2-次数；'},
            {field: 'codeTryTime', sort: true, title: '单码试用时长'},
            {field: 'accountRegisterSwitch', sort: true, title: '账号用户注册开关 0-否；1-是'},
            {field: 'accountRegisterLimit', sort: true, title: '账号注册限制 0-关闭；1-每天；2-永久；'},
            {field: 'accountRegisterNum', sort: true, title: '账号注册次数'},
            {field: 'accountRegisterTime', sort: true, title: '账号试用时间'},
            {field: 'webAlgorithmType', sort: true, title: 'webApi加密算法 0-关闭；1-DES；2-AES；'},
            {field: 'webKey', sort: true, title: 'webApi加密key'},
            {field: 'webSalt', sort: true, title: 'webApi签名盐'},
            {field: 'versionNum', sort: true, title: '版本号id'},
            {field: 'createUser', sort: true, title: '创建人'},
            {field: 'createTime', sort: true, title: '创建时间'},
            {field: 'updateUser', sort: true, title: '更新人'},
            {field: 'updateTime', sort: true, title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AppInfo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(AppInfo.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AppInfo.openAddDlg = function () {
        admin.putTempData('formOk', false);
        func.open({
            type: 2,
            area: '700px',
            title: '添加软件表',
            content: Feng.ctxPath + '/appInfo/add',
            endCallback: function () {
                AppInfo.loadAppInfo();
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AppInfo.exportExcel = function () {
        var checkRows = table.checkStatus(AppInfo.tableId);
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
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AppInfo.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appInfo/delete", function (data) {
                Feng.success("删除成功!");
                AppInfo.loadAppInfo();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("appId", data.appId);
            ajax.set("appNum", data.appNum);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    AppInfo.loadAppInfo = function () {
        dataGrid.render({
            elem: '#demoGrid1',  // 容器
            templet: '#demoGridItem3',  // 模板
            height: '1200',
            defaultToolbar: ['filter'],
            data: Feng.ctxPath + '/appInfo/list', // url
            page: {limit: 12, limits: [12, 24, 36, 48, 60]},
            done:function(data,curr,count){
            },
            onItemClick: function (obj) {  // item事件
                var index = obj.index + 1;
                layer.msg('点击了第' + index + '个', {icon: 1});
            },
            onToolBarClick: function (obj) {  // toolBar事件
                var event = obj.event;
                var data = obj.data;
                if (event == 'download') {
                    layer.msg('点击了下载', {icon: 1});
                } else if (event == 'edit') {
                    AppInfo.openEditDlg(data,'基本信息',event);
                } else if (event == 'share') {
                    layer.msg('点击了分享', {icon: 1});
                } else if (event == 'bind') {
                    AppInfo.openEditDlg(data,'绑机配置',event);
                } else if (event == 'open') {
                    AppInfo.openEditDlg(data,'多开配置',event);
                } else if (event == 'trial') {
                    AppInfo.openEditDlg(data,'试用注册',event);
                } else if (event == 'password') {
                    AppInfo.openEditDlg(data,'密匙配置',event);
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
