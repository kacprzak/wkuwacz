package pl.kacprzak.wkuwacz.words;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class WordsFactory {

    public static IWords getWords(File wordsFile) throws FileNotFoundException {

        Words words = new Words();

        Scanner scanner = new Scanner(wordsFile, "UTF-8");

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            if (line.startsWith("#"))
                continue;

            if (!line.contains("="))
                continue;

            String[] pairOfWords = line.split("=", 2);
            pairOfWords[0] = pairOfWords[0].trim();
            pairOfWords[1] = pairOfWords[1].trim();

            words.add(pairOfWords[0], pairOfWords[1]);
        }

        return words;
    }
}
