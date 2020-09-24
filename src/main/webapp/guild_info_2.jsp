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
<%@ page import ="com.eclipse.panel.viewController.CharacterController" %>
<%@ page import ="com.eclipse.panel.gameObject.Guild" %>
<%@ page import ="java.util.List" %>
<c:set var="guilds" scope="request" value="${GuildController.getGuildList()}"/>
<c:if test="${param.id > 0}">
    <c:set var="guild" scope="request" value="${GuildController.getGuild(param.id)}"/>
</c:if>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
        <title>Guild - ${guild.name}</title>
        <meta property="og:title" content="Guild Detail" />
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
                    <option disabled <c:if test="${empty param.id}">selected</c:if>><fmt:message key="label.guilds_list" /></option>
                    <c:forEach items="${guilds}" var="gDet">
                        <option value="${gDet.id}" <c:if test="${gDet.id == guild.id}">selected</c:if>>
                            ${gDet.name} [${gDet.id}]
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${not empty param.id}">
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
                            <c:if test="${not empty guild.recaller}">
                                <c:set var="renderChar" value="${CharacterController.renderCharacter(guild.recaller)}" />
                                <div class="char_show">
                                    <img src="assets/img/ro/characters/${renderChar[0]}" alt="${guild.recaller.name}"/>
                                </div>
                                <div class="acc_show">
                                    <img src="assets/img/ro/characters/${renderChar[1]}" alt="${guild.recaller.name}"/>
                                </div>
                                <p class="pj_name">${guild.recaller.name}</p>
                                <p class="pj_lvl">Lvl ${guild.recaller.lvl}</p>
                            </c:if>
                        </div>
                    </div>
                    <div class="w-100"></div>
                    <div class="col">
                        <p>All members:</p>
                    </div>
                    <div class="w-100"></div>
                    <c:forEach items="${guild.characterList}" var="pj" varStatus="loop">
                        <c:set var="renderChar" value="${CharacterController.renderCharacter(pj)}" />
                        <div class="pj_info col char_${pj.id}">
                            <div class="char_show">
                                <img src="assets/img/ro/characters/${renderChar[0]}" alt="${pj.name}"/>
                            </div>
                            <div class="acc_show">
                                <img src="assets/img/ro/characters/${renderChar[1]}" alt="${pj.name}"/>
                            </div>
                            <p class="pj_name">${pj.name}</p>
                            <p class="pj_lvl">Lvl ${pj.lvl}</p>
                        </div>
                        <c:if test="${(loop.index+1)%3 == 0}">
                            <div class="w-100"></div>
                        </c:if>
                    </c:forEach>
                </div>
                </c:if>
                <c:if test="${empty param.id}">
                    <div>
                        <fmt:message key="label.guild_empty_info" />
                    </div>
                </c:if>
            </div>
        </div>
    <%@include file="includes/footer.jsp" %>
    </body>
</html>
