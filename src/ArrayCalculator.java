import java.util.concurrent.RecursiveTask;

public class ArrayCalculator extends RecursiveTask<Integer> {
    private final int[] array;
    private final int start;
    private final int end;

    public ArrayCalculator(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        final int diff = end - start;
        return switch (diff) {
            case 0 -> 0;
            case 1 -> array[start];
            case 2 -> array[start] + array[start + 1];
            default -> forkTasksAndGetResult();
        };
    }

    private int forkTasksAndGetResult() {
        final int middle = (end - start) / 2 + start;

        ArrayCalculator task1 = new ArrayCalculator(array, start, middle);

        ArrayCalculator task2 = new ArrayCalculator(array, middle, end);

        invokeAll(task1, task2);

        return task1.join() + task2.join();
    }
}
