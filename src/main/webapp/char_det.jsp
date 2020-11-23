<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 03/07/2020
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<!-- GUILD CONTROLLER-->
<%@ page import ="com.eclipse.panel.gameObject.character.Character" %>
<%@ page import ="com.eclipse.panel.viewController.CharacterController" %>
<%@ page import ="java.util.List" %>
<c:set var="pj" scope="request" value="${CharacterController.getCharacter(param.id)}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
        <title>Jobs...</title>
        <meta property="og:title" content="Jobs lists" />
        <%@include file="includes/header.jsp" %>
        <link type="text/css" rel="stylesheet" href="assets/css/index.css">
        <script>
            function urlHandler(v) {
                window.location.assign('?id='+ v);
            }
        </script>
    </head>
    <body>
        <%@include file="includes/menu.jsp" %>
        <div class="container fill">
            <div id="welcome">
                <c:if test="${not empty param.id}">
                    <div class="row">
                        <c:set var="renderChar" value="${CharacterController.renderCharacter(pj)}" />
                        <div class="pj_info col char_${pj.id}">
                            <div class="char_show">
                                <img src="assets/img/ro/characters/${renderChar[0]}" alt="${fn:escapeXml(pj.name)}"/>
                            </div>
                            <div class="acc_show">
                                <img src="assets/img/ro/characters/${renderChar[1]}" alt="${fn:escapeXml(pj.name)}"/>
                            </div>
                            <p class="pj_name">${fn:escapeXml(pj.name)}</p>
                            <p class="pj_lvl">Lvl ${pj.lvl}</p>
                        </div>
                    </div>
                </c:if>
                <c:if test="${empty param.id}">
                    <div>
                        <fmt:message key="label.class_empty_info" />
                    </div>
                </c:if>
            </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>
