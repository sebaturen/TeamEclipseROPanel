<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 01/10/2020
  Time: 0:24
  To change this template use File | Settings | File Templates.
--%>
<%@include file="../includes/globalObject.jsp" %>
<%
    if (    request.getParameter("login_redirect") == null ||
            !request.getParameter("login_redirect").equals("true") ||
            !user.isLogin()
    ) {
        response.sendRedirect("../login.jsp");
    } else {
%>
<%@ page import ="com.eclipse.panel.viewController.CharacterController" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
    <title>RO Del mal Panel - <fmt:message key="label.login" /></title>
    <%@include file="../includes/header.jsp" %>
    <link type="text/css" rel="stylesheet" href="../assets/css/class_info.css">
    <link type="text/css" rel="stylesheet" href="../assets/css/user_panel.css">
</head>
<body>
<%@include file="../includes/menu.jsp" %>
<div class="container fill">
    <div class="justify-content-md-center">
        <c:forEach items="${user.accounts}" var="ac">
            <div class="character_content">
                <div class="character_header">
                    <p class="job_name">${ac.id}</p>
                    <p class="job_totals">?</p>
                </div>
                <div class="row c_character_content">
                    <c:forEach items="${ac.characters}" var="acChar" varStatus="loop">
                        <c:set var="renderChar" value="${CharacterController.renderCharacter(acChar)}" />
                        <div class="pj_info col char_${acChar.id}">
                            <div class="character_display">
                                <div class="acc_show">
                                    <img src="assets/img/ro/characters/${renderChar[1]}"/>
                                </div>
                                <div class="char_show">
                                    <img src="assets/img/ro/characters/${renderChar[0]}"/>
                                </div>
                            </div>
                            <div class="character_name_lvl">
                                    ${acChar.name}<br>Lvl ${acChar.lvl}
                            </div>
                        </div>
                        <c:if test="${(loop.index+1)%3 == 0}">
                            <div class="w-100"></div>
                        </c:if>
                    </c:forEach>
                </div>
            </div>
        </c:forEach>
        <c:if test="${user.accounts.size() == 0}">
            <div class="row">
                <div class="col">
                    <div class="character_content">
                        <div class="character_header">
                            <p class="job_name">Not accounts found</p>
                            <p class="job_totals">?</p>
                        </div>
                        <div class="row c_character_content">
                            <div class="col">
                                Conoce como vincular tus cuentas en:
                                <br><br>
                                <a href="/faq.jsp"><b>Link account FAQ</b></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
        <div class="row">
            <div class="col">
                <div class="character_content">
                    <div class="character_header">
                        <p class="job_name">Link account</p>
                        <p class="job_totals">?</p>
                    </div>
                    <div class="row c_character_content">
                        <div class="col-7 link_info">
                            <code>
                                Link code: ${user.link_code}
                            </code>
                        </div>
                        <div class="col-3">
                            <a href="/faq.jsp">Link account FAQ</a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<%@include file="../includes/footer.jsp" %>
</body>
</html>
<%}%>