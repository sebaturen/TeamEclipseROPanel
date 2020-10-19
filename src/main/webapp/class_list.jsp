<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 03/07/2020
  Time: 22:08
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<!-- GUILD CONTROLLER-->
<%@ page import ="com.eclipse.panel.viewController.JobsController" %>
<%@ page import ="com.eclipse.panel.gameObject.character.Character" %>
<%@ page import ="com.eclipse.panel.viewController.CharacterController" %>
<%@ page import ="java.util.List" %>
<c:set var="jobs" scope="request" value="${JobsController.getJobList()}"/>
<c:set var="characters" scope="request" value="${JobsController.getAllCharacters(param.id)}"/>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="es">
    <head>
        <title>Jobs...</title>
        <meta property="og:title" content="Jobs lists" />
        <%@include file="includes/header.jsp" %>
        <link type="text/css" rel="stylesheet" href="assets/css/class_info.css">
        <script>
            function urlHandler(v) {
                window.location.assign('?id='+ v);
            }
        </script>
    </head>
    <body>
        <%@include file="includes/menu.jsp" %>
        <div class="container fill">
            <div class="character_selector">
                <select onchange="urlHandler(this.value)" class="form-control">
                    <option disabled <c:if test="${empty param.id}">selected</c:if>><fmt:message key="label.classes_list" /></option>
                    <c:forEach items="${jobs}" var="jDet">
                        <option value="${jDet.id}"
                                <c:if test="${jDet.id == param.id}">
                                    selected
                                    <c:set var="jobName" value="${jDet.name}"/>
                                </c:if>
                        >
                                ${jDet.name}
                        </option>
                    </c:forEach>
                </select>
            </div>
            <c:if test="${not empty param.id}">
                <div class="character_content">
                    <div class="character_header">
                        <p class="job_name">${jobName}</p>
                        <p class="job_totals"><fmt:message key="label.total" />: ${characters.size()}</p>
                    </div>
                    <div class="row c_character_content">
                        <c:forEach items="${characters}" var="pj" varStatus="loop">
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
                                        ${pj.name}<br>Lvl ${pj.lvl}
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
                    <fmt:message key="label.class_empty_info" />
                </div>
            </c:if>
            <div>
                * last 30 days
            </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>
