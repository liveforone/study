# jpql
## @Query 사용
* 일반적인 sql문에 보다 객체 지향적인 쿼리 보장
## 예시
```
@Query("select i from Item i where i.title like %:title% order by i.good desc")
List<Item> findByItemContaing(@Param("title") String title);
```
## like 쿼리
```
%키워드% 
```

## order by
```
order by 무엇 desc(or asc) 
```

# 엔티티 프로젝션
* 엔티티 프로젝션 쿼리로 연관관계를 참조할때에는 아래와 같은 방법이있다.
```
@Query("select m.team from Member m)
List<Team> findTeam();
```
* 그런데 이 쿼리는 결국 이너조인 쿼리가 자동으로 나간다.
* 쿼리는 짧으나 조인하는 쿼리가 생략되어있다.
* 즉 묵시적 조인을 하고 있다.
* 묵시적 조인은 예측이 안되기 때문에 사용하지 말자.
* 아래와 같이 변경해 사용하자
```
@Query("select t from Member m join m.team t)
```

## 임베디드 타입 프로젝션
* 엔티티의 필드의 값을 가져오는것
* 스칼라 타입 프로젝션과 다른 점이라면 한 컬럼타입을 가져온다.
```
@Query("select m.name from Member m)
String findName();
```

## 스칼라 타입 프로젝션 - dto 직접조회
* 여러 컬럼을 가져오는 프로젝션을 스칼라 타입 프로젝션이라 한다.
* 여러 타입일 텐데 어떤 타입으로 가져와야할까?
* 이럴때는 dto로 직접 조회하면 된다.
* dto에는 생성자가 있어야하고 new 명령어를 사용한다.
* new 명령어 뒤에는 java 패키지 하위의 패키지명을 전부 적어야한다.
* 간단하게 MemberDto 들어가서 패키지 복사해서 붙여넣으면된다.
* 중요한 점은 생성자의 순서대로 쿼리도 적어야한다.
```
class MemberDto {
    String username;
    int age;
}

@Query("select new jpql.MemberDto(m.username, m.age) from Member m)
List<MemberDto> findScala();
```
