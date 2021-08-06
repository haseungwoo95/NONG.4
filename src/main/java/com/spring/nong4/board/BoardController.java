package com.spring.nong4.board;

import com.spring.nong4.board.model.*;
import com.spring.nong4.cmt.model.BoardCmtDomain;
import com.spring.nong4.security.IAuthenticationFacade;
import com.spring.nong4.user.model.UserEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired private BoardService service;
    @Autowired private IAuthenticationFacade auth;

    @GetMapping("/home")
    public String home() {
        System.out.println("로그인 시도중이다");
        return "board/home";
    }
    @GetMapping("/community")
    public String community(HttpServletResponse response, BoardDomain param, SearchCriteria scri , Model model) {
        UserEntity userParam = new UserEntity();
        Cookie cookie = new Cookie("hit",null);
        cookie.setComment("게시글 조회 확인중");
        System.out.println("경로 ㅣ:"+cookie.getPath());
        System.out.println("조회 작업중 : " + cookie.getComment());
        cookie.setMaxAge(60*60*24);

        response.addCookie(cookie);
        System.out.println("controller 조회수: "+param.getHitCount());

        System.out.println("cookie : "+cookie);

        model.addAllAttributes(service.mainBoardList(param,scri));
        return "board/community";
    }

    @ResponseBody
    @RequestMapping(value = "/community", method = RequestMethod.POST)
    public Map<String,Object> communityPost(BoardDomain param, SearchCriteria scri) {
        System.out.println("comm : " + service.mainBoardList(param,scri));
        return service.mainBoardList(param,scri);
    }
    @GetMapping("/boardWrite")
    public String boardWrite() { return "board/boardWrite"; }

    @PostMapping("/boardWrite")
    public String boardWrite(BoardDomain param, MultipartFile[] imgArr,Model model ) {
        param.setIuser(auth.getLoginUserPk());
        model.addAllAttributes(service.boardWrite(param,imgArr));
        return "redirect:/board/mainBoard?provider=" + param.getProvider();
    }

    @GetMapping("/boardUpdate")
    public String boardUpdate() {
        return "board/boardUpdate";
    }

    @ResponseBody
    @RequestMapping(value = "/boardUpdate", method = RequestMethod.PUT)
    public Map<String,Object> boardUpdate(@RequestBody BoardDomain param, Model model) {
        model.addAllAttributes(service.boardUpdate(param));
        System.out.println("title : "+param.getTitle());
        return service.boardUpdate(param);
    }
    @ResponseBody
    @RequestMapping(value = "/boardDelete",method = RequestMethod.DELETE)
    public Map<String,Object> boardDelete(@RequestBody BoardDomain param, Model model) {
        model.addAllAttributes(service.boardDelete(param));
        System.out.println("title! : "+service.boardDelete(param));
        return service.boardDelete(param);
    }

    @GetMapping("/mainBoard")
    public String mainBoardList(BoardDomain param, SearchCriteria scri, Model model) {
        model.addAllAttributes(service.mainBoardList(param,scri));
        return "board/mainBoard";
    }

    @GetMapping("/boardDetail")
    public String boardDetail(@CookieValue (value="hit", required = false) String cookie, BoardDomain param, BoardImgEntity imgParam, Model model, int hitCount) {
        System.out.println("쿠키 생성 확인 : "+cookie);
        model.addAllAttributes(service.boardDetail(param, imgParam));
        System.out.println("폼 hit : "+hitCount);
        System.out.println("hit : "+param.getHitCount());
        return "board/boardDetail";
    }

    @ResponseBody
    @RequestMapping(value = "/insCmt", method = RequestMethod.POST)
    public Map<String, Integer> insCmt(@RequestBody BoardCmtDomain param){
        int result = service.insCmt(param);

        Map<String, Integer> data = new HashMap<>();
        data.put("result", result);

        return data;
    }

    @ResponseBody
    @RequestMapping("/cmt/{iboard}")
    public List<BoardCmtDomain> cmtList(@PathVariable("iboard") int iboard){
        BoardCmtDomain param = new BoardCmtDomain();
        param.setIboard(iboard);
        return service.cmtList(param);
    }

    //댓글 수정
    @ResponseBody
    @RequestMapping(value = "/updcmt", method = RequestMethod.PUT)
    public Map<String, Integer> updCmt(@RequestBody BoardCmtDomain param) {
        int result = service.updCmt(param);

        Map<String, Integer> data = new HashMap<>();
        data.put("result", result);

        return data;
    }

    //댓글 삭제
    @ResponseBody
    @RequestMapping(value = "/delcmt/{icmt}", method = RequestMethod.DELETE)
    public Map<String, Integer> delCmt(@PathVariable("icmt") int icmt) {
        BoardCmtDomain param = new BoardCmtDomain();
        param.setIcmt(icmt);

        int result = service.delCmt(param);

        Map<String, Integer> data = new HashMap<>();
        data.put("result", result);

        return data;
    }
}
