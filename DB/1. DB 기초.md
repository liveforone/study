# Database 기본 개념

## 1. comceptual data model
* 높은 수준으로 데이터 모델을 추상화 한것임
* entity-relationship 으로 표현함.
* 흔히 표현하는 er 다이어그램
## 2. logical data model
* 이해하기 쉽고 디테일하게 구조화 함
* 데이터가 컴퓨터에 저장될 때의 구조와 크게 다르지 않게 db 구조화를 가능하게 한다.
* 특정 DBMS나 storage에 종속되지 않는 수준에서 db를 구조화함
* relational data model
* object data model
* object-realtoinal data model 등이 있음.
### relational data model
* 이것이 일반적인 db이며
* row에는 데이터들이
* column에는 데이터의 속성이 들어가는 형태이다.

## 3. schema
* data model을 바탕으로 DB 구조를 description 한 것
* 스키마는 DB를 설계할 때 정해지며
* 한 번 정해진 후에는 자주 바뀌지 않는다.
* 어렵게 생각할 것이 없이 어떤 테이블이 있고, 테이블들에는 어떤 속성이 있는가를 표현한것이 스키마이다.

## 4. state
* DB에 있는 데이터는 꽤 자주 바뀔 수 있다.
* 특정 시점에 DB에 있는 데이터를 state 혹은 snapshot이라고 한다.
* 혹은 DB에 있는 현재 instance들의 집합이라고도 한다.

## 5. three schema architecture
* 유저 application과 물리적인 DB를 분리하는 목적으로 사용됨.
* 물리적 DB가 변경되더라도 유저 application에 영향을 주지 않음
### internal schema
* DB에 가장 가깝게 있는 스키마
* data storage, 자료구조, 인덱스 등 실체가 있는 내용이 담겨있다.
* 즉 데이터가 존재하는 곳임.
### conceptual schema
* 전체 DB에 대한 구조를 담고 있음
* 물리적인 저장 구조에 관한 내용은 숨김
* 즉 internal과 external의 사이에 있는 스키마임
* internal schema를 한 번 추상화 한것이라고 생각하면된다.
### external schema
* DB에 가장 멀리 있고 유저에 가장 가까운 스키마임
* 특정 유저들이 필요로 하는 데이터만 표현한다. 
### 결론
* 각 레벨을 독립시켜서 어느 레벨에서의 변화가 상위 레벨에 영향을 주지 않게 만듦
* 대부분의 DB가 3레벨을 완벽하게 나누지는 않음

## 6. DB 언어
### DDL
* conceptual schema를 정의하기 위해 사용됨
* internal 까지 정의 하기도 함.
### SDL
* internal schema를 정의하는 용도로 사용됨.
* 요즘은 거의 사용되지 않고 파라미터 등의 설정으로 대체된다.
### VDL
* external schema를 정의하기 위해 사용되는 언어
* 대부분의 DB에서는 DDL이 VDL 역할 까지 함.
