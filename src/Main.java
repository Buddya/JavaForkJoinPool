import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class Main {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        System.out.println("Размер 2_000_000");
        int[] array = InitArray(2_000_000);
        System.out.println("Время выполнения одним потоком: " + casualCalculation(array));
        System.out.println("Время выполнения несколькими потоками: " + multiThreadingCalculation(array));
        System.out.println();

        System.out.println("Размер 2_000");
        int[] array1 = InitArray(2_000);
        System.out.println("Время выполнения одним потоком: " + casualCalculation(array1));
        System.out.println("Время выполнения несколькими потоками: " + multiThreadingCalculation(array1));
        System.out.println();

        System.out.println("Размер 200");
        int[] array2 = InitArray(200);
        System.out.println("Время выполнения одним потоком: " + casualCalculation(array2));
        System.out.println("Время выполнения несколькими потоками: " + multiThreadingCalculation(array2));
        System.out.println();

        System.out.println("Размер 20");
        int[] array3 = InitArray(20);
        System.out.println("Время выполнения одним потоком: " + casualCalculation(array3));
        System.out.println("Время выполнения несколькими потоками: " + multiThreadingCalculation(array3));
        System.out.println();
    }

    private static long casualCalculation(int[] array) throws InterruptedException {
        final long start = System.currentTimeMillis();

        final int result = Arrays.stream(array).sum();
        System.out.println("Сумма = " + result);
        System.out.println("Среднее арифметическое = " + result / array.length);
        return System.currentTimeMillis() - start;
    }

    private static long multiThreadingCalculation(int[] array) throws ExecutionException, InterruptedException {
        final long start = System.currentTimeMillis();

        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());
        ForkJoinTask<Integer> forkJoinTask = forkJoinPool.submit(new ArrayCalculator(array, 0, array.length));
        int result = forkJoinTask.get();

        System.out.println("Сумма = " + result);
        System.out.println("Среднее арифметическое = " + result / array.length);
        return System.currentTimeMillis() - start;
    }

    private static int[] InitArray(int capacity) {
        final int THRESHOLD = 500;
        final Random random = new Random();
        final int[] array = new int[capacity];
        for (int i = 0; i < array.length; i++) {
            array[i] = random.nextInt(THRESHOLD);
        }
        return array;
    }
}
