package og.aoc.day1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Day1 {
    private static final Map<String, Character> spelledDigits = Map.of(
            "zero", '0',
            "one", '1',
            "two", '2',
            "three", '3',
            "four", '4',
            "five", '5',
            "six", '6',
            "seven", '7',
            "eight", '8',
            "nine", '9'
    );

    public static void main(String[] args) {
        String filePath = "src/main/resources/day1/input.txt";
        try (Stream<String> lines = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            List<Integer> calibrationValues = new ArrayList<>();
            lines.forEach(line -> {
                Character firstDigit = null;
                Character lastDigit = null;
                StringBuilder calibrationValue = new StringBuilder();

                for (int i = 0; i < line.length(); ) {
                    Optional<Character> digit = findDigit(line.substring(i));
                    if (digit.isPresent()) {
                        if (firstDigit == null) {
                            firstDigit = digit.get();
                        }
                        lastDigit = digit.get();
                    }
                    i++;
                }

                if (firstDigit != null) {
                    calibrationValue.append(firstDigit).append(lastDigit);
                    calibrationValues.add(Integer.parseInt(calibrationValue.toString()));
                } else {
                    System.out.println("No digits found in line: " + line);
                }
            });
            Integer sumCalibrationValues = calibrationValues.stream().reduce(Integer::sum).orElse(0);
            System.out.println("Puzzle Answer : " + sumCalibrationValues);
        } catch (IOException e) {
            System.out.println("Error reading file" + e);
        }
    }

    private static Optional<Character> findDigit(String s) {
        for (Map.Entry<String, Character> entry : spelledDigits.entrySet()) {
            if (s.startsWith(entry.getKey())) {
                return Optional.of(entry.getValue());
            }
        }
        if (!s.isEmpty() && Character.isDigit(s.charAt(0))) {
            return Optional.of(s.charAt(0));
        }
        return Optional.empty();
    }
}
