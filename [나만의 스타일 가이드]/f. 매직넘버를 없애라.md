# 매직넘버를 없애라

## 목차
1. 매직넘버란?
2. 상수의 단점
3. enum으로 해결하라
4. 결론
   

## 1. 매직넘버란?
* 매직넘버는 구체적인 수치 값을 의미한다.
* 일반적으로 구체적인 수치 값이라고 하지만,
* 이 뿐만 아니라 의미가 있는 숫자도 매직넘버라 생각한다.
* 예를 들어 true로 1을 false로 0도 매직넘버이고,
* 파일 최대 크기를 20mb라고 한다면 maxFileSize = 20 도 매직넘버이다.
* 이렇듯 의미가 있는 숫자도 여기선 매직넘버라고 다루도록 하겠다.
* 매직넘버를 왜 없애야할까?
* 정확하게 의미를 파악하기 어렵다.
* 대표적으로 pi, 3.14는 모르는 사람이 없을 것이다.
* 그렇더라도 뜬금없이 3.14가 나오면 당황스럽다.
* pi라고 명명하는 것이 더욱 좋은 방법이다.
* 매직넘버를 해결하는 방법은 대표적으로 상수가 있다.

## 2. 상수의 단점
* 상수도 좋은 대안이지만 상수는 문제점이 있다.
* 상수는 자료형만 같지 사용하려는 목적과 이에 따른 종류가 모두 다른데
* 종류가 다른 상수끼리 연산해도 경고메세지가 나오지 않는다.
* 즉 타입의 안전성을 보장해주지 않는다.
* 컴파일 후 상수의 값이 바뀌면 다시 컴파일 해주어야 한다.
* 즉 프로그램이 깨지기 쉽다.

## 3. enum으로 해결하라
* 이 이야기는 Effective-java 라는 책에서도 하고 있다.
* enum으로 바꾸면 인스턴스가 하나씩만 존재함을 보장한다.
* 또한 타입의 안전성을 보장한다.
* 이렇게 좋은 물건이 어디있는가! 당장 사용하자.
### 예시(1)
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
### 예시(2)
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

## 4. 결론
* 매직넘버는 생각보다 시한폭탄 같은 친구이다.
* enum으로 없애버리자!
