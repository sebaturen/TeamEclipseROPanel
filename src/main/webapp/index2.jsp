<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 26/07/2020
  Time: 0:19
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<%@ page import ="com.eclipse.panel.viewController.CharacterController" %>
<html>
    <head>
        <title>RO Del mal Panel</title>
        <meta property="og:title" content="Home" />
        <%@include file="includes/header.jsp" %>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=500" />
    </head>
    <body>
    <%@include file="includes/menu.jsp" %>
    <div class="container fill">
        <div class="row">
            <div class="col-auto">
                <img src="assets/img/4_M_ROTERT.gif">
            </div>
            <div class="col">
                <div class="row c_character_content">
                    <c:set var="pj" value="${CharacterController.getRandomCharacter()}" />
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
                        <div class="character_name_lvl container">
                            <div class="row">
                                <div class="col-auto">
                                    <div class="guild_emblem">
                                        <c:if test="${pj.guild != null}">
                                            <img src="assets/img/ro/guilds/emblems/Poring_${pj.guild.id}_${pj.guild.emblem_id}.png">
                                        </c:if>
                                    </div>
                                </div>
                                <div class="col">
                                    <div class="character_name">${fn:escapeXml(pj.name)}</div>
                                    <div class="guild_name">
                                        <c:if test="${pj.guild != null}">
                                            ${pj.guild.name}
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <c:if test="${(loop.index+1)%3 == 0}">
                        <div class="w-100"></div>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <%@include file="includes/footer.jsp" %>
    </body>
</html>