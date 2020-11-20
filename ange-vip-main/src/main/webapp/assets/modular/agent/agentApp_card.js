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

layui.use(['table', 'form', 'formX', 'admin', 'ax', 'element'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var element = layui.element;
    var table = layui.table;


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
        }
            // else if(data.index==1){
            //     // 渲染表格
            //      table.render({
            //         elem: '#' + AgentApp.currentCard,
            //         url: Feng.ctxPath + '/agentCard/list',
            //         page: true,
            //         toolbar: '#' + AgentApp.currentCard + '-toolbar',
            //         defaultToolbar: [],
            //         height: "full-115",
            //         cellMinWidth: 100,
            //          where:{
            //              'cardType':1,
            //              'agentAppId':Feng.getUrlParam("agentAppId")
            //          },
            //         cols: AgentApp.initColumn()
            //     });
        // }
        else {
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
        return [[
            // {field: 'cardTypeId', type: 'checkbox'},
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
            {field: 'marketPrice', align: 'center', title: '市场价',templet: '#marketPriceEditTpl'},
            {field: 'agentPrice', align: 'center', title: '代理价',templet: '#agentPriceEditTpl'
                // style: 'outline: 1px solid #e6e6e6;outline-offset: -5px;'
            },
            {field: 'createTime', align: 'center', width: 160, sort: true, title: '创建时间'},
            {align: 'center', toolbar: '#tableBar', width: 120, fixed: 'right', title: '操作'}
        ]];
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
    admin.iframeAuto();

    //监听单元格编辑
    // table.on('edit(card)', function (obj) {
    //     console.log(obj)
    //     var value = obj.value //得到修改后的值
    //         , data = obj.data //得到所在行所有键值
    //         , field = obj.field; //得到字段
    //     if (obj.event =="cellClick"){
    //         $("table input").attr("type","number");
    //         $("table input").attr("step","0.1");
    //     }
    //     layer.msg('[ID: ' + data.cardTypeId + '] ' + field + ' 字段更改为：' + value);
    // });
    //监听行工具条
    // table.on('tool(card)', function (obj) {
    //     console.log(obj.event)
    //     switch (obj.event) {
    //         case 'cellClick':
    //             CellClick(this, obj);
    //             break;
    //     }
    //     ;
    // });
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
        top.layui.admin.open({
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

    // 单码卡密表头工具条点击事件
    table.on('toolbar(' + AgentApp.card + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AgentApp.openAddDlg();
        } else if (obj.event === 'initialize') {
            AgentApp.initializeItemCodeCard(0);
        }
    });
    // 通用卡密表头工具条点击事件
    // table.on('toolbar(' + AgentApp.currentCard + ')', function(obj){
    //     //添加
    //     if(obj.event === 'btnAdd'){
    //         AgentApp.openAddDlg();
    //     } else if(obj.event === 'initialize'){
    //         AgentApp.initializeItem(1);
    //     }
    // });
    // 账号卡密表头工具条点击事件
    table.on('toolbar(' + AgentApp.account + ')', function (obj) {
        //添加
        if (obj.event === 'btnAdd') {
            AgentApp.openAddDlg();
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

            // var flag = false;
            // $(this).on('input', function () {
            //     if (!flag)
            //         console.log($(this).val());
            // }).on('compositionstart', function () {
            //     flag = true;
            //     console.log('输入法，录入开始');
            // }).on('compositionend', function () {
            //     flag = false;
            //     console.log('输入法，输入结束--'+$(this).val());
            // });
        }
    });
    function CellClick(that,obj){
        //当前点击字段
        var field = $(that).data("field");
        console.log(field)
        //判断是否需要添加编辑框
        if(field=="edit")return true;
        //当前行数据
        var data = obj.data;
        console.log(data)
        //当前单元格的值
        var value = data[field];
        console.log(value)
        //当前点击td的宽高
        var height = $(that)[0].offsetHeight,width = $(that)[0].offsetWidth;
        //当前点击td的坐标
        var top = $(that).offset().top,left = $(that).offset().left;

        //输入框 这里可以自定义表单内容
        var input = '<input type="number" class="layui-input" value="'+value+'" id="'+field+'_input" data-field="'+field+'" style="width:'+width+'px;height:'+height+'px">';
        //弹出层
        layer.open({
            type: 1
            ,title:false
            ,page:true
            ,limit:1
            ,closeBtn:0
            ,area: [width+"px", height+"px"]
            ,shade: [0.01, '#fff']
            ,shadeClose:true
            ,content: input //这里content是一个普通的String
            ,offset:[top,left]
            ,success:function(){
                //使弹出层相对定位
                $(".layui-layer-page").css("position","absolute")
                //设置输入框的值
                $("#"+field+"_input").focus();
                $("#"+field+"_input").val(value);
                $("#"+field+"_input").blur(function(){
                    //同步更新缓存对应的值
                    data[field] = $(this).val();
                    obj.update(data);
                })
            }
        });
    }

    // 行内工具条点击事件
    // table.on('tool(' + AgentApp.currentCard + ')', function (obj) {
    //     var data = obj.data;
    //     var layEvent = obj.event;
    //     if (layEvent === 'edit') {
    //         AgentApp.openEditDlg(data,1);
    //     } else if (layEvent === 'delete') {
    //         AgentApp.onDeleteItem(data,1);
    //     }
    // });

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