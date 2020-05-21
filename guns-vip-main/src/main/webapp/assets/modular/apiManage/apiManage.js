layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 接口管理管理
     */
    var ApiManage = {
        tableId: "apiManageTable"
    };

    /**
     * 初始化表格的列
     */
    ApiManage.initColumn = function () {
        return [[
            {align: 'center',field: 'apiManageId', fixed: 'left',type: 'checkbox'},
            // {align: 'center',field: 'appId', sort: true, title: '应用id'},
            {align: 'center',field: 'callCode',  title: '调用码'},
            {align: 'center',field: 'apiStatus', title: '接口状态 '},
            {align: 'center',field: 'apiType', title: '接口类别'},
            {align: 'center',field: 'apiName', title: '接口名称'},
            {align: 'center',field: 'apiCode', title: '接口编码'},
            {align: 'center',field: 'parameterNum', title: '参数数量'},
            {align: 'center',field: 'parameterOne', title: '参数一'},
            {align: 'center',field: 'parameterOneRemark', title: '参数一说明'},
            {align: 'center',field: 'parameterTwo', title: '参数二'},
            {align: 'center',field: 'parameterTwoRemark', title: '参数二说明'},
            {align: 'center',field: 'parameterThree', title: '参数三'},
            {align: 'center',field: 'parameterThreeRemark', title: '参数三说明'},
            {align: 'center',field: 'parameterFour', title: '参数四'},
            {align: 'center',field: 'parameterFourRemark', title: '参数四说明'},
            {align: 'center',field: 'parameterFive', title: '参数五'},
            {align: 'center',field: 'parameterFiveRemark', title: '参数五说明'},
            {align: 'center',field: 'parameterSix', title: '参数六'},
            {align: 'center',field: 'parameterSixRemark', title: '参数六说明'},
            {align: 'center',field: 'parameterSeven', title: '参数七'},
            {align: 'center',field: 'parameterSevenRemark', title: '参数七说明'},
            {align: 'center',field: 'returnRemark', title: '返回说明'},
            {align: 'center',field: 'remark', title: '备注'},
            {align: 'center',field: 'sort', title: '排序'},
            {align: 'center',field: 'createTime', title: '创建时间'},
            {align: 'center',field: 'updateTime', title: '更新时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    ApiManage.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(ApiManage.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    ApiManage.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加接口管理',
            area: '700px',
            content: Feng.ctxPath + '/apiManage/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(ApiManage.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    ApiManage.exportExcel = function () {
        var checkRows = table.checkStatus(ApiManage.tableId);
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
    ApiManage.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改接口管理',
            area: '700px',
            content: Feng.ctxPath + '/apiManage/edit?apiManageId=' + data.apiManageId,
            end: function () {
                admin.getTempData('formOk') && table.reload(ApiManage.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    ApiManage.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/apiManage/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(ApiManage.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("apiManageId", data.apiManageId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    ApiManage.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].apiManageId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/apiManage/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(ApiManage.tableId);
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
        elem: '#' + ApiManage.tableId,
        url: Feng.ctxPath + '/apiManage/list',
        page: true,
        toolbar: '#' + ApiManage.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: ApiManage.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        ApiManage.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        ApiManage.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        ApiManage.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + ApiManage.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            ApiManage.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(ApiManage.tableId);
        } else if(obj.event === 'batchRemove'){
            ApiManage.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + ApiManage.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            ApiManage.openEditDlg(data);
        } else if (layEvent === 'delete') {
            ApiManage.onDeleteItem(data);
        }
    });
});
