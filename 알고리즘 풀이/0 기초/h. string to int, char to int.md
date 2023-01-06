# string to int, char to int

# 풀이
* 아스키코드 48이 0이고, 57이 9이다.
* int인 문자형을 뽑기위해선 Character.getNumericValue(ch) 를 사용하면된다.
```
class Solution {
    public int solution(String my_string) {
        int answer = 0;
        for (int i = 0; i < my_string.length(); i++) {
            char ch = my_string.charAt(i);
            if (48 <= ch && ch <= 57) {
                answer += Character.getNumericValue(ch);
            }
        }
        return answer;
    }
}
```
