<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Leaderboard</title>
</head>
<body>
   <div align="center">
        <table border="1" cellpadding="5">
            <caption><h2>Leaderboard</h2></caption>
            <tr>
				<th>Wallet Address</th>
                <th>User Name</th>
                <th>Earned Reward</th>
            </tr>
            <c:forEach var="contestant" items="${contestantRanking}">
                <tr style="text-align:center">
                    <td><c:out value="${contestant.contestant_id}" /></td>
                    <td><c:out value="${contestant.login_id}" /></td>
                    <td><c:out value="${contestant.reward_balance}" /></td>
                </tr>
            </c:forEach>
        </table>
    </div>   
</body>
</html>