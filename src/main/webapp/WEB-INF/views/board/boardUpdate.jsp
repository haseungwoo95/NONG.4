<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<link rel="stylesheet" href="https://unpkg.com/swiper/swiper-bundle.min.css">
<script src="https://unpkg.com/swiper/swiper-bundle.min.js"></script>
<div class="background">
    <div class="background-left"></div>
    <div class="background-center">
        <div class="kategorie">
            <img src="/res/img/main.png">
            <div style="font-size: 50px; font-weight: bold"> Nong 4</div>
            <div><input type="text" class="searchtxt"><i class="fas fa-search pointer"></i></div>
            <div><a href="friendBoard"><button class="writeBtn pointer"><i class="fas fa-pen"></i>글쓰기</button></a></div>
            <div class="kategorieitem1">
                <div class="title">알림판 <i class="fas fa-list"></i></div>
                <div><a href="">공지사항</a></div>
                <div><a href="">이벤트</a></div>
            </div>
            <div class="kategorieitem2">
                <div class="title">커뮤니티 <i class="fas fa-list"></i></div>
                <div><a href="">통합 게시판</a></div>
                <div><a href="friendBoardList?provider=freedom">자유 게시판</a></div>
                <div><a href="friendBoardList?provider=question">질문 게시판</a></div>
                <div><a href="friendBoardList?provider=strategy">공략 게시판</a></div>
                <div><a href="friendBoardList?provider=friend">친구 게시판</a></div>
            </div>
        </div>
        <div class="community">
            <div class="community-top"></div>
            <div class="communityboard">
                <div>
                    <form action="boardUpdate" method="post" enctype="multipart/form-data">
                        <label for="provider"></label>
                        <select id="provider" name="provider" size="1">
                            <option value="freedom">자유게시판</option>
                            <option value="question">질문게시판</option>
                            <option value="strategy">공략게시판</option>
                            <option value="friend">친구게시판</option>
                        </select>
                        <input type="hidden" name="iboard" value="${param.iboard}">
                        <div>
                            <input type="text" name="title" value="${data.title}">
                        </div>
                        <div>
                            <textarea name="ctnt">${data.ctnt}</textarea>
                        </div>
                        <div>
                            <input type="file" name="imgArr" multiple accept="image/*">
                        </div>
                        <div>
                            <input type="submit" value="수정">
                            <input type="reset" value="초기화">
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div class="background-right"></div>
</div>
</body>
</html>