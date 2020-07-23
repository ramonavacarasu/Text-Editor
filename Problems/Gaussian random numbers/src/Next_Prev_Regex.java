import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Next_Prev_Regex {

    public static int index = 1;
    public static int index1 = 1;
    public static Map<Integer, String> map = new LinkedHashMap<>();
    public static Map<Integer, String> map1 = new LinkedHashMap<>();

    public static void main(String[] args) {

        String regex = "number";
        String input = "For the given numbers K, N, and M number" +
                " find the first seed that is greater or equal number" +
                " to K where each of number N Gaussian numbers is less than or equal to M.";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        while (matcher.find()) {
            map.put(index, matcher.start() + " " + matcher.end());
            index++;
        }

        for (int x : map.keySet()) {
            System.out.println(map.get(x));
        }

        for (int x : map.keySet()) {
            String bounds = map.get(x);

            String[] b = bounds.split(" ");

            System.out.println(input.substring(Integer.parseInt(b[0]), Integer.parseInt(b[1])));
        }


        if (input.contains(regex)) {
            int i = input.indexOf(regex);
            while (i >= 0) {
                map1.put(index1, i + " " + (i + regex.length()));
                index1++;
                i = input.indexOf(regex, i + 1);
            }
        }

        for (int x : map1.keySet()) {
            System.out.println(map1.get(x));
        }

        for (int x : map1.keySet()) {
            String bounds = map1.get(x);
            String[] b = bounds.split(" ");
            System.out.println(input.substring(Integer.parseInt(b[0]), Integer.parseInt(b[1])));
        }
    }
}
