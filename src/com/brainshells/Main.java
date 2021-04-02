package com.brainshells;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    private static final Color COLOR_GRAY = new Color(120, 120, 120);
    private static final Color COLOR_DARK_RED = new Color(96, 34, 34);
    private static final Map<String, List<Map<String, Integer>>> cardValueCords = new HashMap<>();
    private static final Map<Integer, Map<String, Integer>> cardCords = new HashMap<>();
    private static final String[] cardKeys = {"10", "9", "3", "8", "2", "4", "Q", "6", "5", "7", "J", "K", "A"};

    static {
        cardValueCords.put("2", List.of(
                Map.of("x", 18, "y", 13),
                Map.of("x", 22, "y", 23),
                Map.of("x", 12, "y", 32),
                Map.of("x", 23, "y", 33)
        ));

        cardValueCords.put("3", List.of(
                Map.of("x", 20, "y", 18),
                Map.of("x", 23, "y", 28),
                Map.of("x", 12, "y", 30),
                Map.of("x", 18, "y", 13)
        ));

        cardValueCords.put("4", List.of(
                Map.of("x", 23, "y", 18),
                Map.of("x", 23, "y", 27),
                Map.of("x", 11, "y", 27),
                Map.of("x", 23, "y", 33)
        ));

        cardValueCords.put("5", List.of(
                Map.of("x", 23, "y", 14),
                Map.of("x", 19, "y", 14),
                Map.of("x", 13, "y", 14),
                Map.of("x", 13, "y", 22),
                Map.of("x", 23, "y", 28),
                Map.of("x", 12, "y", 31),
                Map.of("x", 18, "y", 32)
        ));
        cardValueCords.put("6", List.of(
                Map.of("x", 12, "y", 23),
                Map.of("x", 12, "y", 28),
                Map.of("x", 15, "y", 31),
                Map.of("x", 25, "y", 27),
                Map.of("x", 20, "y", 21),
                Map.of("x", 15, "y", 15),
                Map.of("x", 23, "y", 14)
        ));

        cardValueCords.put("7", List.of(
                Map.of("x", 16, "y", 13),
                Map.of("x", 21, "y", 20),
                Map.of("x", 17, "y", 28),
                Map.of("x", 24, "y", 14)
        ));

        cardValueCords.put("8", List.of(
                Map.of("x", 18, "y", 13),
                Map.of("x", 18, "y", 23),
                Map.of("x", 17, "y", 33),
                Map.of("x", 12, "y", 17),
                Map.of("x", 24, "y", 17),
                Map.of("x", 24, "y", 28),
                Map.of("x", 11, "y", 28)
        ));
        cardValueCords.put("9", List.of(
                Map.of("x", 12, "y", 19),
                Map.of("x", 25, "y", 18),
                Map.of("x", 18, "y", 13),
                Map.of("x", 18, "y", 25)
        ));

        cardValueCords.put("10", List.of(
                Map.of("x", 32, "y", 23),
                Map.of("x", 29, "y", 31),
                Map.of("x", 30, "y", 15),
                Map.of("x", 18, "y", 23)
        ));

        cardValueCords.put("J", List.of(
                Map.of("x", 21, "y", 15),
                Map.of("x", 21, "y", 24),
                Map.of("x", 19, "y", 32),
                Map.of("x", 11, "y", 31)
        ));


        cardValueCords.put("Q", List.of(
                Map.of("x", 15, "y", 15),
                Map.of("x", 17, "y", 32),
                Map.of("x", 24, "y", 26),
                Map.of("x", 28, "y", 16),
                Map.of("x", 31, "y", 32)
        ));


        cardValueCords.put("K", List.of(
                Map.of("x", 13, "y", 19),
                Map.of("x", 13, "y", 29),
                Map.of("x", 23, "y", 27),
                Map.of("x", 25, "y", 15)
        ));
        cardValueCords.put("A", List.of(
                Map.of("x", 9, "y", 32),
                Map.of("x", 17, "y", 15),
                Map.of("x", 26, "y", 33),
                Map.of("x", 17, "y", 27),
                Map.of("x", 22, "y", 23),
                Map.of("x", 13, "y", 23)
        ));
    }

    static {
        //Each card's start and width
        cardCords.put(1, Map.of("start", 2, "width", 66));
        cardCords.put(2, Map.of("start", 2 + 72, "width", 65));
        cardCords.put(3, Map.of("start", 2 + 72 + 71, "width", 66));
        cardCords.put(4, Map.of("start", 2 + 72 + 72 + 71, "width", 66));
        cardCords.put(5, Map.of("start", 2 + 72 + 72 + 71 + 71, "width", 66));
    }

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.out.println("Directory path is not given");
            return;
        }

        var directory = new File(args[0]);
        var photos = directory.listFiles();

        if (photos == null) {
            System.out.println("The given directory is empty");
            return;
        }

        for (File photo : photos) {
            var pokerTable = readPokerTableImage(photo);

            var cardsSection = cropCardsSection(pokerTable);
            var cards = cropCards(cardsSection);
            var result = new StringBuilder();
            for (var card : cards) {
                result.append(getCardValue(card));

                if (isRed(getCardSuitColor(card)) || isDarkRed(card)) {
                    // process red cards
                    if (isDiamond(card)) {
                        result.append("d");
                    } else {
                        result.append("h");
                    }

                } else {
                    if (isClub(card)) {
                        result.append("c");
                    } else {
                        result.append("s");
                    }

                }
            }
            System.out.println(photo.getName() + " - " + result);
        }
    }

    private static boolean isDarkRed(BufferedImage card) {
        return getCardSuitColor(card).equals(COLOR_DARK_RED);
    }

    private static boolean isClub(BufferedImage card) {
        var color = new Color(card.getRGB(34, 65));
        return isWhite(color) || color.equals(COLOR_GRAY);
    }

    private static boolean isDiamond(BufferedImage card) {
        var color = new Color(card.getRGB(32, 61));
        return isWhite(color) || color.equals(COLOR_GRAY);
    }

    private static boolean isCard(BufferedImage card) {
        var cardColor = new Color(card.getRGB(43, 31));
        return cardColor.equals(COLOR_GRAY) || isWhite(cardColor);
    }

    private static Color getCardSuitColor(BufferedImage card) {
        return new Color(card.getRGB(43, 70));
    }

    private static BufferedImage cropCardsSection(BufferedImage image) {
        return image.getSubimage(140, 580, 365, 100);
    }

    private static BufferedImage readPokerTableImage(File file) throws IOException {
        return ImageIO.read(file);
    }

    public static List<BufferedImage> cropCards(BufferedImage cardsSection) {
        var cards = new ArrayList<BufferedImage>();
        for (var cardIndex : cardCords.keySet()) {
            var pair = cardCords.get(cardIndex);
            var card = cardsSection.getSubimage(pair.get("start"), 0, pair.get("width"), 100);

            if (!isCard(card)) {
                break;
            }

            cards.add(card);
        }

        return cards;
    }

    private static boolean isRed(Color color) {
        return color.getRed() > 190 && color.getGreen() < 100 && color.getBlue() < 100;
    }

    private static boolean isWhite(Color color) {
        return 0.2126 * color.getRed() + 0.7152 * color.getGreen() + 0.0722 * color.getBlue() >= 128;
    }

    private static boolean isCardValueEqualsTo(String value, BufferedImage card) {
        for (var cord : cardValueCords.get(value)) {
            var cardColor = new Color(card.getRGB(cord.get("x"), cord.get("y")));

            if (isWhite(cardColor) || cardColor.equals(COLOR_GRAY)) {
                return false;
            }
        }

        return true;
    }

    private static String getCardValue(BufferedImage card) {
        for (var value : cardKeys) {
            if (isCardValueEqualsTo(value, card)) {
                return value;
            }
        }

        return "Unknown";
    }
}