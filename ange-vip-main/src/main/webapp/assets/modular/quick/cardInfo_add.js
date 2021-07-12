/**
 * 详情对话框
 */
var CardInfoInfoDlg = {
    data: {
        appId: "",
        cardTypeId: "",
        userId: "",
        userName: "",
        isUniversal: "",
        cardCode: "",
        isCustomTime: "",
        customTimeNum: "",
        cardStutas: "",
        cardMac: "",
        cardIp: "",
        cardToken: "",
        activeTime: "",
        expireTime: "",
        cardBindType: "",
        cardOpenRange: "",
        cardOpenNum: "",
        cardRemark: "",
        prohibitRemark: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['form', 'formX','admin', 'ax', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var notice = layui.notice;
    var CardInfo = {};
    var cards = "";

    CardInfo.copy = function (data) {
        var cla = '.cardStr' + data;
        var clipboard = new ClipboardJS(cla, {
            text: function () {
                return data;
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
    //表单初始赋值
    form.val('cardInfoForm', {
        "addNum":1
    });
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

    form.on('submit(exportSubmit)', function (data) {
        if (!cards){
            notice.msg("请先生成卡密!", {icon: 2});
            return false;
        }
        window.location.href=Feng.ctxPath + "/quick/cardAdd/addExport?cards=" + cards;
        return false;
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var loading = layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
        data.field.hasOwnProperty('isCustomTime')?'': data.field.isCustomTime = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('isActivation')?'': data.field.isActivation = 'off'; //true 值为on,false 值给赋off
        var cardTypeId = $("[name='cardTypeId']").val();
        data.field.cardTypeName = $("[name='cardTypeId']").children("[value=" + cardTypeId + "]").text();
        var ajax = new $ax(Feng.ctxPath + "/quick/cardAdd/addItem", function (result) {
            layer.close(loading);
            Feng.success("添加成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            $('#cardAddResultBar').show();
            cards = "";
            $('#cardAddResultBarItem').html("");
            for (let i = 0; i < result.data.length; i++) {
                cards += result.data[i] + ",";
                $('#cardAddResultBarItem').append('<div class="layui-form-item" style="margin-top: 5px!important;"><span style="color: red;margin-right: 5px;">'+(Number(i)+1)+'、</span>'+result.data[i]+' &nbsp;<button type="button" style="float: right;" value="'+result.data[i]+'" onclick="'+CardInfo.copy(result.data[i])+'" class="layui-badge layui-badge-blue cardStr cardStr'+result.data[i]+'">复制</button></div>')
            }
           // top.layui.admin.open({
           //      type: 2,
           //      title: '结果导出',
           //      area: '600px',
           //      content: Feng.ctxPath + '/quick/cardAdd/addResult?cards=' + cards
           //  });
            return false;
        }, function (result) {
            if (result.responseJSON.code==420){
                layer.close(loading);
                notice.msg("添加失败！" + result.responseJSON.message + "，请维护该卡密价格信息！", {icon: 2});
                layui.admin.open({
                    type: 2,
                    title: data.field.cardTypeName,
                    area: ['500px','252px'],
                    content: Feng.ctxPath + '/cardInfo/addPriceEdit?cardTypeId=' + cardTypeId
                });
                return false;
            }else {
                layer.close(loading);
                notice.msg("添加失败!" + result.responseJSON.message + "!", {icon: 2});
            }
        },true);
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});