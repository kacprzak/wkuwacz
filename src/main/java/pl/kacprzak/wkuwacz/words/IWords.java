package pl.kacprzak.wkuwacz.words;

/**
 * @author Marcin Kacprzak
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
