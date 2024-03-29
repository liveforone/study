# 함수

## 목차
1. 함수는 한 가지 일만 하라.
2. 함수 네이밍
3. bool을 리턴하라.
4. 낭비를 줄여라
5. 인수 네이밍
6. 플래그 인수를 쓰지 말아라
7. 함수 호출 시 인수의 추상화
8. 함수 호출 시 시간적 결합 

## 1. 함수는 한 가지 일만 하라
* 함수는 한가지의 책임만져야한다.
* 다시 말해 함수는 한가지 일만 해야한다.
* 함수가 여러가지 일을 하면 어떻게될까?
* 테스트를 꼭 작성하는 독자라면 한 번쯤 함수가 너무 많은 일을 하거나
* 강합 결합을 가지고 있어서 테스트가 힘들었거나, 불가능했던 일을 경험한적이 있을 것이다(내가 그랬다).
* 이 뿐만인가? 가독성도 좋지 않다. 함수가 도데체 무엇을 하고 있는지 맥락을 파악하기 어려워진다.
* 다만 모든 상황에서 이 규칙을 적용해선 안된다.
* 금융권처럼 돈이 오가는 상황은 조금 다르다.
* 바로 트랜잭션 때문이다.
* 이체를 할때 누군가의 계좌는 '증가'되지만,
* 누구가의 계좌는 '감소'한다.
* 이 일은 단 한번의 트랜잭션에 발생해야지, 서로 다른 트랜잭션에서 발생하게 되면 아주..끔찍한 상황이 발생한다.
* 이 경우에는 어쩔수 없다. 함수는 두가지 일을 해야한다.
* 추가하고, 삭제하고. 이런 특수한 경우를 제외하고는 함수는 한가지 일만 하자.

## 2. 함수의 네이밍
* 함수의 네이밍은 동사를 사용해야한다.
* 스타일 가이드중 네이밍 이란 챕터에서도 설명하고 있지만 
* 함수의 네이밍은 동사를 사용해야한다.
* 게시글을 저장한다면 boardSave()보단 saveBoard()가 더 좋다는 말이다.
* 또한 길더라도 '직관적'이고 '일관'되게 지어야한다.
* 긴 함수 이름을 두려워말자. 하는 일을 명확하게 설명하고 있다면 아주 좋은 이름이다.
* 간혹 부수 효과가 발생하는(변경이 일어나는 함수를 호출하는 행위 등) 상황이 있다.
* 부수효과는 명시해주는것이 좋다.
* 나는 저장만 하려 했는데, 다른 곳을 수정해 버리게되면 내가 예상 결과를 넘어서기 때문이다.
* 코드를 읽는 사람에게 부수효과를 알려주자.
* 함수 네이밍은 주석이라고 생각하라(인수 네이밍도 마찬가지)
```
//게시글을 수정하면 기존의 파일을 날리는 구조라면?
public void editBoardWithdeleteFile() {...}
```

## 3. bool을 리턴하라
* 간혹 true와 false를 정수로(1과0) 표현하는 독자들이 있다.
* sql같은 경우 true, false가 없이 0과 1로 표현하기는 한다.
* 하지만 프로그래밍 언어는 bool대수가 있다. 있는데 왜 쓰지 않는가? bool을 사용하라.
* 0과 1과 같은 매직넘버를 사용하면 이해하기 힘들다.
```
/*
* 이메일 중복 검증
* 반환 값 : false(중복아님), true(중복)
*/
public static boolean isDuplicateEmail(Users users) {

    if (CommonUtils.isNull(users)) {
        return true;  //중복
    }
    return false; //중복아님
}
```

## 4. 낭비를 줄여라.
* 똑똑하지 않은 함수가 있다.
* 바로 낭비하는 함수이다.
* 다른 곳에서 어떤 객체를 조회하고 사용했다.
* 이번 함수에서 객체의 id를 받아 다시 조회하고 객체를 사용한다면 어떨까?
* 정말 멍청한 함수라 할 수 있다.
* 쓸데없는 자원 낭비를 줄여라.
* 다시 그 객체를 인수로써 받으면 된다.
* 이런 낭비는 생각보다 많은 곳에서 발생한다.
* 지금 체크해보라. 독자의 함수는 똑똑한 함수인지 멍청한 함수인지.
```
[멍청한 함수]
public ResponseEntity<?> changeEmail(@ReqeustBody String email) {
    Users users = userRepository.findByEmail(email);

    Boolean checkDuplicate = userService.checkDuplicateEmail(email);
    .....
}

public boolean checkDuplicateEmail(String email) {
    Users users = userRepository.findByEmail(email);
    ....이메일 중복체크 로직..
}

[똑똑한 함수]
public ResponseEntity<?> changeEmail(@ReqeustBody String email) {
    Users users = userRepository.findByEmail(email);

    Boolean checkDuplicate = userService.checkDuplicateEmail(users);

    .....
}

public boolean checkDuplicateEmail(Users users) {
    ....이메일 중복체크 로직..
}
```

## 5. 인수 네이밍
* 함수는 모든 부분이 주석이라고 생각하라 말하였다.
* 함수를 보면 무슨일을 해야하는지, 어떤 값들이 인수로 들어가야하는지 알 수 있어야한다.
* email이 들어가야하는데 name이라고 적어놓으면.... 절대로 안된다.
* 특히나 ide가 발달된 지금은 ide가 들어가야할 인수의 이름을 미리보기로 보여주기때문에
* 이 미리보기를 보고 어떤 값을 넣어주야할지 예상할 수 있다.
* 그러나 여기에 이상한, 혹은 어울리지 않는 인수 네이밍을 했다면
* 어떤 인수를 넣어야할지 헷갈리게 된다.
* 올바른 인수 네이밍을 하라.

## 6. 플래그 인수를 사용하지 말아라
* 플래그 인수는 bool값을 인수로 사용하는 것을 말한다.
* 플래그 인수는 참과 거짓인 두가지 상황에 따라 두가지 일을 처리해야한다.
* 즉 함수가 여러 기능을 수행한다는 증거가된다.
* 함수는 단 한가지 일을 해야한다는 중요한 법칙을 어기게 된다.
* 또한 혼란을 초래할 수 있기에 플래그 인수는 지양하라.

## 7. 함수 호출 시 인수의 추상화
* [가독성](https://github.com/liveforone/study/blob/main/%5B%EB%82%98%EB%A7%8C%EC%9D%98%20%EC%8A%A4%ED%83%80%EC%9D%BC%20%EA%B0%80%EC%9D%B4%EB%93%9C%5D/b.%20%EA%B0%80%EB%8F%85%EC%84%B1.md)부분에서도 함수를 호출하면 추상화 하라고 말한다.
* 함수를 호출하여 매개변수로 사용하지말아라.
* 물론 네이밍을 잘 하였다면 함수가 무슨일을 하는지,
* 무엇을 리턴하는지 알 수 있다.
* 그러나 그것을 이해하는데에는 아무래도 시간이 걸린다.
* 코드를 몇줄 더 치더라도 변수로 추상화하여 사용하자.
* 그것이 더욱 이해하기 쉬울것이다.

## 8. 함수 호출 시 시간적 결합 
* 클래스 안은 그렇게 절차적이지 않을 수 있으나
* 메서드 안은 반드시 절차적이여야한다.
* 시간적인 결합들이 있을때 이해하기 제일 쉬운 방법이 절차적이기 때문
* 즉 시간적 결합을 가장 쉽게 풀어낼 키이다.
* 따라서 함수는 절차적으로 선언하는 것이 가장 좋다.
* 아래는 예시이다.
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
### 번외 : 연결소자로 시간적 결합 풀기
* 클린코드에서는 연결소자를 활용해 시간적 결합을 풀 수 있는 방법을 소개한다.
* 다만 이 방법은 함수가 복잡하다고 불평을 할 수도 있다.
* 의도적으로 시간적인 결합을 드러냄으로써 순서가 바뀌어서 생기는 문제를 없애준다.
* 아래는 예시이다.
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
