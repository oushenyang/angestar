layui.use(['table', 'form','dropdown', 'admin', 'ax', 'xmSelect','laydate'], function () {
    var $ = layui.$;
    var table = layui.table;
    var $ax = layui.ax;
    var admin = layui.admin;
    var dropdown = layui.dropdown;
    var form = layui.form;
    var xmSelect = layui.xmSelect;
    var laydate = layui.laydate;
    // var clipboard = layui.clipboard;
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
            {align: 'center', field: 'cardId', fixed: 'left', type: 'checkbox'},
            {
                align: 'center', field: 'appName', fixed: 'left', width: 100, title: '所属应用', templet: function (d) {
                    if (!d.appName) {
                        return '通用卡密';
                    } else {
                        return d.appName;
                    }
                }
            },
            {align: 'center', field: 'cardCode', width: 300, title: '卡密', templet: '#cardCodeTpl'},
            {
                align: 'center', field: 'cardTypeName', sort: true, title: '卡类', templet: function (d) {
                    if (!d.cardTypeName) {
                        return '自定义';
                    } else {
                        return d.cardTypeName;
                    }
                }
            },
            // {align: 'center',field: 'userId', sort: true, title: '申请人ID'},
            {align: 'center', field: 'userName', title: '申请人名称'},
            // {align: 'center',field: 'isUniversal', sort: true, title: '是否通用 0-否；1-是'},
            // {align: 'center',field: 'isCustomTime', sort: true, title: '是否自定义时间'},
            // {align: 'center',field: 'customTimeNum', sort: true, title: '自定义时间值(天)'},
            {align: 'center', field: 'cardStatus', sort: true, title: '状态', templet: '#cardStatusTpl'},
            // {align: 'center',field: 'cardMac', sort: true, title: '绑定mac'},
            // {align: 'center',field: 'cardIp', sort: true, title: '绑定ip'},
            // {align: 'center',field: 'cardToken', sort: true, title: '卡密token'},
            {align: 'center', field: 'activeTime', sort: true, title: '激活时间'},
            {align: 'center', field: 'expireTime', sort: true, title: '过期时间'},
            // {align: 'center',field: 'cardBindType', sort: true, title: '绑机配置 0-默认；1-关闭；2-MAC；3-IP；4-混合；'},
            // {align: 'center',field: 'cardOpenRange', sort: true, title: '多开开关 0-默认；1-关闭；2-开启'},
            // {align: 'center',field: 'cardOpenNum', sort: true, title: '多开数量'},
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
        table.reload(CardInfo.tableId, {where: queryData});
    };

    /**
     * 弹出添加对话框
     */
    CardInfo.openAddDlg = function () {
        admin.putTempData('formOk', false);
        top.layui.admin.open({
            type: 2,
            title: '添加卡密表',
            area: '700px',
            content: Feng.ctxPath + '/cardInfo/add',
            end: function () {
                admin.getTempData('formOk') && table.reload(CardInfo.tableId);
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
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    CardInfo.openEditDlg = function (data) {
        var url = Feng.ctxPath + '/cardInfo/edit';
        // admin.putTempData('formOk', false);
        admin.open({
            // type: 1,
            title: '修改卡密表',
            area: '600px',
            url: url,
            data: {
                name: '妲己',
                sex: '女'
            },
            tpl: true,
            success: function (layero, dIndex) {
                form.val('cardInfoForm', data);
                //表单提交事件
                form.on('submit(cardInfoForm)', function (data) {
                    data.field.roleIds = insRoleSel.getValue('valueStr');
                    var loadIndex = layer.load(2);
                    $.get(mData ? '../../json/ok.json' : '../../json/ok.json', data.field, function (res) {  // 实际项目这里url可以是mData?'user/update':'user/add'
                        layer.close(loadIndex);
                        if (res.code === 200) {
                            layer.close(dIndex);
                            layer.msg(res.msg, {icon: 1});
                            insTb.reload({page: {curr: 1}});
                        } else {
                            layer.msg(res.msg, {icon: 2});
                        }
                    }, 'json');
                    return false;
                });
                // 渲染多选下拉框
                // var insRoleSel = xmSelect.render({
                //     el: '#appIdSelect',
                //     name: 'appIdSelect',
                //     layVerify: 'required',
                //     layVerType: 'tips',
                //     data: [{
                //         name: '管理员',
                //         value: 1
                //     }, {
                //         name: '普通用户',
                //         value: 2
                //     }, {
                //         name: '游客',
                //         value: 3
                //     }]
                // });
                // 回显选中角色
                // if (mData && mData.roles) {
                //     insRoleSel.setValue(mData.roles.map(function (item) {
                //         return item.roleId;
                //     }));
                // }
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
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/delete", function (data) {
                Feng.success("删除成功!");
                table.reload(CardInfo.tableId);
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("cardId", data.cardId);
            ajax.start();
        };
        Feng.confirm("是否删除?", operation);
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
            var ajax = new $ax(Feng.ctxPath + "/cardInfo/batchRemove", function (data) {
                Feng.success("删除成功!");
                table.reload(CardInfo.tableId);
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
        elem: '#' + CardInfo.tableId,
        url: Feng.ctxPath + '/cardInfo/list',
        page: true,
        toolbar: '#' + CardInfo.tableId + '-toolbar',
        defaultToolbar: ['filter', 'print'],
        height: "full-158",
        cellMinWidth: 100,
        cols: CardInfo.initColumn()
    });

    // 搜索按钮点击事件
    $('#btnSearch').click(function () {
        CardInfo.search();
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
    form.on('select(appId)', function (data) {
        $("select[name=cardTypeId]").empty();
        form.render('select');
        var appId=$("select[name=appId]").val();
        var ajax = new $ax(Feng.ctxPath + "/cardInfo/getCardTypeByAppId", function (result) {
            var list = result.data;
            if (list.length>0){
                var html="<option value=''>不限</option>";
                for(var key in list){
                        html+="<option value='"+list[key].cardTypeId+"'>"+list[key].cardTypeName+"</option>";
                }
                $("select[name=cardTypeId]").append(html);
                form.render('select');
            }else {
                var operation = function () {
                    var ajax = new $ax(Feng.ctxPath + "/cardInfo/addCardTypeByAppId", function (result) {
                        Feng.success("创建成功!");
                        var list = result.data;
                        for(var key in list){
                            var html="<option value='"+list[key].cardTypeId+"'>"+list[key].cardTypeName+"</option>";
                            $("select[name=cardTypeId]").append(html);
                        }
                        form.render('select');
                    }, function (data) {
                        Feng.error("创建失败!" + data.responseJSON.message + "!");
                    });
                    ajax.set('appId',appId);
                    ajax.start();
                };
                Feng.confirm("还未创建卡密类型，是否初始化卡密类型数据?", operation);
            }
        }, function (data) {
            Feng.error("获取卡类信息失败！" + data.responseJSON.message)
        });
        ajax.set('appId',appId);
        ajax.start();
    });
    // 表头工具条点击事件
    table.on('toolbar(' + CardInfo.tableId + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            CardInfo.openAddDlg();
        } else if (obj.event === 'refresh') {
            table.reload(CardInfo.tableId);
        } else if (obj.event === 'batchRemove') {
            CardInfo.batchRemove(obj)
        }
    });
    // 行内工具条点击事件
    table.on('tool(' + CardInfo.tableId + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;

        if (layEvent === 'edit') {
            CardInfo.openEditDlg(data);
        } else if (layEvent === 'delete') {
            CardInfo.onDeleteItem(data);
        } else if (layEvent === 'copy') {
            console.log(obj)
            CardInfo.copy(data)
        }
    });
});
