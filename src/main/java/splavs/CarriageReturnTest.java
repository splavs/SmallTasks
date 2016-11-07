package splavs;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class CarriageReturnTest {
    private final int PRICE_CHECKER_LINE_WIDTH = 10;
    private final int MIN_NUMBER_OF_CHARS_BY_LINE = 3;
    private final String LINE_BREAK = "\n";



    public static void main(String[] args) {
        String str = "12345678901234567890123";
        String delimiters = "((?<!\\d)\\.(?!\\d))|( )";
        System.out.printf("%s", new CarriageReturnTest().wrapLineByTwo(str, delimiters));

    }

    /**
     * Smart wrapping str by two lines. Line break inserted between words where possible.
     * Rest of the line smart truncated (by word) to fulfill maxLineWidth
     * @param line to parse
     * @param delimiters regex containing delimiters
     * @return new parsed string
     */
    private String wrapLineByTwo(String line, String delimiters) {
        return new StringBuilder(line).insert(PRICE_CHECKER_LINE_WIDTH, LINE_BREAK).toString();
/*
        Pattern patternDelimiters = Pattern.compile(delimiters);
        String result = line;
        int breakLinePosition = -1;

        if (line.length() > PRICE_CHECKER_LINE_WIDTH) {
            Matcher matcher = patternDelimiters.matcher(line.substring(0, PRICE_CHECKER_LINE_WIDTH));
            while (matcher.find()) {
                breakLinePosition = matcher.end();
            }

            if (breakLinePosition == -1) {
                if ((line.length() - PRICE_CHECKER_LINE_WIDTH) > MIN_NUMBER_OF_CHARS_BY_LINE) {
                    breakLinePosition = PRICE_CHECKER_LINE_WIDTH;
                } else {
                    breakLinePosition = PRICE_CHECKER_LINE_WIDTH - (MIN_NUMBER_OF_CHARS_BY_LINE - (line.length() - PRICE_CHECKER_LINE_WIDTH));
                }
            }

            breakLinePosition = PRICE_CHECKER_LINE_WIDTH;

            result = new StringBuilder(line).insert(breakLinePosition, LINE_BREAK).toString();
        }

        //smart truncate rest of the line
        if ( (line.length() - breakLinePosition) > PRICE_CHECKER_LINE_WIDTH) {
            int endLinePosition = -1;
            Matcher matcher = patternDelimiters.matcher(line.substring(breakLinePosition, breakLinePosition + PRICE_CHECKER_LINE_WIDTH));
            while (matcher.find()) {
                endLinePosition = matcher.end();
            }

            if (endLinePosition == -1) {
                endLinePosition = breakLinePosition + PRICE_CHECKER_LINE_WIDTH;
            } else {
                endLinePosition += breakLinePosition;
            }

            result = result.substring(0, endLinePosition + 1);
        }

        return result;
*/
    }

}


