package splavs;

/**
 * Created by Vyacheslav Silchenko on 27.10.2016.
 */
public class SwapSortTask {
    int[] initial = {3, 7, 1, 6, 2, 4, 8, 5};
    int nOfSwaps = 0;

    public void swap(int i, int j, int[] a) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
    }

    public boolean isSorted(int[] a) {
        for (int i = 0; i < a.length-1; i++) {
            if (a[i] > a[i+1]) {
                return false;
            }
        }
        return true;
    }

    public void swapR(int i1, int j1, int[] a) {
        swap(i1, j1, a);
        System.out.println(a);
        nOfSwaps++;
        for (int i = 1; i < a.length; i++) {
            for (int j = i+1; j < a.length; j++) {
                swapR(i, j, a);
                if (isSorted(a)) {
                    System.out.println(nOfSwaps);
                }
            }
        }
        nOfSwaps--;

    }




    public static void main(String[] args) {
        final SwapSortTask swapSortTask = new SwapSortTask();
        swapSortTask.swapR(0, 1, swapSortTask.initial);
    }
}
