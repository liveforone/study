# 함수 규칙

## 쿼리 낭비를 줄여라.
* 예를들어서 컨트롤러에서 유저 email을 입력받는다.
* 서비스로직에서 email을 인수로 받아 유저를 조회해서 리턴하는 함수를 컨트롤러에서 호출한다.
* 유저의 이메일의 중복을 체크하는 함수가 있다.
* 이 함수는 이메일을 인수로 받아 함수안에서 유저를 조회하고 중복을 체크한다.
* 즉 쓸데없는 조회쿼리가 한 번더 나가는 것이다.
* 이런식의 함수는 좋지 않다.
* 특히 기능, db이외에 기능을 하는 함수들에 이런 조회로직을 넣어 쿼리를 낭비하는 일이 많다.
* 이런 로직은 이미 조회한 객체를 인수로 받아, 원하는 기능을 수행하는 함수로 변경해야한다.
* 아래는 낭비쿼리 예시이다.
```
public ResponseEntity<?> changeEmail(@ReqeustBody String email) {
    Users users = userRepository.findByEmail(email);

    Boolean checkDuplicate = userService.checkDuplicateEmail(email);

    .....
}

public boolean checkDuplicateEmail(String email) {
    Users users = userRepository.findByEmail(email);
    ....이메일 중복체크 로직..
}
```
* 아래와 같이 변경하자.
```
public ResponseEntity<?> changeEmail(@ReqeustBody String email) {
    Users users = userRepository.findByEmail(email);

    Boolean checkDuplicate = userService.checkDuplicateEmail(users);

    .....
}

public boolean checkDuplicateEmail(Users users) {
    ....이메일 중복체크 로직..
}
```

## 상수(int)를 리턴하는 함수보다 boolean을 리턴하는 함수가 더 좋다.
* 함수안에서 true, false를 체크해서 맞다면 1이라는 상수를 리턴하고
* 아니라면 0을 리턴하는 함수등을 만들 수 있다.
* 이러한 함수를 사용하면 상수도 만들고, 이 값을 체크하는 if문도 복잡하고 지저분해진다.
* 하지만 boolean을 리턴하면 체크하는 if문을 보다 깔끔하게 만들 수 있다.
* 또한 스타일 가이드에서 말했듯이 if문은 gate-way 스타일로 동작하기 때문에 is부정인상황 으로 네이밍하여
* 부정일때 true를 긍정일때 false를 리턴하면된다.
* 아래는 상수리턴 예시이다.
```
/*
* 이메일 중복 검증
* 반환 값 : 1(중복아님), 0(중복)
*/
public static int checkDuplicateEmail(Users users) {

    if (CommonUtils.isNull(users)) {
        return UserConstants.NOT_DUPLICATE.getValue();
    }
    return UserConstants.DUPLICATE.getValue();
}

int checkEmail = UserUtils.checkDuplicateEmail(email);

if (checkEmail == UserConstants.DUPLICATE.getValue()) {
    return ResponseEntity.ok("중복되는 이메일이 있어 회원가입이 불가능합니다.");
}
```
* 이러한 함수를 아래처럼 바꾸면 좋다.
```
/*
* 이메일 중복 검증
* 반환 값 : false(중복아님), true(중복)
*/
public static boolean isDuplicateEmail(Users users) {

    if (CommonUtils.isNull(users)) {
        return true;  //중복일때(부정적 상황) : true
    }
    return false; //중복 아닐때(긍정적 상황) : false
}

if (UserUtils.isDuplicateEmail(email)) {
    return ResponseEntity.ok("중복되는 이메일이 있어 회원가입이 불가능합니다.");
}
```

## 부수효과가 존재한다면 네이밍을 더 정확히 하라.
* 부수효과(변경이 일어나는 함수를 호출하는 행위 등)는 발생하지 않는것이좋다.
* 그렇지만 불가피하게 부수효과를 발생한다면 네이밍을 더욱 정확히 해야한다.
* 즉, 함수가 발생시키는 부수효과를 이름에 명시하는것이다.
* 함수 네이밍은 주석이라고 생각하라(인수 네이밍도 마찬가지)

## 인수 네이밍을 정확히 하라.
* 인수 네이밍이 중요한 이유는 함수를 호출하여 인수를 넣을때
* ide가 들어가야할 인수의 이름을 미리보기로 보여주기때문에
* 이 미리보기를 보고 어떤 값을 넣어주야할지 예상할 수 있다.
* 그러나 여기에 이상한, 혹은 어울리지 않는 인수 네이밍을 했다면
* 어떤 인수를 넣어야할지 헷갈리게 된다.

## 인수로 boolean 값을 사용하지 말아라(플래그 인수를 지양하라).
* 플래그 인수는 참과 거짓인 두가지 상황에 따라 두가지 일을 처리해야한다.
* 즉 함수가 여러 기능을 수행한다는 증거가된다.
* 함수는 단 한가지 일을 해야한다는 중요한 법칙을 어기게 된다.
* 또한 혼란을 초래할 수 있기에 플래그 인수는 지양하라.
