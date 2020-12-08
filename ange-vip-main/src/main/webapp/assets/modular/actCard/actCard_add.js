/**
 * 详情对话框
 */
var CardInfoInfoDlg = {
    data: {
        appId: "",
        cardTypeId: "",
        userId: "",
        userName: "",
        isUniversal: "",
        cardCode: "",
        isCustomTime: "",
        customTimeNum: "",
        cardStutas: "",
        cardMac: "",
        cardIp: "",
        cardToken: "",
        activeTime: "",
        expireTime: "",
        cardBindType: "",
        cardOpenRange: "",
        cardOpenNum: "",
        cardRemark: "",
        prohibitRemark: "",
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
    //表单初始赋值
    layui.form.val('cardInfoForm', {
        "addNum":1
    });

    //让当前iframe弹层高度适应
    admin.iframeAuto();

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        data.field.hasOwnProperty('isActivation')?'': data.field.isActivation = 'off';
        data.field.developerUserId = Feng.getUrlParam("developerUserId");
        data.field.appId = Feng.getUrlParam("appId");
        //是否自定义时间
        data.field.isCustomTime = false;
        var ajax = new $ax(Feng.ctxPath + "/cardInfo/actAddItem", function (data) {
            Feng.success("添加成功！");
            //传给上个页面，刷新table用
            admin.putTempData('formOk', true);
            //关掉对话框
            admin.closeThisDialog();
            let cards = "";
            for (let i = 0; i < data.data.length; i++) {
                cards += data.data[i] + ",";
            }
            top.layui.admin.open({
                type: 2,
                title: '结果导出',
                area: '600px',
                content: Feng.ctxPath + '/cardInfo/addResult?cards=' + cards
            });
            return false;
        }, function (data) {
            Feng.error("添加失败！" + data.responseJSON.message)
        });
        ajax.set(data.field);
        ajax.start();
        return false;
    });
});