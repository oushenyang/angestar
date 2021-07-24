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
    $('#know').click(function (){
        $('#iosDialog1').fadeOut(200);
    });
    $('#showTooltips').click(function (){

        // toptips的fixed, 如果有`animation`, `position: fixed`不生效
        $('.page.cell').removeClass('slideIn');
        $('#loadingToast').fadeIn(100);
        var ajax = new $ax(Feng.ctxPath + "/quick/cardSearch/searchItem", function (result) {
            $('#loadingToast').fadeOut(100);
            //未使用
            if (result.data.cardStatus===0){
                $('#iosDialog1 .detail').html('卡密未使用');
            }else if (result.data.cardStatus===1){
                $('#iosDialog1 .detail').html('卡密已使用<br>使用时间：'+result.data.activeTime+'<br>'+'到期时间：'+result.data.expireTime);
            }else if (result.data.cardStatus===2){
                $('#iosDialog1 .detail').html('卡密已过期<br>使用时间：'+result.data.activeTime+'<br>'+'到期时间：'+result.data.expireTime);
            }else if (result.data.cardStatus===3){
                $('#iosDialog1 .detail').html('卡密已禁用');
            }
            $('#iosDialog1').fadeIn(200);
            return false;
        }, function (result) {
            $('#loadingToast').fadeOut(100);
            $('#iosDialog1 .detail').text(result.responseJSON.message);
            $('#iosDialog1').fadeIn(200);
        },true);
        ajax.set('appId',$('#appId').val());
        ajax.set('card',$('#js_input').val());
        ajax.start();
        return false;


    });

});