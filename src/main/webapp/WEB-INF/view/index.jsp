<%--
  Created by IntelliJ IDEA.
  User: laudukang
  Date: 2016/3/2
  Time: 16:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url value="" var="home"></c:url>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>10点半</title>
    <style>
        body, div, h1, h2, p {
            margin: 0;
            padding: 0;
        }

        html, body {
            height: 100%;
        }

        body {
            position: relative;
            background: #eee;
            font: 16px 'Microsoft YaHei', '宋体';
            color: #333;
        }

        .container {
            width: 700px;
            position: absolute;
            left: 50%;
            top: 50px;
            bottom: 50px;
            padding: 30px 50px;
            margin-left: -400px;
            border: 1px solid #ccc;
            border-radius: 10px;
            box-shadow: 0px 0px 10px 2px #ccc;
        }

        .header {
            text-align: center;
            font-size: 24px;
            height: 60px;
            line-height: 60px;
        }

        .main p {
            margin: 20px 30px;
            line-height: 1.5;
        }

        .footer {
            float: right;
            color: #000;
            font-size: 20px;
        }

        .footer select {
            height: 24px;
            width: 50px;
        }

        .footer input[type='submit'] {
            padding: 0 15px;
            margin-left: 5px;
            line-height: 24px;
            background: #099;
            text-align: center;
            color: #fff;
            border: none;
            border-radius: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <h1 class="header">10点半游戏</h1>
    <div class="main">
        <h2>游戏规则:</h2>
        <p>其中一人为庄家<br/>
            派牌前，各闲家向庄家下注。<br/>
            每人获派一张牌。<br/>
            闲家打开牌，庄家轮流问各闲家是否“要”加牌。<br/>
            A,2,3,4,5,6,7,8,9,10　分别为一至十点。J,Q,K为半点。<br/>
            闲家可以一路加牌，但若超过十点半，即“爆煲”(Busted)，输给庄家。<br/>
            闲家倘若为一只10加一只 J,Q或K 成十点半，庄家立赔。<br/>
            若果闲家共有五张牌而仍未“爆煲”，称“五龙”，庄家立赔。<br/>
            庄家和闲家都10点半闲家要赔<br/>
        </p>
        <p>
            最后庄家派牌给自己。<br/>
            倘若庄家爆煲，只需赔给仍未爆的闲家。<br/>
            倘若庄家没有爆煲，跟闲家比点数。倘若点数相同，庄家胜，称“食夹棍”<br/>
        </p>
    </div>
    <form class="footer" action="${ home }game">
        请选择玩家人数:
        <select name="number">
            <option value="1">2</option>
            <option value="2">3</option>
        </select>
        <input type="submit" value="开始游戏"/>
    </form>

</div>
</body>
</html>
