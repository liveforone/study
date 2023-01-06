# 최빈값 - 외우기
```
import java.util.*;
class Solution {
    public int solution(int[] array) {
        int answer=0;
        int arrayMax=0;
        for(int i=0; i<array.length; i++) {
            if(array[i]>arrayMax) {
                arrayMax=array[i];
            }
        }
        
        int[] count = new int[arrayMax+1];
        for(int i=0; i<array.length; i++) {
            count[array[i]]++;
        }
        
        int countMax=0;
        for(int i=0; i<count.length; i++) {
            if(count[i]>countMax) {
                countMax=count[i];
                answer=i;
            }
        }                                            
        
        int rep=0;
        for(int i=0; i<count.length; i++) {
            if(countMax==count[i]) {
                rep++;
            }
        }
        
        if(rep!=1) {
            answer=-1;
        } 
        return answer;
    }              
}
```
