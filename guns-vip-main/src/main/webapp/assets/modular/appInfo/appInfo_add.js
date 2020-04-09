/**
 * 详情对话框
 */
var AppInfoInfoDlg = {
    data: {
        appNum: "",
        userId: "",
        appName: "",
        cydiaFlag: "",
        appNotice: "",
        customData1: "",
        customData2: "",
        customData3: "",
        codeBindType: "",
        codeBindOption: "",
        codeBindNum: "",
        codeBindTime: "",
        accountBindType: "",
        accountBindOption: "",
        accountBindNum: "",
        accountBindTime: "",
        codeOpenRange: "",
        codeSignType: "",
        codeOpenNum: "",
        accountOpenRange: "",
        accountSignType: "",
        accountOpenNum: "",
        codeTryType: "",
        codeTryTime: "",
        accountRegisterSwitch: "",
        accountRegisterLimit: "",
        accountRegisterNum: "",
        accountRegisterTime: "",
        webAlgorithmType: "",
        webKey: "",
        webSalt: "",
        versionNum: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};
//注意：选项卡 依赖 element 模块，否则无法进行功能性操作
layui.use('element', function(){
    var element = layui.element;

    //一些事件监听
    element.on('tab(demo)', function(data){
        console.log(data);
    });
});

layui.use(['form', 'admin', 'ax'], function () {
    //表单初始赋值
    layui.form.val('appInfoForm', {
        "codeBindNum": 1,
        "codeBindTime": 0,
        "accountBindNum": 1,
        "accountBindTime": 0,
        "codeOpenNum": 1,
        "accountOpenNum": 1,
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
    var admin = layui.admin;

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/appInfo/addItem", function (data) {
            Feng.success("添加成功！");

            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);

            //关掉对话框
            admin.closeThisDialog();
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
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