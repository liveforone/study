# 단순 for-each 문을 람다식으로 바꾸자

## 단순 for-each문
* 기존의 단순 for-each문도 가독성이 충분히 좋다고 생각한다.
* 그렇지만 람다식을 활용해서 조금이라도 더 가독성을 향상하고 코드를 줄여보자.
* 해당 설명은 List와 Page 자료형에 특화되어있는 설명이다.

## 1. dtoBuilder 메소드가 있는경우
* 리스트나 페이지로 가져온 객체들을 dto로 변환하는 메소드가 같은 클래스인 ItemMapper 안에 존재할 때에는 아래와 같이 사용한다.
### 예제 - List 형태일때
```
public static List<ItemResponse> entityToDtoList(List<Item> itemList) {
    return itemList.stream()
    .map(ItemMapper::dtoBuilder)
    .collect(Collectors.toList());
}
```
### 예제 - Page 형태일때
```
public static Page<ItemResponse> entityToDtoPage(Page<Item> itemList) {
    return itemList.map(ItemMapper::dtoBuilder);
}
```

## 2. dto 변환 메소드가 필요없는 일반적인 경우
* 이 방법의 장점이라고 하면 객체에서 연관관계가 있을때에도,
* 연관관계를 참조하여 원하는 값을 가져올 수 있다.
```
public List<String> getMyFollowList(String email) {
    List<Follow> followList = followRepository.findByFollower(email);
    return followList
      .stream()
      .map(follow -> follow.getUsers().getNickname())
      .collect(Collectors.toList());
}
```
