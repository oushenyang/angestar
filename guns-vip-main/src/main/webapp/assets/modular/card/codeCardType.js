layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 单码卡类列表管理
     */
    var CodeCardType = {
        tableId: "codeCardTypeTable"
    };

    /**
     * 初始化表格的列
     */
    CodeCardType.initColumn = function () {
        return [[
            {type: 'checkbox'},
            {field: 'cardTypeId', hide: true, title: '卡类Id'},
            {field: 'appId', sort: true, title: '所属软件'},
            {field: 'cardTypeName', sort: true, title: '卡类名称'},
            {field: 'cardTimeType', sort: true, title: '卡类时间类型'},
            {field: 'cardTypeData', sort: true, title: '卡值'},
            {field: 'cardTypePrefix', sort: true, title: '卡密前缀'},
            {field: 'cardTypeRule', sort: true, title: '卡密规则 0-字母+数字；1-字母；2-数字'},
            {field: 'cardTypeLength', sort: true, title: '卡密长度 0-32位；1-16位；2-8位；'},
            {field: 'cardTypePrice', sort: true, title: '售价'},
            {field: 'cardTypeAgentPrice', sort: true, title: '代理售价'},
            {field: 'revision', sort: true, title: '乐观锁'},
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
    CodeCardType.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(CodeCardType.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    CodeCardType.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加单码卡类列表',
            area: '700px',
            content: Feng.ctxPath + '/codeCardType/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(CodeCardType.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    CodeCardType.exportExcel = function () {
        var checkRows = table.checkStatus(CodeCardType.tableId);
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
    CodeCardType.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '修改单码卡类列表',
            area: '700px',
            content: Feng.ctxPath + '/codeCardType/edit?cardTypeId=' + data.cardTypeId,
            end: function () {
                admin.getTempData('formOk') && table.reload(CodeCardType.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CodeCardType.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/codeCardType/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CodeCardType.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("cardTypeId", data.cardTypeId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    CodeCardType.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].cardTypeId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/codeCardType/batchRemove", function (data) {
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

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + CodeCardType.tableId,
        url: Feng.ctxPath + '/codeCardType/list',
        page: true,
        toolbar: '#' + CodeCardType.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: CodeCardType.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CodeCardType.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        CodeCardType.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        CodeCardType.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + CodeCardType.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            CodeCardType.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(CodeCardType.tableId);
        } else if(obj.event === 'batchRemove'){
            CodeCardType.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + CodeCardType.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            CodeCardType.openEditDlg(data);
        } else if (layEvent === 'delete') {
            CodeCardType.onDeleteItem(data);
        }
    });
});
