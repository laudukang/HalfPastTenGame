<%--
  Created by IntelliJ IDEA.
  User: laudukang
  Date: 2016/3/2
  Time: 21:20
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="" var="home"></c:url>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title></title>
    <style>
        body, div, h1, h2, h3, p {
            margin: 0;
            padding: 0;
        }

        a {
            text-decoration: none;
        }

        html, body {
            height: 100%;
        }

        body {
            background: #6bc235;
            font: 16px 'Microsoft YaHei', '宋体';
            color: #fff;
        }

        .header {
            position: relative;
            box-sizing: border-box;
            height: 80px;
            border-bottom: 4px solid #999;
        }

        .title {
            font-size: 40px;
            text-align: center;
            line-height: 76px;
        }

        .github {
            text-align: left;
            position: absolute;
            left: 50px;
            height: 30px;
            top: 23px;
            height: 30px;
            line-height: 30px;
        }

        .btn {
            display: inline-block;
            padding: 0 15px;
            background: #259e80;
            color: #fff;
            border-radius: 4px;
        }

        .btn:hover {
            opacity: .8;
        }

        .btn:active {
            opacity: .6;
        }

        .restart {
            position: absolute;
            right: 50px;
            height: 30px;
            top: 23px;
            height: 30px;
            line-height: 30px;
        }

        #main {
            position: absolute;
            top: 80px;
            left: 0;
            right: 0;
            bottom: 0;
        }

        .container {
            position: relative;
            float: left;
            box-sizing: border-box;
            padding: 30px 50px;
            border-right: 2px solid #999;
            width: 100%;
            height: 100%;
        }

        .containerActive {
            background: #068043;
        }

        .containerActive .cover {
            display: none;
        }

        .container:last-child {
            border: none;
        }

        .container_title {
            font-size: 30px;
        }

        .stage {
            position: relative;
            margin: 20px 30px 200px;
            height: 108px;
        }

        .img {
            position: absolute;
            left: 70%;
            top: 180px;
            transform-origin: center;
            transform: scale(2);
            transition: all 1s linear 0s;
        }

        .img.change {
            transform: scale(1);
            top: 0;
        }

        .result {
            display: none;
            position: absolute;
            left: 50px;
            top: 300px;
        }

        .result h3 {
            margin-bottom: 8px;
        }

        .result p {
            padding-left: 12px;
            margin-bottom: 4px;
        }

        .description {
            font-size: 20px;
            margin-bottom: 20px;
        }

        .description span {
            display: inline-block;
            font-size: 30px;
            color: #99620e;
            margin-left: 10px;
        }

        .container_btn {
            height: 40px;
            line-height: 40px;
            margin-right: 30px;
        }

        .endBtn {
            background: #ff5064;
        }

        .cover {
            position: absolute;
            left: 0;
            top: 0;
            right: 0;
            bottom: 0;
            z-index: 1000;
            background: rgba(0, 0, 0, .5);
        }

        .cover_top {
            position: absolute;
            left: 80px;
            right: 80px;
            top: 90px;
            height: 120px;
            background: #000;
        }

        .cover_bottom {
            position: absolute;
            left: 50px;
            right: 50px;
            top: 400px;
            height: 100px;
            background: #000;
        }


    </style>
</head>
<body>
<c:if test="${ msg != null }">
    <script>
        alert("${ msg }");
        window.location = "/";
    </script>
</c:if>
<c:if test="${ msg == null }">
    <div class="header">
        <a href="https://github.com/laudukang/half-past-ten-game" target="_blank" class="github">Github</a>
        <h1 id="title" class="title">玩家1回合</h1>
        <a class="btn restart" href="/">重新开始</a>
    </div>
    <div id="main">
        <div class="container">
            <h2 class="container_title">庄家:</h2>
            <div class="stage"></div>
            <div class="result">
                <h3>游戏结果：</h3>
            </div>
            <p class="description">当前总点数:<span class="achievement">0</span></p>
            <div class="footer">
                <a class="btn container_btn againBtn" href="javascript:;">取牌</a>
                <a class="btn container_btn endBtn" href="javascript:;">结束回合</a>
            </div>
            <div class="cover">
                <div class="cover_top"></div>
                <div class="cover_bottom"></div>
            </div>
        </div>
    </div>
</c:if>
<script src="js/jquery-1.11.3.min.js"></script>
<script>
    $(function () {
        var playerSum =
        ${ userCount } ||
        0;
        var playerNumber = 0;
        var count = 0;
        var animation = {
            delay: 500,
            duration: 1000,
            increace: 20,
            complete: true
        };

        init();
        $('.container').delegate('.againBtn', 'click', getCard);
        $('.container').delegate('.endBtn', 'click', nextRound);


        function getCard() {
            playerNumber = $(this).data('id') || 0;
            var url = playerNumber == 0 ? '${ home }computerGetCard' : '${ home }userGetCard';
            if (animation.complete) {
                animation.complete = false;
                $.ajax({
                    url: url,
                    data: {'userid': playerNumber - 1},
                    dataType: 'json',
                    success: function (res) {
                        if (res.isGet) {
                            getSqueezer(res);
                        } else {
                            alert(res.msg);
                            animation.complete = true;
                        }
                    },
                });
            }
        }

        function nextRound() {
            if (animation.complete) {
                if (!count) {
                    alert('请先取牌');
                } else {
                    if (playerNumber == 0) {
                        endRound();
                    } else {
                        turnRound();
                    }
                }
            }
        }

        function getSqueezer(res) {
            var elem = $('<img class="img" src="${ home }img/' + res.card.suit + "-" + res.card.number + '.png"/>').appendTo('.stage:eq(' + playerNumber + ')');
            setTimeout(function () {
                elem.addClass('change');
                elem[0].style.left = count++ * animation.increace + 'px';
            }, animation.delay);
            setTimeout(function () {
                $('.container').eq(playerNumber).find('.achievement').html(res.totalPoint);
                animation.complete = true;
            }, animation.delay + animation.duration);
        }

        function turnRound() {
            var next = (playerNumber + 1) % playerSum;
            $('.container').eq(next).addClass('containerActive').siblings().removeClass('containerActive');
            if (next) {
                $('#title').html('玩家' + next + '回合');
            } else {
                $('#title').html('庄家回合');
            }
            count = 0;
        }

        function endRound() {
            $('.container').addClass('containerActive');
            $('#title').html('游戏结束');
            $('.container').undelegate('.againBtn', 'click', getCard);
            $('.container').undelegate('.endBtn', 'click', nextRound);
            $.ajax({
                url: '${ home }winner',
                success: function (res) {
                    $('.result').each(function (i) {
                        var result = '';
                        if (!i) {
                            for (var j = 0; j < playerSum - 1; j++) {
                                result += '<p>' + res['au' + j] + '</p>';
                            }
                        } else {
                            result += '<p>' + res['u' + ( i - 1 )] + '</p>';
                        }
                        $(this).append(result).show();
                    })
                },
            });
        }

        function init() {
            for (var i = 1; i < playerSum; i++) {
                $('.container').eq(0).clone()
                        .find('.container_title').html('玩家' + i).end()
                        .find('.btn').data('id', i).end()
                        .appendTo('#main');
            }
            $('.container').css('width', 100.0 / playerSum + '%').eq(1).addClass('containerActive');
        }
    });
</script>
</body>
</html>