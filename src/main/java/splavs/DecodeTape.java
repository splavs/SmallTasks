package splavs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Decode Tape program.
 * http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=1819
 * Before submit to uva rename DecodeTape class to Main.
 *
 * @author Vyacheslav Silchenko
 */
class DecodeTape {
    public static void main(String[] args) throws Exception {
        new DecodeTape().parse();
    }

    void parse() {
        for (int buffer = 8192; buffer <= 8192; buffer += 8192) {
            System.out.println("BUFFER SIZE = "+ buffer);
            parse(buffer);

        }
    }

    void parse(int bufferSize) {
        long startTimeMillis = System.currentTimeMillis();
        String line = null;

        StringBuilder result = new StringBuilder();

        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File("C:/Documents and Settings/Vyacheslav Silchenko/IdeaProjects/SmallTasks/target/classes/Decode_the_tape")), bufferSize);
            while ((line = bufferedReader.readLine()) != null) {
                if (line.matches("^\\|[ o]{5}.[ o]{3}\\|$")) {
                    line = line.replaceAll("[\\|.]", "").replaceAll(" ", "0").replaceAll("o", "1");

                    result.append((char) Integer.parseInt(line, 2));
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print(result);
        long endTimeMillis = System.currentTimeMillis();
        System.out.println(endTimeMillis - startTimeMillis);


    }
}
