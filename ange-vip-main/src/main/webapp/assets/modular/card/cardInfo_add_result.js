layui.use(['form','admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var form = layui.form;
    var admin = layui.admin;

    //让当前iframe弹层高度适应
    admin.iframeAuto();
    // 添加按钮点击事件
    $('.cardStr').click(function () {
        var value = this.value;
        var cla = '.cardStr' + value;
        var clipboard = new ClipboardJS(cla, {
            text: function () {
                return value;
            }
        });
        clipboard.on('success', function (e) {
            e.clearSelection();
            Feng.success("已复制到粘贴板");
            return false;
        });
        clipboard.on('error', function (e) {
            e.clearSelection();
            Feng.error("复制失败");
            return false;
        });
    });

    // $('.btnSubmit').click(function () {
    //     var ajax = new $ax(Feng.ctxPath + "/cardInfo/addExport", function (data) {
    //         Feng.success("添加成功！");
    //     }, function (data) {
    //         Feng.error("添加失败！" + data.responseJSON.message)
    //     });
    //     ajax.set("cards",Feng.getUrlParam("cards"));
    //     ajax.start();server
    //     return false;
    // });

    form.on('submit(exportSubmit)', function (data) {
        console.log(4444)
        window.location.href=Feng.ctxPath + "/cardInfo/addExport?cards=" + Feng.getUrlParam("cards");
        // var ajax = new $ax(Feng.ctxPath + "/cardInfo/addExport?cards=" + Feng.getUrlParam("cards"));
        // ajax.start();
        return false;
    });
});