/*
1. 연도가 주어졌을 때, 윤년이면 1, 아니면 0을 출력하는 프로그램을 작성하시오.
2. 윤년은 4, 100, 400으로 나누어떨어지는 년도를 의미한다.
 */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {

    public static void main(String[] args) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int year = Integer.parseInt(br.readLine());
        
        if(year % 4 == 0) {
            if(year % 400 == 0) {
                System.out.print(1);
            } else if (year % 100 == 0) {
                System.out.print(0);
            } else {
                System.out.print(1);
            }
        } else {
            System.out.print(0);
        }
    }
}
