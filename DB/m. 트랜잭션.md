# 1. 트랜잭션
* 단일한 논리적인 작업 단위
* 여러 sql문은 단일 작업으로 묶어 나눠질 수 없게 한것이 트랜잭션임.
* 일부만 성공한다고 db에 반영되는 것이 아닌,
* 모두 성공해야 트랜잭션이 끝난다(커밋된다).

## 예제 코드로 살펴보기
```
>> START TRANSATION;
>> UPDATE...
>> UPDATE...
>> COMMIT;
```

# 2. 용어 살펴보기
## 커밋
* 지금까지의 작업 내용을 db에 영구적으로 저장함.
* 트랜잭션을 종료함.
* 커밋은 두가지의 의미를 가진다.

## 롤백
* 지금까지 작업들을 모두 취소하고 트랜잭션 이전으로 되돌림.
* 트랜잭션을 종료함.

## 오토 커밋
* 트랜잭션이 성공하면 커밋하고
* 성공하지 못할경우 자동으로 롤백함.
* 대부분의 dbms의 default임.

# 3. ACID 속성
* atomicity
* consistency
* isolation
* duratbility

## atomicity
* all or nothing
* 트랜잭션은 논리적으로 쪼갤 수 없는 단위이기에 반드시 모두 성공해야한다.
* 실패하면 모든 작업을 취소하고 rollback한다.
## consistency
* 제약이나 trigger 등으로 db의 규칙을 위반했다면 롤백한다.
* 즉 db의 일관성을 깨뜨리면 롤백한다.
## isolation
* 여러 트랜잭션이 동시에 실행될때도 혼자 실행되는 것처럼 동작하는것.
* 여러 isolation level이 있고 어떤 level을 사용할지 설정가능.
* concurrency control의 주 목표가 isolation이다.
## duratbility
* 커밋된 트랜잭션은 db에 영구 저장함.
* db시스템에 문제가 생겨도 커밋되면 db에 남아있다.
* 영구적으로 저장한다고 할때에는 비휘발성 메모리에 저장함을 의미한다.
* 기본적으로 트랜잭션의 duratbility는 db가 보장한다.
