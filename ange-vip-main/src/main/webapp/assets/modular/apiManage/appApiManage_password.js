layui.use(['form', 'formX','admin', 'ax', 'textool'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var textool = layui.textool;

    textool.init({
        // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
        eleId: 'publicKey',
        // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
        maxlength: 216
    });

    textool.init({
        // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
        eleId: 'privateKey',
        // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
        maxlength: 848
    });

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    // 加密算法单选框事件
    form.on('radio(webAlgorithmType)', function (data) {
        console.log(data.value);
        if (data.value==5){
            $('.encryptionMode').hide();
            $('.webKey').hide();
            $('.webSalt').hide();
            $('.publicKey').show();
            $('.privateKey').show();
            $('.webAlgorithmOutput').attr("style","margin-top: 20px;");
            form.render();
        }else {
            $('.encryptionMode').show();
            $('.webKey').show();
            $('.webSalt').show();
            $('.publicKey').hide();
            $('.privateKey').hide();
            $('.webAlgorithmOutput').attr("style","margin-top: 0;");
            form.render();
        }
    });

    // 加密模式事件
    form.on('select(encryptionMode)', function (data) {
        console.log(data.value);
        if (data.value==0){
            $('.webSalt').hide();
            form.render();
        }else {
            $('.webSalt').show();
            form.render();
        }
    });


    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/apiManage/detail?apiManageId=" + Feng.getUrlParam("apiManageId"));
    var result = ajax.start();
    form.val('apiManageForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
        var ajax = new $ax(Feng.ctxPath + "/apiManage/editItem", function (data) {
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
        return false;
    });
});