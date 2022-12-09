/*
1. 첫 줄에는 정수 x가 주어진다.
2. 다음 줄에는 정수 y가 주어진다.
3. 점 (x, y)의 사분면 번호(1, 2, 3, 4 중 하나)를 출력한다
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int x = Integer.parseInt(br.readLine());
        int y = Integer.parseInt(br.readLine());

        if (x > 0) {
            if (y > 0) {
                System.out.print(1);
            } else if (y < 0) {
                System.out.print(4);
            }
        } else if (x < 0) {
            if (y > 0) {
                System.out.print(2);
            } else if (y < 0) {
                System.out.print(3);
            }
        }
    }
}
