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
    //表单初始赋值
    layui.form.val('accountInfoForm', {
        "accountPoint":0,
    });

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    laydate.render({
        elem: '#expireTime',
        position: 'fixed',
        type: 'datetime'
    });

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/accountInfo/addItem", function (data) {
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
        return false;
    });

    form.verify({
        digital: function (account) {
            if (addAccountWhetherAlready(account)){
                return '该账号已存在';
            }
        }
    });

    function addAccountWhetherAlready(account) {
        var result;
        var ajax = new $ax(Feng.ctxPath + "/accountInfo/addAccountWhetherAlready", function (data) {
            result = data.data;
        });
        ajax.set("appId", $('#appId option:selected').val());
        ajax.set("account", account);
        ajax.start();
        return result;
    }
});