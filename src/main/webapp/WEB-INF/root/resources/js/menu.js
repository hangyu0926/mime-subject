// 全局菜单事件
function Menu(){
    return {
        init: function(){// 菜单初始化
            var menuIndex = 2;// 默认执行点击项目查看
            if(!!sessionStorage.getItem("xypm-menuIndex")){
                menuIndex = parseInt(sessionStorage.getItem("xypm-menuIndex"));
            }
            $('[menu-index=' + menuIndex + ']').click();
            return this;
        },
        changeMenu: function(menuIndex){// javascript切换菜单
            $('[menu-index=' + menuIndex + ']').click();
            return this;
        },
        bindEvent: function(){// 绑定菜单事件
            var menu = this;
            $('#indexMenu').find('dl').find('dt').find('span').click(function(event){
                if($(this).hasClass('xy-index-menu-checked')){
                    return;
                }
                $('#indexMenu').find('dl').find('dt').find('span.xy-index-menu-checked').removeClass('xy-index-menu-checked');
                $(this).addClass('xy-index-menu-checked');
                $('#projectQueryDetail').hide();
                // 记录当前点击菜单
                sessionStorage.setItem("xypm-menuIndex", $(this).attr('menu-index'));
                // 执行右侧刷新事件
                menu.refreshContent($(this).attr('menu-index'));
                // 阻止事件冒泡
                event.stopPropagation();
            })
            $('#indexMenu').unbind('click').click(function(e){
                e.stopPropagation();
            })
            $(document).click(function(){
                if($('#indexMenu').hasClass('show-xy-index-menu')){
                    $('#indexMenuBtn').removeClass('xy-index-menu-btn-show');
                    $('#indexMenu').removeClass('show-xy-index-menu');
                    $('#indexHeader').removeClass('xy-index-menu-hasChecked').find('.xy-index-body-header-logo').removeClass('xy-index-body-header-logo-checked');
                }
            })
            return menu;
        },
        rightEventsRoutes: {// 右侧事件映射关系
            '1': function(){// 项目录入
                $('body').removeClass('body-background');
                Project.getInstance().projectInputInit();
            },
            '2': function(){// 项目查看
                $('body').addClass('body-background');
                if(!window.projectQueryObject){
                    window.projectQueryObject = {};
                }
                window.projectQueryObject['datagrid'] = Project.getInstance().projectQueryInit();
            },
            '3': function(){// 账户录入
                $('body').addClass('body-background');
                Account.getInstance().accountInputInit();
            },
            '4': function(){// 账户查看
                $('body').addClass('body-background');
                if(!window.accountQueryObject){
                    window.accountQueryObject = {};
                }
                window.accountQueryObject['datagrid'] = Account.getInstance().accountQueryInit();
            }
        },
        refreshContent: function(index){// 点击菜单项执行右侧刷新
            var menu = this;
            menu.rightEventsRoutes[index]();
            return menu;
        }
    };
}

$(function(){
    window.global_menu = new Menu();
})