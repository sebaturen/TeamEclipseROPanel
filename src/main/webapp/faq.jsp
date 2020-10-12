<%--
  Created by IntelliJ IDEA.
  User: Seba
  Date: 12/10/2020
  Time: 12:05
  To change this template use File | Settings | File Templates.
--%>
<%@include file="includes/globalObject.jsp" %>
<html>
    <head>
        <title>RO Del mal Panel - FAQ</title>
        <%@include file="includes/header.jsp" %>
        <link type="text/css" rel="stylesheet" href="../assets/css/class_info.css">
        <link type="text/css" rel="stylesheet" href="../assets/css/user_panel.css">
    </head>
    <body>
        <%@include file="includes/menu.jsp" %>
        <div class="container fill">
            <div class="character_content">
                <div class="character_header">
                    <p class="job_name">Vinculacion de cuenta</p>
                    <p class="job_totals">?</p>
                </div>
                <div class="row c_character_content">
                    <h3>Como vincular tus cuentas:</h3>
                    <div class="row">
                        <div class="col">
                            Primero, ingresa al panel de tu cuenta logeandote en el menu superior o aca:
                            <a href="<%= redirectUri %>" target="_blank">
                                <button class="btn btn-dark" type="button">
                                    <c:if test="${user.login}">
                                        ${user.nick}
                                    </c:if>
                                    <c:if test="${!user.login}">
                                        <img src="assets/img/icons/Discord-Logo-Color.png" alt="" style="width: 26px;">
                                        <fmt:message key="label.discord_login" />
                                    </c:if>
                                </button>
                            </a>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            Abajo de tus personajes, veras el codigo de vinculacion:
                            <div class="link_info">
                                <div class="col">
                                    <code>
                                        Link code: ----
                                    </code>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br><br>
                    <div class="row">
                        <div class="col">
                            <br>
                            Ahora con este codigo, debes ir al Prontera (spawn kafra abajo) [116, 72] crear un cartel privado y copiar el codigo (no importa que contraseña tenga el chat)<br><br>
                            <img src="assets/img/chat_position.png" width="500px">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <br>
                            * Este proceso no debe tardar mas de 1 minuto, si aun asi no aparece informacion, intentelo de nuevo mas tarde
                            <br>
                            * El codigo cambia cada vez que inicias secion, preocura usar el ultimo siempre para vincular tu nueva cuenta
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>