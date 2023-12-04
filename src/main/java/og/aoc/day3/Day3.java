package og.aoc.day3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day3 {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/day3/input.txt";
        List<String> schematic = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
        part1(schematic);
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
        int[] offset = new int[]{-1, 0, 1};
        for (int i : offset) {
            for (int j : offset) {
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
