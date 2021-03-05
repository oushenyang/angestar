layui.use(['table', 'admin', 'pearOper', 'notice', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var form = layui.form;
    var $ax = layui.ax;
    var admin = layui.admin;
    var notice = layui.notice;
    var pearOper = layui.pearOper;


    /**
     * 版本表管理
     */
    var AppEdition = {
        tableId: "appEditionTable"
    };

    /**
     * 初始化表格的列
     */
    AppEdition.initColumn = function () {
        return [[
            {align: 'center', field: 'editionId', type: 'checkbox'},
            {align: 'center', field: 'appName', title: '所属应用'},
            // {align: 'center',field: 'editionSerial', sort: true, title: '版本编号'},
            {align: 'center', field: 'editionNum', sort: true, title: '版本号'},
            {align: 'center', field: 'editionName', sort: true, title: '版本名称'},
            {align: 'center', field: 'editionStatus', sort: true, title: '状态', templet: '#editionStatusTpl'},
            {align: 'center', field: 'needUpdate', sort: true, title: '强制更新', templet: '#needUpdateTpl'},
            {align: 'center', field: 'createTime', sort: true, title: '创建时间'},
            // {align: 'center', field: 'remark', sort: true, title: '备注'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AppEdition.search = function () {
        var queryData = {};
        queryData['appId'] = $("#appId").val();
        queryData['editionName'] = $("#editionName").val();
        table.reload(AppEdition.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AppEdition.openAddDlg = function () {
        admin.putTempData('formOk', false);
        admin.open({
            type: 2,
            area: '700px',
            title: '添加版本表',
            content: Feng.ctxPath + '/appEdition/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AppEdition.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AppEdition.exportExcel = function () {
        var checkRows = table.checkStatus(AppEdition.tableId);
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
    AppEdition.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        admin.open({
            type: 2,
            area: '700px',
            title: '修改版本表',
            content: Feng.ctxPath + '/appEdition/edit?editionId=' + data.editionId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AppEdition.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AppEdition.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appEdition/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AppEdition.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("editionId", data.editionId);
            ajax.set("appId", data.appId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);

        // pearOper.confirm({
        //     title: "删除数据",
        //     message: "确定要删除这些数据?",
        //     success: function () {
        //         console.log("确认")
        //     },
        //     cancle: function () {
        //         console.log("取消");
        //     }
        // })
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AppEdition.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].editionId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appEdition/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AppEdition.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定要删除这些数据?", operation);
    };

    /**
     * 修改版本是否强制更新
     *
     * @param editionId 版本id
     * @param checked 是否选中（true,false），选中就是解锁用户，未选中就是锁定用户
     */
    AppEdition.changeUserStatus = function (editionId, checked) {
        if (checked) {
            var ajax = new $ax(Feng.ctxPath + "/appEdition/needUpdate", function (data) {
                Feng.success("开启成功!");
            }, function (data) {
                Feng.error("开启失败!");
                table.reload(AppEdition.tableId);
            });
            ajax.set("editionId", editionId);
            ajax.set("needUpdate", true);
            ajax.start();
        } else {
            var ajax = new $ax(Feng.ctxPath + "/appEdition/needUpdate", function (data) {
                Feng.success("关闭成功!");
            }, function (data) {
                Feng.error("关闭失败!" + data.responseJSON.message + "!");
                table.reload(AppEdition.tableId);
            });
            ajax.set("editionId", editionId);
            ajax.set("needUpdate", false);
            ajax.start();
        }
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AppEdition.tableId,
        url: Feng.ctxPath + '/appEdition/list',
        toolbar: '#appEdition-toolbar',
        defaultToolbar: ['filter'],
        height: "full-80",
        page: {limit: 15, limits: [15, 30, 45, 60, 75, 90, 105, 120, 200]},
        cellMinWidth: 100,
        cols: AppEdition.initColumn(),
        done: function () {this.where={};}
    });

    //加载查询数据
    var queryData = {};
    queryData['appId'] = $("#appId").val();
    queryData['editionName'] = $("#editionName").val();
    table.reload(AppEdition.tableId, {where: queryData});

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AppEdition.search();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AppEdition.exportExcel();
    });

    table.on('toolbar(' + AppEdition.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AppEdition.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(AppEdition.tableId);
        } else if(obj.event === 'batchRemove'){
            AppEdition.batchRemove(obj)
        }
    });

    // 工具条点击事件
    table.on('tool(' + AppEdition.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AppEdition.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AppEdition.onDeleteItem(data);
        }
    });

    // 修改版本是否强制更新
    form.on('switch(needUpdate)', function (obj) {

        var editionId = obj.elem.value;
        var checked = obj.elem.checked ? true : false;

        AppEdition.changeUserStatus(editionId, checked);
    });
});
