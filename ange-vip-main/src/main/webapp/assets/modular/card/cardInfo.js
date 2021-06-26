layui.use(['table', 'form','dropdown', 'admin', 'ax', 'xmSelect','laydate', 'selectApp', 'textool'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var dropdown = layui.dropdown;
    var form = layui.form;
    var xmSelect = layui.xmSelect;
    var laydate = layui.laydate;
    var textool = layui.textool;
    var selectApp=layui.selectApp; //获取自定义模块
    selectApp.renderAppAll(); //渲染
    //重新渲染select数据
    form.render('select');
    // var clipboard = layui.clipboard;

    //日期范围
    laydate.render({
        elem: '#createTimeStr',
        range: true,
        trigger: 'click',

    });
    laydate.render({
        elem: '#activeTimeStr',
        range: true,
        trigger: 'click',
    });
    laydate.render({
        elem: '#expireTimeStr',
        range: true,
        trigger: 'click',
    });
    /**
     * 卡密表管理
     */
    var CardInfo = {
        tableId: "cardInfoTable"
    };
    CardInfo.copy = function (data) {
        var cla = '.cardCode' + data.cardId;
        console.log(data.cardCode);
        var clipboard = new ClipboardJS(cla, {
            text: function () {
                return data.cardCode;
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
    CardInfo.initColumn = function () {
        return [[
            // {type: 'checkbox'},
            {align: 'center', type: 'checkbox'},
            // {field: 'appId', hide: true},
            {
                align: 'center', field: 'appName', width: 100, title: '所属应用', templet: function (d) {
                    if (!d.appName) {
                        return '通用卡密';
                    } else {
                        return d.appName;
                    }
                }
            },
            {align: 'center', field: 'cardCode', width: 300, title: '卡密', templet: '#cardCodeTpl'},
            {
                align: 'center', field: 'cardTypeName', title: '卡类', templet: function (d) {
                    if (!d.cardTypeName) {
                        return '自定义';
                    } else {
                        return d.cardTypeName;
                    }
                }
            },
            // {align: 'center',field: 'userId', sort: true, title: '申请人ID'},
            {align: 'center', field: 'userName', title: '申请人名称'},
            {align: 'center', field: 'cardStatus',  title: '状态', templet: '#cardStatusTpl'},
            {align: 'center', field: 'activeTime', sort: true, title: '激活时间'},
            {align: 'center', field: 'expireTime', sort: true, title: '过期时间'},
            {align: 'center', field: 'cardRemark', title: '备注'},
            // {align: 'center',field: 'prohibitRemark', sort: true, title: '禁用备注'},
            {align: 'center', toolbar: '#tableBar', width: 125, fixed: 'right', title: '操作'}
        ]];
    };

    /**
     * 点击查询按钮
     */
    CardInfo.search = function () {
        var queryData = {};
        queryData['appId'] = $("#appId").val();
        queryData['cardTypeId'] = $("#cardTypeId").val();
        queryData['cardCode'] = $("#cardCode").val();
        queryData['cardStatus'] = $("#cardStatus").val();
        queryData['createTimeStr'] = $("#createTimeStr").val();
        queryData['activeTimeStr'] = $("#activeTimeStr").val();
        queryData['expireTimeStr'] = $("#expireTimeStr").val();
        queryData['cardRemark'] = $("#cardRemark").val();
        table.reload(CardInfo.tableId, {
            where: queryData, page: {curr: 1}
        });
    };

    /**
     * 弹出添加对话框
     */
    CardInfo.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加卡密',
            area: ['700px','637px'],
            content: Feng.ctxPath + '/cardInfo/add?type='+$('#type').val(),
            end: function () {
                admin.getTempData('formOk') && table.reload(CardInfo.tableId);
            }
        });
    };

    /**
     * 弹出导入易游对话框
     */
    CardInfo.openYyImportDlg = function (obj) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '导入易游',
            area: '700px',
            content: Feng.ctxPath + '/cardInfo/yyImport?type='+$('#type').val(),
            end: function () {
                admin.getTempData('formOk') && table.reload(CardInfo.tableId);
            }
        });
    };

    /**
     * 弹出自定义导入对话框
     */
    CardInfo.openImportDlg = function (obj) {
        admin.open({
            // type: 1,
            title: '自定义导入',
            area: '600px',
            url: Feng.ctxPath + '/cardInfo/customImport?type='+$('#type').val(),
            tpl: true,
            success: function (layero, dIndex) {
                laydate.render({
                    elem: '#activeTime',
                    position: 'fixed',
                    type: 'datetime',
                    trigger: 'click'
                });

                form.render();
                //单选框事件监听
                form.on('radio(exportFlag)', function (data) {
                    if (data.value==0){
                        $("select[name=splitSymbol]").attr("disabled", false);
                        form.render('select');
                    }else {
                        $("select[name=splitSymbol]").attr("disabled", "disabled");
                        form.render('select');
                    }
                });
                //表单提交事件
                form.on('submit(cardEditSubmit)', function (data) {
                    // admin.showLoading(undefined, 3, '.8');
                    var loading = layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
                    let idData = table.checkStatus(obj.config.id).data;
                    let ids = "";
                    for (let i = 0; i < idData.length; i++) {
                        ids += idData[i].cardId + ",";
                    }
                    ids = ids.substr(0, ids.length - 1);
                    if (data.field.operateFlag==0 && idData.length ==0){
                        layer.close(loading);
                        layer.msg('未选中数据!', {icon: 2});
                        return false;
                    }
                    data.field.ids = ids;
                    data.field.event = obj.event;
                    if (obj.event=='export'){
                        layer.close(loading);
                        window.location.href=Feng.ctxPath + "/cardInfo/exportCard?data=" + JSON.stringify(data.field);
                    }else {
                        var ajax = new $ax(Feng.ctxPath + "/cardInfo/editItem", function (data) {
                            layer.close(loading);
                            layer.closeAll();
                            layer.msg("更新成功！", {icon: 1});
                            table.reload(CardInfo.tableId);
                            //关掉对话框
                            admin.closeThisDialog();
                        }, function (data) {
                            layer.close(loading);
                            layer.msg("更新失败！" + data.responseJSON.message, {icon: 2});
                        },true);
                        ajax.set(data.field);
                        ajax.start();
                    }
                    return false;
                });
                // 禁止弹窗出现滚动条
                $(layero).children('.layui-layer-content').css('overflow', 'visible');
            }
        });
    };

    /**
     * 导出excel按钮
     */
    CardInfo.exportExcel = function () {
        var checkRows = table.checkStatus(CardInfo.tableId);
        if (checkRows.data.length === 0) {
            Feng.error("请选择要导出的数据");
        } else {
            table.exportFile(tableResult.config.id, checkRows.data, 'xls');
        }
    };

    /**
     * 弹出详情
     */
    CardInfo.openDetailDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '详情',
            area: ['700px','700px'],
            content: Feng.ctxPath + '/cardInfo/detail?cardId='+data.cardId,
            end: function () {
                admin.getTempData('formOk');
            }
        });
    };
    /**
     * 弹出卡密配置
     */
    CardInfo.openConfigEditlDlg = function (data) {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '卡密配置',
            area: ['700px','560px'],
            content: Feng.ctxPath + '/cardInfo/configEdit?cardId='+data.cardId,
            end: function () {
                admin.getTempData('formOk');
            }
        });
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.openEditDlg = function (obj) {
        var url = Feng.ctxPath + '/cardInfo/edit?type='+$('#type').val();
        admin.open({
            // type: 1,
            title: obj.name,
            area: '600px',
            url: url,
            data: {
                event: obj.event
            },
            tpl: true,
            success: function (layero, dIndex) {
                //封禁备注
                textool.init({
                    // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
                    eleId: null,
                    // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
                    maxlength: 128
                });
                if (obj.event=='unsealing'){
                    form.val('cardEditForm', {
                        "organizationType":1,
                        "cardType":1,
                        "cardStatus":5
                    });
                }else if (obj.event=='overtime'){
                    form.val('cardEditForm', {
                        "organizationType":1,
                        "cardType":1,
                        "cardStatus":1
                    });
                    laydate.render({
                        elem: '#addTime',
                        position: 'fixed',
                        type: 'datetime',
                        trigger: 'click',
                        done: function(value, date){ //监听日期被切换
                            if (value){
                                $("input[name=addDayNum]").attr("disabled", "disabled");
                                $("input[name=addDayNum]").attr("class", "layui-input layui-disabled");
                                $("input[name=addDayNum]").val("");
                                $("input[name=addHourNum]").attr("disabled", "disabled");
                                $("input[name=addHourNum]").attr("class", "layui-input layui-disabled");
                                $("input[name=addHourNum]").val("");
                                $("input[name=addMinuteNum]").attr("disabled", "disabled");
                                $("input[name=addMinuteNum]").attr("class", "layui-input layui-disabled");
                                $("input[name=addMinuteNum]").val("");
                            }else {
                                $("input[name=addDayNum]").attr("disabled", false);
                                $("input[name=addDayNum]").attr("class", "layui-input");
                                $("input[name=addHourNum]").attr("disabled", false);
                                $("input[name=addHourNum]").attr("class", "layui-input");
                                $("input[name=addMinuteNum]").attr("disabled", false);
                                $("input[name=addMinuteNum]").attr("class", "layui-input");
                            }
                        }
                    });
                }else {
                    form.val('cardEditForm', {
                        "organizationType":1,
                        "cardType":1,
                        "cardStatus":1
                    });
                }

                // form.val('cardEditForm', data);
                //单选框事件监听
                form.on('radio(operateFlag)', function (data) {
                    if (data.value==0){
                        $("select[name=operateApp]").attr("disabled", "disabled");
                        $("select[name=cardType]").attr("disabled", "disabled");
                        $("select[name=cardStatus]").attr("disabled", "disabled");
                        form.render('select');
                    }else {
                        $("select[name=operateApp]").attr("disabled", false);
                        $("select[name=cardType]").attr("disabled", false);
                        $("select[name=cardStatus]").attr("disabled", false);
                        form.render('select');
                    }
                });
                //单选框事件监听
                form.on('radio(exportFlag)', function (data) {
                    if (data.value==0){
                        $("select[name=splitSymbol]").attr("disabled", false);
                        form.render('select');
                    }else {
                        $("select[name=splitSymbol]").attr("disabled", "disabled");
                        form.render('select');
                    }
                });
                //表单提交事件
                form.on('submit(cardEditSubmit)', function (data) {
                    // admin.showLoading(undefined, 3, '.8');
                    var loading = layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
                    let idData = table.checkStatus(obj.config.id).data;
                    let ids = "";
                    for (let i = 0; i < idData.length; i++) {
                        ids += idData[i].cardId + ",";
                    }
                    ids = ids.substr(0, ids.length - 1);
                    if (data.field.operateFlag==0 && idData.length ==0){
                        layer.close(loading);
                        layer.msg('未选中数据!', {icon: 2});
                        return false;
                    }
                    data.field.ids = ids;
                    data.field.event = obj.event;
                    if (obj.event=='export'){
                        layer.close(loading);
                        window.location.href=Feng.ctxPath + "/cardInfo/exportCard?data=" + JSON.stringify(data.field);
                    }else {
                        var ajax = new $ax(Feng.ctxPath + "/cardInfo/editItem", function (data) {
                            layer.close(loading);
                            layer.closeAll();
                            layer.msg("更新成功！", {icon: 1});
                            table.reload(CardInfo.tableId);
                            //关掉对话框
                            admin.closeThisDialog();
                        }, function (data) {
                            layer.close(loading);
                            layer.msg("更新失败！" + data.responseJSON.message, {icon: 2});
                        },true);
                        ajax.set(data.field);
                        ajax.start();
                    }
                    return false;
                });
                // 禁止弹窗出现滚动条
                $(layero).children('.layui-layer-content').css('overflow', 'visible');
            }
        });
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.openItemEditDlg = function (obj) {
        var url = Feng.ctxPath + '/cardInfo/itemEdit?type='+$('#type').val();
        admin.open({
            // type: 1,
            title: obj.name,
            area: '600px',
            url: url,
            data: {
                event: obj.event
            },
            tpl: true,
            success: function (layero, dIndex) {
                //封禁备注
                textool.init({
                    // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
                    eleId: 'prohibitRemark',
                    // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
                    maxlength: 128
                });
                //封禁备注
                textool.init({
                    // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
                    eleId: 'cardData',
                    // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
                    maxlength: 255
                });
                if (obj.event==='unsealing'){
                    form.val('cardEditForm', {
                        "organizationType":1,
                        "cardType":1,
                        "cardStatus":5
                    });
                }else if (obj.event==='itemEdit'){
                    table.render({
                        elem: '#device',
                        url: Feng.ctxPath + '/device/list',
                        page: true,
                        defaultToolbar: [],
                        height: "full-398",
                        cellMinWidth: 100,
                        where: {
                            'cardType': 1,
                            'cardOrUserId': obj.data.cardId
                        },
                        cols:[[
                            {field: 'mac', align: 'center', title: 'MAC'},
                            {field: 'ip', align: 'center', title: 'IP'},
                            {field: 'ipAddress', align: 'center', title: 'IP地址'},
                            {field: 'loginNum', align: 'center', title: '登录次数'},
                            {field: 'updateTime', align: 'center', width: 100, sort: true, title: '上次登录时间'},
                            {align: 'center', toolbar: '#tableBar', width: 50, fixed: 'right', title: '操作'}
                        ]],
                    });
                }else if (obj.event==='itemOvertime'){
                    obj.event='overtime';
                    laydate.render({
                        elem: '#addTime',
                        position: 'fixed',
                        type: 'datetime',
                        trigger: 'click',
                        done: function(value, date){ //监听日期被切换
                            if (value){
                                $("input[name=addDayNum]").attr("disabled", "disabled");
                                $("input[name=addDayNum]").attr("class", "layui-input layui-disabled");
                                $("input[name=addDayNum]").val("");
                                $("input[name=addHourNum]").attr("disabled", "disabled");
                                $("input[name=addHourNum]").attr("class", "layui-input layui-disabled");
                                $("input[name=addHourNum]").val("");
                                $("input[name=addMinuteNum]").attr("disabled", "disabled");
                                $("input[name=addMinuteNum]").attr("class", "layui-input layui-disabled");
                                $("input[name=addMinuteNum]").val("");
                            }else {
                                $("input[name=addDayNum]").attr("disabled", false);
                                $("input[name=addDayNum]").attr("class", "layui-input");
                                $("input[name=addHourNum]").attr("disabled", false);
                                $("input[name=addHourNum]").attr("class", "layui-input");
                                $("input[name=addMinuteNum]").attr("disabled", false);
                                $("input[name=addMinuteNum]").attr("class", "layui-input");
                            }
                        }
                    });
                }else if (obj.event==='itemData'){
                    obj.event='data';
                }else {
                    form.val('cardEditForm', {
                        "organizationType":1,
                        "cardType":1,
                        "cardStatus":1
                    });
                }
                //单选框事件监听
                form.on('radio(operateFlag)', function (data) {
                    if (data.value==0){
                        $("select[name=operateApp]").attr("disabled", "disabled");
                        $("select[name=cardType]").attr("disabled", "disabled");
                        $("select[name=cardStatus]").attr("disabled", "disabled");
                        form.render('select');
                    }else {
                        $("select[name=operateApp]").attr("disabled", false);
                        $("select[name=cardType]").attr("disabled", false);
                        $("select[name=cardStatus]").attr("disabled", false);
                        form.render('select');
                    }
                });
                //表单提交事件
                form.on('submit(cardItemEditSubmit)', function (data) {
                    var loading=top.layer.msg('处理中',{icon:16, shade:[0.1,'#000'], time:false});
                    data.field.ids = obj.data.cardId;
                    data.field.event = obj.event;
                    data.field.operateFlag = 0;
                    var ajax = new $ax(Feng.ctxPath + "/cardInfo/editItem", function (data) {
                        top.layer.close(loading);
                        layer.closeAll();
                        layer.msg("操作成功！", {icon: 1});
                        table.reload(CardInfo.tableId);
                        //关掉对话框
                        admin.closeThisDialog();
                    }, function (data) {
                        top.layer.close(loading);
                        layer.msg("操作失败！" + data.responseJSON.message, {icon: 2});
                    },true);
                    ajax.set(data.field);
                    ajax.start();
                    return false;
                });
                // 禁止弹窗出现滚动条
                $(layero).children('.layui-layer-content').css('overflow', 'visible');
            }
        });
    };

    /**
     * 点击删除
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.onDeleteItem = function (data) {
        var operation = function () {
            var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/delete", function (data) {
                layer.close(loading);
                Feng.success("删除成功!");
                table.reload(CardInfo.tableId);
            }, function (data) {
                layer.close(loading);
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            },true);
            ajax.set("cardId", data.cardId);
            ajax.set("cardCode", data.cardCode);
            ajax.set("appId", data.appId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
    };

    /**
     * 点击封禁
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.itemProhibition = function (obj) {
        var operation = function () {
            var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/editItem", function (data) {
                layer.close(loading);
                Feng.success("封禁成功!");
                table.reload(CardInfo.tableId);
            }, function (data) {
                layer.close(loading);
                Feng.error("封禁失败!" + data.responseJSON.message + "!");
            },true);
            ajax.set("ids", obj.data.cardId);
            ajax.set("operateFlag", 0);
            ajax.set("event", "prohibition");
            ajax.start();
        };
        Feng.confirm("是否封禁?", operation);
    };

    /**
     * 点击解绑
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.itemUntying = function (obj) {
        var operation = function () {
            var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/editItem", function (data) {
                layer.close(loading);
                Feng.success("解绑成功!");
                table.reload(CardInfo.tableId);
            }, function (data) {
                layer.close(loading);
                Feng.error("解绑失败!" + data.responseJSON.message + "!");
            },true);
            ajax.set("ids", obj.data.cardId);
            ajax.set("operateFlag", 0);
            ajax.set("event", "untying");
            ajax.start();
        };
        Feng.confirm("是否解绑?", operation);
    };

    /**
     * 点击解封
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.itemUnsealing = function (obj) {
        var operation = function () {
            var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/editItem", function (data) {
                layer.close(loading);
                Feng.success("解封成功!");
                table.reload(CardInfo.tableId);
            }, function (data) {
                layer.close(loading);
                Feng.error("解封失败!" + data.responseJSON.message + "!");
            },true);
            ajax.set("ids", obj.data.cardId);
            ajax.set("operateFlag", 0);
            ajax.set("event", "unsealing");
            ajax.start();
        };
        Feng.confirm("是否解封?", operation);
    };

    /**
     * 批量删除
     *
     * @param obj 选择的行数据
     */
    CardInfo.batchRemove = function (obj) {
        let data = table.checkStatus(obj.config.id).data;
        if (data.length === 0) {
            Feng.error("未选中数据!");
            return false;
        }
        let ids = "";
        for (let i = 0; i < data.length; i++) {
            ids += data[i].cardId + ",";
        }
        ids = ids.substr(0, ids.length - 1);
        var operation = function () {
            var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/batchRemove", function (data) {
                layer.close(loading);
                Feng.success("删除成功!");
                table.reload(CardInfo.tableId);
            }, function (data) {
                layer.close(loading);
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            },true);
            ajax.set("ids", ids);
            ajax.start();
        };
        Feng.confirm("确定要删除这些数据?", operation);
    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + CardInfo.tableId,
        url: Feng.ctxPath + '/cardInfo/list?type='+$('#type').val(),
        // page: true,
        toolbar: '#' + CardInfo.tableId + '-toolbar',
        defaultToolbar: ['filter'],
        height: "full-80",
        page: {limit: 15, limits: [15, 30, 45, 60, 75, 90, 105, 120, 200]},
        cellMinWidth: 100,
        cols: CardInfo.initColumn(),
        // done: function () {this.where={};}
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CardInfo.search();
    });

    // 重置按钮点击事件
    $('#btnReset').click(function () {
        var queryData = {};
        $("#appId").val("");
        $("#cardTypeId").val("");
        $("#cardCode").val("");
        $("#cardStatus").val("");
        $("#createTimeStr").val("");
        $("#activeTimeStr").val("");
        $("#expireTimeStr").val("");
        $("#cardRemark").val("");
        queryData['appId'] = "";
        queryData['cardTypeId'] = "";
        queryData['cardCode'] = "";
        queryData['cardStatus'] = "";
        queryData['createTimeStr'] = "";
        queryData['activeTimeStr'] = "";
        queryData['expireTimeStr'] = "";
        queryData['cardRemark'] = "";
        form.render();
        table.reload(CardInfo.tableId, {
            where: queryData, page: {curr: 1}
        });
    });

    // 添加按钮点击事件
    $('#btnAdd').click(function () {
        CardInfo.openAddDlg();
    });

    // 导出excel
    $('#btnExp').click(function () {
        CardInfo.exportExcel();
    });
    //应用选择下拉框事件监听
    // form.on('select(appId)', function (data) {
    //     $("select[name=cardTypeId]").empty();
    //     form.render('select');
    //     var appId=$("select[name=appId]").val();
    //     var ajax = new $ax(Feng.ctxPath + "/cardInfo/getCardTypeByAppId", function (result) {
    //         var list = result.data;
    //         if (list.length>0){
    //             var html="<option value=''>不限</option>";
    //             for(var key in list){
    //                     html+="<option value='"+list[key].cardTypeId+"'>"+list[key].cardTypeName+"</option>";
    //             }
    //             $("select[name=cardTypeId]").append(html);
    //             form.render('select');
    //         }else {
    //             var operation = function () {
    //                 var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
    //                 var ajax = new $ax(Feng.ctxPath + "/cardInfo/addCardTypeByAppId", function (result) {
    //                     layer.close(loading);
    //                     Feng.success("创建成功!");
    //                     var list = result.data;
    //                     for(var key in list){
    //                         var html="<option value='"+list[key].cardTypeId+"'>"+list[key].cardTypeName+"</option>";
    //                         $("select[name=cardTypeId]").append(html);
    //                     }
    //                     form.render('select');
    //                 }, function (data) {
    //                     layer.close(loading);
    //                     Feng.error("创建失败!" + data.responseJSON.message + "!");
    //                 },true);
    //                 ajax.set('appId',appId);
    //                 ajax.start();
    //             };
    //             Feng.confirm("还未创建卡密类型，是否初始化卡密类型数据?", operation);
    //         }
    //     }, function (data) {
    //         Feng.error("获取卡类信息失败！" + data.responseJSON.message)
    //     },true);
    //     ajax.set('appId',appId);
    //     ajax.start();
    // });
    // 表头工具条点击事件
    table.on('toolbar(' + CardInfo.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            CardInfo.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(CardInfo.tableId);
        } else if (obj.event === 'refresh') {
            table.reload(CardInfo.tableId);
        } else if (obj.event === 'openYyImport') {
            CardInfo.openYyImportDlg(obj)
            //批量封禁
        } else if (obj.event === 'customImport') {
            CardInfo.openImportDlg(obj)
            //批量封禁
        } else if (obj.event === 'export') {
            CardInfo.openEditDlg(obj)
            //批量封禁
        }else if (obj.event === 'prohibition') {
            obj.name = '批量封禁';
            CardInfo.openEditDlg(obj);
            //批量解封
        }else if (obj.event === 'unsealing') {
            obj.name = '批量解封';
            CardInfo.openEditDlg(obj);
            //批量加时
        }else if (obj.event === 'overtime') {
            obj.name = '批量加时';
            CardInfo.openEditDlg(obj);
            //批量解绑
        }else if (obj.event === 'untying') {
            obj.name = '批量解绑';
            CardInfo.openEditDlg(obj);
            //修改备注
        }else if (obj.event === 'editRemark') {
            obj.name = '修改备注';
            CardInfo.openEditDlg(obj);
        }else if (obj.event === 'batchRemove') {
            CardInfo.batchRemove(obj);
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + CardInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'detail') {
            CardInfo.openDetailDlg(data);
        } else if (layEvent === 'edit') {
            CardInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            CardInfo.onDeleteItem(data);
        } else if (layEvent === 'copy') {
            CardInfo.copy(data)
        } else if (layEvent === 'itemEdit') {
            obj.name = '卡密编辑';
            CardInfo.openItemEditDlg(obj)
        } else if (layEvent === 'itemProhibition') {
            obj.name = '卡密封禁';
            CardInfo.itemProhibition(obj)
        }  else if (layEvent === 'itemUnsealing') {
            obj.name = '卡密解封';
            CardInfo.itemUnsealing(obj)
        } else if (layEvent === 'itemOvertime') {
            obj.name = '卡密加时';
            CardInfo.openItemEditDlg(obj)
        } else if (layEvent === 'itemConfig') {
            obj.name = '卡密配置';
            CardInfo.openConfigEditlDlg(data)
        } else if (layEvent === 'itemData') {
            obj.name = '卡密数据';
            CardInfo.openItemEditDlg(obj)
        } else if (layEvent === 'itemUntying') {
            obj.name = '卡密解绑';
            CardInfo.itemUntying(obj)
        }
    });
});
