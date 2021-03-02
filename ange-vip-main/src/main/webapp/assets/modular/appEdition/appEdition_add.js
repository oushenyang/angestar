/**
 * 详情对话框
 */
var AppEditionInfoDlg = {
    data: {
        editionSerial: "",
        appId: "",
        editionName: "",
        editionNum: "",
        editionStatus: "",
        needUpdate: "",
        editionMd5: "",
        updateUrl: "",
        updateDescribe: "",
        remark: "",
        revision: "",
        createUser: "",
        createTime: "",
        updateUser: "",
        updateTime: ""
    }
};

layui.use(['form', 'admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //让当前iframe弹层高度适应
    admin.iframeAuto();



    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var ajax = new $ax(Feng.ctxPath + "/appEdition/addItem", function (data) {
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
        digital: function (editionNum) {
            if (isAlreadyAppEdition(editionNum)){
                return '该版本号已存在';
            }
        }
    });

    function isAlreadyAppEdition(editionNum) {
        var result;
        var ajax = new $ax(Feng.ctxPath + "/appEdition/addIsAlreadyAppEdition", function (data) {
            result = data.data;
        });
        ajax.set("appId", $('#appId option:selected').val());
        ajax.set("editionNum", editionNum);
        ajax.start();
        return result;
    }
});