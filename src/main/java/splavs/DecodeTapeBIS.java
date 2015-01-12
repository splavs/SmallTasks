package splavs;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

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
        int numberOfBytes;

        String line = null;

        StringBuilder result = new StringBuilder();

        try {

            while ((numberOfBytes = bufferedInputStream.read(byteBuffer, 0, bufferSize)) >= 0) {
                line = new String(byteBuffer);
                if (line.matches("^\\|[ o]{5}.[ o]{3}\\|\\n$")) {
                    line = line.replaceAll("[\\|.]", "").replaceAll(" ", "0").replaceAll("o", "1").trim();

                    result.append((char) Integer.parseInt(line, 2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print(result);

    }
}
