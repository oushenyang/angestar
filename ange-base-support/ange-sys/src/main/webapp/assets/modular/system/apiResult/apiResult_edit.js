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

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/apiResult/detail?apiResultId=" + Feng.getUrlParam("apiResultId"));
    var result = ajax.start();
    form.val('apiResultForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.hasOwnProperty('resultSuccess')?'': data.field.resultSuccess = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('whetherEdit')?'': data.field.whetherEdit = 'off'; //true 值为on,false 值给赋off
        data.field.hasOwnProperty('whetherResultJson')?'': data.field.whetherResultJson = 'off'; //true 值为on,false 值给赋off
        var ajax = new $ax(Feng.ctxPath + "/apiResult/editItem", function (data) {
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