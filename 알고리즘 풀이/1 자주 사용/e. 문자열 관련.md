## String
```
toLowerCase();
toUpperCase();
length();

[배열만들기]
String str = "12345";
String[] Arr = str.split("");  

[문자열 뒤집기]
String str = new StringBuilder(words).reverse().toString();ㅣ
```
## StringBuilder
```
StringBuilder sb = new StringBuilder(문자열 또는 char[] 배열);
sb.append(문자열);					// 문자열 추가
sb.insert(int index, String str);		// 위치에 삽입할때는 insert
sb.charAt(int index);				// 해당 인덱스 문자 반환
sb.indexOf(String str);				// 해당 문자열의 인덱스 반환
sb.substring(int start, int end);		// 문자열 슬라이싱
sb.replace(int start, int end, String str);	// 시작과 끝 사이를 str로 교체
sb.delete(int start, int end);			// 시작부터 끝까지 제거
sb.reverse()					// 문자열 뒤집기
sb.length();					// 문자열의 크기
sb.toString();					// String으로 변환
```

## StringTokenizer
```
import java.util.StringTokenizer;
String str = "1, 2, 3";
StringTokenizer st = new StringTokenizer(str, ",");
int re1 = Integer.parseInt(st.nextToken());
int re2 = Integer.parseInt(st.nextToken());
int re3 = Integer.parseInt(st.nextToken());
```
