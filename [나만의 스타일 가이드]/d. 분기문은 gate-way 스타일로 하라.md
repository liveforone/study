# 더좋은 분기문을 위한 Gate Way Style 가이드
> 일반적으로 null-check 또는 작성자 check, 중복 check 같은것에 if문이 많이 쓰인다.

## 1. if else 문의 문제점
* 기존의 if-else문은 가독성이 상당히 나쁘다. 
* 특이 다중 if문이 될경우 if(조건1) -> if(조건2)..하며 깊은 수렁으로 빠진다.
* 가독성이 좋지 않는다면 어플리케이션을 확장하거나 수정시에 많은 문제가 발생한다.

## 2. 이전 코드
```
if (boardEntity != null) {

    if (Objects.equals(boardEntity.getUsers().getEmail(), principal.getName())) {
        BoardResponse board = boardService.entityToDtoDetail(boardEntity);

        return ResponseEntity.ok(board);
    } else {
        return ResponseEntity.ok("게시글 작성자와 회원님이 달라 수정이 불가능합니다.");
    }

} else {
    return ResponseEntity.ok("게시글이 없어 수정이 불가능합니다.");
}
```

## 3. 이전 코드 분석
* 총 2개의 조건이 있다.
* 첫째 : boardEntity가 null인가?
* 둘째 : 작성자의 이메일과 현재 유저의 이메일이 같은가?
* 이러한 스타일의 코드를 버블 스타일 이라고한다.

## 4. Bubble Style
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

## 5. 버블 스타일의 문제점
* 흐름 파악이 어려워진다. -> 가독성을 심각하게 저하시킴.
* 어떤 코드가 언제 실행될지 알 수 없게 만든다.

## 6. Gate Way Style
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

## 7. Gate Way 이점
* 조건이 아닌것을 판단하고 조건에 안맞는다면 일찌감치 종료시킨다.
* 모든 게이트가 통과되면 중요한코드가 중첩되지 않은 상태로 한눈에 보인다.
* 모든 게이트를 통과하고 마지막에 참인 실행값을 넣음으로써 흐름파악이 쉬워지고 가독성이 향상된다.
* 이러한 gate way 스타일을 early return 이라고 하기도한다.(이 표현이 더 유명한 듯 하다)

## 8. 개선 코드
```
if (boardEntity == null) {
    return ResponseEntity.ok("게시글이 존재하지않아 조회가 불가능합니다.");
}

if (!Objects.equals(boardEntity.getUsers().getEmail(), principal.getName())) {
    return ResponseEntity.ok("게시글 작성자와 회원님이 달라 수정이 불가능합니다.");
}

return ResponseEntity.ok(boardService.entityToDtoDetail(boardEntity));
```

## 9. 조심할점
* 이러한 형식의 코드는 딱 한가지 단점이 있다.
* 바로 순서를 잘 따져야하는것이다.
* 순서를 잘못잡으면 완전히 실패할 수 있다.
* 논리적으로 순서를 잘 그리고 작성하도록하자.

## 10. 실제 프로젝트에 적용하며 느낀점
* 해당 코드로 리팩토링 작업을 하고 나서 정말 가독성이 엄청 향상됬다.
* 또한 코드 전체에서 흐름을 파악하기 쉬워졌다.
* 어떤 조건을 거치는지 파악하기 쉽고 참인 조건이 무엇인지, 중요한 코드가 무엇인지 알기 쉬워졌다.
* 수많은 조건이 달려도 흐름과 가독성을 모두 가져가서 이러한 코드 스타일을 앞으로 반드시 사용할것이다.

## 11. 최종 - 리스트와 문자열의 Null 체크
```
public static boolean isNull(Object obj) {

        //일반 객체 체크
        if(obj == null) {
            return true;
        }

        //문자열 체크
        if ((obj instanceof String) && (((String)obj).trim().length() == 0)) {
            return true;
        }

        //리스트 체크
        if (obj instanceof List) {
            return ((List<?>)obj).isEmpty();
        }

        return false;
}
```

## 12. 참고 링크
* [참고링크](https://wpshout.com/unconditionally-refactoring-nested-statements-cleaner-code/)
