# Null체크와 중복체크

## 왜 필요한가?
* post맨으로 원하는 값들을 넣어보며 여러 예외 테스트를 하다보면
* NPE, 즉 널포인터예외가 뜨는 경우가 있다.
* 이는 null이 반환 되었기에 뜨는 에러다.
* 중복은 NPE처럼 대놓고 에러라 하지 않는다.
* 북마크나 팔로잉 처럼 중복이 불가능한 경우에 조용히 숨죽여 논리를 박살낸다.
* 원래 개발자의 논리에 의도대로가 아니라 그 논리를 박살내는 방식으로 움직인다.

## 어떻게 해결할 것인가? - NPE
* 스프링 시큐리티를 사용하면 url에 접근 권한을 걸 수 있다.
* Principal, 즉 현재 유저를 가져오는 모든 url은 시큐리티에 권한을 걸어 NPE를 막는다.
* 유저의 경우 이렇게 해결하면 되지만 객체는 그렇지 못하다
* id나 무언가를 프론트로 부터 받았다면 객체를 가져오는 함수를 호출하여 if문으로 null인지 판별하여주면된다.

### NPE 예시
```
Board boardEntity = boardService.getBoardEntity(id);

if (boardEntity == null) {
    return ResponseEntity.ok("해당 게시글이 존재하지 않아 조회가 불가능합니다.");
}
```

## 어떻게 해결할 것인가? - 중복
* 중복의 경우에는 분명히 a와 b의 값이 들어간다면 전체 db중에 a와 b가 같이 들어가 있는경우가 딱 한번 존재할것이다
* where and 쿼리로 객체 한개를 조회해서
* 존재한다면(null이 아니라면) 중복이고
* 존재하지않는다면(null이라면) 중복이 아닌것으로 판별하여 진행한다.

### 중복 예시
```
Follow follow = followService.getFollowDetail(principal.getName(), nickname);

if (follow != null) {  //팔로우 중복 check
    return ResponseEntity.ok("이미 팔로우 되어있습니다.");
}
```

## 이것으론 부족하다 함수로 처리하자!! - 최종
* 위와 같은 if(obj == null) {...} 방식은 지저분하고 계속 반복된다. 
* 모든 클래스에서 전역으로 사용할 수 있는 함수를 만들어 처리하면 더욱 간편해진다.
* Null 인경우 true를 리턴하고, 아닐경우 false 를 리턴한다.

### 전역 널체크 처리 함수 예시
#### CommonUtils
```
public static boolean isNull(Object obj) {

        //== 일반 객체 체크 ==//
        if(obj == null) {
            return true;
        }

        //== 문자열 체크 ==//
        if ((obj instanceof String) && (((String)obj).trim().length() == 0)) {
            return true;
        }

        //== 리스트 체크 ==//
        if (obj instanceof List) {
            return ((List<?>)obj).isEmpty();
        }

        return false;
}
```
#### 사용
```
Item item = itemService.getItemDetail(id);

if (CommonUtils.isNull(item)) {
    return ResponseEntity.ok("상품이 없어 조회가 불가능합니다.");
}
```
