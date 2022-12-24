<%-- 
    Document   : login
    Created on : 24 дек. 2022 г., 17:41:06
    Author     : ShadowUsurper
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<h1>Авторизация</h1>
<form action="j_security_check" method="POST">
    <div id="loginBox">
        <p><strong>Ваш логин:</strong>
            <input placeholder="Введите логин" type="text" size="20" name="j_username"></p>
        <p><strong>Пароль:</strong>
            <input placeholder="Введите пароль" type="password" size="20" name="j_password"></p>
        <p><input type="submit" value="Авторизоваться"></p>
    </div>
</form>
