function clearInput(){
    $('#js_input').val('');
    $('.weui-btn_input-clear').css('display',"none");
    $('#showTooltips').addClass('weui-btn_disabled');
    $("#showTooltips").attr("disabled",true).css("pointer-events","none");
}
layui.use(['admin', 'ax'], function () {
    var $ = layui.jquery;
    var $ax = layui.ax;
    var admin = layui.admin;
    $('#js_input').on('input', function () {
        if ($('#js_input').val()) {
            //显示清空按钮
            $('.weui-btn_input-clear').css('display',"block");
            //确定按钮变亮
            $('#showTooltips').removeClass('weui-btn_disabled');
            //确定按钮可点击
            $("#showTooltips").attr("disabled",false).css("pointer-events","auto");
        } else {
            //隐藏清空按钮
            $('.weui-btn_input-clear').css('display',"none");
            $('#showTooltips').addClass('weui-btn_disabled');
            $("#showTooltips").attr("disabled",true).css("pointer-events","none");
        }
    });
    //清空输入框
    $('#js_input_clear').click(function (){

    });
    $('#showTooltips').click(function (){
        // toptips的fixed, 如果有`animation`, `position: fixed`不生效
        $('.page.cell').removeClass('slideIn');
        $('#loadingToast').fadeIn(100);
        var ajax = new $ax(Feng.ctxPath + "/quick/cardUnBind/unBindItem", function (result) {
            console.log(result)
            $('#loadingToast').fadeOut(100);
            $('#js_toast .weui-toast__content').text(result.message);
            $('#js_toast').fadeIn(100);
            setTimeout(function () {
                $('#js_toast').fadeOut(100);
            }, 2000);
            return false;
        }, function (result) {
            $('#loadingToast').fadeOut(100);
            $('#warnToast .weui-toast__content').text(result.responseJSON.message);
            $('#warnToast').fadeIn(100);
            setTimeout(function () {
                $('#warnToast').fadeOut(100);
            }, 2000);
        },true);
        ajax.set('appId',$('#appId').val());
        ajax.set('card',$('#js_input').val());
        ajax.start();
        return false;


    });

});