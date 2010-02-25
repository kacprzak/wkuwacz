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

/**
 *
 * @author Marcin Kacprzak <martin.kacprzak at gmail.com>
 */
public interface IWords {

    /**
     * Lesson type.
     */
    static enum Type {
        ONLY_ONCE, UNTIL_SUCCES
    };

    /**
     * @return one word from the list. It never returns the same word
     * two times in a row.
     */
    String getWord();

    /**
     * Tests the anser for the last taken word.
     * @param anserw
     * @return true if correct.
     */
    boolean isCorrect(String anserw);

    /**
     * @return correct for last taken word.
     */
    String getCorrect();

    /**
     * @return hint for last taken word.
     */
    String getHint(String text);

    /**
     * @return number of hints taken.
     */
    int getHintsNum();

    /**
     * @return number of wrong anserws.
     */
    int getErrorsNum();

    /**
     * Sets errors to 0, and wordsLeft to all.
     */
    void reset();

    /**
     * Number of words left to the end of session.
     * Depends on lesson type (ONCE or UNTIL_SOCCES).
     */
    int getWordsLeftNum();

    /**
     * @return number of elements.
     */
    int getLength();

    /**
     * Reverses learning Lang2->Lang1.
     */
    void revers(boolean reversed);

    boolean isReversed();

    Type getType();

    void setType(Type type);
}
