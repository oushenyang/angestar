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
        resultDataText: "",
        customResultData: "",
        customResultDataText: "",
        resultRemark: "",
        whetherEdit: "",
        outputFormat: "",
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
    $("#codeText").append(HTMLDecode( result.data.resultDataText));
    $("#edit").append(HTMLDecode( result.data.customResultDataText));
    function HTMLDecode(text) {
        var temp = document.createElement("div");
        temp.innerHTML = text;
        var output = temp.innerText || temp.textContent;
        temp = null;
        return output;
    }
});