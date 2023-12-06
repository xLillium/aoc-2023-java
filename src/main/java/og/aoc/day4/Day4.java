package og.aoc.day4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class Day4 {
    public static void main(String[] args) throws IOException {
        String filePath = "src/main/resources/day4/input.txt";
        List<String> scratchCards = Files.readAllLines(Paths.get(filePath));
        part1(scratchCards);
    }

    private static void part1(List<String> scratchCards) {
        System.out.println("The pile of scratchcards is worth in total : " +
                computeTotalScore(scratchCards)
                + " points");
    }

    private static int computeTotalScore(List<String> scratchCards) {
        return scratchCards.stream()
                .mapToInt(Day4::computeScore)
                .sum();
    }

    private static int computeScore(String scratchCard) {
        long amountOfWinningNumbers = getAmountOfWinningNumbers(scratchCard);
        return amountOfWinningNumbers > 0 ? (int) Math.pow(2, amountOfWinningNumbers - 1) : 0;
    }

    private static long getAmountOfWinningNumbers(String scratchCard) {
        String cardContent = scratchCard.split(": ")[1];
        List<String> playerNumbers = extractNumbersFromContent(cardContent, 0);
        List<String> winningNumbers = extractNumbersFromContent(cardContent, 1);

        return playerNumbers.stream()
                .filter(winningNumbers::contains)
                .count();
    }

    private static List<String> extractNumbersFromContent(String cardContent, int index) {
        return Arrays.asList(cardContent.split(" \\| ")[index].trim().split("\\s+"));
    }

}
