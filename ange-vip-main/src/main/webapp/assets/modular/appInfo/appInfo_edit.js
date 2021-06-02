layui.use(['form', 'admin', 'ax','dict', 'notice', 'formX', 'textool','element'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var dict=layui.dict; //获取自定义模块
    var notice = layui.notice;
    var element = layui.element;
    var formX = layui.formX;
    var textool = layui.textool;
    textool.init({
        // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
        eleId: null,
        // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
        maxlength: 512
    });
    dict.renderDictAll(); //渲染
    //重新渲染select数据
    form.render('select');

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/appInfo/detail?appId=" + Feng.getUrlParam("appId"));
    var result = ajax.start();
    form.val('appInfoForm', result.data);

    $('.btnSubmit').click(function () {
        //表单提交事件
        form.on('submit(btnSubmit)', function (data) {
            var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
            // notice.destroy();
            var ajax = new $ax(Feng.ctxPath + "/appInfo/editItem", function (data) {
                layer.close(loading);
                Feng.success("更新成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                layer.close(loading);
                Feng.error("更新失败！" + data.responseJSON.message)
            },true);
            ajax.set(data.field);
            ajax.start();
            //添加 return false 可成功跳转页面
            return false;
        });
    });
});