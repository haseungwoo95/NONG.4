package com.spring.nong4.board;

import com.spring.nong4.api.ApiService;
import com.spring.nong4.api.model.apiVideoDomain;
import com.spring.nong4.api.model.monthFarmTechDomain;
import com.spring.nong4.board.model.*;
import com.spring.nong4.cmt.BoardCmtMapper;
import com.spring.nong4.cmt.model.BoardCmtDomain;
import com.spring.nong4.common.MyFileUtils;
import com.spring.nong4.security.IAuthenticationFacade;
import com.spring.nong4.user.model.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    @Autowired private ApiService apiService;

    // 이메일 인증 처리
    public int auth(UserEntity param) {
        return mapper.auth(param);
    }

    public int boardWrite(BoardDomain param, MultipartFile[] imgArr) {
        int result = 0;

        String chkTitle = param.getTitle().replaceAll(" ", "");
        String chkCtnt  = param.getCtnt().replaceAll(" ", "");

        if(param.getTitle().isEmpty() || chkTitle.equals("")){
            result = 3;
            return result;
        } else if(param.getCtnt().isEmpty() || chkCtnt.equals("")){
            result = 4;
            return result;
        } else {
            result = mapper.boardWrite(param);
            // 파일 업로드
            if (param.getIboard() > 0 && imgArr != null && imgArr.length > 0) {
                BoardImgEntity paramImg = new BoardImgEntity();
                paramImg.setIboard(param.getIboard());

                String target = "board/" + param.getIboard();
                for (MultipartFile img : imgArr) {
                    String saveFileNm = MyFileUtils.transferTo(img, target);
                    if (saveFileNm != null) {
                        paramImg.setImg(saveFileNm);
                        mapper.boardWriteImg(paramImg);
                    }
                }
            }
            return result;
        }
    }

    public List<BoardDomain> newsList(BoardDomain param, SearchCriteria scri){
        return mapper.mainBoardList(param, scri);
    }

    public Map<String, Object> boardUpdate(BoardDomain param) {
        param.setIuser(auth.getLoginUserPk());
        Map<String, Object> map = new HashMap<>();
        map.put("data",mapper.boardUpdate(param));
        return map;
    }

    public Map<String,Object> boardDelete(BoardDomain param) {
        param.setIuser(auth.getLoginUserPk());
        Map<String, Object> map = new HashMap<>();
        map.put("data",mapper.boardDelete(param));
        return map;
    }

    public Map<String,Object> mainBoardList(BoardDomain param, SearchCriteria scri) {
        param.setIuser(auth.getLoginUserPk());
        Map<String,Object> map = new HashMap<>();

        int total = mapper.countBoardList(param);

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(scri);
        pageMaker.setTotalCount(total);

        map.put("pageMaker",pageMaker);
        map.put("list",mapper.mainBoardList(param,scri));

        return map;
    }

    public Map<String,Object> boardDetail(BoardDomain param, BoardImgEntity imgParam) {
        param.setIuser(auth.getLoginUserPk());
        Map<String,Object> map = new HashMap<>();

        map.put("detail", mapper.boardDetail(param));
        map.put("img", mapper.selBoardImgList(imgParam));

        return map;
    }

    public Map<String,Object> boardDetailHit(BoardDomain param) {
        param.setIuser(auth.getLoginUserPk());
        Map<String,Object> map = new HashMap<>();
        map.put("hit", mapper.detailHitCount(param));
        return map;
    }

    public int favProc(BoardDomain param){
        param.setIuser(auth.getLoginUserPk());
        if(param.getIsFav() == 1){
            return mapper.delFav(param);
        }
        return mapper.insFav(param);
    }

    public int insCmt(BoardCmtDomain param){
        param.setIuser(auth.getLoginUserPk());
        return cmtMapper.insCmt(param);
    }

    public List<BoardCmtDomain> cmtList(BoardCmtDomain param){
        return cmtMapper.cmtList(param);
    }

    public int updCmt(BoardCmtDomain param) {
        param.setIuser(auth.getLoginUserPk());
        return cmtMapper.updCmt(param);
    }

    public int delCmt(BoardCmtDomain param){
        param.setIuser(auth.getLoginUserPk());
        return cmtMapper.delCmt(param);
    }

    public Map<String, Object> totalSearch(apiVideoDomain apiVideoDomain, monthFarmTechDomain farmTechDomain, BoardDomain param, SearchCriteria scri) {
        Map<String, Object> map = new HashMap<>();

        int total = mapper.totalSearchCount(param, scri);

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(scri);
        pageMaker.setTotalCount(total);
        pageMaker.setKeyword(scri.getKeyword());


//        map.put("farmTechGet", apiService.monthFarmTech(farmTechDomain, scri));
//        Map farmTechGet = (Map) map.get("farmTechGet");
//        monthFarmTechDomain farmTechDomainMap = (monthFarmTechDomain) farmTechGet.get("farmTechDomain");
//        farmTechDomainMap.setSrchStr(scri.getKeyword());
//
//        map.put("result", apiService.monthFarmTech(farmTechDomainMap, scri));
//        Map result = (Map) map.get("result");
//        System.out.println("RESULT : " + result);
//        monthFarmTechDomain farmTech = (monthFarmTechDomain) result.get("farmTechDomain");
//        System.out.println("KEYWORD_MAP : " + farmTech);
//
//        System.out.println("FARM_3 : " + farmTechDomainMap);

        map.put("pageMaker",pageMaker);
        map.put("video", apiService.apiVideo(apiVideoDomain, scri));
//        map.put("farmTech", farmTech);

        return map;
    }
    public Map<String, Object> searchPaging(BoardDomain param, SearchCriteria scri){
        int total = mapper.totalSearchCount(param, scri);
        Map<String, Object> map = new HashMap<>();

        PageMaker pageMaker = new PageMaker();
        pageMaker.setCri(scri);
        pageMaker.setTotalCount(total);
        map.put("total", mapper.totalSearch(param, scri));
        map.put("pageMaker", pageMaker);

        return map;
    }
}