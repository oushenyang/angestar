/**
 * 详情对话框
 */
var RemoteDataInfoDlg = {
    data: {
        appId: "",
        dataCode: "",
        dataValue: "",
        createType: "",
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

    var lastEditRange;
    var editEle = document.getElementById('edit');
    // 编辑框点击事件
    editEle.onclick = function() {
        // 获取选定对象
        var selection = getSelection();
        // 设置最后光标对象
        lastEditRange = selection.getRangeAt(0);
    };

    editEle.onpaste=function(event){
        var e = event || window.event
        // 阻止默认粘贴
        e.preventDefault();
        // 粘贴事件有一个clipboardData的属性，提供了对剪贴板的访问
        // clipboardData的getData(fomat) 从剪贴板获取指定格式的数据
        var text =  (e.originalEvent || e).clipboardData.getData('text/plain');
        // 插入
        document.execCommand("insertText", false, text);
    };

    // 编辑框按键弹起事件
    editEle.onkeyup = function() {
        // 获取选定对象
        var selection = getSelection();
        // 设置最后光标对象
        lastEditRange = selection.getRangeAt(0);
    };
    // 选择表情
    $('.emoji-content-select').click(function (e) {
        var target = e.target;
        console.log(e.target.outerHTML)
        if ($(target).children().length == 0) { // 不能选择父元素
            $(target).attr('contentEditable', false);
            console.log(e.target.outerHTML)
            _insertimg(e.target.outerHTML)
        }
    });

    function _insertimg(str) {
        var selection = window.getSelection ? window.getSelection() : document.selection;
        document.getElementById('edit').focus();
        if (lastEditRange) {
            // 存在最后光标对象，选定对象清除所有光标并添加最后光标还原之前的状态
            selection.removeAllRanges()
            selection.addRange(lastEditRange)
        }
        var range = selection.createRange ? selection.createRange() : selection.getRangeAt(0);
        if (!window.getSelection) {
            var selection = window.getSelection ? window.getSelection() : document.selection;
            var range = selection.createRange ? selection.createRange() : selection.getRangeAt(0);
            range.pasteHTML(str);
            range.collapse(false);
            range.select();
        } else {
            var hasR = range.createContextualFragment(str);
            var hasR_lastChild = hasR.lastChild;
            while (hasR_lastChild && hasR_lastChild.nodeName.toLowerCase() == "br" && hasR_lastChild.previousSibling && hasR_lastChild.previousSibling.nodeName.toLowerCase() == "br") {
                var e = hasR_lastChild;
                hasR_lastChild = hasR_lastChild.previousSibling;
                hasR.removeChild(e)
            }
            range.insertNode(hasR);
            if (hasR_lastChild) {
                range.setEndAfter(hasR_lastChild);
                range.setStartAfter(hasR_lastChild);
            }
            range.collapse(false);
            selection.removeAllRanges();
            selection.addRange(range)
        }
        // 无论如何都要记录最后光标对象
        lastEditRange = selection.getRangeAt(0)
    }

    //表单提交事件
    form.on('submit(btnSubmit)', function (data) {
        var textM = $('#edit').html();
        var html = textM.replace(/<span\s*[^>]*>(.*?)<\/span>/g, function ($1, $2) {
            var el = document.createElement('div');
            $(el).html($1);
            var emoji = $(el).children('span').attr('data-value');
            return '[[' + emoji + ']]';
        });
        var text = html.replace(/(<br>)?(<\/div>)?<div>/g, '') // 空行 表情结尾行 表情行
            .replace(/(<br>)|(<div>)/g, '')
            .replace(/&nbsp;/g, ' ')
            .replace(/(<br>)?<\/div>$/, '');
        data.field.dataValue = text;
        data.field.dataValueText = HTMLEncode($('#edit').html());
        var ajax = new $ax(Feng.ctxPath + "/remoteData/addItem", function (data) {
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

    function HTMLEncode(html) {
        var temp = document.createElement("div");
        (temp.textContent != null) ? (temp.textContent = html) : (temp.innerText = html);
        var output = temp.innerHTML;
        temp = null;
        return output;
    }
});