package com.spring.nong4.board;

import com.spring.nong4.board.model.*;
import com.spring.nong4.cmt.BoardCmtMapper;
import com.spring.nong4.cmt.model.BoardCmtDomain;
import com.spring.nong4.common.MyFileUtils;
import com.spring.nong4.security.IAuthenticationFacade;
import com.spring.nong4.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BoardService {

    @Autowired private BoardMapper mapper;
    @Autowired private BoardCmtMapper cmtMapper;
    @Autowired private IAuthenticationFacade auth;
    @Autowired private MyFileUtils MyFileUtils;

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

    public Map<String, Object> friendWrite(BoardDomain param, MultipartFile[] imgArr) {
        Map<String, Object> map = new HashMap<>();
        if(imgArr == null && param.getTitle() == null && param.getCtnt() == null) { return null; }

        int write = mapper.friendWrite(param);

        System.out.println("imgArr : "+imgArr);
        System.out.println("getIboard" +param.getIboard());


        // 파일 업로드
        if(param.getIboard() > 0 && imgArr != null && imgArr.length > 0) {
            BoardImgEntity paramImg = new BoardImgEntity();
            paramImg.setIboard(param.getIboard());

            String target = "board/" + param.getIboard();
            for(MultipartFile img : imgArr) {
                String saveFileNm = MyFileUtils.transferTo(img , target);
                System.out.println("NM : "+saveFileNm);
                if(saveFileNm != null) {
                    paramImg.setImg(saveFileNm);
                    System.out.println("IMG"+paramImg);
                    map.put("data",mapper.friendWriteImg(paramImg));
                }
            }
        }
        map.put("data",write);
        return map;
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

    public Map<String,Object> boardDetail(BoardDomain param) {

        Map<String,Object> map = new HashMap<>();

        map.put("detail", mapper.boardDetail(param));
        return map;
    }

    public int insCmt(BoardCmtDomain param){
        param.setIuser(auth.getLoginUserPk());
        return cmtMapper.insCmt(param);
    }

    public List<BoardCmtDomain> cmtList(BoardCmtDomain param){
        return cmtMapper.cmtList(param);
    }
}
