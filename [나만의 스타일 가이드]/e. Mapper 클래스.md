# Mapper 클래스

## 목차
1. Mapper 클래스란?
2. dto -> entity
3. entity -> dto 
4. entity -> dto list
5. entity -> dto page
6. 결론

## 1. Mapper 클래스란?
* 엔티티를 직접 노출하지 않는 dto는 상당히 많은 장점을 가진다.
* 순환 참조 해결이나 등등 dto는 현재 시점에서 반드시 사용해야하는 객체이다.
* 이 글을 보는 독자들은 dto를 사용하는 이유에 대해 충분히 숙지했을것이라고 생각한다.
* 엔티티를 만들때마다 중구난방으로 흩어져 있는 상호 변환 메서드를 한곳에 모은 것이 Mapper 클래스이다.

## 2. dto -> entity 
* 프론트로부터 입력받은 request를 dto에 set한다.
* getter/setter가 있는 dto라면 프론트로부터 requestbody를 통해 값을 dto에 넣을 수 있게된다.
* 이렇게 입력받은 값을 엔티티로 변환하여야 jpa의 save() 메소드를 사용할 수 있다.
* dto 클래스 안에 dto를 entity로 변환하는 메서드를 만드는 사람들이 있다.
* 이는 좋은 방법이 아니다. 왜냐하면 관심사의 분리(SOC)를 어겼기 때문이다.
* 변환 메소드는 모두 Mapper 클래스에 두어 통일성을 갖도록 할 뿐만 아니라 dto가 단일 책임만을 갖도록 한다.
* 아래는 예제이다.
```
public static Board dtoToEntity(BoardRequest board) {
    return Board.builder()
            .id(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .users(board.getUsers())
            .view(board.getView())
            .good(board.getGood())
            .build();
}

//사용
boardRepository.save(
    BoardMapper.dtoToEntity(boardRequest)
);
```

## 3. entity -> dto
* 뷰에 값을 넘길때 절대로 entity로 넘겨서는 안된다.
* 연관관계가 있다면 순환참조문제가 발생하고 여러가지 에러들이 발생할 수 있다.
* 또한 불필요한 값이나, 클라이언트에 제공되어서는 안되는 값들이 삽입되어 클라이언트에 전달 될 수 있다.
* 넘기는 자료형태, 즉 List, Page, 객체 하나(detail)에 맞게 함수를 만들어 호출하도록 한다.
* 지금 다룰 부분은 세가지 형태중 객체하나(detail)이다.
* 아래는 예제이다.
```
private static BoardResponse dtoBuilder(Board board) {
    return BoardResponse.builder()
            .id(board.getId())
            .title(board.getTitle())
            .content(board.getContent())
            .view(board.getView())
            .good(board.getGood())
            .createdDate(board.getCreatedDate())
            .build();
}

public static BoardResponse entityToDtoDetail(Board board) {
    return BoardMapper.dtoBuilder(board);
}
```

## 4. entity -> dto list
* stream을 사용하면 아주 편리하게 entity 리스트를 dto 리스트로 변환할 수 있다.
* dtoBuilder는 위에서 선언했으니 재선언하진 않겠다.
* 아래는 예제이다.
```
public static List<BoardResponse> entityToDtoList(List<Board> boardList) {
        return itemList
                .stream()
                .map(BoardMapper::dtoBuilder)
                .collect(Collectors.toList());
 }
```

## 5. entity -> dto page
* 마지막은 페이징이다.
* 페이징에서 어려워하는 사람들을 많이 보았다.
* 이 또한 람다, 그중에서도 map과 메서드 참조를 사용하면 아주 편하다.
* dtoBuilder는 위에서 선언했으니 재선언하진 않겠다.
* 아래는 예제이다.
```
public static Page<BoardResponse> entityToDtoPage(Page<Board> boardList) {
    return boardList.map(BoardMapper::dtoBuilder);
}
```

## 6. 결론
* 이렇듯 관심사에 따라 클래스를 분리하는 것은 중요하다.
* 또한 중구난방으로 흩어져있는 매퍼 메서드를 하나에 담아 사용하면 아주좋다.
* dto를 사용하는 것은 권장이 아니라 필수이다.
* 귀찮더라도 dto와 매퍼클래스를 사용해 엔티티와 dto를 자유자재로 상호 변환하자.
