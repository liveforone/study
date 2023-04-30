## 0. 선언형 프로그래밍이란?
* 명령형 프로그래밍과 비교하며 알아보자.
### 명령형 프로그래밍
* 선언형의 반대인 명령형은 어떻게 프로그래밍해야하는지 알려준다.
* 마치 아래와 같다.
* 3개의 배열에 값을 넣고
* 3번 순회하여
* 배열중에 가장 큰 값을 찾아라.
* 이러한 
```
int[] arr = new int[3];

int bigOne = 0;

for (int i=0; i<3; i++) {
    if (arr[i] > bigOne) {
        bigOne = arr[i];
    }
}
```
### 선언형 프로그래밍
* 선언형 프로그래밍은 하는 방법을 알려주지 않고,
* 그저 "하라"라고 표현한다.
* 아래와 같이 말이다.
* 아래는 그저 "배열에서 제일 큰거 찾아"라고 말하고 끝낸다.
* 몇번 순회하고, 어떻게 비교해서 제일 큰 값을 찾는게 아니란 말이다.
* 아래처럼 라이브러리를 빌려 사용해도 되고, 직접 함수를 구현하여 호출해 사용해도 된다.
* 라이브러리는 한계가 있기에, 많은 경우 함수를 구현하고, 그 함수를 호출하여 사용할 것이다.
* 이 경우 인터페이스를 떠올리면된다.
* 인터페이스의 함수를 구현하는 구현부에서 함수의 내부가 모두 구현되고,
* 인터페이스는 그저 그 함수를 가지고만 있다.
* 이와 유사하게 service, controller 등에서는 원하는 함수를 선언하여 호출할뿐 구현부는 따로두어 
* 전체적인 로직이 어떻게 흘러가는지 파악이 쉽게 한다.
* 코드가 길어지고 거대해지면 이런 선언형 스타일은 더욱 빛을 본다.
* 함수형 언어가 아니여도 충분히 선언형 스타일로 작성할 수 있다.
```
int[] arr = new int[3];
Integer maxValue = arr.stream()
                .mapToInt(x -> x)
                .max()
```

## 1. 백엔드에서 선언형 프로그래밍의 목적
* 보통 백엔드에서는 3가지 계층으로 나뉜다.
* 사용자와 api를 통해 소통하는 컨트롤러 계층
* 트랜잭션을 컨트롤하는 서비스 계층 + 도메인 계층
* DB와 닿아있는 리파지토리 계층
* 선언형 프로그래밍 스타일 가이드의 핵심은 바로 백엔드의 주요 계층에 선언형 스타일을 적용하는 것이다.
* 즉 중요하고, 복잡한 주요 계층에 선언형을 적용하여, 유지보수 및 가독성을 향상하는 것이 주 목적이다.

## 2. 컨트롤러에서 선언형 프로그래밍
* 회원가입하는 코드이다.
* 코드를 보면 흐름을 파악하는 것이 쉽고,
* 무슨일을 하는지 알 수 있을것이다.
* 코드는 그저 무엇을 해라 라고만 말한다.
* 어떻게 하라 라고 하지 않는다.
* 아래는 코드의 전문이다.
```
@PostMapping(MemberUrl.SIGNUP)
public ResponseEntity<?> signup(
    @RequestBody @Valid MemberSignupRequest memberSignupRequest,
    BindingResult bindingResult
) {
    //void타입의 코드로 오로지 함수를 호출(선언)만 했다.
    //dto의 바인딩을 검증한다.
    memberValidator.validateBinding(bindingResult);

    //회원의 중복 회원가입을 검증한다.
    String email = memberSignupRequest.getEmail();
    memberValidator.validateDuplicateEmail(email);

    //회원 가입한다.
    memberService.signup(memberSignupRequest);
    log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

    //201코드와 회원가입 성공 메세지를 body에 담아 던진다.
    return RestResponse.signupSuccess();
}
```
### validator : 검증
* 검증은 주로 컨트롤러 단에서 한다.
* validation하는 부분을 어떻게 해야할까?
* 필자의 주 방법은 ControllerAdvice와 Custom Exception을 사용하는 것이다.
* 이렇게 하면 if-else문없이 오로지 validator 함수 호출만으로 검증 에러 발생시 적절한 리턴처리까지 모두 깔끔하게 처리할 수 있다.
* 위의 코드에서 validation 부분을 살펴보면 void메소드를 호출하는 것을 알 수 있다.
* 내부 로직에서 문제가 발생하면 커스텀 에러를 throw 하고, 
* 그렇지 않다면 무시하고 넘어간다.
### 최종 리턴
* RestResponse는 모든 처리가 끝나고 성공했을때 호출되는 객체이다.
* 함수들의 네이밍은 모두 XxSuccess()이다. 
* 이들은 적절한 http status와 성공한 body를 담고 있고,
* 이를 ResponseEntity에 담아 리턴한다.
* ResponseEntity를 빌더를 이용해 build하는 것보다,
* 객체에 build 로직을 구현하고, 이를 선언하여 사용하도록 했다.
* 이를 통해 전체적이 흐름파악과 가독성이 매우 향상된것을 알 수 있다.
### 결론
* 결론적으로 컨트롤러에서는 커스텀 에러와 controller advice, validator를 만들어서
* 이들 안에 적절한 로직을 구현하고, 중요하고 복잡한 로직인 컨트롤러 계층에서는 오로지 선언하여 사용한다.
* 또한 RestResponse객체를 만들어서 모든 과정이 끝나고 성공하는 경우에는 적절한 메소드를 만들어 ResponseEntity를 리턴하도록 하여 처리한다.

## 3. 서비스로직에서 선언형 프로그래밍
* 다른 코드는 볼 필요도 없다.
* 함수의 하는 일은 회원가입이다.
* dto를 받고, 빌더를 이용해 빈 회원을 만든 후
* 회원 엔티티에 구현해놓은 signup이라는 함수를 이용해 회원가입을 완료했다.
* signup의 구현부는 코드가 너무 길어서 넣지 않았다.
* 이처럼 (1) : 회원을 만들어라
* (2) : 회원가입해라
* (3) : 저장해라
* 처럼 선언하듯 프로그래밍 하면 된다.
```
@Transactional
@Async(AsyncConstant.commandAsync)
public void signup(MemberSignupRequest memberSignupRequest) {
    Member member = Member.builder().build();
    member.signup(memberSignupRequest);

    memberRepository.save(member);
}
```
* 이러한 코드를 성공적으로 완성하기 위해서는 도메인 모델 패턴을 반드시 사용해야한다.
* 특히나 jpa라는 영속성 orm의 도움으로 도메인 모델 패턴은 빛을 발한다.
* 도메인 모델 패턴에 대한 설명은 [도메인 모델 패턴](https://github.com/liveforone/study/blob/main/%5B%EB%82%98%EB%A7%8C%EC%9D%98%20%EC%8A%A4%ED%83%80%EC%9D%BC%20%EA%B0%80%EC%9D%B4%EB%93%9C%5D/q.%20%EB%8F%84%EB%A9%94%EC%9D%B8%20%EB%AA%A8%EB%8D%B8%20%ED%8C%A8%ED%84%B4(%EB%8D%94%ED%8B%B0%EC%B2%B4%ED%82%B9).md)에서 살펴보길 바란다.

## 4. 결론
* 즉 최상위 계층과 mvc의 주요 요소인 service와 controller 등에서는 선언하듯 프로그래밍하여 
* 가독성과 전체 흐름파악에 용이하도록 하고,
* 최하위 구현 계층에서는 코드를 구현하여, 상위 계층에서 호출하여 선언하듯 프로그래밍하게 한다.
* 선언형은 "어떻게 하라" 를 알려주는 스타일이 아닌
* "무엇을 하라" 를 이야기하는 스타일이라는 것을 염두하면 좋겠다.
