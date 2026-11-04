package exercises;

import java.util.Arrays;
import java.util.stream.Stream;

public class ch01_Intro {


    public static int increment(int x) {
        return x + 1;
    }

    public static String concatenate(String a, String b) {
        return a + b;
    }

    public static int calculateScore(String word)  {
        int score = 0;
        for(char c : word.toCharArray()) {
            if (c != 'a'){
                score++;
            }
        }
        return score;
    }

    public static int wordScore_v2(String word)  {
       return (int) word.chars().filter(c -> c != 'a').count();
    }

    public static int wordScore(String word)  {
        return word.replace("a", "").length();
    }




    public static void main(String[] args) {

        assert calculateScore("Imperative") == 9;
        assert calculateScore("no")  == 9;
        assert wordScore("Declarative") == 9;
        assert wordScore("yes") == 2;
    }
}
