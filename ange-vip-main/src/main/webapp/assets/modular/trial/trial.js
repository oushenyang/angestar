layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 试用信息管理
     */
    var Trial = {
        tableId: "trialTable"
    };

    /**
     * 初始化表格的列
     */
    Trial.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {align: 'center',field: 'appName',  title: '所属应用'},
            {align: 'center',field: 'mac', title: '设备唯一序列号'},
            // {align: 'center',field: 'ip', sort: true, title: 'ip'},
            {align: 'center',field: 'model', title: '设备型号'},
            {align: 'center',field: 'trialNum', title: '剩余时间/次数', templet: function (d) {
                    if (d.trialNum===0) {
                        return d.trialTime+'分钟';
                    } else {
                        return d.trialNum+'次';
                    }
                   }
                },
            // {align: 'center',field: 'trialTime', sort: true, title: '到期时间'},
            {align: 'center',field: 'expire',  title: '状态', templet: '#trialExpireTpl'},
            // {align: 'center',field: 'createUser', sort: true, title: '创建人'},
            {align: 'center',field: 'createTime', title: '开始时间'}
            // {align: 'center',field: 'updateUser', sort: true, title: '更新人'},
            // {align: 'center',field: 'updateTime', sort: true, title: '更新时间'},
            // {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    Trial.search = function () {
        var queryData = {};
        queryData['model'] = $("#model").val();
        table.reload(Trial.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    Trial.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加试用信息',
            area: '700px',
            content: Feng.ctxPath + '/trial/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(Trial.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    Trial.exportExcel = function () {
        var checkRows = table.checkStatus(Trial.tableId);
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
    Trial.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改试用信息',
            area: '700px',
            content: Feng.ctxPath + '/trial/edit?trialId=' + data.trialId,
            end: function () {
                admin.getTempData('formOk') && table.reload(Trial.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    Trial.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/trial/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(Trial.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("trialId", data.trialId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    Trial.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].trialId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/trial/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(Trial.tableId);
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
        elem: '#' + Trial.tableId,
        url: Feng.ctxPath + '/trial/list',
        page: true,
        toolbar: '#' + Trial.tableId + '-toolbar',
        defaultToolbar: ['filter'],
        height: "full-158",
        cellMinWidth: 100,
        cols: Trial.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        Trial.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        Trial.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        Trial.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + Trial.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            Trial.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(Trial.tableId);
        } else if(obj.event === 'batchRemove'){
            Trial.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + Trial.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            Trial.openEditDlg(data);
        } else if (layEvent === 'delete') {
            Trial.onDeleteItem(data);
        }
    });
});
