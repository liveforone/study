# Null체크와 중복체크

## 왜 필요한가?
* request로 원하는 값들을 넣어보며 여러 테스트를 하다보면
* Get 메소드의 경우에 NPE, 즉 널포인터예외가 뜨는 경우가 있다.
* 이는 null이 반환 되었기에 뜨는 에러다.
* 중복은 더 위험하게도 NPE처럼 대놓고 에러라 하지 않는다.
* 북마크나 팔로잉 처럼 중복이 불가능한 경우에도 중복을 체크하지 않는다면 중복된 상태로 조용히 숨죽여 프로그램을 박살낸다.
* 이 두 문제는 프로그램을 멈추게 만드는 큰 요인이 되곤 한다.

## 어떻게 해결할 것인가? - NPE
* 스프링 시큐리티를 사용하면 url에 접근 권한을 걸 수 있다.
* Principal, 즉 현재 유저를 가져오는 모든 url은 시큐리티에 권한을 걸어 NPE를 막는다.
* 로그인을 안한 유저는 접근조차 못하기 때문에, 로그인에 성공한 유저라면 유저객체는 Null값을 갖을 수 없다.
* 유저의 경우 이렇게 해결하면 되지만 객체는 그렇지 못하다
* 클라이언트로부터 파라미터나 db에 조회할 수 있는 값으로 객체를 가져와 if문으로 null인지 판별하여주면된다.

### 예시
```
Board boardEntity = boardService.getBoardEntity(id);

if (boardEntity == null) {
    return ResponseEntity.ok("해당 게시글이 존재하지 않아 조회가 불가능합니다.");
}
```

## 어떻게 해결할 것인가? - 중복
* 중복의 경우에는 a라는 객체가 b라는 객체를 팔로잉한다면 팔로잉 db중에 a와 b가 같이 들어가 있는경우가 딱 한번 존재할것이다
* where 조건1 and 조건2 쿼리로 객체 한개를 조회해서
* 존재한다면(= null이 아니라면) 중복이고
* 존재하지않는다면(= null이라면) 중복이 아닌것으로 판별하여 진행한다.

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
* 이런경우 전역에서 사용할 수 있는 Util 클래스를 만들어 그 안에 아래와 같은 값을 넣는다.
* Null 인경우 true를 리턴하고, 아닐경우 false 를 리턴한다.

### 전역 널체크 처리 함수 예시
#### CommonUtils.java
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
