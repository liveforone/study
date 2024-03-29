# 명시적 프로그래밍

## 목차
1. 명시적 프로그래밍이란?
2. 왜 명시적 프로그래밍을 해야하는가?
3. 명시적 프로그래밍의 사용처1 : 양방향 연관관계
4. 명시적 프로그래밍의 사용처2 : Cascade 옵션
5. 명시적 프로그래밍의 사용처3 : 프로시저
6. 결론

## 1. 명시적 프로그래밍이란?
* 코드로 눈에 보이게 하라는 것이다.
* 눈에 보이지않게 코드를 조작하지 말라는 의미이다.
* 코드로써 표현하라.

## 2. 왜 명시적 프로그래밍을 해야하는가?
* 코드를 조작할때 눈에 보이지 않는 다면 어떤 문제가 생길까?
* 코드는 계속 진화하고 변경된다.
* 지속적으로 수정이 이루어지는 코드 생태계에서 눈에 보이지않는 부분을 항상 염두하며 변경해야할까?
* 그리고 그 일이 쉬울까?
* 그렇지 못하다. 따라서 명시지적이지 않고 묵시적인 코드는 문제를 일으키게된다.
* 시스템이 복잡하고 거대해질수록 묵시적인 코드는 찾기 어려워지고 
* 많은 곳에서 문제를 발생시킨다.
* 특히 DB관련 코드들을 묵시적으로 하는 경우가 많은데 이는 정말 위험한 일이다.
* DB는 너무나도 중요하다. 중요한 역할을 담당한다. 이런 DB는 정말 묵시적으로 더욱더욱!! 표현하면안된다.

## 3. 명시적 프로그래밍의 사용처1 : 양방향 연관관계
* Jpa에서는 어노테이션 몇개로 쉽게 양방향 관계를 맺을 수 있다.
* 하지만 이 양방향 연관관계를 무분별하게, 또 아무 이유없이,
* 혹은 편리하다는 이유로 무자비하게 사용해서는 절대로 안된다.
* 양방향 연관관계는 변경에 유연하지 않다.
* 양방향으로 맺어진 두 객체는 서로 영향을 주고받는다.
* 변경시에 관리해야할 부분이 하나에서 두개로 늘어나게 된다.
* 항상 변경이나 확장 등에 반대쪽 관계까지 고려하며 변경해야한다.
* 이러한 문제는 시스템을 아주 복잡하게 만들며, 
* 더욱 거대해지는 어플리케이션에서는 손을 쓸 수 없는 사태까지 만들게된다.
* 양방향 연관관계는 아래와 같이 코드로 풀어날 수 있다.
### 양방향 연관관계를 코드로 풀어내자
* Board와 Comment를 예를 들때 일반적으로 어플리케이션을 두가지 방법으로 클라이언트에게 리턴하도록 만들 수 있다.
* 첫째 : 한 화면에 Board에 Comment가 같이 딸려있는 형태
* 둘째 : Board에서 Comment를 보려면 버튼을 클릭하여 Comment로 넘어가는 형태, 즉 분리된 형태
#### (1) : 같이 붙어있는 형태일때
* Board 컨트롤러에서 CommentService를 호출하여 
* 해당 Board의 id를 외래키고 가지고 있는 Comment들을 List/Page의 형태로 가져온다.
* Map/HashMap을 만들어서 Board와 Comments를 put 해주고 이를 클라이언트로 보내준다.
#### (2) : 분리된 형태일때
* Board는 board만 클라이언트에게 전송하고
* 프론트단에서는 board의 id를 comment의 api에 넣어서 호출한다.
* Comment 컨트롤러에서 api를 받을 때에는 /comment/{boardId} 와 같은 형태로 api를 받아서 
* boardId를 외래키고 가지고 있는 Comments 들을 호출하여 클라이언트에게 보내준다.

## 4. 명시적 프로그래밍의 사용처2 : Cascade 옵션
* 양방향 연관관계에서 이어진다.
* cascade는 아주 간편하게 고아객체를 삭제하는 방법이다.
* 그런데 많은 데이터를 한번에 없애주는 행위는 상당히 위험한 행위이다.
* 그것도 우리가 직접 코드로 표현한것이 아닌
* 자동으로 다중 삭제가 일어난다는 것은 아주 위험하다.
* cascade는 벌크 연산으로 해결할 수 있다.
* 벌크연산은 update와 delete를 지원하는데
* 우리가 코드로 직접 벌크연산을 한다면 눈에 보이는 명시적인 코드가 된다.
* 물론 조금 복잡해지고, 귀찮아질 수 있다.
* 그러나, 데이터를 변경하고 삭제하는 작업은 우리의 손을 떠나서는 안된다.
* 편리함을 주는 기능이 끔찍한 결과를 초래할 수 있고, 복구 할수 없는 결과를 초래할 수 있다는 것을 명시하고 코드를 치자.
## 5. 명시적 프로그래밍의 사용처3 : 프로시저
* 특정한 로직을 처리하기만 하고 결과 값을 반환하지 않는 서브 프로그램을 프로시저라 한다.
* 테이블에서 데이터를 조작하고 그 결과를 다른 테이블에 다시 저장하거나 갱신하는 일을 한다.
* 간단하게 DB의 함수라 생각하면 된다.
* 근데 위의 설명에서 프로시저가 명시적이라는 생각이 드나?
* 프로시저는 묵시적이다.
* 앞서 말했던 것처럼, DB는, 데이터를 조작하는 행위는 반드시 명시적이어야한다.
* 프로시저는 그렇지 않다.
* 따라서 프로시저를 지양하자.
* 코드로 풀어낼 수 있는 일들은 코드로 풀어내자.
* 불편하더라도 우리가 감수할 수 있는 범위내에서 문제를 해결하는 것이 제일 좋은 방법이라 생각한다.
## 6. 명시적 프로그래밍의 사용처4 : 컨트롤러
* 컨트롤러에는 파라미터나 json body를 받는 부분이 존재한다.
* 이때 편리함을 위해서 해당 body나 파라미터의 이름을 생각하며 오로지 변수로만 작성하는 경우가 있는데,
* feign client같은 부분에서 명시하지 않고 변수로 처리하면 에러가 발생하는 일이 있다.
* 이런 부분들도 명시적으로 이름을 작성해줌으로써 가독성을 올리고, 컴퓨터에게 정확하게 명령을 내릴 수 있다.
* ex) : pathvariable, request body, request param 등

## 7. 결론
* 당장의 편리함보다 앞으로의 불편함을 생각하며 코드를 쳐야한다.
* 문제는 반드시 우리가 해결할 수 있는 범위 내에 존재해야한다.
* 명시적으로 나타내자. 좋은 코드는 내가 무엇을 하고 있는지 개발자에게 아주 잘 나타내주는 코드가 제일 좋은 코드이다.
