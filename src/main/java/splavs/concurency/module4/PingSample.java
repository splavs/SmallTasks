package splavs.concurency.module4;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by Vyacheslav Silchenko on 05.06.2016.
 */
public class PingSample {
    public static void main(String[] args) throws IOException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        //pool.schedule(() -> System.out.println("ping google.com"), 1, TimeUnit.SECONDS);

        //pool.scheduleAtFixedRate(() -> System.out.println("ping google.com 2"), 2, 1, TimeUnit.SECONDS);

        final InetAddress inetAddress = InetAddress.getByName("google.com");
        System.out.println(inetAddress.isReachable(20));
        pool.scheduleAtFixedRate(() -> {
            try {
                System.out.println(inetAddress.isReachable(20));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }, 1, 20, TimeUnit.MILLISECONDS);



    }
}
