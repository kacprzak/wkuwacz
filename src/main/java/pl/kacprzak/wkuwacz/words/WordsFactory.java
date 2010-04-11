/*
 *  Copyright (C) 2010 Marcin Kacprzak <martin.kacprzak at gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pl.kacprzak.wkuwacz.words;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Marcin Kacprzak <martin.kacprzak at gmail.com>
 * @version $Revision$, $Date$
 */
public final class WordsFactory {

    private WordsFactory() { }

    public static IWords getWords(final File wordsFile)
            throws FileNotFoundException {

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
