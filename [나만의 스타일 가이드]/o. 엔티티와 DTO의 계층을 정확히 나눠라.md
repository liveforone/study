# 엔티티와 DTO의 계층을 정확히 나누어라
> 부제 : controller와 service와 repository의 계층을 정확히 나누어라

## 1. 엔티티는 어디에 존재해야하는가?
* 엔티티는 간단히 말해 DB와 바로 연동되는 객체라고 할 수 있다.
* 이러한 엔티티를 조작하는 행위는 데이터를 변경시키는 행위로 주의를 요한다.
* 이러한 엔티티는 repository 계층에 존재하는것이 좋다.

## 2. DTO는 어디에 존재해야하는가?
* dto는 엔티티에서 필요한 부분들만 적절히 가져온 객체이다.
* 또한 계층간의 데이터 교환과 클라언트에게 데이터를 전달할때 사용한다.
* 이러한 DTO는 컨트롤러에 위치하는 것이 좋다.

## 3. 두 계층의 만남, 서비스 로직
* 서비스 로직은 entity와 dto가 만나는 부분이다.
* 서비스 로직에서는 비즈니스 로직에 맞추어 데이터를 변경하고
* 튜닝한 후에 controller로 보낸다.
* 또한 controller로 부터 받은 데이터를 적절히 튜닝하여 repsitory로 보낸다.
* 이 서비스 로직에서는 repository로 부터 엔티티를 받고
* controller로 부터 dto를 받는다.
* 일반적으로 entity to dto, dto to entity를 수행하는 mapper는 서비스 로직 패키지 안에 위치하는 것이 좋다.
* 이러한 서비스 계층은 어플리케이션의 경계를 정의할 뿐만 아니라 비즈니스 로직을 캡슐화시켜 데이터를 제어하는 역할을 하는 계층인 만큼 이 계층에서 모든 처리를 하고 적절히 데이터를 나누어 주어야한다.

## 4. 예외 validation
* 예를 들어 password를 체크해야한다고 가정할때 
* 컨트롤러에는 서비스로직으로 부터 아마 password를 뺀 상태의 dto를 받을것이다.
* 그렇다면 password 체크는 어떻게 해야할까?
* 이때에는 @Component로 선언한 validator를 만들고,
* validator를 repository와 연결시킨후 validation check을 하면된다.
```
[controller]
public ResponseEntity<?> updateEmailAndPassword(
    @RequestBody MemberDto dto
) {
    
    if (memberValidator.isWrongMember(dto)) {
        return ...//실패
    }

    memberService.update...(dto);  //성공
}

[validator]
@Component
@RequiredArgsConstructor
pubilc class MemberValidator {

    private final MemberRepository repository;

    PasswordEncoder encoder = new Bcrypt...();

    /*
    * 실제 시큐리티에서도 이메일과 비밀번호를 구분하지 않고 
    * 동시에 validation애서 둘중 하나라도 잘못되면 로그인을 실패시킨다.
    */
    public static boolean isWrongMember(MemberDto dto) {
        Member member = repository.findByEmail(dto.getEmail());

        //member 객체를 이메일로 조회했는데 없다면 이메일이 틀림
        if (CommonUtils.isNull(member)) {
            return false;
        }

        /*
        * 비밀번호는 member객체가 존재해야하기 때문에 
        * 객체 조회 후에 validation 한다.
        */
        if (!encoder.match(member.getPassword(), dto.getPassword())) {
            return false;
        }

        return true;
    }
}
```
