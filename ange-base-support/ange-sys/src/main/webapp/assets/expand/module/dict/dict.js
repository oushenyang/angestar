/**
 * 扩展layui组件
 */
layui.define(["element","jquery",'ax'], function(exports){
    var element = layui.element,
        $ = layui.$;
    var $ax = layui.ax;
    FsForm = function () {}
    FsForm.prototype.renderDictAll = function (formId, b) {
        var element = layui.element, $ = layui.$;
        $.ajaxSettings.async = false; //设置为同步，否则layui会先渲染表格，导致数据显示不出来
        //此处拿到的dict即字典code，可以通过此字典code去数据库或者redis中查询相应的字典数据并加载到select中的option中。
        $('.selDict').each(function(){
            var _this = $(this);
            var dict = _this.attr("dict");
            var ajax = new $ax(Feng.ctxPath + "/dict/listDictsByCode", function (data) {
                for (var i = 0; i < data.data.length; i++) {
                    var name = data.data[i].name;
                    var code = data.data[i].code;
                    _this.append("<option value='"+code+"'>"+name+"</option>");
                    // $("#dictDetails").append('<input type="radio" name="systemType" value="' + code + '" title="' + name + '">');
                }
            }, function (data) {
            });
            ajax.set("dictTypeCode", dict);
            ajax.start();
        });
        $.ajaxSettings.async = true; //ajax恢复为异步
    };
    var fsForm = new FsForm();
    exports("dict", fsForm);
});