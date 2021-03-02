layui.use(['table', 'form', 'formX', 'admin', 'ax', 'element', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var notice = layui.notice;

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/agentCard/detail?agentCardId=" + Feng.getUrlParam("agentCardId"));
    var result = ajax.start();
    form.val('agentAppForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/agentCard/editItem", function (data) {
            notice.msg('更新成功!', {icon: 1});
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();

        }, function (data) {
            notice.msg("更新失败！"+ data.responseJSON.message,{icon:2})
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
    form.verify({
        digital: function (agentPrice) {
            var marketPrice = $('#agentAppForm input[name=marketPrice]').val();
            if (marketPrice){
                if (Number(marketPrice)<Number(agentPrice)){
                    return '代理价格不能大于市场价格';
                }
            }
        }
    });
});