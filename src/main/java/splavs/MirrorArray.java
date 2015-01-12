package splavs;

import java.util.Arrays;

/**
 * Class description goes here.
 *
 * @author Vyacheslav Silchenko
 */
public class MirrorArray {
    public static void main(String[] args) {
        int[] array = {1, 2, 3, 4};
        System.out.println(Arrays.toString(array));

        for (int i = 0; i < Math.floor(array.length / 2); i++) {
            array[i] = array[i] ^ array[array.length - i - 1];
            array[array.length - i - 1] = array[i] ^ array[array.length - i - 1];
            array[i] = array[i] ^ array[array.length - i - 1];
        }

        System.out.println(Arrays.toString(array));
    }


}
