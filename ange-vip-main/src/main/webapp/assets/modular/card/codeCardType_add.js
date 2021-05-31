/**
 * 详情对话框
 */
var CodeCardTypeInfoDlg = {
    data: {
        appId: "",
        cardTypeName: "",
        cardTimeType: "",
        cardTypeData: "",
        cardTypePrefix: "",
        cardTypeRule: "",
        cardTypeLength: "",
        cardTypePrice: "",
        cardTypeAgentPrice: "",
        revision: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['form','formX','admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var loading = top.layer.msg('导入中', {icon: 16, shade: [0.1, '#000'], time: false});
        var ajax = new $ax(Feng.ctxPath + "/codeCardType/addItem", function (data) {
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
        return false;
    });
});