/**
 * 详情对话框
 */
var AgentAppInfoDlg = {
    data: {
        appId: "",
        developerUserId: "",
        agentUserId: "",
        agentUserName: "",
        agentUserAccount: "",
        agentGrade: "",
        pid: "",
        pids: "",
        balance: "",
        status: "",
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
    var ajax = new $ax(Feng.ctxPath + "/agentPower/detail?agentPowerId=" + Feng.getUrlParam("agentPowerId"));
    var result = ajax.start();
    form.val('agentAppForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.hasOwnProperty('cardCreate')?'': data.field.cardCreate = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('cardDisable')?'': data.field.cardDisable = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('cardLook')?'': data.field.cardLook = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('cardData')?'': data.field.cardData = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('cardTime')?'': data.field.cardTime = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('cardDelete')?'': data.field.cardDelete = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('accountCreate')?'': data.field.accountCreate = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('accountDisable')?'': data.field.accountDisable = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('accountEditPassword')?'': data.field.accountEditPassword = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('accountDelete')?'': data.field.accountDelete = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('accountTime')?'': data.field.accountTime = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('accountData')?'': data.field.accountData = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('accountQuery')?'': data.field.accountQuery = 'off'; //true 值为on,false 值给赋off
        data.field.type = Feng.getUrlParam("type");
        var ajax = new $ax(Feng.ctxPath + "/agentPower/editItem", function (data) {
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