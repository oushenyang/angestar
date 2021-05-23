layui.use(['form', 'formX','admin', 'ax', 'notice', 'upload'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var notice = layui.notice;
    var upload = layui.upload;
    var field = {};
    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //指定允许上传的文件类型
    upload.render({
        elem: '#txtFileUpload',
        url: 'https://httpbin.org/post', //改成您自己的上传接口
        accept: 'file', //普通文件
        // auto: false,
        exts: 'txt', //只允许上传txt文件
        before: function(obj){
            obj.preview(function(index, file, result){
                $('#txtFileName').val(file.name);
                field['txtFile'] = file;
            });
        },
        done: function(res) {
            layer.msg('上传成功');
        }
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        field['appId'] = $('#appId').val();
        field['yyCardAddress'] = $('#yyCardAddress').val();
        var ajax = new $ax(Feng.ctxPath + "/cardInfo/yyImportItem", function (result) {
            Feng.success("添加成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            return false;
        }, function (result) {
            notice.msg("添加失败!" + result.responseJSON.message + "!", {icon: 2});
        });
        ajax.set(field);
        ajax.start();
        return false;
    });
});