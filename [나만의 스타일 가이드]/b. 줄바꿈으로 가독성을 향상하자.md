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
* 매개변수명이 복잡한 경우
* 매개변수 2개 이상인경우(짧다면 줄바꿈X).
* 함수안에서 함수를 호출하는 값을 인수로 받는 경우.
* 함수안에서 함수를 호출하는 값을 인수로 받는 경우에는 해당 인수를 다른곳에서 한 번더 사용한다면 변수로 빼서 인수로 넣는다.
* 이렇게 변수로 빼게 되면 전체적인 길이가 짧아져서 줄바꿈을 안해도 되는 경우가 만들어지게된다.
* 다만 아무리 짧은 매개변수라도 3개이상이라면 반드시 줄바꿈해야한다.
* 예외의 경우는 Objects.equals() 처럼 비교하는 함수를 호출하는 경우이다.
* 이 경우에는 아무리 길어도 비교의 의미가 희석되지 않도록 줄바꿈하지 않는다.
* 특히 이런경우 함수안에 무엇이 매개변수로 들어가고 무슨 일을 하는 하는지 알기가 쉽지않다.
* 줄바꿈은 괄호를 기준으로 줄바꿈하며 블럭의 한 블럭에 매개변수들이 들어가 있는것 처럼 표현한다.
* 함수의 마지막 괄호는 맨 앞까지 끌어온다.

### 예시1 - 매개변수가 함수를 호출하는 값일때
```
- 변경 전 - 
int checkPassword = userService.checkPasswordMatching(userRequest.getPassword(), users.getPassword());

- 변경 후 -
int checkPassword = userService.checkPasswordMatching(
                        userRequest.getPassword(),
                        users.getPassword()
);
```

### 예시2 - 함수 안에 함수
* 예시가 약간의 억지일 수 있으나 함수 안에서 함수를 또 호출하는 경우이다.
* 지나친 함수안에 함수 호출은 피해야겠지만 불가피한 경우 아래처럼 사용한다.
```
Number number = new.....;
int num = numberService.calculator(
                number.getPositive(120, -10)
);
```

### 예시3 - 매개변수 재사용이 가능할떄
* 주로 principal.getName()과 같은 것들은 매개변수 재사용하는 경우가 많다.
* 이 경우에는 변수로 뺀 후에 그 변수를 매개변수로 넣는 것이 좋다.
```
String email = principal.getName();
Item item = itemService.getItemEntity(itemId);

orderService.saveOrder(
  item,
  ordersRequest,
  email
);
itemService.minusItemRemaining(
  ordersRequest.getOrderCount(),
  itemId
);
userService.plusCount(email);
log.info("주문 성공");
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
