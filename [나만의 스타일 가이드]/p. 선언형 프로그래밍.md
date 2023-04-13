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

## 1. 컨트롤러에서 선언형 프로그래밍
* 회원가입하는 코드이다.
* 코드를 보면 흐름을 파악하는 것이 쉽고,
* 무슨일을 하는지 알 수 있을것이다.
* 코드는 그저 무엇을 해라 라고만 말한다.
* 어떻게 하라 라고 하지 않는다.
* 아래에 rest response는 리턴할 값의 구현부이다.
* (1) 입력받은 dto를 체크해 문제가 있다면 미리 구현해놓은 rest response 객체의 validError을 호출해서 리턴하라
* (2) 이메일을 이용해 이메일을 중복체크하고 중복이라면 rest response객체의 duplicateEmail를 호출하라.
* 아래의 코드들도 마찬가지이다. 비슷하게 무엇을 하라라고만 하고 더이상 코드가 신경 쓰지 않는다.
```
@PostMapping(MemberUrl.SIGNUP)
public ResponseEntity<?> signup(
        @RequestBody @Valid MemberSignupRequest memberSignupRequest,
        BindingResult bindingResult
) {
    if (bindingResult.hasErrors()) {
        return RestResponse.validError(bindingResult);
    }

    String email = memberSignupRequest.getEmail();
    if (memberValidator.isDuplicateEmail(email)) {
        return RestResponse.duplicateEmail();
    }

    memberService.signup(memberSignupRequest);
    userProducer.createMileage(email);
    log.info(ControllerLog.SIGNUP_SUCCESS.getValue());

    return RestResponse.signupSuccess();
}
```

## 2. 서비스로직에서 선언형 프로그래밍
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

## 3. 결론
* 즉 최상위 계층과 mvc의 주요 요소인 service와 controller 등에서는 선언하듯 프로그래밍하여 
* 가독성과 전체 흐름파악에 용이하도록 하고,
* 최하위 구현 계층에서는 코드를 구현하여, 상위 계층에서 호출하여 선언하듯 프로그래밍하게 한다.
* 선언형은 "어떻게 하라" 를 알려주는 스타일이 아닌
* "무엇을 하라" 를 이야기하는 스타일이라는 것을 염두하면 좋겠다.
