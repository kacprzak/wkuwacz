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

import java.util.ArrayList;
import java.util.HashSet;

/**
 *
 * @author Marcin Kacprzak <martin.kacprzak at gmail.com>
 */
class Words implements IWords {

    private ArrayList<String> wordsLeft = new ArrayList<String>();
    private ArrayList<String> wordsRight = new ArrayList<String>();
    private HashSet<Integer> deactivated = new HashSet<Integer>();
    private int lastTaken = -1;
    private int errorsNum = 0;
    private int hintsNum = 0;
    private boolean reversed = false;
    private Type type = Type.UNTIL_SUCCES;

    @Override
    public String getHint(String text) {
        if (lastTaken == -1)
            throw new IllegalArgumentException();

        String hintWord = wordsRight.get(lastTaken);
        if (hintWord.startsWith(text)) {
            if (hintWord.length() == text.length())
                return text;
            hintWord = hintWord.substring(0, text.length() + 1);
        } else {
            hintWord = hintWord.substring(0, 1);
        }

        hintsNum++;
        return hintWord;
    }

    @Override
    public String getWord() {
        // if (lastTaken == -1) throw new IllegalArgumentException();
        // Test if there is more than one word left
        if (getWordsLeftNum() < 1)
            return null;

        if (getWordsLeftNum() == 1)
            lastTaken = -1;

        // Normal action
        int randIndex;
        do {
            randIndex = (int) (Math.random() * wordsLeft.size());
        } while (randIndex == lastTaken || deactivated.contains(randIndex));
        lastTaken = randIndex;
        return wordsLeft.get(lastTaken);
    }

    @Override
    public boolean isCorrect(String anserw) {
        if (lastTaken == -1)
            throw new IllegalArgumentException();

        if (anserw.equals(wordsRight.get(lastTaken))) {
            deactivated.add(lastTaken);
            return true;
        } else {
            // Every word will be active only once
            if (type == Type.ONLY_ONCE)
                deactivated.add(lastTaken);

            errorsNum++;
            return false;
        }
    }

    void add(String word1, String word2) {
        wordsLeft.add(word1);
        wordsRight.add(word2);
    }

    @Override
    public int getLength() {
        return wordsLeft.size();
    }

    @Override
    public int getWordsLeftNum() {
        return getLength() - deactivated.size();
    }

    @Override
    public void reset() {
        errorsNum = 0;
        hintsNum = 0;
        lastTaken = -1;
        deactivated.clear();
    }

    @Override
    public int getErrorsNum() {
        return errorsNum;
    }

    @Override
    public int getHintsNum() {
        return hintsNum;
    }

    @Override
    public void revers(boolean reversed) {
        if (this.reversed != reversed) {
            ArrayList<String> tmp = wordsLeft;
            wordsLeft = wordsRight;
            wordsRight = tmp;
            this.reversed = reversed;
        }
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public boolean isReversed() {
        return reversed;
    }

    @Override
    public String getCorrect() {
        return wordsRight.get(lastTaken);
    }
}
