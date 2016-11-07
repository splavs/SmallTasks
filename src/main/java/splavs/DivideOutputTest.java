package splavs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class DivideOutputTest {
    public static void main(String[] args) {
        Double weight = Double.valueOf(65);
        String result = String.valueOf(weight / 1000);

        System.out.println(result);

        String formatTest = String.format("weight = %f", weight);
        System.out.println(formatTest);

        String formatTest2 = String.format("%.2f", 123.1234);
        System.out.println(formatTest2);

        String itemIdentifier;
        Double itemWeight;
        String request = "F2526691009668";

        Pattern patternWeight25 = Pattern.compile("25([A-Za-z0-9\\.]){5}(\\d){5}\\d");
        Matcher matcherWeight25 = patternWeight25.matcher(request);
        if (matcherWeight25.find() ) {
            itemIdentifier = matcherWeight25.group();
            String weightAlias = itemIdentifier.substring(0, 7);
            itemWeight = Double.valueOf(itemIdentifier.substring(7, 12));
            itemIdentifier = weightAlias;

            System.out.printf("OLD request = %s, itemIdentifier = %s, itemWeight = %f\n", request, itemIdentifier, itemWeight);

        }

        Pattern patternWeight25NEW = Pattern.compile("F(25(?:[A-Za-z0-9\\.]){5})((?:\\d){5})(\\d)");
        Matcher matcherWeight25NEW = patternWeight25NEW.matcher(request);
        if (matcherWeight25NEW.matches() && matcherWeight25NEW.groupCount() == 3) { // request should exactly match pattern
            itemWeight = Double.valueOf(matcherWeight25NEW.group(2));
            itemIdentifier = matcherWeight25NEW.group(1);

            System.out.printf("NEW request = %s, itemIdentifier = %s, itemWeight = %f\n", request, itemIdentifier, itemWeight);
        }


        request = "F2700193801474";

        Pattern patternWeight27 = Pattern.compile("27\\d([A-Za-z0-9\\.]){5}(\\d){4}\\d");
        Matcher matcherWeight27 = patternWeight27.matcher(request);
        if (matcherWeight27.find()) {
            itemIdentifier = matcherWeight27.group();
            String nomenclatureCode = itemIdentifier.substring(2, 8);
            itemWeight = Double.valueOf(itemIdentifier.substring(8, 12));
            //TODO Remove all leading zeros to implement UPC barcode
            itemIdentifier = nomenclatureCode.startsWith("00") ? nomenclatureCode.substring(1) : nomenclatureCode;

            System.out.printf("OLD request = %s, itemIdentifier = %s, itemWeight = %f\n", request, itemIdentifier, itemWeight);
        }

        Pattern patternWeight27NEW = Pattern.compile("F(27)(\\d(?:[A-Za-z0-9\\.]){5})((?:\\d){4})(\\d)");
        Matcher matcherWeight27NEW = patternWeight27NEW.matcher(request);
        if (matcherWeight27NEW.matches() && matcherWeight27NEW.groupCount() == 4) { // request should exactly match pattern
            String nomenclatureCode = matcherWeight27NEW.group(2);
            itemWeight = Double.valueOf(matcherWeight27NEW.group(3));
            //TODO Check in ID_IDN_PS table how itemIdentifieres are stored.
            itemIdentifier = nomenclatureCode.startsWith("00") ? nomenclatureCode.substring(1) : nomenclatureCode;

            System.out.printf("UPCA request = %s, itemIdentifier = %s, itemWeight = %f\n", request, itemIdentifier, itemWeight);
        }

        request = "A054881013048";

        Pattern patternUpca = Pattern.compile("A([A-Za-z0-9\\.]{12})");
        Matcher matcherUpca = patternUpca.matcher(request);
        if (matcherUpca.matches() && matcherUpca.groupCount() == 1) { // request should exactly match pattern
            itemIdentifier = String.format("0%s", matcherUpca.group(1)); // Add leading 0 to 13 symbols

            System.out.printf("NEW request = %s, itemIdentifier = %s\n", request, itemIdentifier);
        }

        Integer x = 3 + 4;
        System.out.println(x);


    }
}
