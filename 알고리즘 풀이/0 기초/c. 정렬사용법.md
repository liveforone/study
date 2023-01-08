# 정렬 사용법
## 예제
* import 해주어야 Arrays 사용가능하다.
```
import java.util.*;
class Solution {
    public int solution(int[] sides) {
        Arrays.sort(sides);  //오름차순
        Arrays.sort(sides, Comparator.reverseOrder());  //내림차순
    }
}
```
