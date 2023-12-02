package og.aoc.day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Day2 {

    public static void main(String[] args) {
        String filePath = "src/main/resources/day2/input.txt";

        try (Stream<String> games = Files.lines(Paths.get(filePath), StandardCharsets.UTF_8)) {
            int sumOfValidGameIDs = games.filter(Day2::isValidGame)
                    .mapToInt(Day2::getGameId)
                    .sum();
            System.out.println("Sum of valid game IDs : " + sumOfValidGameIDs);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static boolean isValidGame(String game) {
        String[] gameRounds = game.split(":")[1].split(";");
        for (String gameRound : gameRounds) {
            String[] picks = gameRound.split(",");
            for (String pick : picks) {
                if (!isValidPick(pick.trim())) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean isValidPick(String pick) {
        String[] pickData = pick.split(" ");
        int number = Integer.parseInt(pickData[0]);
        String color = pickData[1];
        return switch (color) {
            case "red" -> number <= 12;
            case "green" -> number <= 13;
            case "blue" -> number <= 14;
            default -> throw new IllegalArgumentException("Unknown color: " + color);
        };
    }

    private static int getGameId(String game) {
        return Integer.parseInt(game.split(":")[0].split(" ")[1]);
    }
}
