import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;

public class Main {
    public static void main(String[] args) throws InterruptedException {

        //Создаём пул потоков, с которым будут работать все доступные процессоры
        ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        int[] shop1 = randomArrayGenerate(4, 100);
        int[] shop2 = randomArrayGenerate(5, 100);
        int[] shop3 = randomArrayGenerate(3, 100);

        LongAdder longAdder = new LongAdder();

        Arrays.stream(shop1).forEach(i -> executorService.submit(() -> longAdder.add(i)));
        Arrays.stream(shop2).forEach(i -> executorService.submit(() -> longAdder.add(i)));
        Arrays.stream(shop3).forEach(i -> executorService.submit(() -> longAdder.add(i)));

        executorService.awaitTermination(3, TimeUnit.SECONDS);
        System.out.println("Сумма всех продаж: " + longAdder.sum());
        executorService.shutdown();

    }


    public static int[] randomArrayGenerate(int i, int j) {
        //int i - размер массива, т.е. количество продуктов, которое было куплено.
        //int j - максимальное значение элемента массива, т.е. максимальная цена продукта
        int[] array = new int[i];
        Random random = new Random();
        for (i = 0; i < array.length; i++) {
            array[i] = random.nextInt(j);
        }
        System.out.println("Цены продуктов: " + Arrays.toString(array));
        return array;
    }
}
