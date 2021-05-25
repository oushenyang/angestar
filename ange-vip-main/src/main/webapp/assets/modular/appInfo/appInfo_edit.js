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
layui.use(['form', 'admin', 'ax','dict', 'notice'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var dict=layui.dict; //获取自定义模块
    var notice = layui.notice;
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
            // notice.destroy();
            var ajax = new $ax(Feng.ctxPath + "/appInfo/editItem", function (data) {
                Feng.success("更新成功！");
                //传给上个页面，刷新table用
                admin.putTempData('formOk', true);
                //关掉对话框
                admin.closeThisDialog();
            }, function (data) {
                Feng.error("更新失败！" + data.responseJSON.message)
            });
            ajax.set(data.field);
            ajax.start();
            //添加 return false 可成功跳转页面
            return false;
        });
    });
});