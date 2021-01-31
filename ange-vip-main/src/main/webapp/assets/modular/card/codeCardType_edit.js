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

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/codeCardType/detail?cardTypeId=" + Feng.getUrlParam("cardTypeId"));
    var result = ajax.start();
    form.val('codeCardTypeForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        if (Number(data.field.cardTypePrice)<Number(data.field.cardTypeAgentPrice)){
            Feng.error("代理价格不能大于市场价格！");
            return false;
        }
        var ajax = new $ax(Feng.ctxPath + "/codeCardType/editItem", function (data) {
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