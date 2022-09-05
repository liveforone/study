/*
1. 한줄에 숫자 a와 b를 공백을 두고 입력받는다.
2. 두 수를 더한 수를 출력한다.
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        StringTokenizer st = new StringTokenizer(str, " ");  //성능면에서 좋은 토크나이저로 분류해준다.

        int a = Integer.parseInt(st.nextToken()) + Integer.parseInt(st.nextToken());

        System.out.println(a);
    }
}
