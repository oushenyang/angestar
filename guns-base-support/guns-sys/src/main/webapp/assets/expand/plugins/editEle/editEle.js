
////////////--main--/////////////////
var lastEditRange;
var editEle = document.getElementById('edit');
// 编辑框点击事件
editEle.onclick = function() {
    // 获取选定对象
    var selection = getSelection();
    // 设置最后光标对象
    lastEditRange = selection.getRangeAt(0);
};

// 编辑框按键弹起事件
editEle.onkeyup = function() {
    // 获取选定对象
    var selection = getSelection();
    // 设置最后光标对象
    lastEditRange = selection.getRangeAt(0);
};

$('.emoji-btn').on('click', function(e) {
    e.stopPropagation();
    var target = $('.emoji-content-select');
    target.toggleClass('select-display');
});

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
// 隐藏
$(document).on('click', function() {
    var target = $('.emoji-content-select');
    if (!target.hasClass('select-display')) {
        target.addClass('select-display');
    }
});

// 将编辑框中数据提交给后台
$('.commit-btn').on('click', function () {
    var textM = $('#edit').html();
    var html = textM.replace(/<span\s*[^>]*>(.*?)<\/span>/g, function ($1, $2) {
        var el = document.createElement('div');
        $(el).html($1);
        var emoji = $(el).children('span').attr('class').slice(11);
        var num1 = parseInt(emoji, 16);
        return '&#' + num1 + ';';
    });
    console.log(html)
    var text = html.replace(/(<br>)?(<\/div>)?<div>/g, '\\n') // 空行 表情结尾行 表情行
        .replace(/(<br>)|(<div>)/g, '\\n')
        .replace(/&nbsp;/g, ' ')
        .replace(/(<br>)?<\/div>$/, '');
    console.log('res--->', text);
})

