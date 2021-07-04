layui.use(['form', 'formX', 'admin', 'ax', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var notice = layui.notice;
    var laydate = layui.laydate;
    var textool = layui.textool;
    //表单初始赋值
    layui.form.val('cardInfoForm', {
        "addNum": 1
    });

    //应用选择下拉框事件监听
    form.on('select(appId)', function (data) {
        $("select[name=cardTypeId]").empty();
        form.render('select');
        var appId = $("select[name=appId]").val();
        var agentAppId = $("select[name=appId] option:selected").attr("data-agentAppId");
        var ajax = new $ax(Feng.ctxPath + "/actCard/findCardTypeByAppIdAndAgentAppId", function (result) {
            var list = result.data;
            if (list.length > 0) {
                var html="<option value=''>请选择卡密类型</option>";
                for (var key in list) {
                    html += "<option value='" + list[key].cardTypeId + "'>" + list[key].cardTypeName + "</option>";
                }
                $("select[name=cardTypeId]").append(html);
                form.render('select');
            }
        }, function (data) {
            Feng.error("获取卡类信息失败！" + data.responseJSON.message)
        }, true);
        ajax.set('appId', appId);
        ajax.set('agentAppId', agentAppId);
        ajax.start();
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.hasOwnProperty('isActivation') ? '' : data.field.isActivation = 'off';
        data.field.developerUserId = $("select[name=appId] option:selected").attr("data-developerUserId");
        data.field.appId = $("select[name=appId]").val();
        data.field.agentAppId = $("select[name=appId] option:selected").attr("data-agentAppId");
        //是否自定义时间
        data.field.isCustomTime = false;
        var ajax = new $ax(Feng.ctxPath + "/cardInfo/actAddItem", function (data) {
            notice.msg("新增卡密成功！", {icon: 1});
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            let cards = "";
            for (let i = 0; i < data.data.length; i++) {
                cards += data.data[i] + ",";
            }
            top.layui.admin.open({
                // type: 1,
                title: '结果导出',
                area: '600px',
                url: Feng.ctxPath + '/cardInfo/addResult?cards=' + cards,
                tpl: true,
                success: function (layero, dIndex) {
                    // 禁止弹窗出现滚动条
                    // $(layero).children('.layui-layer-content').css('overflow', 'visible');
                }
            });
            return false;
        }, function (data) {
            notice.msg("新增卡密失败！" + data.responseJSON.message, {icon: 2});
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});