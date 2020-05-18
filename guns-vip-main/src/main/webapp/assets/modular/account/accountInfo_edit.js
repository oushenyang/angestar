/**
 * 详情对话框
 */
var AccountInfoInfoDlg = {
    data: {
        appId: "",
        account: "",
        email: "",
        phone: "",
        qq: "",
        password: "",
        accountStatus: "",
        registrationTime: "",
        expireTime: "",
        accountPoint: "",
        surplusPoint: "",
        referrer: "",
        referrerCode: "",
        accountData: "",
        accountToken: "",
        accountBindType: "",
        accountBindNum: "",
        accountOpenRange: "",
        accountOpenNum: "",
        remark: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['form', 'formX','admin', 'ax','laydate'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;
    var laydate = layui.laydate;

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    laydate.render({
        elem: '#expireTime',
        position: 'fixed',
        type: 'datetime'
    });

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/accountInfo/detail?accountId=" + Feng.getUrlParam("accountId"));
    var result = ajax.start();
    form.val('accountInfoForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/accountInfo/editItem", function (data) {
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