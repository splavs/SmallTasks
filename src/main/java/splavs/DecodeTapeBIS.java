package splavs;

import java.io.BufferedInputStream;

/**
 * Decode Tape program.
 * http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1819
 * Before submit to uva rename DecodeTape class to Main.
 *
 * @author Vyacheslav Silchenko
 */
class DecodeTapeBIS {
    public static void main(String[] args) {
        new DecodeTapeBIS().parse();
    }

    void parse() {
        BufferedInputStream bufferedInputStream = new BufferedInputStream(System.in);
        int bufferSize = 12;
        byte[] byteBuffer = new byte[bufferSize];

        StringBuilder result = new StringBuilder();

        try {

            while (bufferedInputStream.read(byteBuffer, 0, bufferSize) >= 0) {
                char c = 0;
                if (124 == byteBuffer[0]) { // |
                    for (byte bc : byteBuffer) {
                        if ((32 == bc) || (111 == bc)) { // 32 == space 111 == o
                            c <<= 1;
                            if (111 == bc) {
                                c++;
                            }
                        }
                    }
                    result.append(c);
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print(result);

    }
}
