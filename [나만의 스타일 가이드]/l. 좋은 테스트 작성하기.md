# 좋은 테스트 작성하기

## 테스트 명명
* 직관적인 네이밍을 하여야한다.
* 무슨 테스트를 할것인지, 무엇을 의도 하고 있는지 정확하게 적어주는것이 좋다.
* 또한 이 테스트는 다른 어딘가에서 사용하는 메소드도 아니기때문에 네이밍이 길어져도 괜찮다.

## 테스트는 단일 동작만 하도록 하라
* 여러개의 테스트코드가 서로 공유하거나
* 서로 의존하거나
* 조건을 공유하는 코드는 별로 좋지 않다.

## Given-When-Then 패턴을 사용하라
* 간단히 하면 given(어떤 상황이 주어지면)
* when(어떤 조건으로 실행한다)
* then(그리고 검증한다)

## test하나에는 하나의 Assertion만 
* 여러 개의 assert 메소드를 사용는 경우가 있다. 
* 이렇게 하면 테스트 실패 시 디버깅이 오래 걸릴 수 있고,
* ‘하나의 테스트 케이스는 단위 기능 중 하나의 시나리오만 테스트하라’는 원칙도 어기게된다.
* 따라서 한 개의 테스트 케이스에 여러 개의 assertion을 넣고 싶다면 테스트를 더 잘게 쪼개면된다.

## 테스트 코드 작성시 EntityManage와 영속성
* em.persist()를 하면 영속성 컨텍스트에 저장한다.
* 주로 persist()는 새로운 값을 영속성 컨텍스트에 저장하여 영속화 할 때 사용한다.
* em.flush()를 하면 db에 값이 반영된다.
* 그런데 테스트코드 작성시에는 굳이 flush 하지 않아도된다.
* 영속성 컨텍스트에 저장만한 상태로 그 값을 꺼내고 수정하는 것이 더 편하고 유연하기 때문이다.
* 값을 수정하는 경우에는 em.merge()를 하여주면된다.
* 결론은 flush와 clear를 굳이 테스트코드에서 작성할 필요가 없다는 것이다.
* 쿼리 로그는 보이지 않겠지만 편하고 유연하게 테스트 코드를 작성하자
* 아래는 예시 코드이다.
```
@Autowired
private EntityManager em;

@Transactional
public Long makeItemAndUser() {
    Item item = Item.builder()
            .title("test1")
            .build();
    em.persist(item);  //영속화

    return item.getId();
}

@Test
@Transactional
void editItemTest() {
    //given
    Long id = makeItemAndUser();  //영속 상태(1차 캐시에 저장된 영속상태)

    //when
    String title = "updated title";  //변경할 제목
    Item updateTitle = Item.builder()
            .id(id)
            .title(title)
            .build();
    em.merge(updateTitle);  //머지(1차 캐시에 있는 객체 업데이트)

    Item finalItem = em.find(Item.class, id);  //1차 캐시에 있는 객체 조회

    //then
    Assertions.assertThat(finalItem.getTitle()).isEqualTo(title);
}
```
