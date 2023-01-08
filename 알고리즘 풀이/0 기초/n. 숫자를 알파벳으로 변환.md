# int를 알파벳으로 변환
* 숫자와 소문자 알파벳은 아스키코드로 49가 차이남
```
String answer ="";
int age = 58;
String str = Integer.toString(age);
for(int i=0; i<str.length(); i++) {            
     answer += (char) (str.charAt(i)+49);  
}
```
