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

public class WkuwaczApp extends WkuwaczGUI {

    private IWords words;

    protected WkuwaczApp(Display display) {
        super(display);
    }

    private void initModel() {

        try {
            words = WordsFactory.getWords(new File("slowka.txt")); //$NON-NLS-1$
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
                System.out.println("Ending: " + Messages.getString("APP_NAME")); //$NON-NLS-1$ //$NON-NLS-2$
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
                    statusBar.setText(Messages.getString("WkuwaczApp.3")); //$NON-NLS-1$
                    nextWord();
                } else {
                    statusBar.setText(Messages.getString("WkuwaczApp.4") + " " + text1.getText() + " " + Messages.getString("WkuwaczApp.6") + " " + words.getCorrect()); //$NON-NLS-1$
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
        statusBar.setText(Messages.getString("WkuwaczApp.5")); //$NON-NLS-1$
    }

    /**
     * Takes next word from IWord object.
     */
    private void nextWord() {
        String nextWord = words.getWord();

        if (nextWord != null) {
            text1.setText(nextWord);
            text2.setText(""); //$NON-NLS-1$
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
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("Starting: " + Messages.getString("APP_NAME")); //$NON-NLS-1$ //$NON-NLS-2$
        System.out.println("Platform: " + SWT.getPlatform()); //$NON-NLS-1$
        System.out.println("Version: " + SWT.getVersion()); //$NON-NLS-1$

        Display display = new Display();
        new WkuwaczApp(display);
        display.dispose();
    }
}
