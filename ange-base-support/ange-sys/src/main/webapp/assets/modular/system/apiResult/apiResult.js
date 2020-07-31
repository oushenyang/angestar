layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 接口自定义返回管理
     */
    var ApiResult = {
        tableId: "apiResultTable"
    };

    /**
     * 初始化表格的列
     */
    ApiResult.initColumn = function () {
        return [[
            {align: 'center',field: 'apiResultId', fixed: 'left',type: 'checkbox'},
            {align: 'center',field: 'resultType',  title: '返回类别'},
            {align: 'center',field: 'resultSuccess', title: '是否成功'},
            // {align: 'center',field: 'resultVariables', title: '变量编码集合'},
            {align: 'center',field: 'resultCode', title: '默认返回码'},
            {align: 'center',field: 'resultData', title: '默认返回数据'},
            {align: 'center',field: 'customResultData',  title: '自定义返回数据'},
            {align: 'center',field: 'resultRemark', title: '返回说明'},
            {align: 'center',field: 'whetherEdit',  title: '是否可编辑'},
            // {align: 'center',field: 'whetherResultJson', sort: true, title: '是否返回json  0-否；1-是'},
            {align: 'center',field: 'sort', sort: true, title: '排序'},
            {align: 'center',toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ApiResult.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ApiResult.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    ApiResult.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加接口自定义返回',
            area: '700px',
            content: Feng.ctxPath + '/apiResult/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(ApiResult.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    ApiResult.exportExcel = function () {
        var checkRows = table.checkStatus(ApiResult.tableId);
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
    ApiResult.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改接口自定义返回',
            area: '700px',
            content: Feng.ctxPath + '/apiResult/edit?apiResultId=' + data.apiResultId,
            end: function () {
                admin.getTempData('formOk') && table.reload(ApiResult.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ApiResult.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/apiResult/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ApiResult.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("apiResultId", data.apiResultId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    ApiResult.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].apiResultId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/apiResult/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(ApiResult.tableId);
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
        elem: '#' + ApiResult.tableId,
        url: Feng.ctxPath + '/apiResult/list',
        page: true,
        toolbar: '#' + ApiResult.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: ApiResult.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ApiResult.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ApiResult.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ApiResult.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + ApiResult.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            ApiResult.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(ApiResult.tableId);
        } else if(obj.event === 'batchRemove'){
            ApiResult.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + ApiResult.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ApiResult.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ApiResult.onDeleteItem(data);
        }
    });
});
