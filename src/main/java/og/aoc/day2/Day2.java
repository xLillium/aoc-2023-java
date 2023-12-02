package og.aoc.day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Day2 {

    private static final String RED = "red";
    private static final String GREEN = "green";
    private static final String BLUE = "blue";

    public static void main(String[] args) {
        String filePath = "src/main/resources/day2/input.txt";

        try {
            List<String> games = Files.readAllLines(Paths.get(filePath), StandardCharsets.UTF_8);
            part1(games);
            part2(games);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void part1(List<String> games) {
        int sumOfValidGameIDs = games.stream()
                .filter(Day2::isValidGame)
                .mapToInt(Day2::getGameId)
                .sum();
        System.out.println("Sum of valid game IDs : " + sumOfValidGameIDs);
    }

    private static void part2(List<String> games) {
        int sumOfThePowerOfAllSets = games.stream()
                .mapToInt(Day2::computeSetPower)
                .sum();
        System.out.println("Sum of the power of all the sets : " + sumOfThePowerOfAllSets);
    }

    private static int computeSetPower(String game) {
        String[] gameRounds = game.split(":")[1].split(";");
        int fewestRedRequired = 0;
        int fewestGreenRequired = 0;
        int fewestBlueRequired = 0;

        for (String gameRound : gameRounds) {
            String[] picks = gameRound.split(",");
            for (String pick : picks) {
                String[] pickData = pick.trim().split(" ");
                int number = Integer.parseInt(pickData[0]);
                String color = pickData[1];
                switch (color) {
                    case RED:
                        fewestRedRequired = Math.max(fewestRedRequired, number);
                        break;
                    case GREEN:
                        fewestGreenRequired = Math.max(fewestGreenRequired, number);
                        break;
                    case BLUE:
                        fewestBlueRequired = Math.max(fewestBlueRequired, number);
                        break;
                    default:
                        throw new RuntimeException("Unknown color : " + color);
                }
            }
        }
        return fewestRedRequired * fewestGreenRequired * fewestBlueRequired;
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
            case RED -> number <= 12;
            case GREEN -> number <= 13;
            case BLUE -> number <= 14;
            default -> throw new IllegalArgumentException("Unknown color: " + color);
        };
    }

    private static int getGameId(String game) {
        return Integer.parseInt(game.split(":")[0].split(" ")[1]);
    }
}
