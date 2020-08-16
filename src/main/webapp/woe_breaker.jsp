<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 26/07/2020
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<%@ page import ="com.eclipse.panel.viewController.GuildController" %>
<%@ page import ="com.eclipse.panel.gameObject.woe.CastleBreaker" %>
<%@ page import ="com.eclipse.panel.gameObject.woe.Castle" %>
<%@ page import ="java.util.List" %>
<html>
    <head>
        <title>WoE Cast Breaker TimeLine</title>
        <%@include file="includes/header.jsp" %>
        <link rel="stylesheet" href="assets/css/woe_breaker.css">
        <script src="assets/js/woe_breaker.js"></script>
    </head>
    <body>
        <%@include file="includes/menu.jsp" %>
        <div class="container fill">
            <div id="last_time">
                <button id="20200725" type="button" class="btn btn-primary">2020/07/25</button>
                <button id="20200801" type="button" class="btn btn-primary">2020/08/01</button>
                <button id="last_woe" type="button" class="btn btn-primary">Last WoE (2020/08/08)</button>
                <button id="today_woe" type="button" class="btn btn-primary">Today WoE (2020/08/15)</button>
            </div>
            <div id="loading">
                Loading... :eyes
            </div>
            <div id="break_info">
                <div id="prt_gld"></div>
                <div id="gef_fild13"></div>
                <div id="pay_gld"></div>
                <div id="alde_gld"></div>
            </div>
        </div>
        <script></script>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>