public class FindConsecutiveTwoNumber {
    public static void main(String[] args) {
        solution(94, 1104); // 9999, 10000
        solution(21, 31); // 2, 3
        solution(9181, 9101); // 89, 90
        solution(92817101, 81711102); // 78099, 78100
        solution(92811103, 911105); // 1000899, 1000900
    }
    static void solution (long s1, long s2) {
        String str1 = Long.toString(s1);
        String str2 = Long.toString(s2);

        /*
        입력값을 정수형 배열 digitCnt[10]로 변환
        예를 들어 s1에 1이 4개 있다면 digitCnt[1] = 4
        digitCnt는 n,n+1을 구간별로 완성하면서 사용한 수들은 제거
        */
        int[] digitCnt1 = makeDigitCnt(str1);
        int[] digitCnt2 = makeDigitCnt(str2);

        //n의 끝부분이 9...9이고 n+1의 끝부분이 0...0이라면 그 부분을 만들어 주는 구간
        //9의 개수 = 0의 개수이므로 9의 개수만 구한다.
        int decreasedNineCnt = Math.max(digitCnt1[9] - digitCnt2[9], 0); //9개수가 줄었다면 얼마나?
        int increasedZeroCnt = Math.max(digitCnt2[0] - digitCnt1[0], 0); //0개수가 늘었다면 얼마나?
        int lastNineCnt = Math.max(decreasedNineCnt, increasedZeroCnt);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        for (int i = 0; i < lastNineCnt; i++){
            sb1.append("9");
            digitCnt1[9]--;

            sb2.append("0");
            digitCnt2[0]--;
        }

        //n과 n+1에서 앞 자리에 영향을 주지 않고 1 증가하는 자리를 만들어 주는 구간
        for (int i = 0; i <= 9; i++){
            if (digitCnt1[i] != digitCnt2[i]){
                //n = 9..9, n+1 = 10..0일 경우를 위한 if문
                if (i == 1){
                    sb2.insert(0, 1);
                    digitCnt2[1]--;
                }
                else{
                    sb1.insert(0, i);
                    digitCnt1[i]--;

                    sb2.insert(0, i+1);
                    digitCnt2[i+1]--;
                }
                break;
            }
        }

        //n과 n+1의 동일한 앞부분을 만들어주는 구간
        StringBuilder front = makeFrontSamePart(digitCnt1);
        sb1.insert(0, front);
        sb2.insert(0, front);

        System.out.println(sb1 + ", " + sb2);
    }


    static StringBuilder makeFrontSamePart(int[] digitCnt) {
        /*
        숫자는 0으로 시작할 수 없으므로 0을 제외한 나머지 숫자들을 오름차순으로 나열한 다음
        만들어진 수 의 두 번째 자리에 남은 0들을 삽입하면
        주어진 숫자들로 만들 수 있는 가장 작은 수가 된다.
         */

        StringBuilder front = new StringBuilder();

        for (int i = 1; i <= 9; i++){
            if (digitCnt[i] != 0){
                front.append(i);
            }
        }
        for (int i = 0; i < digitCnt[0]; i++){
            front.insert(1, 0);
        }
        return front;
    }

    static int[] makeDigitCnt(String str1){
        int[] digitCnt = new int[10];
        for (int i = 0; i < str1.length(); i+=2){
            int digit = str1.charAt(i) - '0';
            int cnt = str1.charAt(i+1) - '0';
            digitCnt[digit] = cnt;
        }
        return digitCnt;
    }
}
