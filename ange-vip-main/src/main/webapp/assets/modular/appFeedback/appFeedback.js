layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 应用反馈列表管理
     */
    var AppFeedback = {
        tableId: "appFeedbackTable"
    };

    /**
     * 初始化表格的列
     */
    AppFeedback.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {align: 'center',field: 'appId',  title: '所属应用'},
            {align: 'center',field: 'context', title: '反馈的内容'},
            {align: 'center',field: 'edition', title: '版本号'},
            {align: 'center',field: 'model', title: '设备型号'},
            {align: 'center',field: 'mac', title: '机器码'},
            {align: 'center',field: 'links',  title: '联系方式'},
            {align: 'center',field: 'createTime', title: '创建时间'},
            {align: 'center',field: 'updateTime', sort: true, title: '更新时间'},
            {align: 'center',toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    AppFeedback.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(AppFeedback.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    AppFeedback.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加应用反馈列表',
            area: '700px',
            content: Feng.ctxPath + '/appFeedback/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(AppFeedback.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    AppFeedback.exportExcel = function () {
        var checkRows = table.checkStatus(AppFeedback.tableId);
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
    AppFeedback.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改应用反馈列表',
            area: '700px',
            content: Feng.ctxPath + '/appFeedback/edit?feedbackId=' + data.feedbackId,
            end: function () {
                admin.getTempData('formOk') && table.reload(AppFeedback.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    AppFeedback.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appFeedback/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(AppFeedback.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("feedbackId", data.feedbackId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    AppFeedback.batchRemove = function(){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].feedbackId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/appFeedback/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(AppFeedback.tableId);
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
        elem: '#' + AppFeedback.tableId,
        url: Feng.ctxPath + '/appFeedback/list',
        // toolbar: '#' + AppFeedback.tableId + '-toolbar',
        // defaultToolbar: ['filter'],
        height: "full-80",
        page: {limit: 15, limits: [15, 30, 45, 60, 75, 90, 105, 120, 200]},
        cellMinWidth: 100,
        cols: AppFeedback.initColumn(),
        done: function () {this.where={};}
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        AppFeedback.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        AppFeedback.openAddDlg();
    });

    // 批量删除点击事件
    $('#batchRemove').click(function () {
        AppFeedback.batchRemove()
    });

    // 导出excel
    $('#btnExp').click(function () {
        AppFeedback.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + AppFeedback.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            AppFeedback.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(AppFeedback.tableId);
        } else if(obj.event === 'batchRemove'){
            AppFeedback.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + AppFeedback.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            AppFeedback.openEditDlg(data);
        } else if (layEvent === 'delete') {
            AppFeedback.onDeleteItem(data);
        }
    });
});
