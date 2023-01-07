# 대문자 소문자 변환

## 풀이
* 대소문자 변환은 간단하다
* 하나씩 char를 꺼내서 대문자면 소문자로 소문자면 대문자로 변환하면된다.
* 대소문자 판별은 아스키코드로 하면되구.
* 문제는 대소문자 판별시 ||를 쓰게되면 문제가 발생한다.
* 반드시 아스키코드로 비교할 떄에는 &&를 써야한다.

```
class Solution {
    public String solution(String my_string) {
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<my_string.length(); i++) {
            char ch = my_string.charAt(i);
            if (ch >= 65 && ch <= 90) {
                sb.append(Character.toLowerCase(ch));
            } else if (ch >= 97 && ch <= 122) {
                sb.append(Character.toUpperCase(ch));
            }
        }
        String answer = sb.toString();
        return answer;
    }
}
```
