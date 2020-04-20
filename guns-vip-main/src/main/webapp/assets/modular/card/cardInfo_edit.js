/**
 * 详情对话框
 */
var CardInfoInfoDlg = {
    data: {
        appId: "",
        cardTypeId: "",
        userId: "",
        userName: "",
        isUniversal: "",
        cardCode: "",
        isCustomTime: "",
        customTimeNum: "",
        cardStutas: "",
        cardMac: "",
        cardIp: "",
        cardToken: "",
        activeTime: "",
        expireTime: "",
        cardBindType: "",
        cardOpenRange: "",
        cardOpenNum: "",
        cardRemark: "",
        prohibitRemark: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['form', 'formX','admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/cardInfo/detail?cardId=" + Feng.getUrlParam("cardId"));
    var result = ajax.start();
    form.val('cardInfoForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/cardInfo/editItem", function (data) {
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
        return false;
    });
});