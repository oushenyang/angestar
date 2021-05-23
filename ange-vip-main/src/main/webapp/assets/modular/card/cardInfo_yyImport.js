layui.use(['form', 'formX','admin', 'ax', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var notice = layui.notice;
    //让当前iframe弹层高度适应
    admin.iframeAuto();
    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/cardInfo/yyImportItem", function (result) {
            Feng.success("添加成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            let cards = "";
            for (let i = 0; i < result.data.length; i++) {
                cards += result.data[i] + ",";
            }
           top.layui.admin.open({
                type: 2,
                title: '结果导出',
                area: '600px',
                content: Feng.ctxPath + '/cardInfo/addResult?cards=' + cards
            });
            return false;
        }, function (result) {
            notice.msg("添加失败!" + result.responseJSON.message + "!", {icon: 2});
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});