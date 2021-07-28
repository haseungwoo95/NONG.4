package com.spring.nong4.board;

import com.spring.nong4.board.model.*;
import com.spring.nong4.cmt.BoardCmtMapper;
import com.spring.nong4.cmt.model.BoardCmtDomain;
import com.spring.nong4.security.IAuthenticationFacade;
import com.spring.nong4.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired private BoardMapper mapper;
    @Autowired private BoardCmtMapper cmtMapper;
    @Autowired private IAuthenticationFacade auth;

    // 이메일 인증 처리
    public int auth(UserEntity param) {
        return mapper.auth(param);
    }

    public int freeBoard(BoardEntity param) {
        String chkTitle = param.getTitle();
        String chkCtnt  = param.getCtnt();

        System.out.println("ttitle : " + param.getTitle());
        if(chkTitle == "" && chkCtnt == "" && chkTitle == null && chkCtnt == null) {
            return 0;
        }
        return mapper.freeBoard(param);
    }

    public List<BoardEntity> freeBoardList() {
        return mapper.freeBoardList();
    }

    public int friendWrite(BoardDomain param) {
        return mapper.friendWrite(param);
    }

    public Map<String,Object> friendList(BoardDomain param, SearchCriteria scri) {
        param.setIuser(auth.getLoginUserPk());

        Map<String,Object> map = new HashMap<>();

        int total = mapper.countBoardList(param);

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(scri);
        pageMaker.setTotalCount(total);

        map.put("pageMaker",pageMaker);
        map.put("list",mapper.friendList(param,scri));

        return map;
    }

    public Map<String,Object> boardDetail(BoardDomain param, BoardCmtDomain cmtParam) {

        Map<String,Object> map = new HashMap<>();

        map.put("detail", mapper.boardDetail(param));
        map.put("cmt", cmtMapper.cmtList(cmtParam));
        return map;
    }

    public int insCmt(BoardCmtDomain param){
        param.setIuser(auth.getLoginUserPk());
        return cmtMapper.insCmt(param);
    }
}
