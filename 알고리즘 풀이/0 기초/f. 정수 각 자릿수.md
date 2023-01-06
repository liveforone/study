# 정수 각 자릿수 뽑기
* 정수의 각 자릿수를 뽑는 방법은 간단하다
* 자기자신 % 10
* 원래 정수 = 원래 정수 / 10
* 번외로 정수의 갯수를 구하려면 
* int length = (int)(Math.log10(num)+1); 를 사용한다.
```
int num = 12345;
List<int> arr = new ArrayList<>();
while (num > 0) {
    arr.add(num%10);
    num = num / 10;
}
```
