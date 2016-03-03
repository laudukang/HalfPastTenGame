<%--
  Created by IntelliJ IDEA.
  User: laudukang
  Date: 2016/3/3
  Time: 23:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta charset="UTF-8">
    <title>
        404 Not Found
    </title>
    <meta name="renderer" content="webkit">
    <meta name="viewport" content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1">
    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <meta http-equiv="Cache-Control" content="no-siteapp">
    <meta http-equiv="Cache-Control" content="no-transform">
    <style>
        @font-face {
            font-family: 'error';
            src: url('./font/404.eot');
            src: url('./font/404.eot') format('embedded-opentype'), url('./font/404.woff') format('woff'), url('./font/404.ttf') format('truetype'), url('./font/404.svg') format('svg')
        }

        body {
            font-family: 'Source Sans Pro', 'Helvetica Neue', 'Luxi Sans', 'DejaVu Sans', Tahoma, 'Hiragino Sans GB',
            'Microsoft Yahei', sans-serif;
            overflow: hidden;
            padding: 0;
            color: #555
        }

        div, p, a {
            margin: 0;
            padding: 0
        }

        .wrapper {
            position: absolute;
            display: table;
            width: 100%;
            height: 100%
        }

        .container {
            display: table-cell;
            text-align: center;
            vertical-align: middle
        }

        .error-wrap {
            max-width: 750px;
            margin: 0 auto
        }

        .icon:after {
            font-family: error;
            font-size: 200px;
            content: '\e600'
        }

        .error {
            font-size: 32px;
            font-weight: 700;
            line-height: 1.6em;
            margin: 1em 0
        }

        a {
            font-size: 16px;
            display: inline-block;
            padding: .5em 1em;
            text-decoration: none;
            color: #555;
            border: 1px solid #555
        }

        a:hover {
            color: #fff;
            background: #555
        }
    </style>
</head>

<body>
<div class="wrapper">
    <div class="container">
        <div class="error-wrap">
            <div class="icon">
            </div>
            <p class="error">
                迷失
                <br>
                是为了下一次的相遇
            </p>
            <a href="http://poker.laudukang.me">
                返回首页
            </a>
        </div>
    </div>
</div>
</body>

</html>
