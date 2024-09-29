package backend.academy.model;

public class WordMasker {
    public String maskWord(String word) {
        StringBuilder maskedWord = new StringBuilder();
        for (char ch : word.toCharArray()) {
            if (ch == ' ') {
                maskedWord.append(' ');
            } else if (ch == '-') {
                maskedWord.append('-');
            } else {
                maskedWord.append('*');
            }
        }
        return maskedWord.toString();
    }
}
