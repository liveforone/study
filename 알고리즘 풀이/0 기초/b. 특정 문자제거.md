# 특정 문자제거

## 풀이
* replaceAll()을 사용해서 if문이나 다른 것을 사용하지 않고 쉽게 바꾸었다.
* replaceAll("문자", "바꿀것")
```
class Solution {
    public String solution(String my_string, String letter) {
        String answer = my_string.replaceAll(letter, "");
        return answer;
    }
}
```
