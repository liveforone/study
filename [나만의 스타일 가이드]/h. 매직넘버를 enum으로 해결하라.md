# Magic Number를 enum으로 처리하라.
> magic number : 구체적인 수치 값

## 매직넘버에 대해 정의
* 일반적으로 구체적인 수치 값이라고 하지만,
* 나는 무언가를 check하는 함수에서 
* true의 의미로 1을, false의 의미로 0을 리턴하곤한다.
* 이러한 숫자들은 대체 무슨 의미로 쓰인것인지 잘 파악이 안되곤한다.
* 여기선 이런 숫자들까지 모두 매직넘버로 정의한다.

### 매직넘버 예시
```
public int checkDuplicateEmail(String email) {
    Users users = userRepository.findByEmail(email);

    if (users == null) {  //중복이 아닐때
        return 1;
    }
    return 0;  //중복일 때
}
```

## 상수로 해결할까?
* 이 경우 상수를 떠올리는 독자들이 많을 것이라고 예상한다.
* 상수도 좋은 대안이지만 상수는 문제점이 있다.
* 상수는 자료형만 같지 사용하려는 목적과 이에 따른 종류가 모두 다른데
* 종류가 다른 상수끼리 연산해도 경고메세지가 나오지 않는다.
* 즉 타입의 안전성을 보장해주지 않는다.
* 컴파일 후 상수의 값이 바뀌면 다시 컴파일 해주어야 한다.
* 즉 프로그램이 깨지기 쉽다.

## 상수를 enum으로 바꿔라
* 이 이야기는 Effective-java 라는 책에서도 하고 있다.
* enum으로 바꾸면 인스턴스가 하나씩만 존재함을 보장한다.
* 또한 타입의 안전성을 보장한다.

### 예제1
```
[열거형]
public enum UserConstants {
    DUPLICATE, NOT_DUPLICATE;
}

[유틸클래스]
public UserConstants checkDuplicateEmail(Users users) {
    if (users == null) {
        return UserConstants.NOT_DUPLICATE;
    }
    return UserConstants.DUPLICATE;
}
```

## 예제2
```
[열거형]
@Getter
@AllArgsConstructor
public enum BoardConstants {
    LIMIT_UPLOAD_SIZE(4);
    
    private int value;
}

[컨트롤러]
for (MultipartFile file : uploadFile) {
    if (cnt == BoardConstants.LIMIT_UPLOAD_SIZE.getValue()) {
        break;
    }
    uploadFileService.saveFile(file, board);
    log.info("파일 저장 성공");
    cnt++;
}
```
