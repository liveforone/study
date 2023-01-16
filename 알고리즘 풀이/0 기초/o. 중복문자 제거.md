# 중복 문자제거

## 풀이
* 간단하다. 문자의 형태로 set에 넣으면 빈칸도 유지하면서 중복을 없애준다.
```
import java.util.*;
class Solution {
    public String solution(String my_string) {
        char[] chars = my_string.toCharArray();
        Set<Character> set = new LinkedHashSet<>();
        for(char c : chars){
            set.add(c);
        }
        StringBuilder sb = new StringBuilder();
        for(char c : set){
            sb.append(c);
        }
        String answer = sb.toString();
        return answer;
    }
}
```
