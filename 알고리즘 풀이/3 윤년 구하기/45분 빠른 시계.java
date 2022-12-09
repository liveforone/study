/*
1. 첫째 줄에 두 정수 H와 M이 주어진다. (0 ≤ H ≤ 23, 0 ≤ M ≤ 59)
   그리고 이것은 현재 상근이가 설정한 놓은 알람 시간 H시 M분을 의미한다.
2. 입력 시간은 24시간 표현을 사용한다.
   24시간 표현에서 하루의 시작은 0:0(자정)이고, 끝은 23:59(다음날 자정 1분 전)이다.
3. 현재 시각에서 45분 뺀 시간을 입력과 같은 형식으로 출력한다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;


public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String str = br.readLine();
        StringTokenizer st = new StringTokenizer(str, " ");
        int h = Integer.parseInt(st.nextToken());
        int m = Integer.parseInt(st.nextToken());

        if (h != 0) {
            if (m - 45 < 0) {
                System.out.printf("%d %d", h - 1, 60 - (45 - m));
            } else {
                System.out.printf("%d %d", h, m - 45);
            }
        } else {
            if (m - 45 < 0) {
                System.out.printf("%d %d", 23, 60 - (45 - m));
            } else {
                System.out.printf("%d %d", 0, m - 45);
            }
        }
    }
}
