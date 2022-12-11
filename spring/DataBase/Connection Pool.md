# Connection Pool in spring 

## Connection Pool 이란?
* 서버가 데이터베이스에 연결하는 과정을 Connecting 이라고 하며
* 이 작업은 상당히 큰 비용이 든다.
* 커넥션 풀은 쓰레드 풀과 비슷하게 미리 커넥션을 만들어놓고,
* 이를 이용하여 빠르게 데이터베이스에 접속하는 것을 의미한다.

## In Spring
* Spring 에서는 HikariCP라는 친구가 도와준다.

## 커넥션 풀 사이즈 정하기
* 아래와 같은 공식이 많이 쓰인다.
```
size = 전체쓰레드수 x (하나의 task에서 필요한 커넥션 수 - 1) + 1
```
