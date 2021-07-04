layui.use(['form', 'formX', 'admin', 'ax', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var notice = layui.notice;
    var CardInfo = {};
    var cards = "";
    //表单初始赋值
    layui.form.val('cardInfoForm', {
        "addNum": 1
    });

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

    form.on('submit(exportSubmit)', function (data) {
        if (!cards){
            notice.msg("请先生成卡密!", {icon: 2});
            return false;
        }
        window.location.href=Feng.ctxPath + "/quick/cardAdd/addExport?cards=" + cards;
        return false;
    });

    //表单提交事件
    form.on('submit(cardBtnSubmit)', function (data) {
        var loading = layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
        data.field.hasOwnProperty('isActivation') ? '' : data.field.isActivation = 'off';
        //是否自定义时间
        data.field.isCustomTime = false;
        var ajax = new $ax(Feng.ctxPath + "/quick/agentAddItem", function (result) {
            layer.close(loading);
            Feng.success("添加成功！");
            //传给上个页面，刷新table用
            // admin.putTempData('formOk', true);
            //关掉对话框
            // admin.closeThisDialog();
            // $('#cardAddResultBar').show();
            cards = "";
            $('#cardAddResultBarItem').html("");
            for (let i = 0; i < result.data.length; i++) {
                cards += result.data[i] + ",";
                $('#cardAddResultBarItem').append('<div class="layui-form-item" style="margin-top: 5px!important;"><span style="color: red;margin-right: 5px;">'+(Number(i)+1)+'、</span>'+result.data[i]+' &nbsp;<button type="button" style="float: right;" value="'+result.data[i]+'" onclick="'+CardInfo.copy(result.data[i])+'" class="layui-badge layui-badge-blue cardStr cardStr'+result.data[i]+'">复制</button></div>')
            }
            return false;
        }, function (data) {
            layer.close(loading);
            notice.msg("新增卡密失败！" + data.responseJSON.message, {icon: 2});
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});