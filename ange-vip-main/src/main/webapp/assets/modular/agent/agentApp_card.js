/**
 * 详情对话框
 */
var AgentAppInfoDlg = {
    data: {
        appId: "",
        developerUserId: "",
        agentUserId: "",
        agentUserName: "",
        agentUserAccount: "",
        agentGrade: "",
        pid: "",
        pids: "",
        balance: "",
        status: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['table', 'form', 'formX', 'admin', 'ax', 'element', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var element = layui.element;
    var table = layui.table;
    var notice = layui.notice;


    /**
     * 代理软件表管理
     */
    var AgentApp = {
        card: "card",
        currentCard: "current_card",
        account: "account"
    };

    //一些事件监听
    element.on('tab(cardTabBrief)', function (data) {
        if (data.index == 0) {
            // 渲染表格
            table.render({
                elem: '#' + AgentApp.card,
                url: Feng.ctxPath + '/agentCard/list',
                page: true,
                toolbar: '#' + AgentApp.card + '-toolbar',
                defaultToolbar: [],
                height: "full-126",
                cellMinWidth: 100,
                where: {
                    'cardType': 0,
                    'agentAppId': Feng.getUrlParam("agentAppId")
                },
                cols: AgentApp.initColumn()
            });
        }else {
            // 渲染表格
            table.render({
                elem: '#' + AgentApp.account,
                url: Feng.ctxPath + '/agentCard/list',
                page: true,
                toolbar: '#' + AgentApp.account + '-toolbar',
                defaultToolbar: [],
                height: "full-126",
                cellMinWidth: 100,
                where: {
                    'cardType': 2,
                    'agentAppId': Feng.getUrlParam("agentAppId")
                },
                cols: AgentApp.initColumn()
            });
        }
    });
    /**
     * 初始化表格的列
     */
    AgentApp.initColumn = function () {
        if (Number(Feng.getUrlParam("type"))===2){
            return [[
                {
                    field: 'appName', align: 'center', title: '所属应用', templet: function (d) {
                        if (!d.appName) {
                            return '通用卡类';
                        } else {
                            return d.appName;
                        }
                    }
                },
                {field: 'cardTypeName', align: 'center', title: '卡类名称'},
                {field: 'marketPrice', align: 'center', title: '我的代理价', templet: function (d) {
                        return '<span style="color: orange">'+'￥'+d.marketPrice+'</span>';
                    }},
                {field: 'agentPrice', align: 'center', title: '下级代理价', templet: function (d) {
                        return '<span style="color: orange">'+'￥'+d.agentPrice+'</span>';
                    }
                },
                {field: 'createTime', align: 'center', width: 160, sort: true, title: '创建时间'},
                {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
            ]];
        }else {
            return [[
                {
                    field: 'appName', align: 'center', title: '所属应用', templet: function (d) {
                        if (!d.appName) {
                            return '通用卡类';
                        } else {
                            return d.appName;
                        }
                    }
                },
                {field: 'cardTypeName', align: 'center', title: '卡类名称'},
                {field: 'marketPrice', align: 'center', title: '市场价', templet: function (d) {
                        return '<span style="color: orange">'+'￥'+d.marketPrice+'</span>';
                    }},
                {field: 'agentPrice', align: 'center', title: '代理价', templet: function (d) {
                        return '<span style="color: orange">'+'￥'+d.agentPrice+'</span>';
                    }
                },
                {field: 'createTime', align: 'center', width: 160, sort: true, title: '创建时间'},
                {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
            ]];
        }

    };

    // 渲染表格
    var tableResult = table.render({
        elem: '#' + AgentApp.card,
        url: Feng.ctxPath + '/agentCard/list',
        page: true,
        toolbar: '#' + AgentApp.card + '-toolbar',
        defaultToolbar: [],
        height: "full-126",
        cellMinWidth: 100,
        where: {
            'cardType': 0,
            'agentAppId': Feng.getUrlParam("agentAppId")
        },
        cols: AgentApp.initColumn(),
    });
    //让当前iframe弹层高度适应
    // admin.iframeAuto();

    /**
     * 初始化单码卡类
     */
    AgentApp.initializeItemCodeCard = function (cardType) {
        console.log(Feng.getUrlParam("appId"));
        var initializeItem = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentCard/initializeItemCodeCard", function (data) {
                Feng.success("初始化成功!");
                if (cardType == 0) {
                    table.reload(AgentApp.card);
                }
                    // else if (cardType==1){
                    //     table.reload(AgentApp.currentCard);
                // }
                else {
                    table.reload(AgentApp.account);
                }

            }, function (data) {
                Feng.error("初始化失败!" + data.responseJSON.message + "!");
            });
            ajax.set("cardType", cardType);
            ajax.set("agentAppId", Feng.getUrlParam("agentAppId"));
            ajax.set("appId", Feng.getUrlParam("appId"));
            ajax.start();
        };
        Feng.confirm("是否初始化?", initializeItem);
    };

    /**
     * 初始化注册码卡类
     */
    AgentApp.initializeItemAccountCard = function (cardType) {
        console.log(Feng.getUrlParam("appId"));
        var initializeItem = function () {
            var ajax = new $ax(Feng.ctxPath + "/agentCard/initializeItemAccountCard", function (data) {
                Feng.success("初始化成功!");
                if (cardType == 0) {
                    table.reload(AgentApp.card);
                }
                    // else if (cardType==1){
                    //     table.reload(AgentApp.currentCard);
                // }
                else {
                    table.reload(AgentApp.account);
                }

            }, function (data) {
                Feng.error("初始化失败!" + data.responseJSON.message + "!");
            });
            ajax.set("cardType", cardType);
            ajax.set("agentAppId", Feng.getUrlParam("agentAppId"));
            ajax.set("appId", Feng.getUrlParam("appId"));
            ajax.start();
        };
        Feng.confirm("是否初始化?", initializeItem);
    };

    /**
     * 点击编辑
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.openEditDlg = function (data, cardType) {
        admin.putTempData('formOk', false);
        admin.open({
            type: 2,
            title: '编辑价格',
            area: '500px',
            content: Feng.ctxPath + '/agentCard/edit?agentCardId=' + data.agentCardId + '&cardType=' + cardType,
            end: function () {
                if (cardType == 0) {
                    admin.getTempData('formOk') && table.reload(AgentApp.card);
                } else if (cardType == 1) {
                    admin.getTempData('formOk') && table.reload(AgentApp.currentCard);
                } else {
                    admin.getTempData('formOk') && table.reload(AgentApp.account);
                }
            }
        });
    };

    /**
     * 点击添加授权卡密
     *
     * @param data 点击按钮时候的行数据
     */
    AgentApp.openAddDlg = function (cardType) {
        admin.open({
            type: 1,
            title: '添加授权卡密',
            area: '500px',
            url: Feng.ctxPath + '/agentCard/add?agentAppId=' + Feng.getUrlParam("agentAppId") + '&cardType=' + cardType+ '&appId=' + Feng.getUrlParam("appId")+ '&type=' + Feng.getUrlParam("type"),
            success: function (layero, dIndex) {
                form.render('select');
                form.verify({
                    digital: function (agentPrice) {
                        var marketPrice = $('#agentAppCardForm input[name=marketPrice]').val();
                        if (marketPrice){
                            if (Number(marketPrice)<Number(agentPrice)){
                                return '代理价格不能大于市场价格';
                            }
                        }
                    },
                    subordinateDigital: function (agentPrice) {
                        var marketPrice = $('#agentAppCardForm input[name=marketPrice]').val();
                        if (marketPrice){
                            if (Number(marketPrice)>Number(agentPrice)){
                                return '下级代理价格不能小于我的代理价格';
                            }
                        }
                    }
                });
                if (Number(Feng.getUrlParam("type"))===2){
                    form.on('select(cardTypeId)', function (data) {
                        var agentPrice = $("select[name=cardTypeId] option:selected").attr("data-agentPrice");
                        $("input[name=marketPrice]").val(agentPrice);
                    });
                }
                //表单提交事件
                form.on('submit(agentAppCardSubmit)', function (data) {
                    data.field.cardTypeName = $('select[name="cardTypeId"] option:selected').text();
                    var loadIndex = layer.load(2);
                    var ajax = new $ax(Feng.ctxPath + "/agentCard/addItem", function (data) {
                        layer.close(loadIndex);
                        notice.msg('添加成功!', {icon: 1});
                        if (cardType === 0) {
                            table.reload(AgentApp.card);
                        } else if (cardType === 1) {
                            table.reload(AgentApp.currentCard);
                        } else {
                            table.reload(AgentApp.account);
                        }
                        layer.close(dIndex);
                    }, function (data) {
                        layer.close(loadIndex);
                        notice.msg('添加失败！'+ data.responseJSON.message, {icon: 2});
                    });
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
    AgentApp.onDeleteItem = function (data,cardType) {
        var operation = function (dIndex) {
            var ajax = new $ax(Feng.ctxPath + "/agentCard/delete", function (data) {
                notice.msg("删除成功!", {icon: 1});
                parent.layer.closeAll('dialog');
                if (cardType === 0) {
                    table.reload(AgentApp.card);
                } else if (cardType === 1) {
                    table.reload(AgentApp.currentCard);
                } else {
                    table.reload(AgentApp.account);
                }
            }, function (data) {
                notice.msg("删除失败!" + data.responseJSON.message + "!", {icon: 2});
                parent.layer.closeAll('dialog');
            });
            ajax.set("agentCardId", data.agentCardId);
            ajax.start();
        };
        var confirm = Feng.confirm("是否删除授权卡类?", operation);
    };

    // 单码卡密表头工具条点击事件
    table.on('toolbar(' + AgentApp.card + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AgentApp.openAddDlg(0);
        } else if (obj.event === 'initialize') {
            AgentApp.initializeItemCodeCard(0);
        }
    });

    // 账号卡密表头工具条点击事件
    table.on('toolbar(' + AgentApp.account + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AgentApp.openAddDlg(2);
        } else if (obj.event === 'initialize') {
            AgentApp.initializeItemAccountCard(2);
        }
    });

    // 行内工具条点击事件
    table.on('tool(' + AgentApp.card + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        console.log(layEvent);
        if (layEvent === 'edit') {
            AgentApp.openEditDlg(data, 0);
        } else if (layEvent === 'delete') {
            AgentApp.onDeleteItem(data, 0);
        }
        else if (layEvent === 'cellClick1') {
            $(this).on('input porpertychange',function(e){
                var val= $(this).val();
                var name= $(this).attr('name');
                data[name]=val
                // obj.update(data);//更新行对象数据
                console.log(obj);
            })
        }
    });

    // 行内工具条点击事件
    table.on('tool(' + AgentApp.account + ')', function (obj) {
        var data = obj.data;
        var layEvent = obj.event;
        if (layEvent === 'edit') {
            AgentApp.openEditDlg(data, 2);
        } else if (layEvent === 'delete') {
            AgentApp.onDeleteItem(data, 2);
        }
    });

    // //表单提交事件
    // form.on('submit(btnSubmit)', function (data) {
    //     var ajax = new $ax(Feng.ctxPath + "/agentPower/editItem", function (data) {
    //         Feng.success("更新成功！");
    //
    //         //传给上个页面，刷新table用
    //         admin.putTempData('formOk', true);
    //
    //         //关掉对话框
    //         admin.closeThisDialog();
    //     }, function (data) {
    //         Feng.error("更新失败！" + data.responseJSON.message)
    //     });
    //     ajax.set(data.field);
    //     ajax.start();
    //     return false;
    // });
});