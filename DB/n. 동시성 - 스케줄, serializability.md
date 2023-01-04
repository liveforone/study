# 동시성 - schedule & serializability

## schedule 이란?
* 여러 트랜잭션들이 동시에 실행될 때
* 각 트랜잭션에 속한 operation들의 실행 순서를 의미함.
* 각 트랜잭션 내의 operation 순서는 바뀌지 않는다.

## serial schedule
* 한 번에 하나의 트랜잭션만 실행되는 스케줄을 의미함.
* 동시성이 없어 좋은 성능을 내지 못하나,
* 오류가 발생할 가능성이 적다.
* 다만 현실적으로 사용 할 순 없는 방식이다.

## nonserial schedule
* 트랜잭션들이 겹쳐서 실행된다.
* 동시성이 높아져서 같은 시간 동안 더 많은 트랜잭션들을 처리할 수 있다.
* 단 이상한 결과가 나오는 문제가 발생할 수 있다.

## 문제를 해결하는 아이디어
* serial과 동일한 nonserial을 실행하면 되지 않을까?
* 따라서 스케줄이 동일하다 라는 의미가 무엇인지 먼저 정의한다는 아이디어를 갖게된다.

## conflict
* 두가지 operation에 대해서 세가지 조건을 만족하면 conflict이다.
* 서로 다른 트랜잭션 소속이다.
* 같은 데이터에 접근한다.
* 최소 하나는 쓰기 operation이다.
* conflict는 순서가 참 중요한데,
* 순서가 바뀌면 결과도 바뀌기 때문이다.

## conflict equivalent
* 두개의 스케줄이 두 조건을 만족하면 conflict equivalent이다.
* 두 스케줄은 같은 트랜잭션들을 가진다.
* 어떤 conflicting operation의 순서도 양쪽 스케줄 모두 동일하다.

## conflict serializable
* 스케줄 하나가 serial schedule과 equivalent하면 스케줄은 serialiable이라 한다.

## 결론
* nonserial 하면서 이상한 결과가 나오지 않도록 하려면
* conflict serializable 한 nonserial 스케줄을 허용하면된다.

## 실제 구현
* 여러 트랜잭션이 동시에 실행해도 
* 스케줄이 conflict serializable 하도록 보장하는 프로토콜ㅇ르 적용한다.
