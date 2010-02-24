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
	 * Return one word from the list. It never returns the same word
	 * two times in a row.
	 * @return
	 */
	String getWord();
	
	/**
	 * Tests the anser for the last taken word.
	 * @param anserw
	 * @return true if correct.
	 */
	boolean isCorrect(String anserw);
	
	/**
	 * Returns correct for last taken word.
	 * @return
	 */
	String getCorrect();
	
	/**
	 * Returns hint for last taken word.
	 * @return
	 */
	String getHint(String text);
	
	/**
	 * Number of hints taken.
	 * @return
	 */
	int getHintsNum();
	
	/**
	 * Number of wrong anserws
	 * @return
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
	 * Returns number of elements.
	 * @return
	 */
	int getLength();
	
	/**
	 * Reverses learning Lang2->Lang1.
	 */
	void revers(boolean reversed);
	
	boolean isReversed();
	
	public Type getType();

	public void setType(Type type);
}
