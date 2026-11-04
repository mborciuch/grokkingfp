package exercises;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ch04_WordScoring {

    static int score(String word) {
        return word.replaceAll("a", "").length();
    }

    static int scoreWithBonus(String word) {
        int base = score(word);
        if (word.contains("c")) return base + 5;
        else return base;
    }

    static int scoreWithBonusAndSubstract(String word) {
        int baseScore = scoreWithBonus(word);
        if (word.contains("s")) {
            return baseScore - 7;
        }
        return baseScore;
    }



    static List<String> rankedWords(Function<String, Integer> wordScore,
                                    List<String> words) {
        Comparator<String> wordComparator =
                (w1, w2) -> Integer.compare(
                        wordScore.apply(w2),
                        wordScore.apply(w1)
                );
        return words
                .stream()
                .sorted(wordComparator)
                .collect(Collectors.toList());
    }

    static List<String> rankedWordsv2(Function<String, Integer> wordScore,
                                    List<String> words) {
        Comparator<String> wordComparator =
                (w1, w2) -> Integer.compare(
                        wordScore.apply(w2),
                        wordScore.apply(w1)
                );
        return words
                .stream()
                .sorted(wordComparator)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        List<String> words = List.of("ada", "haskell", "scala", "java", "rust");
        System.out.println(rankedWords(w -> scoreWithBonusAndSubstract(w), words));


    }
}
