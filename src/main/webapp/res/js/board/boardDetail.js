const cmtFrmElem   = document.querySelector('#cmtFrm');
const cmtListElem  = document.querySelector('#cmtList');
const boardModElem = document.querySelector('#boardMod');
const updParentTElem = document.querySelector('#updParentT');
const updParentCElem = document.querySelector('#updParentC');
const originTitleElem = document.querySelector('#title');
const originCtntElem = document.querySelector('#ctnt');

const setTitle = boardModElem.dataset.title;
const setCtnt  = boardModElem.dataset.ctnt;


function boardUpd() {
    const updSpan    = document.createElement('span');
    const delSpan    = document.createElement('span');
    const updBtn     = document.createElement('button');
    const delBtn     = document.createElement('button');
    const titleDiv   = document.createElement('div');
    const ctntDiv    = document.createElement('div');
    const titleInput = document.createElement('input');
    const ctntInput  = document.createElement('textarea');

    const realBtnU = document.createElement('button');

    updBtn.innerText       = '수정';
    delBtn.innerText       = '삭제';
    realBtnU.innerText     = '진짜수정';
    realBtnU.style.display = 'none';


    updBtn.addEventListener('click',() => {
        cmtFrmElem.style.display      = 'none';
        originTitleElem.style.display = 'none';
        originCtntElem.style.display  = 'none';

        updBtn.style.display   = 'none';
        realBtnU.style.display = 'block'

        titleInput.type  = 'text';

        titleInput.value = setTitle;
        ctntInput.value  = setCtnt;

        titleDiv.append(titleInput);
        ctntDiv .append(ctntInput);

        updParentTElem.append(titleDiv);
        updParentCElem.append(ctntDiv);
        console.log('originT  ! : '+ originTitleElem.innerHTML);
        console.log('titleInput !! : ' + titleInput.value);

        realBtnU.addEventListener('click',()=> {
            const param = {
                iboard:   boardModElem.dataset.iboard,
                provider: boardModElem.dataset.provider,
                title:    titleInput.value,
                ctnt:     ctntInput.value
            };
            console.log(param);

            const init = {
                method: 'PUT',
                body: JSON.stringify(param),
                headers: {
                    'accept' : 'application/json',
                    'content-type' : 'application/json;charset=UTF-8'
                }
            };

            fetch('boardUpdate',init)
                .then(res => res.json())
                .then(myJson => {
                    console.log('json : '+myJson.data)
                    switch (myJson.data) {
                        case 0:
                            alert('오류입니다.');
                            break;
                        case 1:
                            console.log('titleInput real : ' + titleInput.value);
                            cmtFrmElem.style.display      = 'block'; // 댓글 입력 div 활성화
                            originTitleElem.style.display = 'block'; // 디테일 제목 div 활성화
                            originCtntElem.style.display  = 'block'; // 디테일 내용 div 활성화
                            updBtn.style.display   = 'block'; // 원래 수정 버튼 활성화
                            realBtnU.style.display = 'none'   // 수정 trigger에 필요한 버튼 비활성하
                            break;
                    }

                })
        })

    })
    delBtn.addEventListener('click',() => {
        const param = {
            iboard:   boardModElem.dataset.iboard,
            provider: boardModElem.dataset.provider,
            title:    titleInput.value,
            ctnt:     ctntInput.value
        };
        console.log(param);

        const init = {
            method: 'DELETE',
            body: JSON.stringify(param),
            headers: {
                'accept' : 'application/json',
                'content-type' : 'application/json;charset=UTF-8'
            }
        };
        fetch('boardDelete',init)
            .then(res => res.json())
            .then(myJson => {
                if (confirm("정말 삭제하시겠습니까?") == true) {
                    location.href = `/board/friendBoardList?provider=${param.provider}`;
                    myJson.submit();
                } else {   //취소
                    return false;
                }
            })
    })

    updSpan.append(updBtn);
    updSpan.append(realBtnU);
    delSpan.append(delBtn);

    boardModElem.append(updSpan,delSpan);
}
function boardDel() {

}


function enterInsCmt(){
    if(window.event.keyCode == 13){
        insCmt();
    }
}

function insCmt() {
    var cmtVal = cmtFrmElem.cmt.value;
    var param = {
        iboard: cmtListElem.dataset.iboard,
        cmt: cmtVal
    };
    insCmtAjax(param);
}

function insCmtAjax(param) {
    const init = {
        method: 'POST',
        body: JSON.stringify(param),
        headers:{
            'accept' : 'application/json',
            'content-type' : 'application/json;charset=UTF-8'
        }
    };
    fetch('insCmt', init)
        .then(function(res){
            return res.json();
        })
        .then(function(myJson){
            console.log(myJson);
            switch(myJson.result){
                case 0:
                    alert('댓글 등록 실패!');
                    break;
                case 1:
                    cmtFrmElem.cmt.value='';
                    alert('댓글 등록 완료!');
                    cmtListAjax();
                    break;
            }
        })
}

function cmtListAjax() {
    var iboard = cmtListElem.dataset.iboard;

    fetch('cmt/' + iboard)
        .then(function(res){
            return res.json();
        })
        .then(function(myJson) {
            console.log(myJson);

            makeCmtElemList(myJson);
        });
}

function makeCmtElemList(data) {
    cmtListElem.innerHTML = '';

    data.forEach(function (item){
        var cmtListDiv = document.createElement('div');
        var userNickDiv = document.createElement('div');
        var cmtDiv = document.createElement('div');
        var regdtDiv = document.createElement('div');

        cmtListDiv.className = 'cmtlistDiv';
        userNickDiv.className = 'cmtUserNick'
        cmtDiv.className = 'cmt'
        regdtDiv.className = 'cmtRegdt'

        userNickDiv.append(item.userNick);
        cmtDiv.append(item.cmt);
        regdtDiv.append(item.regdt);

        cmtListDiv.append(userNickDiv);
        cmtListDiv.append(cmtDiv);
        cmtListDiv.append(regdtDiv);

        cmtListElem.append(cmtListDiv);
    })
}
boardUpd();
cmtListAjax();