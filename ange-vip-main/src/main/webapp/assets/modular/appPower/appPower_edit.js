/**
 * 详情对话框
 */
var AppPowerInfoDlg = {
    data: {
        appName: "",
        appTypeCode: "",
        sign: "",
        whetherLegal: "",
        whetherSanction: "",
        customData: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: "",
        sanctionTime: ""
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
    var ajax = new $ax(Feng.ctxPath + "/appPower/detail?appPowerId=" + Feng.getUrlParam("appPowerId"));
    var result = ajax.start();
    form.val('appPowerForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/appPower/editItem", function (data) {
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