<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 03/07/2020
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ page import ="java.util.Date" %>
<!-- GENERAL CONTROLLER -->
<%@ page import ="com.eclipse.panel.User" %>
<jsp:useBean id="user" class="com.eclipse.panel.User" scope="session" />
<jsp:useBean id="dateObject" class="java.util.Date" />
<!-- Language locale -->
<c:if test="${empty locale}">
    <c:set var="locale" value="en_US" />
    <c:if test="${cookie['locale'].value != null}">
        <c:set var="locale" value="${cookie['locale'].value}"/>
    </c:if>
    <c:if test="${cookie['locale'] == null}">
        <%
            Cookie newLocale = new Cookie("locale", "en_US");
            response.addCookie(newLocale);
        %>
    </c:if>
</c:if>
<fmt:setLocale value="${locale}" />
<fmt:setBundle basename="messages" />
<%
    // Setting if user is a guild member, or blizzardPanel is setting all information is public.
    boolean guildMember = false;
    if (user != null && user.isIs_guild_member()) {
        guildMember = true;
    }
%>