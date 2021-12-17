<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="junior_back">
    <div><!-- 글 -->
        <div>글 내용 채워야함</div>
        <div><button class="videoBtn">버어어어어튼</button></div>
    </div>
    <div class="levelVideo hidden"><!-- 영상 -->
        <div>영상 1</div>
        <div>영상 2</div>
    </div>
</div>

<div id="videoApi">
    <div>비디오 API</div>
    <c:forEach items="${itemList}" var="itemList">
        <tr>
            <td><c:out value="${itemList.videoImg}" /></td>
            <td>${itemList}</td>
            <td>${itemList.videoOriginInstt}</td>
            <td>${itemList.videoTitme}</td>
        </tr>
    </c:forEach>
</div>
