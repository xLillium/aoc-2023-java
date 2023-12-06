package og.aoc.day3;

import java.awt.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class Day3 {

    public static final int[] OFFSET = new int[]{-1, 0, 1};

    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/day3/input.txt";
        List<String> schematic = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        part1(schematic);
        part2(schematic);
    }

    private static void part2(List<String> schematic) {
        Map<Point, List<Integer>> gearsAndPartNumbersAssociation = new HashMap<>();
        for (int i = 0; i < schematic.size(); i++) {
            for (int j = 0; j < schematic.get(i).length(); j++) {
                if (Character.isDigit(schematic.get(i).charAt(j))) {
                    StringBuilder currentNumber = new StringBuilder();
                    Set<Point> adjacentAsterisks = new HashSet<>();
                    int k = j;
                    while (k < schematic.get(i).length() && Character.isDigit(schematic.get(i).charAt(k))) {
                        currentNumber.append(schematic.get(i).charAt(k));
                        adjacentAsterisks.addAll(getAdjacentAsterisks(schematic, i, k));
                        k++;
                    }
                    for (Point adjacentAsterisk : adjacentAsterisks) {
                        gearsAndPartNumbersAssociation.computeIfAbsent(adjacentAsterisk, partNumbers -> new ArrayList<>())
                                .add(Integer.valueOf(currentNumber.toString()));
                    }
                    j = k - 1;
                }
            }
        }
        int sumOfGearRatios = computeSumOfGearRatios(gearsAndPartNumbersAssociation);
        System.out.println("Sum of all the gear ratios : " + sumOfGearRatios);
    }


    private static Set<Point> getAdjacentAsterisks(List<String> schematic, int row, int col) {
        Set<Point> adjacentAsterisks = new HashSet<>();
        for (int i : OFFSET) {
            for (int j : OFFSET) {
                try {
                    char currentChar = schematic.get(row + i).charAt(col + j);
                    if (currentChar == '*') {
                        adjacentAsterisks.add(new Point(row + i, col + j));
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return adjacentAsterisks;
    }

    private static int computeSumOfGearRatios(Map<Point, List<Integer>> gearsAndPartNumbersAssociation) {
        return gearsAndPartNumbersAssociation.values().stream()
                .filter(partNumbers -> partNumbers.size() == 2)
                .mapToInt(partNumbers -> partNumbers.get(0) * partNumbers.get(1))
                .sum();
    }

    private static void part1(List<String> schematic) {
        System.out.println("Sum of all of the part numbers : " + computeSumOfALlPartNumbers(schematic));
    }

    private static Integer computeSumOfALlPartNumbers(List<String> schematic) {
        int sum = 0;
        for (int i = 0; i < schematic.size(); i++) {
            for (int j = 0; j < schematic.get(i).length(); j++) {
                if (Character.isDigit(schematic.get(i).charAt(j))) {
                    StringBuilder currentNumber = new StringBuilder();
                    boolean isPartNumber = false;
                    int k = j;
                    while (k < schematic.get(i).length() && Character.isDigit(schematic.get(i).charAt(k))) {
                        currentNumber.append(schematic.get(i).charAt(k));
                        isPartNumber = isPartNumber || isSurroundedByNonDotCharacters(schematic, i, k);
                        k++;
                    }
                    j = k - 1;

                    if (isPartNumber) {
                        sum += Integer.parseInt(currentNumber.toString());
                    }
                }
            }
        }
        return sum;
    }

    private static boolean isSurroundedByNonDotCharacters(List<String> schematic, int row, int col) {
        for (int i : OFFSET) {
            for (int j : OFFSET) {
                try {
                    char currentChar = schematic.get(row + i).charAt(col + j);
                    if (currentChar != '.' && !Character.isDigit(currentChar)) {
                        return true;
                    }
                } catch (Exception ignored) {
                }
            }
        }
        return false;
    }

}
