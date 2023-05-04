## 0. 도메인 모델 패턴
* 도메인 모델 패턴에 대한 이야기는 수많은 블로그 등에 기재되어있다.
* 이 저장소는 스타일 가이드에 대해 이야기하는 저장소이다.
* 따라서 도메인 모델 패턴이 '어떤 것' 인지 이야기하는 것이 아닌,
* 도메인 모델 패턴을 어떻게 활용해야하는지를 이야기한다.

## 1. 간단한 소개
* 기존의 디자인은 service 클래스에 모든 비즈니스로직을 넣었다.
* serivce가 너무 방대해지고, 흐름파악이 너무 나빠졌다.
* 이에 따라 엔티티를 그저 데이터를 담는 그릇에서 넘어서,
* 엔티티에 비즈니스로직을 담도록하는 것이다.
* 이를 통해 엔티티는 데이터를 담은 것에서, 비즈니스 로직까지 모두 담은 객체가 된다.
* 상태를 변경하는 모든 작업은 엔티티를 통해 한다.

## 2. 도메인 모델 패턴 예시
* 아래는 Member 엔티티에 정의된 비즈니스로직 일부이다.
* 일반 회원은 일반 회원에 맞는 회원가입을 제공하고,
* 판매자는 판매자에 맞게하며
* 여러 컬럼의 업데이트도 제공한다.
```
//일반 회원 생성
public static Member create(MemberSignupRequest request) {
    final String ADMIN = "admin@maybeAllHere.com";

    if (Objects.equals(request.getEmail(), ADMIN)) {
        request.setAuth(Role.ADMIN);
    } else {
        request.setAuth(Role.MEMBER);
    }
    request.setPassword(PasswordUtils.encodePassword(request.getPassword()));

    return new Member(
        createUsername(),
        request.getEmail(),
        request.getPassword(),
        request.getRealName(),
        request.getAuth()
    );
}

//가게 주인 회원 생성
public static Member createOwner(MemberSignupRequest request) {
    request.setPassword(PasswordUtils.encodePassword(request.getPassword()));
    request.setAuth(Role.OWNER);

    return new Member(
        createUsername(),
        request.getEmail(),
        request.getPassword(),
        request.getRealName(),
        request.getAuth()
    );
}

//uuid로 username 생성 메서드
private static String createUsername() {
    return UUID.randomUUID() + RandomStringUtils.randomAlphabetic(MemberConstant.RANDOM_STRING_LENGTH);
}

//update 이메일 메서드 : 도메인 모델 패턴의 대표적인 예시이다.
public void updateEmail(String newEmail) {
    this.email = newEmail;
}

public void updatePassword(String password) {
    this.password = PasswordUtils.encodePassword(password);
}
```
* 아래는 서비스 로직에서 이를 활용하는 코드이다.
* 추후 후술하겠지만, 스프링에서 도메인 모델 패턴을 잘 사용하려면
* 더티체킹을 사용해야한다.
* 이를 통해 깔끔하게 값을 변경하는 것이 가능해진다.(도메인 모델 패턴의 장점을 극대화 시켜준다)
```
@Transactional
@Async(AsyncConstant.commandAsync)
public void signup(MemberSignupRequest memberSignupRequest) {
    Member member = Member.create(memberSignupRequest);

    memberRepository.save(member);
}

@Transactional
@Async(AsyncConstant.commandAsync)
public void signupSeller(MemberSignupRequest memberSignupRequest) {
    Member member = Member.createOwner(memberSignupRequest);

    memberRepository.save(member);
}

@Transactional
@Async(AsyncConstant.commandAsync)
public void updateEmail(String email, ChangeEmailRequest changeEmailRequest) {
    String newEmail = changeEmailRequest.getEmail();
    
    Member member = memberRepository.findByEmail(email);
    member.updateEmail(newEmail);  //더티체킹으로 손쉽게 업데이트가 끝남.
}
```

## 3. 도메인 모델 패턴과 더티 체킹
* 도메인 모델 패턴과 더티체킹은 같이 가는 동반자다.
* 도메인 모델 패턴을 적용하면 서비스로직이 선언형 스타일처럼 깔끔해지고, 로직파악이 쉬워진다.
* 더티체킹을 사용하게 되면 업데이트 쿼리를 모두 대체할 수 있다.
* 이로써 불필요한 코드가 사라지고, 리파지토리와 쿼리는 오로지 '조회'에 집중하여, 얇은 CQRS를 적용할 수 있게된다.
* 엔티티를 항상 조회해야 하는 성능 부담이 있을 수 있는데,
* 성능에 영향을 미치는 것의 대다수는 리스트같은 많은 데이터의 조회이다.
* 사실 PK 기반의 데이터 단건 조회이기 때문에 전체 성능에 거의 영향을 미치지 않는다.
* 암달의 법칙처럼 선택과 집중을 잘해야한다. 적은 성능을 소모하는 것에 집중할게 아니라, 
* 흔하게 성능을 요구하는 부분에 집중하는 것이 더 좋은 문제 접근 방법이다.

## 4. 정적 팩토리메서드
* 도메인 모델 패턴에서 객체를 생성할때에는 정적 팩토리 메소드를 사용하는 것이 좋다.
* 빌더 패턴을 이용해 빈 빌더를 만드는 방법도 있겠지만,
* 정적 팩토리 메서드를 이용하는것이 더욱 깔끔하다.
* 이때 정적 팩토리 메서드에서 꼭 필요한것은 기본생성자(noargsconstructor)이며, 이를 꼭 protected로 놓자. 
* 이유는 jpa에서 리플랙션을 사용하기때문이다.

## 5. 주의 사항
* 더하고 빼는 연산에 주의해야한다. 
* 계산연산의 경우 계산을 잘 작성하고, 상수를 적극 활용해 가독성을 확보한다.
```
this.object += 값
this.object -= 값
```
