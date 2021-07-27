layui.use(['form', 'formX','admin', 'ax', 'textool', 'formX'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var formX = layui.formX;
    var admin = layui.admin;
    var textool = layui.textool;
    var pass = {};

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
        // console.log(data.value);
        if (data.value==5){
            $('.encryptionMode').hide();
            $('.webKey').hide();
            $('.webSalt').hide();
            $("#webKey").removeAttr("lay-verify");
            $("#webSalt").removeAttr("lay-verify");
            $('.publicKey').show();
            $('.privateKey').show();
            $("#publicKey").attr("lay-verify","required");
            $("#privateKey").attr("lay-verify","required");
            $('.webAlgorithmOutput').attr("style","margin-top: 20px;");
            form.render();
        }else {
            $('.encryptionMode').show();
            $('.webKey').show();
            $('.webSalt').show();
            $("#webKey").attr("lay-verify","required");
            $("#webSalt").attr("lay-verify","required");
            $('.publicKey').hide();
            $('.privateKey').hide();
            $("#publicKey").removeAttr("lay-verify");
            $("#privateKey").removeAttr("lay-verify");
            $('.webAlgorithmOutput').attr("style","margin-top: 0;");
            form.render();
        }
        pass.refresh(data.value,$('#encryptionMode').val());
    });

    // 加密模式事件
    form.on('select(encryptionMode)', function (data) {
        console.log(data.value);
        pass.refresh($("input[name='webAlgorithmType']:checked").val(),data.value);
        // if (data.value==0){
        //     $('.webSalt').hide();
        //     $("#webSalt").removeAttr("lay-verify");
        //     form.render();
        // }else {
        //     $('.webSalt').show();
        //     $("#webSalt").attr("lay-verify","required");
        //     var webAlgorithmType = $('#webAlgorithmType').val();
        //     //0-明文；1-DES；2-AES；3-DESede；4-SM4；
        //     if (Number(webAlgorithmType)===0){
        //
        //     }
        //     form.render();
        // }
    });

    pass.refresh = function (webAlgorithmType,encryptionMode){
        console.log(webAlgorithmType)
        console.log(encryptionMode)
        //0-明文；1-DES；2-AES；3-DESede；4-SM4；
        if (Number(webAlgorithmType) === 0){
            $("#webKey").removeAttr("lay-verify");
            $("#webSalt").removeAttr("lay-verify");
            form.render();
        }else if (Number(webAlgorithmType) === 1){
            if (Number(encryptionMode)===0){
                $('.webSalt').hide();
                $("#webSalt").removeAttr("lay-verify");
                form.render();
            }else {
                $('.webSalt').show();
                $("#webKey").attr("lay-verify","required|noChina|h5");
                $("#webKey").attr("minlength","16");
                $("#webKey").attr("maxlength","16");

                $("#webSalt").attr("lay-verify","required|noChina|h5");
                $("#webSalt").attr("minlength","8");
                $("#webSalt").attr("maxlength","8");
                form.render();
            }
        }else if (Number(webAlgorithmType) === 2||Number(webAlgorithmType) === 4){
            if (Number(encryptionMode)===0){
                $('.webSalt').hide();
                $("#webSalt").removeAttr("lay-verify");
                form.render();
            }else {
                $('.webSalt').show();
                $("#webKey").attr("lay-verify","required|noChina|h5");
                $("#webKey").attr("minlength","16");
                $("#webKey").attr("maxlength","16");

                $("#webSalt").attr("lay-verify","required|noChina|h5");
                $("#webSalt").attr("minlength","16");
                $("#webSalt").attr("maxlength","16");
                form.render();
            }
        }else if (Number(webAlgorithmType) === 3){
            if (Number(encryptionMode)===0){
                $('.webSalt').hide();
                $("#webSalt").removeAttr("lay-verify");
                form.render();
            }else {
                $('.webSalt').show();
                $("#webKey").attr("lay-verify","required|noChina|h5");
                $("#webKey").attr("minlength","24");
                $("#webKey").attr("maxlength","24");

                $("#webSalt").attr("lay-verify","required|noChina|h5");
                $("#webSalt").attr("minlength","16");
                $("#webSalt").attr("maxlength","16");
                form.render();
            }
        }

    }

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/apiManage/detail?apiManageId=" + Feng.getUrlParam("apiManageId"));
    var result = ajax.start();
    form.val('apiManageForm', result.data);
    pass.refresh($("input[name='webAlgorithmType']:checked").val(),$('#encryptionMode').val());


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