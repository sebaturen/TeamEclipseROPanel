<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 03/07/2020
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<!-- GUILD CONTROLLER-->
<%@ page import ="com.eclipse.panel.viewController.GuildController" %>
<%@ page import ="com.eclipse.panel.gameObject.Guild" %>
<%@ page import ="java.util.List" %>
<c:if test="${empty param.id}">
    <c:redirect url="index.jsp"/>
</c:if>
<c:set var="guilds" scope="request" value="${GuildController.getGuildList()}"/>
<c:set var="guild" scope="request" value="${GuildController.getGuild(param.id)}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
<head>
    <title>Guild - ${guild.name}</title>
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
        <select onchange="urlHandler(this.value)">
            <c:forEach items="${guilds}" var="gDet">
                <option value="${gDet.id}" <c:if test="${gDet.id == guild.id}">selected</c:if>>
                    [${gDet.id}] ${gDet.name}
                </option>
            </c:forEach>
        </select>
        <div class="row guild_logoName divder">
            <div class="col col-md-6 align-self-center">
                <img src="assets/img/ro/guilds/emblems/Poring_${guild.id}_${guild.emblem_id}.png"/>
                <p class='home_name'>${guild.name}</p>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <p>Recaller:</p>
                <div class="pj_info char_${guild.recaller.id}">
                    <div class="hair_show hair_show_stand hair_show_${guild.recaller.sex}_${guild.recaller.hairStyle}" style="background: transparent url('assets/img/ro/hair/${guild.recaller.sex}/${guild.recaller.hairStyle}.png')"></div>
                    <div class="job_show job_show_stand job_show_${guild.recaller.sex}_${guild.recaller.job_id}" style="background: transparent url('assets/img/ro/jobs/${guild.recaller.sex}/${guild.recaller.job_id}.png')"></div>
                    <p class="pj_name">${guild.recaller.name}</p>
                </div>
            </div>
            <div class="w-100"></div>
            <div class="col">
                <p>All members:</p>
            </div>
            <div class="w-100"></div>
            <c:forEach items="${guild.characterList}" var="pj" varStatus="loop">
                <div class="pj_info col char_${pj.id}">
                    <div class="hair_show hair_show_stand hair_show_${pj.sex}_${pj.hairStyle}" style="background: transparent url('assets/img/ro/hair/${pj.sex}/${pj.hairStyle}.png')"></div>
                    <div class="job_show job_show_stand job_show_${pj.sex}_${pj.job_id}" style="background: transparent url('assets/img/ro/jobs/${pj.sex}/${pj.job_id}.png')"></div>
                    <p class="pj_name">${pj.name}</p>
                    <p class="pj_lvl">Lvl ${pj.lvl}</p>
                </div>
                <c:if test="${(loop.index+1)%3 == 0}">
                    <div class="w-100"></div>
                </c:if>
            </c:forEach>
        </div>
    </div>
</div>
<%@include file="includes/footer.jsp" %>
</body>
</html>
