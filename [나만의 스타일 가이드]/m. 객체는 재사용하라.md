# 객체는 재사용하라

## 문제 발견
* 컨트롤러 단에서 보통은 널체크를 이유로 객체를 조회한다.
* gate-way 스타일의 if 구문을 지나 최종 구문에 오면 서비스로직에서 생성, 수정, 삭제와 관련된 함수를 호출한다.
* 문제는 서비스로직을 까보면 id를 매개변수로 받아 그 id로 db에서 조회하여 dto에 set하여 생성, 수정, 삭제를 발생시킨다.
* 조회하는 코드가 중복되고있다. 이것은 모양만 다른 중복이다.
* 쿼리 또한 낭비되고있다.

## 해결
* 무슨 이유든 조회한 객체는 최대한 재사용하자.
* 서비스단에 id만 넘기고 서비스단에서 또 객체를 조회하는 행위를 지양하자.

## 문제 코드
* 게시글의 작성자로 회원을 넣어 게시글을 저장한다.
* 문제는 한번 조회된 회원을 한 번 더 조회하는 것이다.
```
[컨트롤러]
User user = userService.getUser(email);

if (CommonUtils.isNull(user)) {
    .....
}

boardService.save(userId...);

[서비스]
User user = userRepository.findById(userId);
boardDto.setUser(user);
boardRepository.save(boardDto.toEntity());
```

## 해결 코드
* 조회한 객체(유저)를 다시 재사용했다.
* 이를 통해 중복쿼리를 없애고, 모양만 다른 중복 코드도 없앴다.
```
[컨트롤러]
User user = userService.getUser(email);

if (CommonUtils.isNull(user)) {
    .....
}

boardService.save(user);

[서비스]
boardDto.setUser(user);
boardRepository.save(boardDto.toEntity());
```
