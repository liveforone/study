# 관계형 데이터 베이스 기초

## 1. set
* 서로 다른 요소를 가지는 collection
* 하나의 set에서 요소의 순서는 크게 중요하지 않다.

## 2. 수학에서의 관계
* set1과 set2의 관계를 나타내면 아래와 같이 나타낼 수 있다.
* set1 X set2 로 나타내며 카르테시안 곱 이라고도 한다.
* binary 관계는 set1 X set2의 부분집합이다.
* 이것이 n개의 집합이 있는 관계에서는 n-ary 관계라고 하며 set1 x.. setn 까지의 부분 집합을 의미한다.

## 3, 관계형 DB에서의 관계
* relation name : 테이블의 이름
* attribute : 테이블 안에 column
* tuple : 각 attribute의 값으로 이루어진 집합, 쉽게 생각해서 각 행을 의미함, 일부 값은 Null일수도 있음

## 4. relation schema
* 관계 구조를 나타낸다.
* relation name과 attribute 리스트로 표기된다.
* 아래는 예시이다.
```
STUDENT(id, name, grade, major)
```

## 5. degree of relation
* relation schema 에서 atrribute의 수
* 위의 예시에서 degree는 4개이다.

## 6. relation의 특징
* 튜플(각 행)은 중복된 값을 갖을 수 없다.
```
id | name | grade
1  | chan | 1
1  | chan | 1
```
* relation의 튜플을 식별하기 위해 key를 설정한다. (ex : id)
* 튜플의 순서는 중요하지 않다.(순서가 바뀌어도 의미가 바뀌지 않음)
* 하나의 튜플에서 attribute의 이름은 중복되선 안된다
* 하나의 튜플에서 attribute의 순서는 중요하지 않다.
* attribute는 더이상 나누어지지 않아야한다.(atomic 해야한다)
```
id | name | address | major
1  | chan | 서울시 강남구 청담동 | 컴공, 디자인
여기서 address와 major는 더 나눌 수 있기에 atomic하지 않다 라고 볼 수 있다.
```

## 7. key
### super key
* 튜플들을 unique(중복없게) 식별할 수 있는 attribute
* 중복이 없는 attribute라면 다 super key가 될 수 있다.
### candidate key
* 어느 한 attribute라도 제거하면 unique하게 튜플을 식별 할 수 없는 super key
### primary key
* 튜플들을 unique하게 식별하기 위해 선택된 candidate key
* primary key는 보통 여러개의 attribute를 묶기보단 한개의 attribute를 key 잡는 다고 보면된다.
### unique key
* primary key가 아닌 candidate key
* alternate key라고도 한다.
### foreign key
* 다른 relation의 primary key를 참조하는 atrribute

## 8. constraints
* 관계형 db의 relation 들이 언제나 항상 지켜주어야하는 제약 사항
### implicit constraints
* 관계형 DB model 자체가 가지는 constraints
* relation은 중복되는 튜플을 가질 수 없다.
* relation 내에서는 같은 이름의 attribute를 가질 수 없다.
### domain constraints - explicit 
* attribute의 value는 해당 attribute의 domain에 속한 value여야 한다.
### key constraints - explicit
* 서로 다른 튜플을 같은 value의 key를 가질 수 없다.
### NULL value constraints - explicit
* attribute가 NOT NULL로 명시됬다면 NULL값을 가질 수 없다.
### entity integrity constraints - explicit
* primary key는 value에 NULL을 가질 수 없다.
### referential integrity constraints - explicit
* FK와 PK가 도메인이 같아야하고
* PK에 없는 value를 FK가 값으로 가질 수 없다.
* 즉 없는 FK를 가질 수 없다.
