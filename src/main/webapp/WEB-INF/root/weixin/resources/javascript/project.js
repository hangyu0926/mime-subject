var total_height = document.getElementsByClassName('xy-projecterCon-projectIntroduce-con').scrollHeight; //文章总高度
    var show_height = 95; //定义原始显示高度
    if (total_height > show_height) {
        $(".xy-projecterCon-projectIntroduce-showOrHide>span").css("display", "block");
    };
    $(".xy-projecterCon-projectIntroduce-showOrHide>span").click(function() {
            $(".xy-projecterCon-projectIntroduce>p").height(total_height);
            btn.style.display = 'none';
    });