import java.util.*;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);

        final int k = scanner.nextInt();
        final int n = scanner.nextInt();
        final double m = scanner.nextDouble();

        int seed = k;
        int count = 0;
        Random random = new Random(seed);

        while (true) {
            final double number = random.nextGaussian();
            count++;
            if (Double.compare(number, m) >= 0) {
                count = 0;
                seed++;
                random = new Random(seed);
                continue;
            }
            if (count >= n) {
                break;
            }
        }

        System.out.println(seed);
    }
}
