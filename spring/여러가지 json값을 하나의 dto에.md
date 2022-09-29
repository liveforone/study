# 여러가지 값(JSON기준)을 하나의 dto에 담는 방법

## 설명
* 예를들어 여러가지 물건을 구매할때 물건의 id를 여러개 입력받고,
* 받은 돈과 고객의 이름을 받는 다고 할때 
* 하나의 dto에 받아서 @RequestBody로 처리하는 것은 아주 효과적이다.
* 그런데 같은 자료형의 연속되는 값을 어떻게 받을까? 아래 코드를 보자.

## 예제
### Dto 
```
private List<Long> ids = new ArrayList<>();
private int money;
private String username;
```

### controller
```
public ResponseEntity<?> (@RequestBody Dto dto) {
   //서비스 로직으로 dto보냄 
}
```

### service
```
public Map<String, Object> 영수증(Dto dto) {
    Map<String, Object> map = new HashMap<>();
    int resultMoney;
    
    for (int i=0; i<dto.getIds().size(); i++) {
        Item item = itemRepository.findOneById(dto.getIds().get(i));
        resultMoney += item.getPrice();
    }
    
    int lasts = dto.getMoney() - resultMoney;
    
    map.put("남은 금액", lasts);
    map.put("유저이름", dto.getUsername());
}
```
