package Box_chat_UDP;

import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.Collectors;

public class StreamTest {
    public static void main(String[] args) {
        var stringStream = Arrays.stream(new String[]{"1", "1", "2", "3", "4", "5", "6", "7", "8", "9", "0"});

        var listString = stringStream
//                .filter(s -> s.equals("4") || s.equals("6") || s.equals("9"))
                .map((Function<String, Object>) s -> s + "0")
//                .mapToInt(s -> Integer.parseInt(s.toS))
//                .flatMapToDouble(s -> DoubleStream.builder().build()).boxed()
//                .distinct()
                .collect(Collectors.toList());

        listString.forEach(System.out::println);
    }
}
