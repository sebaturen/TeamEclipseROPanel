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
        <link type="text/css" rel="stylesheet" href="assets/css/guild_info.css">
        <script>
            function urlHandler(v) {
                window.location.assign('?id='+ v);
            }
        </script>
    </head>
    <body>
    <%@include file="includes/menu.jsp" %>
        <div class="container fill">
            <div class="guild_selector">
                <select onchange="urlHandler(this.value)" class="form-control">
                    <option disabled <c:if test="${empty param.id}">selected</c:if>><fmt:message key="label.guilds_list" /></option>
                    <c:forEach items="${guilds}" var="gDet">
                        <option value="${gDet.id}" <c:if test="${gDet.id == guild.id}">selected</c:if>>
                                ${gDet.name} [${gDet.id}]
                        </option>
                    </c:forEach>
                </select>
            </div>
            <c:if test="${not empty param.id}">
                <div class="guild_content">
                    <div class="guild_header">
                        <img class="guild_emblem_img" src="assets/img/ro/guilds/emblems/Poring_${guild.id}_${guild.emblem_id}.png"/>
                        <p class="guild_name">${guild.name}</p>
                    </div>
                    <div class="row guild_character_content">
                        <div class="col recaller">
                            <div class="guild_group_title">Recaller:</div>
                            <div class="pj_info char_${guild.recaller.id}">
                                <c:if test="${not empty guild.recaller}">
                                    <c:set var="renderChar" value="${CharacterController.renderCharacter(guild.recaller)}" />
                                    <div class="character_display" style="background-image: url('/assets/img/ro/char_bg/${guild.recaller.character_display.get("background_bg").asString}')">
                                        <div class="acc_show">
                                            <img src="assets/img/ro/characters/${renderChar[1]}" alt="${guild.recaller.name}"/>
                                        </div>
                                        <div class="char_show">
                                            <img src="assets/img/ro/characters/${renderChar[0]}" alt="${guild.recaller.name}"/>
                                        </div>
                                        <div class="char_equip">
                                            <c:set var="renderItems" value="${CharacterController.renderItems(guild.recaller)}" />
                                            <c:if test="${renderItems[0].length() > 0}">
                                                <div class="char_shield">
                                                    <img src="assets/img/ro/items/${renderItems[0]}">
                                                </div>
                                            </c:if>
                                            <c:if test="${renderItems[1].length() > 0}">
                                                <div class="char_weapon">
                                                    <img src="assets/img/ro/items/${renderItems[1]}">
                                                </div>
                                            </c:if>
                                        </div>
                                    </div>
                                    <div class="character_name_lvl">
                                            ${fn:escapeXml(guild.recaller.name)}<br>Lvl ${guild.recaller.lvl}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="col">
                            WoE info?
                        </div>
                        <div class="w-100"></div>
                        <div class="col">
                            <div class="guild_group_title">All members:</div>
                        </div>
                        <div class="w-100"></div>
                        <c:forEach items="${guild.characterList}" var="pj" varStatus="loop">
                            <c:set var="renderChar" value="${CharacterController.renderCharacter(pj)}" />
                            <div class="pj_info col char_${pj.id}">
                                <div class="character_display" style="background-image: url('/assets/img/ro/char_bg/${pj.character_display.get("background_bg").asString}')">
                                    <div class="acc_show">
                                        <img src="assets/img/ro/characters/${renderChar[1]}"/>
                                    </div>
                                    <div class="char_show">
                                        <img src="assets/img/ro/characters/${renderChar[0]}"/>
                                    </div>
                                    <div class="char_equip">
                                        <c:set var="renderItems" value="${CharacterController.renderItems(pj)}" />
                                        <c:if test="${renderItems[0].length() > 0}">
                                            <div class="char_shield">
                                                <img src="assets/img/ro/items/${renderItems[0]}">
                                            </div>
                                        </c:if>
                                        <c:if test="${renderItems[1].length() > 0}">
                                            <div class="char_weapon">
                                                <img src="assets/img/ro/items/${renderItems[1]}">
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                <div class="character_name_lvl">
                                        ${fn:escapeXml(pj.name)}<br>Lvl ${pj.lvl}
                                </div>
                            </div>
                            <c:if test="${(loop.index+1)%3 == 0}">
                                <div class="w-100"></div>
                            </c:if>
                        </c:forEach>
                    </div>
                </div>
            </c:if>
            <c:if test="${empty param.id}">
                <div>
                    <fmt:message key="label.guild_empty_info" />
                </div>
            </c:if>
            <div>
                * last 30 days
            </div>
        </div>
    <%@include file="includes/footer.jsp" %>
    </body>
</html>
