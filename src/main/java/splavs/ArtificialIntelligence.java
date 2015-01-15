package splavs;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Artificial Intelligence program.
 * http://uva.onlinejudge.org/index.php?option=com_onlinejudge&Itemid=8&page=show_problem&problem=478
 * Before submit to uva rename ArtificialIntelligence class to Main.
 *
 * @author Vyacheslav Silchenko
 */
class ArtificialIntelligence {
    public static void main(String[] args) throws Exception {
        new ArtificialIntelligence().parse();
    }

    double parseValue(String dataFieldStr) {
        double result = 0;
        char m = dataFieldStr.charAt(dataFieldStr.length()-2);
        switch (m) {
            case 'm':
                result = Double.parseDouble(dataFieldStr.substring(2, dataFieldStr.length() - 2));
                result *= 0.001; //m
                break;
            case 'k':
                result = Double.parseDouble(dataFieldStr.substring(2, dataFieldStr.length() - 2));
                result *= 1000; //k
                break;
            case 'M':
                result = Double.parseDouble(dataFieldStr.substring(2, dataFieldStr.length() - 2));
                result *= 1000000; //M
                break;
            default:
                result = Double.parseDouble(dataFieldStr.substring(2, dataFieldStr.length() - 1));
                break;
        }

        return result;

    }

    void parse() {
        String line;
        String dataFieldStr;
        String dataFieldRegex = "[PUI]=[\\d]+.?[\\d]*[mkM]?[WVA]";
        Pattern dataFieldPattern = Pattern.compile(dataFieldRegex);
        Matcher matcher;
        int numberOfTestCases;
        int k = 0;
        double P = 0;
        double U = 0;
        double I = 0;

        try {

            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
            line = bufferedReader.readLine();
            numberOfTestCases = Integer.parseInt(line);

            while ( ((line = bufferedReader.readLine()) != null) && k++ <= numberOfTestCases) {
                P = U = I = 0;
                matcher = dataFieldPattern.matcher(line);
                while (matcher.find()) {
                    dataFieldStr = matcher.group();

                    switch (dataFieldStr.charAt(0)) {
                        case 'P':
                            P = parseValue(dataFieldStr);
                            break;
                        case 'U':
                            U = parseValue(dataFieldStr);
                            break;
                        case 'I':
                            I = parseValue(dataFieldStr);
                            break;
                    }
                }
                System.out.println("Problem #" + k);
                if (P == 0) {
                    P = U * I;
                    System.out.printf(Locale.ENGLISH, "P=%.2fW\n\n", P);
                } else if (U == 0) {
                    U = P / I;
                    System.out.printf(Locale.ENGLISH, "U=%.2fV\n\n", U);
                } else if (I == 0) {
                    I = P / U;
                    System.out.printf(Locale.ENGLISH, "I=%.2fA\n\n", I);
                }
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
