layui.use(['form', 'formX','admin', 'ax', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var notice = layui.notice;

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var loading = layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
        var ajax = new $ax(Feng.ctxPath + "/quick/cardUnBind/unBindItem", function (result) {
            layer.close(loading);
            Feng.success("解绑成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            return false;
        }, function (result) {
            layer.close(loading);
            notice.msg(result.responseJSON.message + "!", {icon: 2});
        },true);
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});