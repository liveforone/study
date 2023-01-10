# 시간적인 결합을 명시적으로 드러내라

## 문제 : 숨겨진 시간적인 결합
* 순서가 있는 함수를 호출하는 경우가 있다.
* 예를 들어 게시글 수정이 있다.
* 게시글은 파일과 연관이 있다고 할때 게시글을 수정하면 기존의 파일이 남게된다.
* 이 파일을 삭제해야하는데, 이 경우 시간적인 순서가 중요하다.
* 게시글과 연관된 파일삭제 -> 게시글 수정 -> 파일 등록
* 의 순서로 작동해야하기 때문이다.
* 여기서 파일을 등록하고 기존파일을 삭제합시고 게시글 id로 연관관계가 맺어진 파일을 삭제해버리면 게시글에는 파일이 한개도 남지 않게 된다.
```
//정상 순서
fileService.deleteByBoardId(boardId);
boardService.editBoard(boardRequest);
fileService.postFile(uploadFile, boardId);

//잘못된 순서 : 논리 오류
fileService.postFile(uploadFile, boardId);
boardService.editBoard(boardRequest);
fileService.deleteByBoardId(boardId);
```

## 해결법
* 이 경우에는 연결소자를 활용해 함수가 내놓는 결과가 다음함수에
필요한 방식으로 사용한다.
* 함수가 복잡하다고 불평을 할 수도 있다.
* 의도적으로 시간적인 결합을 드러냄으로써 순서가 바뀌어서 생기는 문제를 없애준다.
```
/*
약간의 억지이지만 연결소자를 사용했다.
사실 이 경우에는 더 좋은 방법은 수정 메서드를 만들고 
그안에서 삭제와 등록을 다 하는 것이다.
하지만 대부분의 경우에서는 아래처럼 내놓는 결과를 
다음 함수가 필요로하는 방식이 알맞다.
*/
boardService.editBoard(boardRequest);
Long deleteBoardid = fileService.deleteByBoardId(boardId);
fileService.postFile(uploadFile, deleteBoardId);
```

## 위의 예시가 이해가 안될때 아래 에시를 참조하라.
```
//순서가 있는 메서드
public class MoogDiver {
    Gradient gradient;
    List<Spline> splines;

    public void dive(String reason) {
        saturateGradient();
        reticulateSplines();
        diveForMoog(reason);
    }
}

//연결소자 활용
public class MoogDiver {
    Gradient gradient;
    List<Spline> splines;

    public void dive(String reason) {
        Gradient gradient = satyrateGradient();
        List<Spline> splines = reticulateSplines(gradient);
        diveForMoog(reason);
    }
}
```
