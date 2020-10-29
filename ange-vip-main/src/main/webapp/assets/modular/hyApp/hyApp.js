layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 幻影破解管理
     */
    var HyApp = {
        tableId: "hyAppTable"
    };

    /**
     * 初始化表格的列
     */
    HyApp.initColumn = function () {
        return [[
            {align: 'center',field: 'appinfoid', fixed: 'left',type: 'checkbox'},
            {align: 'center',field: 'utDid', title: '手机型号'},
            {align: 'center',field: 'name', title: '名称'},
            {align: 'center',field: 'packAge', title: '包名'},
            {align: 'center',field: 'sign', title: '应用名MD5'},
            {align: 'center',field: 'createTime', sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    HyApp.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(HyApp.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    HyApp.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加幻影破解',
            area: '700px',
            content: Feng.ctxPath + '/hyApp/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(HyApp.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    HyApp.exportExcel = function () {
        var checkRows = table.checkStatus(HyApp.tableId);
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
    HyApp.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改幻影破解',
            area: '700px',
            content: Feng.ctxPath + '/hyApp/edit?appinfoid=' + data.appinfoid,
            end: function () {
                admin.getTempData('formOk') && table.reload(HyApp.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    HyApp.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/hyApp/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(HyApp.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("appinfoid", data.appinfoid);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    HyApp.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].appinfoid+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/hyApp/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(HyApp.tableId);
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
        elem: '#' + HyApp.tableId,
        url: Feng.ctxPath + '/hyApp/list',
        page: true,
        toolbar: '#' + HyApp.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: HyApp.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        HyApp.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        HyApp.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        HyApp.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + HyApp.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            HyApp.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(HyApp.tableId);
        } else if(obj.event === 'batchRemove'){
            HyApp.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + HyApp.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            HyApp.openEditDlg(data);
        } else if (layEvent === 'delete') {
            HyApp.onDeleteItem(data);
        }
    });
});
