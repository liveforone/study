# StringTokenizer로 split 대체하기

## why?
* split()보다 StringTokenizer가 성능면에서 우수하다.

## How?
* import java.util.StringTokenizer; 디펜던시를 가진다.
* StringTokenizer st = new StringTokenizer(나눌 문자열, "기준");
* 몇개가 됬던 분류된 문자열은 st.nextToken()으로 가져온다.
* 기호에 맞게 int형 같은 것으로 변환할때에는 Integer.parseInt등을 쓴다.
