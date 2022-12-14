# 줄바꿈으로 가독성을 향상하라

## 줄바꿈1
* 변수가 엄청 길어지는 경우가 있다.
* 특히 new로 생성을 하거나 리턴값이 있는 함수를 호출할때 그렇다.
* 줄바꿈하는 상황은 가로스크롤을 해야 변수를 완전히 볼 수 있는 경우에 줄바꿈한다.
* 줄바꿈은 변수에 값을 대입하는 = 을 기준으로 줄바꿈한다.

### 변수 예시
```
List<ItemResponse> itemList =
                itemService.getItemListForMyPage(users.getEmail());
```

## 줄바꿈2 - 함수를 호출하거나 줄바꿈1로도 안보일 경우
* 매개변수 짧아도 2개 이상인경우.
* 함수안에서 함수를 또 호출하거나
* 함수안에 값으로 문자열이 들어가는 경우.
* 특히 이런경우 함수안에 무엇이 매개변수로 들어가고 무슨 일을 하는 하는지 알기가 쉽지않다.
* 줄바꿈은 괄호를 기준으로 줄바꿈하며 블럭의 한 블럭에 매개변수들이 들어가 있는것 처럼 표현한다.
* 함수의 마지막 괄호는 맨 앞까지 끌어온다.

### 예시1 - 매개변수가 2개 이상
```
- 변경 전 - 
int checkPassword = userService.checkPasswordMatching(userRequest.getPassword(), users.getPassword());

- 변경 후 -
int checkPassword = userService.checkPasswordMatching(
                        userRequest.getPassword(),
                        users.getPassword()
);
```

### 예시2 - 함수의 값으로 문자열이 들어감
```
- 변경 전 -
httpHeaders.setLocation(URI.create("/user/logout"));
- 변경 후 -
httpHeaders.setLocation(URI.create(
                "/user/logout"
));
```

### 예시3 - 함수 안에 함수
* 예시가 약간의 억지일 수 있으나 함수 안에서 함수를 또 호출하는 경우이다.
* 지나친 함수안에 함수 호출은 피해야겠지만 불가피한 경우 아래 처럼 괄호를 기준으로 두번 띄어 사용한다.
* calculator 함수에서 한 번 줄바꿈, getPositive 함수에서 한 번 줄바꿈 하여 처리했다.
```
Number number = new.....;
int num = numberService.calculator(
                number.getPositive(
                      120,
                      -10
                )
);
```

## 줄바꿈3 - 빌더 패턴
* 빌더패턴 사용시에는 객체.builder() 를 기준으로 그 뒤는 모두 줄바꿈하여 처리한다.
* .builder()가 없는 경우에는 객체 뒤부터 모두 줄바꿈하여 처리한다.

### 예시1 - 빌더 사용
```
Fruit.builder()
        .quantity(5)
        .price(2000)
        .date("2022-12-14")
        .build();
```
### 예시2 - 빌더가 없는 
```
return ResponseEntity
        .status(HttpStatus.MOVED_PERMANENTLY)
        .headers(httpHeaders)
        .build();
```
