layui.use(['form', 'admin', 'ax','element', 'textool', 'formX'], function () {
    //表单初始赋值
    layui.form.val('appInfoForm', {
        "codeBindOption":1,
        "codeBindNum": 1,
        "codeBindTime": 0,
        "accountBindOption":1,
        "accountBindNum": 1,
        "accountBindTime": 0,
        "codeOpenRange": 1,
        "codeOpenNum": 1,
        "codeSignType": 1,
        "codeClearSpace": 1,
        "accountOpenRange": 1,
        "accountOpenNum": 1,
        "accountClearSpace": 1,
        "codeTryTime": 0,
        "accountRegisterNum": 1,
        "accountRegisterTime": 0,
        "webKey": _getRandomString(10),
        "webSalt": _getRandomString(10),
        "easyKey": _getRandomString(15),
        "easySalt": _getRandomString(15)
    });
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var formX = layui.formX;
    var admin = layui.admin;
    var element = layui.element;
    var textool = layui.textool;
    textool.init({
        // 根据元素 id 值单独渲染，为空默认根据 class='layext-text-tool' 批量渲染
        eleId: null,
        // 批量设置输入框最大长度，可结合 eleId 单独设置最大长度
        maxlength: 512
    });

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.codeClearSpace = 1;
        data.field.accountClearSpace = 1;
        var loading = top.layer.msg('处理中', {icon: 16, shade: [0.1, '#000'], time: false});
        var ajax = new $ax(Feng.ctxPath + "/appInfo/addItem", function (data) {
            layer.close(loading);
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            layer.close(loading);
            Feng.error("添加失败！" + data.responseJSON.message)
        },true);
        ajax.set(data.field);
        ajax.start();
        //添加 return false 可成功跳转页面
        return false;
    });
});

//生成随机字符串
function _getRandomString(len) {
    len = len || 32;
    var $chars = 'ABCDEFGHJKMNPQRSTWXYZabcdefhijkmnprstwxyz2345678'; // 默认去掉了容易混淆的字符oOLl,9gq,Vv,Uu,I1
    var maxPos = $chars.length;
    var pwd = '';
    for (i = 0; i < len; i++) {
        pwd += $chars.charAt(Math.floor(Math.random() * maxPos));
    }
    return pwd;
}