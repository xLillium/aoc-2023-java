package og.aoc.day2;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

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
        final int[] fewestRedRequired = {0};
        final int[] fewestGreenRequired = {0};
        final int[] fewestBlueRequired = {0};

        processGameRounds(game, pick -> {
            String[] pickData = pick.split(" ");
            int number = Integer.parseInt(pickData[0]);
            String color = pickData[1];
            switch (color) {
                case RED:
                    fewestRedRequired[0] = Math.max(fewestRedRequired[0], number);
                    break;
                case GREEN:
                    fewestGreenRequired[0] = Math.max(fewestGreenRequired[0], number);
                    break;
                case BLUE:
                    fewestBlueRequired[0] = Math.max(fewestBlueRequired[0], number);
                    break;
                default:
                    throw new RuntimeException("Unknown color : " + color);
            }
        });

        return fewestRedRequired[0] * fewestGreenRequired[0] * fewestBlueRequired[0];
    }

    private static void processGameRounds(String game, Consumer<String> pickProcessor) {
        String[] gameRounds = game.split(":")[1].split(";");
        for (String gameRound : gameRounds) {
            Arrays.stream(gameRound.split(","))
                    .forEach(pick -> pickProcessor.accept(pick.trim()));
        }
    }


    private static boolean isValidGame(String game) {
        final AtomicBoolean isValid = new AtomicBoolean(true);
        processGameRounds(game, pick -> {
            if (!isValidPick(pick)) {
                isValid.set(false);
            }
        });
        return isValid.get();
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
