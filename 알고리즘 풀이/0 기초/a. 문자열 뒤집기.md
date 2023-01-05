# 문자열 뒤집기

## 풀이
* 두가지가 있다.
* 첫째는 StringBuffer를 쓰는것
* 둘째는 char[]을 만들어 for문을 돌리는것
* 나는 첫째를 쓰겠다.
```
class Solution {
    public String solution(String my_string) {
        String answer = new StringBuffer(my_string).reverse().toString();
        return answer;
    }
}
```
