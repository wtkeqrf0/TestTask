import Enums.Cause;
import Exceptions.ClanNotFoundException;
import GoldChanges.Transaction;
import GoldChanges.Treasury;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

// дополнительный класс для тестирования т̶е̶с̶т̶о̶в̶о̶г̶о̶ задания
public class Main {
    public static void main(String[] args) {
        Treasury treasury = new Treasury();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {

            System.out.println("Введите количество пользователей");
            int threads = Integer.parseInt(br.readLine());

            System.out.println("Введите количество пополнений казны каждым пользователем");
            int repeats = Integer.parseInt(br.readLine());

            startThreads(threads, repeats, treasury); //создание потоков

        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Transaction> result = treasury.getChangeLog();

        result.sort(Comparator.comparing(Transaction::getCreated));

        System.out.println(("\t".repeat(15)) + "Change Log\n\n" + result
                + "\t\tОбщее время выполнения - " + (end - start) + " ns");
    }

    static long start, end;

    private static void startThreads(int threads, int repeats, Treasury treasury) {
        ExecutorService service = Executors.newFixedThreadPool(threads);
        CountDownLatch latch = new CountDownLatch(threads * repeats);

        start = System.nanoTime(); //засечение времени выполнения

        IntStream.range(0, threads * repeats).forEach(e -> {
            service.execute(() -> {
                try {
                    treasury.createTransaction(
                            1, Thread.currentThread().getName(), "0", Cause.TASK);
                } catch (ClanNotFoundException ex) {
                    System.out.println("Clan does not exists");
                }
            });
            latch.countDown(); //сообщение о конце выполнения работы потоком
        });

        try {
            if (!latch.await(2000, TimeUnit.MILLISECONDS)) throw new InterruptedException();

        } catch (InterruptedException e) {
            System.out.println("Что-то пошло не так!");
            System.exit(0);
        }

        end = System.nanoTime();
        service.shutdown();
    }
}