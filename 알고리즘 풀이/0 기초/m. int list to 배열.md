# 정수형 list를 배열로 변환
```
int[] arr2 = list.stream()
                .mapToInt(i -> i)
                .toArray();
```
