package lambdasinaction.chap5;

import org.junit.Test;

import java.net.URL;
import java.util.*;
import java.util.function.IntSupplier;
import java.util.stream.*;
import java.nio.charset.Charset;
import java.nio.file.*;

/**
 * @author Administrator
 */
public class BuildingStreams {

    @Test
    public void testIterator() throws Exception {
        Integer reduce = Stream.iterate(1, p -> p + 1).limit(100).reduce(0, (a, b) -> a + b);
        System.out.println(reduce);


        double sum = Stream.iterate(1, p -> p + 1).limit(100).mapToDouble(p -> p.doubleValue()).sum();
        System.out.println(sum);

        /* fibbonnaci */
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]}).limit(20).forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));

        //不接参数 -> 返回数值
        Stream.generate(() -> System.currentTimeMillis()).limit(100).forEach(System.out::println);
        // the same
        Stream.generate(System::currentTimeMillis).limit(100).forEach(System.out::println);
    }

    @Test
    public void testFlatMap() throws Exception {
        String ss = "Hello";

        String[] aa = ss.split("");

        String[] bb = {"H", "e", "l", "l", "o"};


        String[] strings = {"Hello", "World"};

        //Arrays.stream接收一个数组返回一个流
        List<Stream<String>> streamList = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                map(str -> Arrays.stream(str)).
                collect(Collectors.toList());

        for (Stream<String> stringStream : streamList) {
            System.out.println(stringStream.collect(Collectors.toList()));
        }


        List<String[]> collect = Arrays.stream(strings).map(p -> p.split("")).collect(Collectors.toList());

        for (String[] strings1 : collect) {
            System.out.println(Arrays.asList(strings1));
        }

        //分步写(map)

        Stream<String[]> stream = Arrays.asList(strings).stream().
                map(str -> str.split(""));

        Stream<Stream<String>> streamStream = stream.map(strings1 -> Arrays.stream(strings1));
        List<Stream<String>> streamList1 = streamStream.collect(Collectors.toList());


        List<String> stringList = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                flatMap(str -> Arrays.stream(str))
                .collect(Collectors.toList());


        //分步写(流只能消费一次)(flatMap)
        Stream<String[]> stream1 = Arrays.asList(strings).stream().
                map(str -> str.split(""));

        Stream<String> stringStream = stream1.flatMap(strings1 -> Arrays.stream(strings1));

        List<String> stringList1 = stringStream.collect(Collectors.toList());
    }

    public static void main(String... args) throws Exception {

        // Stream.of
        Stream<String> stream = Stream.of("Java 8", "Lambdas", "In", "Action");
        stream.map(String::toUpperCase).forEach(System.out::println);

        // Stream.empty
        Stream<String> emptyStream = Stream.empty();

        // Arrays.stream
        int[] numbers = {2, 3, 5, 7, 11, 13};

        System.out.println(Arrays.stream(numbers).sum());

        // Stream.iterate
        Stream.iterate(0, n -> n + 2)
                .limit(10)
                .forEach(System.out::println);

        // fibonnaci with iterate
        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .forEach(t -> System.out.println("(" + t[0] + ", " + t[1] + ")"));

        Stream.iterate(new int[]{0, 1}, t -> new int[]{t[1], t[0] + t[1]})
                .limit(10)
                .map(t -> t[0])
                .forEach(System.out::println);

        // random stream of doubles with Stream.generate
        Stream.generate(Math::random)
                .limit(10)
                .forEach(System.out::println);

        // stream of 1s with Stream.generate
        IntStream.generate(() -> 1)
                .limit(5)
                .forEach(System.out::println);

        IntStream.generate(new IntSupplier() {
            @Override
            public int getAsInt() {
                return 2;
            }
        }).limit(5)
                .forEach(System.out::println);


        IntSupplier fib = new IntSupplier() {
            private int previous = 0;
            private int current = 1;

            @Override
            public int getAsInt() {
                int nextValue = this.previous + this.current;
                this.previous = this.current;
                this.current = nextValue;
                return this.previous;
            }
        };
        IntStream.generate(fib).limit(10).forEach(System.out::println);

        String path = BuildingStreams.class.getClassLoader().getResource("").getPath();
        // The quick brown fox jumped over the lazy dog
        // The lazy dog jumped over the quick brown fox
        long uniqueWords = Files.lines(Paths.get(path.substring(1) + "lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .distinct()
                .count();

        System.out.println("There are " + uniqueWords + " unique words in data.txt");


        System.out.println(Files.lines(Paths.get(path.substring(1) + "lambdasinaction/chap5/data.txt"), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split(" ")))
                .collect(Collectors.toList()));

    }
}
