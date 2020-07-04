<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 03/07/2020
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<%@ page import ="com.blizzardPanel.gameObject.guilds.GuildActivity" %>
<%@ page import ="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
    <title>${guild.name}</title>
    <%@include file="includes/header.jsp" %>
    <link type="text/css" rel="stylesheet" href="assets/css/index.css">
</head>
<body>
<%@include file="includes/menu.jsp" %>
<div class="container fill">
    <div id="welcome">
        <div class="row guild_logoName divder">
            <div class="col-md-3 d-none d-md-block log_artofwar">
                <img src="assets/img/eclipse_ro_panel.png"/>
            </div>
            <div class="col col-md-6 align-self-center">
                <p class='home_name warcraft_font'>${guild.name}</p>
            </div>
            <div class="col-md-3 d-none d-md-block log_artofwar">
                <img class='flipImg' src="assets/img/eclipse_ro_panel.png"/>
            </div>
        </div>
        <div class="row">
            contenido 1234
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp" %>
</body>
</html>
