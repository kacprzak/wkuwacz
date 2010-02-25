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

package pl.kacprzak.wkuwacz;

import java.io.File;
import java.io.FileNotFoundException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.MenuItem;

import pl.kacprzak.wkuwacz.words.IWords;
import pl.kacprzak.wkuwacz.words.WordsFactory;
import pl.kacprzak.wkuwacz.words.IWords.Type;

/**
 *
 * @author Marcin Kacprzak <martin.kacprzak at gmail.com>
 */
public final class WkuwaczApp extends WkuwaczGUI {

    private IWords words;

    protected WkuwaczApp(final Display display) {
        super(display);
    }

    private void initModel() {
        try {
            words = WordsFactory.getWords(new File("slowka.txt"));
            reset();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            shell.close();
        }
    }

    @Override
    protected void initUI() {
        super.initUI();
        addListeners();
        initModel();
    }

    private void addListeners() {
        // Printing information to stdout whem main window is disposed.
        shell.addDisposeListener(new DisposeListener() {
            @Override
            public void widgetDisposed(DisposeEvent e) {
                System.out.println("Ending: " + Messages.getString("APP_NAME"));
            }
        });

        // Closing main window.
        exitItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                shell.close();
            }
        });

        nextBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                nextWord();
            }
        });

        aboutItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                AboutDialog dlg = new AboutDialog(shell);
                dlg.open();
            }
        });

        SelectionListener readyListener = new SelectionListener() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                if (words.isCorrect(text2.getText())) {
                    statusBar.setText(Messages.getString("WkuwaczApp.3"));
                    nextWord();
                } else {
                    statusBar.setText(Messages.getString("WkuwaczApp.4") + " "
                            + text1.getText() + " "
                            + Messages.getString("WkuwaczApp.6") + " "
                            + words.getCorrect());
                }
                updateInfo();
                nextWord();
            }

            @Override
            public void widgetDefaultSelected(SelectionEvent e) {
                widgetSelected(e);
            }
        };

        readyBtn.addSelectionListener(readyListener);
        text2.addSelectionListener(readyListener);

        restartItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                reset();
            }
        });

        hintBtn.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                text2.setText(words.getHint(text2.getText()));
                updateInfo();
            }
        });

        firstToSecondItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                MenuItem src = (MenuItem) e.getSource();
                if (src.getSelection() == true)
                    words.revers(false);

                nextWord();
            }
        });

        secondToFirstItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                MenuItem src = (MenuItem) e.getSource();
                if (src.getSelection() == true)
                    words.revers(true);

                nextWord();
            }
        });

        onlyOnceItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                MenuItem src = (MenuItem) e.getSource();
                if (src.getSelection() == true)
                    words.setType(Type.ONLY_ONCE);
            }
        });

        untilSuccesItem.addSelectionListener(new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                super.widgetSelected(e);
                MenuItem src = (MenuItem) e.getSource();
                if (src.getSelection() == true)
                    words.setType(Type.UNTIL_SUCCES);
            }
        });

    }

    /**
     * Resets IWords object to starting state. Updates Info group
     * and takes the next word from IWord Object.
     */
    private void reset() {
        words.reset();
        updateInfo();
        nextWord();
        statusBar.setText(Messages.getString("WkuwaczApp.5"));
    }

    /**
     * Takes next word from IWord object.
     */
    private void nextWord() {
        String nextWord = words.getWord();

        if (nextWord != null) {
            text1.setText(nextWord);
            text2.setText("");
        } else {
            // do nothing
        }
    }

    /**
     * Updates info group with data form IWords object.
     */
    private void updateInfo() {
        allWords.setText(Integer.toString(words.getLength()));
        wordsLeft.setText(Integer.toString(words.getWordsLeftNum()));
        wrongWords.setText(Integer.toString(words.getErrorsNum()));
        hints.setText(Integer.toString(words.getHintsNum()));
    }

    /**
     * @param args application arguments.
     */
    public static void main(String[] args) {
        System.out.println("Starting: " + Messages.getString("APP_NAME"));
        System.out.println("Platform: " + SWT.getPlatform());
        System.out.println("Version: " + SWT.getVersion());

        Display display = new Display();
        new WkuwaczApp(display);
        display.dispose();
    }
}
