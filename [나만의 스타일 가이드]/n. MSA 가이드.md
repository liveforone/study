# MSA 가이드
* msa는 서비스간의 통신이 중요하고, 복잡하다.
* 이를 제대로 처리하지 않으면 코드가 스파게티처럼 꼬이게 된다.

## 1. 기본 식별자 위주의 쿼리
* msa에서는 cqrs패턴을 많이 적용한다.
* command를 하더라도, query를 날리더라도 기본 식별자를 만들어 사용하면 서비스들을 통합하여 관리하기 쉬워진다.
* 기본 식별자는 id가 될수도 있고, uuid가 될수도 있다.
* 필자는 주로 uuid를 사용한다. 이메일같이 외부에서 충분히 변경이 가능한 것들을 쓰는것보다 자체적으로 발급한 uuud를 쓰는 것이 더 낫다.
* 절대로 외부(실세계)에 영향을 받는 요소는 식별자로 사용하지 말자.
* a서비스에서 b서비스가 데이터를 가져온다고 가정할때,
* a서비스에서는 id나 uuid등의 db 컬럼내의 값을 식별자로 정해서 이것을 가지고 통신하는 것이다.
* 물론 상황에 따라 꼭 식별자를 가지고 통신을 할 수 없으나,
* 식별자를 정하면 사용자로부터 입력을 통합하여 제한할 수 있고,
* 쿼리 재사용등 많은 장점이 있다. 
* 아래는 예시이다.
```
[User service]
id, uuid, name
기본 식별자 : uuid, uuid 바탕으로 api와 쿼리가 정의됨

[Bank service]
feign client - @("/bank-info/{uuid})
```

## 2. 외부 테이블 식별자
* 하나의 DB를 쓰느경우 fk를 놓고 조인하면되지만,
* 마이크로 서비스는 서비스 별로 DB를 구축했기 때문에
* 다른 DB와 연결해주기 위해서는 식별자가 필요하다.
* 이러한 식별자가 1대1로 매칭될 경우 반드시 unique 제약 조건을 걸어주어야한다.
* 아래 예시에서는 user_id가 바로 외부 테이블 식별자가 된다.
* FK처럼 선언하고 끝나는 것이 아니기에 제약조건을 상세하게 걸어주야한다.
* 단순 컬럼이라 생각 하면안된다. FK처럼 생각해서는 큰 코 다친다.
```
Bank Table
[id][bank_number][user_id]
```

## 3. 외부 제공 컨트롤러
* 외부에 api를 제공하는 컨트롤러들이 많다.
* 외부와 소통하는 방법은 크게 두가지로 api통신과 메세지큐를 사용한 통신이 있다.
* api를 사용하는 측에서는 feign client를 이용해서 사용하지만,
* 제공하는 측에서는 어떠한 방법도, 제약도 없다.
* 그러나 좋은 규칙은 아래와 같다.
* 제공하는 컨트롤러는 따로 디렉토리를 만들어 관리한다.
* 네이밍은 privideControllerToXx(서비스 명), provideUrlToXx, provideParamToXx 로 한다.

## 3. 다른 서비스에서 데이터를 가져올때는 반드시 서킷 브레이커 설정하자
* cqrs에서 q가 되겠다.
* 다른 서비스에서 쿼리를 날려 데이터를 가져올때에는 서킷브레이커를 반드시 걸어준다.
* 서킷 브레이커가 없으면 connection fail시에 아무리 if-null 로 처리하려해도 에러가 발생한다.
* 서킷 브레이커로 서비스간 통신에서 발생하는 모든 예외들에 대한 처리를 해주면 안전하다.
