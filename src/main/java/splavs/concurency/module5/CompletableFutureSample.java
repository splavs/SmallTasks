package splavs.concurency.module5;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vyacheslav Silchenko on 07.06.2016.
 */
public class CompletableFutureSample {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CompletableFuture future = CompletableFuture.supplyAsync(() -> {
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return  10;})
                .thenApply(i -> i + 2)
                .thenApply(i -> i * 2)
                .thenAcceptAsync(System.out::println)
                .thenRunAsync(() -> System.out.println("I was ran from async run"));

//        TimeUnit.SECONDS.sleep(1);
//        System.out.println(future.get());
    }
}
