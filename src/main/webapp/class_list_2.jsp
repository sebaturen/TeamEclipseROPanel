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
                    <option disabled <c:if test="${empty param.id}">selected</c:if>><fmt:message key="label.classes_list" /></option>
                    <c:forEach items="${jobs}" var="jDet">
                        <option value="${jDet.id}" <c:if test="${jDet.id == param.id}">selected</c:if>>
                            ${jDet.name}
                        </option>
                    </c:forEach>
                </select>
                <c:if test="${not empty param.id}">
                    <div class="row guild_logoName divder">
                        <div class="col col-md-6 align-self-center">
                            <p class='home_name'><fmt:message key="label.total" />: ${characters.size()}</p>
                        </div>
                    </div>
                    <div class="row">
                        <c:forEach items="${characters}" var="pj" varStatus="loop">
                            ${CharacterController.renderCharacter(pj)}
                            <div class="pj_info col char_${pj.id}">
                                <div class="char_show">
                                    <img src="assets/img/ro/characters/char_${pj.job_id}_${pj.getCharacter_view().get("hair_style_id").getAsInt()}_${pj.sexId}_${pj.getCharacter_view().get("clothes_color_id").getAsInt()}_${pj.getCharacter_view().get("hair_color_id").getAsInt()}_0.png" alt="${pj.name}"/>
                                </div>
                                <div class="acc_show">
                                    <img src="assets/img/ro/characters/acc_${pj.job_id}_${pj.sexId}_${pj.getHead_view().get("top_head_view_id").getAsInt()}_${pj.getHead_view().get("mid_head_view_id").getAsInt()}_${pj.getHead_view().get("low_head_view_id").getAsInt()}_0.png" alt="${pj.name}"/>
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
                        <fmt:message key="label.class_empty_info" />
                    </div>
                </c:if>
            </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>