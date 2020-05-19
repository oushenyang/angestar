/**
 * 详情对话框
 */
var RemoteCodeInfoDlg = {
    data: {
        appId: "",
        codeCode: "",
        parameterOne: "",
        parameterTwo: "",
        parameterThree: "",
        parameterFour: "",
        codeValue: "",
        codeText: "",
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

    var editor = ace.edit("codeText");//绑定dom对象
    editor.setTheme("ace/theme/monokai");//设置主题
    editor.getSession().setMode("ace/mode/javascript");//设置程序语言
    editor.setReadOnly(false);//设置只读（true时只读，用于展示代码）
    //自动换行,设置为off关闭
    editor.setOption("wrap", "free");
    //启用提示菜单
    ace.require("ace/ext/language_tools");
    //以下部分是设置输入代码提示的
    editor.setOptions({
        enableBasicAutocompletion: true,
        // enableSnippets: true,
        enableLiveAutocompletion: true
    });
    editor.setHighlightActiveLine(true); //代码高亮
    editor.setShowPrintMargin(false);
    editor.getSession().setUseWrapMode(true); //支持代码折叠

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/remoteCode/detail?codeId=" + Feng.getUrlParam("codeId"));
    var result = ajax.start();
    form.val('remoteCodeForm', result.data);
    editor.setValue(result.data.codeText);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.codeText=editor.getValue();
        var ajax = new $ax(Feng.ctxPath + "/remoteCode/editItem", function (data) {
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