package com.ocp.basejava;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class MainStream {

    static int[] values = {9, 3, 5, 8, 3, 2, 5, 6, 2};

    public static void main(String[] args) {
        System.out.println(minValue(values));
        System.out.println(oddOrEven(IntStream.of(values).boxed().collect(Collectors.toList())));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values)
                .distinct()
                .sorted()
                .reduce(0, (acc, x) -> acc * 10 + x);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        Stream<Integer> stream = integers.stream();
        if ((integers.stream().mapToInt(x -> x).sum()) % 2 == 0) {
            return stream.filter(x -> x % 2 != 0).collect(Collectors.toList());
        } else return stream.filter(x -> x % 2 == 0).collect(Collectors.toList());
    }
}
