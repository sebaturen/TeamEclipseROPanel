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
                    <p class="job_name">Account link</p>
                    <p class="job_totals">?</p>
                </div>
                <div class="c_character_content">
                    <h3>How to link your accounts:</h3>
                    <div class="row">
                        <div class="col">
                            <b>Step one:</b> Login in your account from the top menu or use this button:
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
                            Below your characters, you will see the link code:
                            <div class="link_info">
                                <div class="col">
                                    <code>
                                        Link code: ----
                                    </code>
                                </div>
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col">
                            <b>Step Two:</b> Copy the Link code and go to Prontera (Kafra spawn) [116, 72].
                            <br>
                            <b>Step Three:</b> create a Private room (/chat) and paste the link code. You can use any password on the Private room.
                            <img src="assets/img/chat_position.png" width="500px">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col">
                            <br>
                            * This process shouldn't take more than 1 minute. If after that time, the information is still not apearing, please try again later.
                            <br>
                            * The code changes every time you login. Be sure to use the last one generated when linking your account.
                            <br>
                            * PanelDelMal does not get access to your discord password or any personal information. It's only using Discord as an account manager
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <%@include file="includes/footer.jsp" %>
    </body>
</html>