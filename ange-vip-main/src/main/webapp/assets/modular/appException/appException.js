layui.use(['table', 'admin', 'ax', 'form'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var form = layui.form;

    /**
     * 应用异常列表管理
     */
    var AppException = {
        tableId: "appExceptionTable"
    };

    /**
     * 初始化表格的列
     */
    AppException.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {align: 'center',field: 'appId', title: '所属应用'},
            {align: 'center',field: 'title', title: '异常标题'},
            {align: 'center',field: 'context', title: '异常内容'},
            {align: 'center',field: 'edition', title: '版本号'},
            {align: 'center',field: 'model', title: '设备型号'},
            {align: 'center',field: 'mac', title: '机器码'},
            {align: 'center',field: 'exceptionNum', title: '异常次数'},
            {align: 'center',field: 'updateTime', sort: true, title: '异常时间'},
            // {align: 'center',toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AppException.search = function () {
        var queryData = {};
        queryData['appId'] = $("#appId").val();
        queryData['edition'] = $("#edition").val();
        table.reload(AppException.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AppException.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加应用异常列表',
            area: '700px',
            content: Feng.ctxPath + '/appException/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AppException.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AppException.exportExcel = function () {
        var checkRows = table.checkStatus(AppException.tableId);
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
    AppException.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改应用异常列表',
            area: '700px',
            content: Feng.ctxPath + '/appException/edit?exceptionId=' + data.exceptionId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AppException.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AppException.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appException/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AppException.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("exceptionId", data.exceptionId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AppException.batchRemove = function(){
        let data = table.checkStatus(AppException.tableId).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].exceptionId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appException/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AppException.tableId);
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
        elem: '#' + AppException.tableId,
        url: Feng.ctxPath + '/appException/list',
        height: "full-80",
        page: {limit: 15, limits: [15, 30, 45, 60, 75, 90, 105, 120, 200]},
        cellMinWidth: 100,
        cols: AppException.initColumn(),
        done: function () {this.where={};}
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AppException.search();
    });

    // 重置按钮点击事件
    $('#btnReset').click(function () {
        var queryData = {};
        $("#appId").val("");
        $("#edition").val("");
        queryData['appId'] = "";
        queryData['edition'] = "";
        form.render();
        table.reload(AppException.tableId, {
            where: queryData, page: {curr: 1}
        });
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AppException.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        AppException.exportExcel();
    });
    // 批量删除点击事件
    $('#batchRemove').click(function () {
        AppException.batchRemove()
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AppException.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AppException.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(AppException.tableId);
        } else if(obj.event === 'batchRemove'){
            AppException.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AppException.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AppException.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AppException.onDeleteItem(data);
        }
    });
});
