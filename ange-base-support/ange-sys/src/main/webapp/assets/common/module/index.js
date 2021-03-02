/** EasyWeb iframe v3.1.7 date:2019-03-08 License By http://easyweb.vip */

layui.define(['layer', 'element', 'admin'], function (exports) {
    var $ = layui.jquery;
    var layer = layui.layer;
    var element = layui.element;
    var admin = layui.admin;
    var setter = admin.setter;
    var headerDOM = '.layui-layout-admin>.layui-header';
    var sideDOM = '.layui-layout-admin>.layui-side>.layui-side-scroll';
    var bodyDOM = '.layui-layout-admin>.layui-body';
    var tabDOM = bodyDOM + '>.layui-tab';
    var titleDOM = bodyDOM + '>.layui-body-header';
    var tabFilter = 'admin-pagetabs';
    var navFilter = 'admin-side-nav';
    var tabEndCall = {};  // Tab关闭的事件回调
    var mIsAddTab = false;  // 是否是添加Tab，添加Tab的时候切换不自动刷新
    var index = {homeUrl: undefined, mTabPosition: undefined, mTabList: []};

    /* 处理主体部分切换 */
    index.loadView = function (param) {
        var menuName = param.menuName;  // 标题
        var menuPath = param.menuPath;  // 路径
        if (!menuPath) return layer.msg('url不能为空', {icon: 2, anim: 6});
        if (setter.pageTabs) {  // 多标签模式
            var flag;  // 选项卡是否已经添加
            $(tabDOM + '>.layui-tab-title>li').each(function () {
                if ($(this).attr('lay-id') === menuPath) {
                    flag = true;
                    return false;
                }
            });
            if (!flag) {  // 添加选项卡
                if ((index.mTabList.length + 1) >= setter.maxTabNum) {
                    layer.msg('最多打开' + setter.maxTabNum + '个选项卡', {icon: 2, anim: 6});
                    admin.activeNav(index.mTabPosition);
                    return;
                }
                mIsAddTab = true;
                element.tabAdd(tabFilter, {
                    id: menuPath,
                    title: '<span class="title">' + (menuName ? menuName : '') + '</span>',
                    content: '<iframe lay-id="' + menuPath + '" src="' + menuPath + '" frameborder="0" class="admin-iframe"></iframe>'
                });
                if (menuPath !== index.homeUrl) index.mTabList.push(param);  // 记录tab
                if (setter.cacheTab) admin.putTempData('indexTabs', index.mTabList);  // 缓存tab
            }
            element.tabChange(tabFilter, menuPath);  // 切换到该选项卡
        } else {  // 单标签模式
            admin.activeNav(menuPath);
            var $contentDom = $(bodyDOM + '>div>.admin-iframe');
            if ($contentDom.length <= 0) {  // 第一次补充标题栏
                var contentHtml = '<div class="layui-body-header">';
                contentHtml += '      <span class="layui-body-header-title"></span>';
                contentHtml += '      <span class="layui-breadcrumb pull-right" lay-filter="admin-body-breadcrumb" style="visibility: visible;"></span>';
                contentHtml += '   </div>';
                contentHtml += '   <div style="-webkit-overflow-scrolling: touch;">';
                contentHtml += '      <iframe lay-id="' + menuPath + '" src="' + menuPath + '" frameborder="0" class="admin-iframe"></iframe>';
                contentHtml += '   </div>';
                $(bodyDOM).html(contentHtml);
            } else {
                $contentDom.attr('lay-id', menuPath);
                $contentDom.attr('src', menuPath);
            }
            $('[lay-filter="admin-body-breadcrumb"]').html(index.getBreadcrumbHtml(menuPath));
            var title = menuName;
            if (menuPath === index.homeUrl) {
                title = $(menuName).text() || $(sideDOM + ' [lay-href="' + index.homeUrl + '"]').text() || '主页';
            }
            index.setTabTitle(title);
            index.mTabList.splice(0, index.mTabList.length);
            if (menuPath !== index.homeUrl) {
                index.mTabList.push(param);
                index.mTabPosition = menuPath;
            } else {
                index.mTabPosition = undefined;
            }
            if (setter.cacheTab) {
                admin.putTempData('indexTabs', index.mTabList);
                admin.putTempData('tabPosition', index.mTabPosition);
            }
        }
        if (admin.getPageWidth() <= 768) admin.flexible(true); // 移动端自动收起侧导航
    };

    /* 加载主页 */
    index.loadHome = function (param) {
        var cacheTabs = admin.getTempData('indexTabs');  // 获取缓存tab
        var cachePosition = admin.getTempData('tabPosition');
        index.homeUrl = param.menuPath;
        index.loadView(param);
        if (!setter.pageTabs) admin.activeNav(index.homeUrl);
        // 恢复缓存tab
        if (param.loadSetting === undefined || param.loadSetting) {
            if (setter.cacheTab && cacheTabs) {
                var mi = undefined;
                for (var i = 0; i < cacheTabs.length; i++) {
                    if (setter.pageTabs && !param.onlyLast) index.loadView(cacheTabs[i]);
                    if (cacheTabs[i].menuPath === cachePosition) mi = i;
                }
                if (mi !== undefined) {
                    setTimeout(function () {
                        index.loadView(cacheTabs[mi]);
                        if (!setter.pageTabs) admin.activeNav(cachePosition);
                    }, 150);
                }
            }
        }
    };

    /* 打开新页面 */
    index.openTab = function (param) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) {
            return top.layui.index.openTab(param);
        }
        if (param.end) tabEndCall[param.url] = param.end;
        index.loadView({menuPath: param.url, menuName: param.title});
    };

    /* 关闭选项卡 */
    index.closeTab = function (url) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) {
            return top.layui.index.closeTab(url);
        }
        element.tabDelete(tabFilter, url);
    };

    /* 设置是否记忆Tab */
    index.setTabCache = function (isCache) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) {
            return top.layui.index.setTabCache(isCache);
        }
        admin.putSetting('cacheTab', isCache);
        if (isCache) {
            admin.putTempData('indexTabs', index.mTabList);
            admin.putTempData('tabPosition', index.mTabPosition);
        } else {
            index.clearTabCache();
        }
    };

    /* 清除选项卡记忆 */
    index.clearTabCache = function () {
        admin.putTempData('indexTabs', null);
        admin.putTempData('tabPosition', null);
    };

    /* 设置Tab标题 */
    index.setTabTitle = function (title, tabId) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) {
            return top.layui.index.setTabTitle(title, tabId);
        }
        if (setter.pageTabs) {
            if (!tabId) tabId = $(tabDOM + '>.layui-tab-title>li.layui-this').attr('lay-id');
            if (tabId) $(tabDOM + '>.layui-tab-title>li[lay-id="' + tabId + '"] .title').html(title || '');
        } else {
            if (title) {
                $(titleDOM + '>.layui-body-header-title').html(title);
                $(titleDOM).addClass('show');
                $(headerDOM).css('box-shadow', '0 1px 0 0 rgba(0, 0, 0, .03)');
            } else {
                $(titleDOM).removeClass('show');
                admin.util.removeStyle(headerDOM, 'box-shadow');
            }
        }
    };

    /* 自定义Tab标题 */
    index.setTabTitleHtml = function (html) {
        if (window !== top && !admin.isTop() && top.layui && top.layui.index) {
            return top.layui.index.setTabTitleHtml(html);
        }
        if (!setter.pageTabs) {
            if (html) {
                $(titleDOM).addClass('show');
                $(titleDOM).html(html);
            } else {
                $(titleDOM).removeClass('show');
            }
        }
    };

    /* 获取面包屑 */
    index.getBreadcrumb = function (tabId) {
        if (!tabId) tabId = $(bodyDOM + '>div>.admin-iframe').attr('lay-id');
        var breadcrumb = [];
        var $href = $(sideDOM).find('[lay-href="' + tabId + '"]');
        if ($href) breadcrumb.push($href.text().replace(/(^\s*)|(\s*$)/g, ''));
        while (true) {
            $href = $href.parent('dd').parent('dl').prev('a');
            if ($href.length === 0) break;
            breadcrumb.unshift($href.text().replace(/(^\s*)|(\s*$)/g, ''));
        }
        return breadcrumb;
    };

    /* 获取面包屑结构 */
    index.getBreadcrumbHtml = function (tabId) {
        var breadcrumb = index.getBreadcrumb(tabId);
        var htmlStr = (tabId === index.homeUrl ? '' : ('<a ew-href="' + index.homeUrl + '">首页</a>'));
        for (var i = 0; i < breadcrumb.length - 1; i++) {
            if (htmlStr) htmlStr += '<span lay-separator="">/</span>';
            htmlStr += ('<a><cite>' + breadcrumb[i] + '</cite></a>');
        }
        return htmlStr;
    };

    /* 移动设备遮罩层 */
    var siteShadeDom = '.layui-layout-admin .site-mobile-shade';
    if ($(siteShadeDom).length <= 0) {
        $('.layui-layout-admin').append('<div class="site-mobile-shade"></div>');
    }
    $(siteShadeDom).click(function () {
        admin.flexible(true);
    });

    /* 补充tab的dom */
    if (setter.pageTabs && $(tabDOM).length === 0) {
        var tabDomHtml = '<div class="layui-tab" lay-allowClose="true" lay-filter="' + tabFilter + '">';
        tabDomHtml += '      <ul class="layui-tab-title"></ul>';
        tabDomHtml += '      <div class="layui-tab-content"></div>';
        tabDomHtml += '   </div>';
        tabDomHtml += '   <div class="layui-icon admin-tabs-control layui-icon-prev" ew-event="leftPage"></div>';
        tabDomHtml += '   <div class="layui-icon admin-tabs-control layui-icon-next" ew-event="rightPage"></div>';
        tabDomHtml += '   <div class="layui-icon admin-tabs-control layui-icon-down">';
        tabDomHtml += '      <ul class="layui-nav" lay-filter="admin-pagetabs-nav">';
        tabDomHtml += '         <li class="layui-nav-item" lay-unselect>';
        tabDomHtml += '            <dl class="layui-nav-child layui-anim-fadein">';
        tabDomHtml += '               <dd ew-event="closeThisTabs" lay-unselect><a>关闭当前标签页</a></dd>';
        tabDomHtml += '               <dd ew-event="closeOtherTabs" lay-unselect><a>关闭其它标签页</a></dd>';
        tabDomHtml += '               <dd ew-event="closeAllTabs" lay-unselect><a>关闭全部标签页</a></dd>';
        tabDomHtml += '            </dl>';
        tabDomHtml += '         </li>';
        tabDomHtml += '      </ul>';
        tabDomHtml += '   </div>';
        $(bodyDOM).html(tabDomHtml);
        element.render('nav', 'admin-pagetabs-nav');
    }
    if (setter.pageTabs && setter.tabAutoRefresh) {  // 恢复tab自动刷新
        $(tabDOM).attr('lay-autoRefresh', 'true');
    }

    /* 监听侧导航栏点击事件 */
    element.on('nav(' + navFilter + ')', function (elem) {
        var $that = $(elem);
        var menuUrl = $that.attr('lay-href');
        if (menuUrl && menuUrl !== 'javascript:;') {
            var menuName = $that.attr('ew-title');
            if (!menuName) menuName = $that.text().replace(/(^\s*)|(\s*$)/g, '');  // 去空格
            index.loadView({menuPath: menuUrl, menuName: menuName});
        }
    });

    /* tab选项卡切换监听 */
    element.on('tab(' + tabFilter + ')', function () {
        var layId = $(this).attr('lay-id');
        index.mTabPosition = (layId !== index.homeUrl ? layId : undefined);  // 记录当前Tab位置
        if (setter.cacheTab) admin.putTempData('tabPosition', index.mTabPosition);
        admin.activeNav(layId);
        admin.rollPage('auto');
        // 切换tab自动刷新
        var autoRefresh = $(tabDOM).attr('lay-autoRefresh');
        if (autoRefresh === 'true' && !mIsAddTab) admin.refresh(layId);
        mIsAddTab = false;
    });

    /* tab选项卡删除监听 */
    element.on('tabDelete(' + tabFilter + ')', function (data) {
        var mTab = index.mTabList[data.index - 1];
        if (mTab) {
            var layId = mTab.menuPath;
            index.mTabList.splice(data.index - 1, 1);
            if (setter.cacheTab) admin.putTempData('indexTabs', index.mTabList);
            tabEndCall[layId] && tabEndCall[layId].call();
        }
        // 解决偶尔出现关闭后没有选中任何Tab的bug
        if ($(tabDOM + '>.layui-tab-title>li.layui-this').length <= 0) {
            $(tabDOM + '>.layui-tab-title>li:last').trigger('click');
        }
    });

    /* 多系统切换事件 */
    $(document).off('click.navMore').on('click.navMore', '[nav-bind]', function () {
        var navId = $(this).attr('nav-bind');
        $('ul[lay-filter="' + navFilter + '"]').addClass('layui-hide');
        $('ul[nav-id="' + navId + '"]').removeClass('layui-hide');
        $(headerDOM + '>.layui-nav .layui-nav-item').removeClass('layui-this');
        $(this).parent('.layui-nav-item').addClass('layui-this');
        if (admin.getPageWidth() <= 768) admin.flexible(false);  // 展开侧边栏
    });

    /* 开启Tab右键菜单 */
    if (setter.openTabCtxMenu && setter.pageTabs) {
        layui.use('contextMenu', function () {
            var contextMenu = layui.contextMenu;
            if (!contextMenu) return; // contextMenu模块可能会不存在
            $(tabDOM + '>.layui-tab-title').off('contextmenu.tab').on('contextmenu.tab', 'li', function (e) {
                var layId = $(this).attr('lay-id');
                contextMenu.show([{
                    icon: 'layui-icon layui-icon-refresh',
                    name: '刷新当前',
                    click: function () {
                        element.tabChange(tabFilter, layId);
                        var autoRefresh = $(tabDOM).attr('lay-autoRefresh');
                        if (!autoRefresh || autoRefresh !== 'true') {
                            admin.refresh(layId);
                        }
                    }
                }, {
                    icon: 'layui-icon layui-icon-close-fill ctx-ic-lg',
                    name: '关闭当前',
                    click: function () {
                        admin.closeThisTabs(layId);
                    }
                }, {
                    icon: 'layui-icon layui-icon-unlink',
                    name: '关闭其他',
                    click: function () {
                        admin.closeOtherTabs(layId);
                    }
                }, {
                    icon: 'layui-icon layui-icon-close ctx-ic-lg',
                    name: '关闭全部',
                    click: function () {
                        admin.closeAllTabs();
                    }
                }], e.clientX, e.clientY);
                return false;
            });
        });
    }

    exports('index', index);
});
