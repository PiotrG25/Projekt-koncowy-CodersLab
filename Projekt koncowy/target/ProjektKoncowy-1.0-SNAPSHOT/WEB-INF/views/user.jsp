<%@ page import="pl.coderslab.entity.User" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${user.name}</title>
    <link rel="stylesheet" type="text/css" href="../../css/user.css"/>
    <script src="../../js/jQuery3.3.1.js"></script>
    <script src="../../js/top10Table.js"></script>
</head>
<body>
    <c:import url="header.jsp"/>
    Witaj ${user.name}!<br/>

    <h3>Statysyki</h3>
    <ul>
        <li>Wygrane: ${winCount}</li>
        <li>Łącznie ruchów: ${movesCount}</li>
        <li>Łączny czas gry: ${timeCount}</li>
    </ul>

    <div id="redZone">
        <a href="/editEmail">Edytuj email</a><br/>
        <a href="/editPassword">Edytuj hasło</a><br/>
        <a href="/deleteUser">Usuń konto</a><br/>
    </div><br/>



    <div class="tabDiv">
        <table>
            <tr>
                <td id="top10Level" data-level="${top10Level}">
                    <c:if test="${top10Level == 1}">Latwy</c:if>
                    <c:if test="${top10Level == 2}">Sredni</c:if>
                    <c:if test="${top10Level == 3}">Trudny</c:if>
                </td>
                <td id="top10Moves">ruchy
                    <c:if test="${greenMoves}"><i class="green icon-up-big"></i></c:if>
                    <c:if test="${redMoves}"><i class="red icon-down-big"></i></c:if>
                </td>
                <td id="top10Time">czas
                    <c:if test="${greenTime}"><i class="green icon-up-big"></i></c:if>
                    <c:if test="${redTime}"><i class="red icon-down-big"></i></c:if>
                </td>
            </tr>
            <c:forEach items="${top10}" var="game" varStatus="i">
                <tr>
                    <td>${i.count}</td>
                    <td>${game.moves}</td>
                    <td>${game.time}</td>
                </tr>
            </c:forEach>
        </table>
    </div>

</body>
</html>
