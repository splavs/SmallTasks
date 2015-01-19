package splavs;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Automatic Poetry program.
 * http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1302
 * Before submit to uva rename AutomaticPoetry class to Main.
 *
 * @author Vyacheslav Silchenko
 */
public class AutomaticPoetry {
    public static void main(String[] args) throws Exception {
        new AutomaticPoetry().parse();
    }

    void parse() {
        String line1;
        String line2;
        StringBuilder line2Build;
        String[] s = new String[4];
        int numberOfStringPairs = 0;
        int k = 0;

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            line1 = bufferedReader.readLine();
            numberOfStringPairs = Integer.parseInt(line1);

            while ( ((line1 = bufferedReader.readLine()) != null) && ((line2 = bufferedReader.readLine()) != null) && (k++ <= numberOfStringPairs) ) {
                if (line1.matches("^[a-z ]*<[a-z ]*>[a-z ]*<[a-z ]*>[a-z ]*$")) {
                    s = line1.split("[<>]", 5);
                    line1 = line1.replace("<", "").replace(">", "");
                    System.out.println(line1);

                    line2Build = new StringBuilder();

                    line2Build.append(line2.substring(0, line2.length() - 3));
                    line2Build.append(s[3]).append(s[2]).append(s[1]).append(s[4]);

                    System.out.println(line2Build);
                }
            }

            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
