# Mock Up Test

## Mockito
* 목업을 활용해서 테스트 할 수 있게 해주는 라이브러리이다.
* 테스트시 발생하는 여러거 객체간의 의존성이 단위테스트를 어렵게 한다.
* mockito는 이러한 어려움을 해결해준다.

# Mockito 사용법 정리
## 1.객체 의존성 주입(DI)을 위한 어노테이션
* @Mock : Mock 객체를 만들어줌
* @Spy : 원본 메소드를 그대로 사용함
* @InjectMocks : 위의 두 어노테이션으로 생성된 객체를 자동으로 주입시켜주는 어노테이션
* 즉 <u>자기자신</u>을 @InjectMocks로 다시 선언하고, <u>의존관계객체</u>를 @Mock으로 선언한 후에 테스트 코드를 작성하면된다.
> 컨트롤러에 단위 테스트를 하고자 할때 서비스를 주입시키려면 @Mock으로 가짜 서비스 객체를 만들고, @InjectMocks로 컨트롤러에 주입한다.

## 2.결과처리
* doReturn() : Mock객체가 특정한 값을 반환하는 경우
* doNothing() : Mock객체가 아무것도 반환하지 않는 경우 like void
* doThrow() : Mock객체가 예외를 발생시키는 경우

## 3.JUnit과 Mockito 결합
* JUnit5의 경우 @ExtendWith(MockitoExtension.class)로 결합하면 된다.

## 4.MockMvc - 컨트롤러단
* 컨트롤러를 테스트하는 경우 http 호출이 필요한데 이것을 도와주는 객체이다.
<pre>
private MockMvc mvc;

@BeforeEach
public void init() {
    mvc = MockMvcBuilders.standloneSetup(userController).build();
}
</pre>

## 5.@WebMvcTest - 컨트롤러단
* 위의 MockMvc가 번거롭기에 해당 어노테이션으로 데체가 가능하다.
* 스프링부트가 제공하는 테스트 환경이기에 기존의 @Mock과 @Spy대신에 @MockBean과 @SpyBean을 사용해야한다.
* 주의할점은 캐싱을 사용할경우 위의 MockMvc를 사용하는 것이 좋다.
<pre>
@WebMVcTest(UserController.class)
class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;
}
</pre>

## 6.@DataJpaTest
* jpa를 테스트할때 사용한다.
* 테스트가 끝났다면 트랜잭션 롤백을 해주고, 단순 모킹이 아닌 직접 DB와 통신한다.

## 7.예제
<pre>
@ExtendWith(MockitoExtension.class)
class UserController {
    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;
}
</pre>
