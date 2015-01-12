package splavs;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class IntegerCompare {
    public static void main(String[] args) {
        Integer v1 = 42;
        Integer v2 = 42;
        Integer v3 = 1024;
        Integer v4 = 1024;

        System.out.println(v1 == v2); //true
        System.out.println(v3 == v4); //false

        /*Ответ будет зависить вот от этого параметра:
        -Djava.lang.Integer.IntegerCache.high=<size>
                По умолчанию 127 и меньше не сделаешь:
        в private static class IntegerCache {
            ....
            int i = parseInt(integerCacheHighPropValue);
            i = Math.max(i, 127);
            .....
            но вот больше легко, т.е. если скомпилить с -Djava.lang.Integer.IntegerCache.high=1024,
            то будет вообще
            true
                    true

            А вот как генерится значение:
            public static Integer valueOf(int i) {
                if (i >= IntegerCache.low && i <= IntegerCache.high)
                    return IntegerCache.cache[i + (-IntegerCache.low)];
                return new Integer(i);
            }*/
    }

}
