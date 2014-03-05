<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Test API project</title>
    </head>

        <form action="/test/simple.htm" method="get">
            <input name="id" value="">
            <input type="hidden" name="action" value="getUser">
            <input type="hidden" name="type" value="1">
            <input type="submit">
        </form>
        <form action="/test/simple.htm" method="get">
            <input name="search" value="">
            <input type="hidden" name="action" value="findUser">
            <input type="hidden" name="type" value="1">
            <input type="submit">
        </form>
        <form action="/test/simple.htm" method="post">
            <input name="email" value="">
            <input name="name" value="">
            <input name="id" value="">
            <input type="hidden" name="action" value="findUser">
            <input type="submit">
        </form>
        <form action="/test/simple.htm" method="post">
            <input name="id" value="">
            <input name="acvieve_id" value="">
            <input type="hidden" name="action" value="setAchieve">
            <input type="submit">
        </form>
    </body>
</html>
