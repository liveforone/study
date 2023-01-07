# list를 이용해 배열대체

## 언제사용?
* 배열을 리턴해야하는 경우에 배열의 길이를 특정하기 어렵다면 사용하라

## 풀이
* 길이를 정의 안해도 되는 list를 선언후 넣어준다.
* 배열을 선언하고 배열의 길이를 list의 길이로 한다.
* 정수가 아닐경우 배열[i] = list.get(i); 로 하면되지만
* 정수일경우 배열[i] = list.get(i).intValue();로 해주면된다.
```
import java.util.*;
class Solution {
    public int[] solution(int n, int[] numlist) {
        List<Integer> arr = new ArrayList<>();
        for (int i=0; i<numlist.length; i++) {
            if (numlist[i] % n == 0) {
                arr.add(numlist[i]);
            }
        }
        int[] answer = new int[arr.size()];
        for (int i=0; i<arr.size(); i++) {
            answer[i] = arr.get(i).intValue();
        }
        return answer;
    }
}
```
