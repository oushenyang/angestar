/**
 * 详情对话框
 */
var SerialInfoInfoDlg = {
    data: {
        appId: "",
        cardTypeId: "",
        userId: "",
        userName: "",
        isUniversal: "",
        serialCode: "",
        isCustomTime: "",
        customTimeNum: "",
        cardStatus: "",
        changeBindNum: "",
        activeTime: "",
        expireTime: "",
        remark: "",
        revision: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['form', 'formX','admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //应用选择下拉框事件监听
    // form.on('select(appId)', function (data) {
    //     $("select[name=cardTypeId]").empty();
    //     form.render('select');
    //     var appId=$("select[name=appId]").val();
    //     var ajax = new $ax(Feng.ctxPath + "/cardInfo/getCardTypeByAppId", function (result) {
    //         var list = result.data;
    //         if (list.length>0){
    //             for(var key in list){
    //                 var html="<option value='"+list[key].cardTypeId+"'>"+list[key].cardTypeName+"</option>";
    //                 $("select[name=cardTypeId]").append(html);
    //             }
    //             form.render('select');
    //         }else {
    //             var operation = function () {
    //                 var ajax = new $ax(Feng.ctxPath + "/cardInfo/addCardTypeByAppId", function (result) {
    //                     Feng.success("创建成功!");
    //                     var list = result.data;
    //                     for(var key in list){
    //                         var html="<option value='"+list[key].cardTypeId+"'>"+list[key].cardTypeName+"</option>";
    //                         $("select[name=cardTypeId]").append(html);
    //                     }
    //                     form.render('select');
    //                 }, function (data) {
    //                     Feng.error("创建失败!" + data.responseJSON.message + "!");
    //                 });
    //                 ajax.set('appId',appId);
    //                 ajax.start();
    //             };
    //             Feng.confirm("还未创建卡密类型，是否初始化卡密类型数据?", operation);
    //         }
    //     }, function (data) {
    //         Feng.error("获取卡类信息失败！" + data.responseJSON.message)
    //     });
    //     ajax.set('appId',appId);
    //     ajax.start();
    // });

    // 是否自定义时间
    form.on('switch(isCustomTime)', function (obj) {
        var checked = obj.elem.checked ? true : false;
        if(checked){
            $("#customTimeNumDisplay").show();
            $("#customTimeNum").attr("lay-verify","required|numberX|h5");
            $("#customTimeNum").attr("lay-verType","tips");
            // form.render('customTimeNum');
        }else {
            $("#customTimeNumDisplay").hide();
            $("#customTimeNum").removeAttr("lay-verify");
            $("#customTimeNum").removeAttr("lay-verType");
            // form.render('customTimeNum');
        }
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.hasOwnProperty('isCustomTime')?'': data.field.isCustomTime = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('isActivation')?'': data.field.isActivation = 'off'; //true 值为on,false 值给赋off
        var ajax = new $ax(Feng.ctxPath + "/serialInfo/addItem", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();

            let cards = "";
            for (let i = 0; i < data.data.length; i++) {
                cards += data.data[i] + ",";
            }
            top.layui.admin.open({
                type: 2,
                title: '结果导出',
                area: '600px',
                content: Feng.ctxPath + '/cardInfo/addResult?cards=' + cards
            });
            return false;
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});