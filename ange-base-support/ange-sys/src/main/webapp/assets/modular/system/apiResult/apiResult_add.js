/**
 * 详情对话框
 */
var ApiResultInfoDlg = {
    data: {
        appId: "",
        resultType: "",
        resultSuccess: "",
        resultVariables: "",
        resultCode: "",
        resultData: "",
        customResultData: "",
        resultRemark: "",
        whetherEdit: "",
        whetherResultJson: "",
        sort: "",
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

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.hasOwnProperty('resultSuccess')?'': data.field.resultSuccess = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('whetherEdit')?'': data.field.whetherEdit = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('whetherResultJson')?'': data.field.whetherResultJson = 'off'; //true 值为on,false 值给赋off
        var ajax = new $ax(Feng.ctxPath + "/apiResult/addItem", function (data) {
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
});