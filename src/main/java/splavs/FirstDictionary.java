package splavs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Decode Tape program.
 * http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1756
 * Before submit to uva rename DecodeTape class to Main.
 *
 * @author Vyacheslav Silchenko
 */
class FirstDictionary {
    public static void main(String[] args) throws Exception {
        new FirstDictionary().parse();
    }

    void parse() {
        String line;
        String[] parsedWords;

        Set<String> resultDictionary = new TreeSet<String>();

        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            while ((line = bufferedReader.readLine()) != null) {
                parsedWords = line.toLowerCase().split("[^a-zA-Z]");
                resultDictionary.addAll(Arrays.asList(parsedWords));
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String resultOut : resultDictionary) {
            if (!resultOut.equals("")) {
                System.out.println(resultOut);
            }
        }



    }
}
