layui.use(['table', 'admin', 'ax'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;

    /**
     * 注册码表管理
     */
    var SerialInfo = {
        tableId: "serialInfoTable"
    };

    SerialInfo.copy = function (data) {
        var cla = '.serialCode' + data.cardId;
        var clipboard = new ClipboardJS(cla, {
            text: function () {
                return data.serialCode;
            }
        });
        clipboard.on('success', function (e) {
            e.clearSelection();
            Feng.success("已复制到粘贴板");
            return false;
        });
        clipboard.on('error', function (e) {
            e.clearSelection();
            Feng.error("复制失败");
            return false;
        });
    };

    /**
     * 初始化表格的列
     */
    SerialInfo.initColumn = function () {
        return [[
            {align: 'center',field: 'serialId', fixed: 'left',type: 'checkbox'},
            {field: 'appId', hide: true},
            {
                align: 'center', field: 'appName', fixed: 'left', width: 100, title: '所属应用', templet: function (d) {
                    if (!d.appName) {
                        return '通用卡密';
                    } else {
                        return d.appName;
                    }
                }
            },
            {align: 'center', field: 'serialCode', width: 300, title: '注册码', templet: '#serialCodeTpl'},
            {
                align: 'center', field: 'cardTypeName', sort: true, title: '卡类', templet: function (d) {
                    if (!d.cardTypeName) {
                        return '自定义';
                    } else {
                        return d.cardTypeName;
                    }
                }
            },
            {align: 'center',field: 'userName', title: '申请人名称'},
            {align: 'center',field: 'cardStatus', title: '状态', templet: '#cardStatusTpl'},
            // {align: 'center',field: 'changeBindNum', sort: true, title: '换绑数'},
            {align: 'center',field: 'activeTime', sort: true, title: '激活时间'},
            {align: 'center',field: 'expireTime', sort: true, title: '过期时间'},
            {align: 'center',field: 'remark',title: '备注'},
            {align: 'center',toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    SerialInfo.search = function () {
        var queryData = {};
        queryData['condition'] = $("#condition").val();
        table.reload(SerialInfo.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    SerialInfo.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加注册码',
            area: '700px',
            content: Feng.ctxPath + '/serialInfo/add?type='+$('#type').val(),
            end: function () {
                admin.getTempData('formOk') && table.reload(SerialInfo.tableId);
            }
        });
    };

    /**
     * 导出excel按钮
     */
    SerialInfo.exportExcel = function () {
        var checkRows = table.checkStatus(SerialInfo.tableId);
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
    SerialInfo.openEditDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '编辑',
            area: '700px',
            content: Feng.ctxPath + '/serialInfo/edit?serialId=' + data.serialId,
            end: function () {
                admin.getTempData('formOk') && table.reload(SerialInfo.tableId);
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    SerialInfo.onDeleteItem = function (data) {
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/serialInfo/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(SerialInfo.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("serialId", data.serialId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    SerialInfo.batchRemove = function(obj){
        let data = table.checkStatus(obj.config.id).data;
        if(data.length === 0){
            Feng.error("未选中数据!" );
            return false;
        }
        let ids = "";
        for(let i = 0;i<data.length;i++){
            ids += data[i].serialId+",";
        }
        ids = ids.substr(0,ids.length-1);
        var operation = function () {
            var ajax = new $ax(Feng.ctxPath + "/serialInfo/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(SerialInfo.tableId);
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
        elem: '#' + SerialInfo.tableId,
        url: Feng.ctxPath + '/serialInfo/list?type='+$('#type').val(),
        page: true,
        toolbar: '#' + SerialInfo.tableId + '-toolbar',
                defaultToolbar: [{
                    title:'刷新',
                    layEvent: 'refresh',
                    icon: 'layui-icon-refresh',
                }, 'filter', 'print'],
        height: "full-115",
        cellMinWidth: 100,
        cols: SerialInfo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        SerialInfo.search();
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        SerialInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        SerialInfo.exportExcel();
    });

    // 表头工具条点击事件
    table.on('toolbar(' + SerialInfo.tableId + ')', function(obj){
        //添加
        if(obj.event === 'btnAdd'){
            SerialInfo.openAddDlg();
        } else if(obj.event === 'refresh'){
            table.reload(SerialInfo.tableId);
        } else if(obj.event === 'batchRemove'){
            SerialInfo.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + SerialInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            SerialInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            SerialInfo.onDeleteItem(data);
        }else if (layEvent === 'copy') {
            SerialInfo.copy(data)
        }
    });
});
