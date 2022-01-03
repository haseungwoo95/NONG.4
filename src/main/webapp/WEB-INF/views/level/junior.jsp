<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<div class="background">
    <div class="background-left"></div>
    <div class="background-center">
        <div class="category">
            <a href="/board/community"><img src="/res/img/nong4logo.png">
                <div style="font-size: 50px; margin-bottom: 8px; font-weight: bold"> Nong 4</div></a>

            <div class="search-input">
                <input type="text" class="search-txt">
                <i class="fas fa-search pointer"></i>
            </div>

            <div>
                <button class="writeBtn pointer" onclick="location.href='/board/boardWrite'">
                    <i class="fas fa-pen">글쓰기</i>
                </button>
            </div>

            <div class="cate-event">
                <div class="title">알림판<i class="fas fa-list"></i></div>
                <div><a href="">공지사항</a></div>
                <div><a href="/notice/market">산들장터</a></div>
            </div>

            <div class="cate-board">
                <div class="title">커뮤니티 <i class="fas fa-list"></i></div>
                <div><a href="/board/mainBoard">통합 게시판</a></div>
                <div><a href="/board/mainBoard?provider=freedom">자유 게시판</a></div>
                <div><a href="/board/mainBoard?provider=question">질문 게시판</a></div>
                <div><a href="/board/mainBoard?provider=strategy">공략 게시판</a></div>
                <div><a href="/board/mainBoard?provider=friend">친구 게시판</a></div>
            </div>
        </div>

        <div class="community">
            <div class="community-top"></div>
            <div class="community-board">
                <div id="videoSearch"></div>
                <table>
                    <tr>
                        <th>동영상</th>
                        <th>품목분류</th>
                        <th>주제목</th>
                        <th>짧은 기술동영상 제목</th>
                    </tr>
                    <c:forEach items="${reqDomain.videoItemList}" var="videoList">
                        <tr class="bodyTr pointer" onclick="location.href='${videoList.videoLink}'">
                            <th><img src=${videoList.videoImg}></th>
                            <th>${videoList.stdPrdlstCodeNm}</th>
                            <th>${videoList.sj}</th>
                            <th>${videoList.mvpClipSj}</th>
                        </tr>
                    </c:forEach>
                </table>
                <div id="pageMaker" data-totalcount="${reqDomain.totalCount}" data-pageno="${reqDomain.pageNo}"></div>
            </div>
        </div>
    </div>

    <div class="background-right"></div>
</div>