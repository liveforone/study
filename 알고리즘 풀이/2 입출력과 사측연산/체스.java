/*
1. 체스는 총 16개의 피스를 사용하며, 킹 1개, 퀸 1개, 룩 2개, 비숍 2개, 나이트 2개, 폰 8개로 구성
2. 동혁이는 검정색 피스는 모두 있었지만 흰색피스의 개수가 올바르지않았다.
3. 동혁이가 발견한 흰색 피스의 개수가 주어졌을 때,
   몇 개를 더하거나 빼야 올바른 세트가 되는지 구하는 프로그램을 작성하시오.
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

        int[] chess = {1, 1, 2, 2, 2, 8};
        int[] inputChess = new int[6];

        for (int i=0; i<6; i++) {
            inputChess[i] = Integer.parseInt(st.nextToken());
        }

        for (int i=0; i<inputChess.length; i++) {
            System.out.print(chess[i] - inputChess[i] + " ");
        }
    }
}

/*
* 풀이 *
체스의 올바른 값에서 입력받은 값을 빼면 원하는 값을 출력할 수 있다.
*/
