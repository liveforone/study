# 더좋은 분기문을 위한 Gate Way Style 가이드
> 일반적으로 null-check 또는 작성자 check, 중복 check 같은것에 if문이 많이 쓰인다.

## 목차
1. 일반적인 if-else문의 단점
2. Bubble Style의 분기문 분석
3. Gate-way Style이란?
4. Gate-way Style에서 주의할 점
5. Gate-way Style로 Bubble Style 코드 리팩토링
6. 결론
7. 

## 1. 일반적인 if-else문의 단점
* 기존의 if-else문은 가독성이 상당히 나쁘다. 
* 특이 다중 if문으로 갈수록 여러 조건을 따지며 깊은 수렁으로 빠진다.
* if(조건1) -> if(조건2) ->....
* 무슨 논리를 가졌는지, 무엇을 따지고 있는지, 어떤 상황들이 존재하며 각 상황에서 어떻게 반응하는지 파악하는 것이 너무 힘들다.
* 이런 코드를 Bubble Style 이라고 한다.
* 아래는 버블 스타일의 예시이다.
```
if (조건1일때) {
    if (조건2일때) {
        if (조건 3일때..) {
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
} else {
    return false;
}
```

## 2. Bubble Style의 분기문 분석
* 아래의 예시로 살펴보자.
* 1. boardEntity가 null인가?
* null : 수정이 불가능한 이유를 리턴한다.
* not null : 작성자의 이메일과 현재 유저의 이메일이 같은가?
* 같다 : 게시글을 수정한다.
* 다르다 : 수정이 불가능한 이유를 리턴한다.
* 이러한 스타일의 코드를 버블 스타일 이라고한다.
* 논리가 상당히 꼬여있고, 쉽게 파악하기 어렵다.
* 총 두개의 분기문이 중첩되어있는 것이 이정도이다.
* 만약 3개, 4개...10개가 중첩되어있다면??.. 끔찍해진다.
* 코드가 언제 실행되는지 전혀알 수가 없다.
```
if (boardEntity != null) {

    if (Objects.equals(boardEntity.getUsers().getEmail(), principal.getName())) {
        BoardResponse board = boardService.editBoard(boardRequest);

        return ResponseEntity.ok(board);
    } else {
        return ResponseEntity.ok("게시글 작성자와 회원님이 달라 수정이 불가능합니다.");
    }

} else {
    return ResponseEntity.ok("게시글이 없어 수정이 불가능합니다.");
}
```



## 3. Gate-Way Style이란?
* 아래의 예시를 살펴보자.
* 아주 깔끔한 것을 볼수 있다.
* 각 조건별로 어떤 액션을 하는지 알기 쉽고,
* 어떤 논리로 분기문이 동작하는지 알기 쉽다.
* 즉 gate-way 스타일이란 각 조건별로 끊어버리는 것이다.
* 다시말해 걸러야할 부정적인 조건에 부합한다면 최종적인 목표로 가지 못하도록 게이트에서 막는것이다.
* 이러한 gate way 스타일을 early return 이라고 하기도한다.
```
if (조건1이 아닐때) {
    return false;
}

if (조건 2가 아닐때) {
    return false;
}

if (조건 3이 아닐때) {
    return false;
}

return true;
```

## 4. Gate-way Style에서 주의할 점
* 이러한 형식의 코드는 딱 한가지 단점이 있다.
* 바로 순서를 잘 따져야하는것이다.
* 순서를 잘못잡으면 완전히 실패할 수 있다.
* 다시 말해서 시간적인 순서를 잘 따져 작성하지 않으면 엉뚱한 결과를 낳게 된다.
* 해당 스타일을 사용한다면 반드시 절차적으로, 시간적인 순서를 잘 따져가며 코드를 작성하라.

## 5. Gate-way Style로 Bubble Style 코드 리팩토링
```
if (boardEntity == null) {
    return ResponseEntity.ok("게시글이 존재하지않아 조회가 불가능합니다.");
}

//조건을 잘 살펴보라. !이 붙어 있다. 즉 아니라면~ 이다. 부정의 경우가 조건으로 있다.
if (!Objects.equals(boardEntity.getUsers().getEmail(), principal.getName())) {
    return ResponseEntity.ok("게시글 작성자와 회원님이 달라 수정이 불가능합니다.");
}

Board board = boardService.editBoard(boardRequest);
return ResponseEntity.ok(board);
```

## 6. 결론
* 사실 if와 반복문(for, while)등은 빼놓을 수 없는 필수요소이다.
* 그런데 서비스가 거대해지고, 기능이 많아질수록 조건문은 점점 많아지고 복잡해진다.
* 복잡한 조건을 비교적 간단하게 하나하나씩 표현하는 gate-way 스타일은 보다시피 가독성이 엄청나다.
* 아무리 복잡한 분기문이 오더라도 다 해결해버린다.
* 또한 코드 전체에서 흐름을 파악하기 쉬워졌다.
* 어떤 조건을 거치는지 파악하기 쉽고 참인 조건이 무엇인지, 중요한 코드가 무엇인지 알기 쉬워졌다.
* 수많은 조건이 달려도 흐름과 가독성을 모두 가져가는 이 스타일을 널리 사용하길 바란다.


## 7. 참고 링크
* [참고링크](https://wpshout.com/unconditionally-refactoring-nested-statements-cleaner-code/)
