/**
 * 详情对话框
 */
var SqlInfoDlg = {
    data: {
        sqlTypeId: "",
        code: "",
        name: "",
        parentId: "",
        status: "",
        description: "",
        createTime: "",
        updateTime: "",
        createUser: "",
        updateUser: ""
    }
};

layui.use(['form', 'ax', 'admin'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //获取详情信息，填充表单
    var ajax = new $ax(Feng.ctxPath + "/sql/detail?sqlId=" + Feng.getUrlParam("sqlId"));
    var result = ajax.start();
    form.val('sqlForm', result.data);

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/sql/editItem", function (data) {
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

    //父级sql时
    $('#parentName').click(function () {
        var formName = encodeURIComponent("parent.SqlInfoDlg.data.parentName");
        var formId = encodeURIComponent("parent.SqlInfoDlg.data.parentId");
        var treeUrl = encodeURIComponent("/sql/ztree?sqlTypeId=" + $("#sqlTypeId").val() + "&sqlId=" + $("#sqlId").val());

        layer.open({
            type: 2,
            title: '父级sql',
            area: ['300px', '400px'],
            content: Feng.ctxPath + '/system/commonTree?formName=' + formName + "&formId=" + formId + "&treeUrl=" + treeUrl,
            end: function () {
                $("#parentId").val(SqlInfoDlg.data.parentId);
                $("#parentName").val(SqlInfoDlg.data.parentName);
            }
        });
    });
});