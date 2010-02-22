/**
 * 
 */
package pl.kacprzak.wkuwacz;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/**
 * @author Marcin Kacprzak
 * 
 */
class WkuwaczGUI {

	protected final Shell shell;
	protected CLabel statusBar;
	protected Group lettersGroup;
	protected Group infoGroup;
	protected Text text1;
	protected Text text2;
	protected Button readyBtn;
	protected Button nextBtn;
	protected Button hintBtn;
	protected Label allWords;
	protected Label wordsLeft;
	protected Label wrongWords;
	protected Label hints;
	protected MenuItem restartItem;
	protected MenuItem exitItem;
	protected MenuItem firstToSecondItem;
	protected MenuItem secondToFirstItem;
	protected MenuItem untilSuccesItem;
	protected MenuItem onlyOnceItem;
	protected MenuItem aboutItem;

	protected WkuwaczGUI(Display display) {
		// Window can't be resized or maximized.
		shell = new Shell(display, SWT.SHELL_TRIM ^ (SWT.RESIZE | SWT.MAX));
		// shell = new Shell(display);
		shell.setText(Messages.getString("APP_NAME") + " " + Messages.getString("APP_VERSION")); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$

		// SWT UI Initialization.
		initUI();

		// shell.setSize(800, 600);
		shell.pack();
		// Centering the app window.
		SWTUtil.center(shell);

		shell.open();

		// Main event loop.
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Initialization of User Interface.
	 */
	protected void initUI() {

		FormLayout frmLayout = new FormLayout();
		shell.setLayout(frmLayout);
		frmLayout.marginHeight = 3;
		frmLayout.marginWidth = 3;

		// The order is very important because of FormLayout bulding dependency.
		// The menu is build as first.
		initMenu();
		// The window (shell) content is build from bottom to top.
		initStatusBar();
		initLetters();
		initInfo();

		// Two text fields. The first one shows the word (maybe it should be
		// Label),
		// the seconde text field is for entering the aserw.

		text1 = new Text(shell, SWT.BORDER | SWT.READ_ONLY);
		FormData data = new FormData();
		text1.setLayoutData(data);
		data.width = 180;
		data.top = new FormAttachment(0, 5);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(infoGroup, -5, SWT.LEFT);

		text2 = new Text(shell, SWT.BORDER);
		data = new FormData();
		text2.setLayoutData(data);
		data.top = new FormAttachment(text1, 5, SWT.BOTTOM);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(infoGroup, -5, SWT.LEFT);
		text2.setFocus();

		// The most important buttons for the application :)

		readyBtn = new Button(shell, SWT.PUSH);
		readyBtn.setText(Messages.getString("WkuwaczGUI.4")); //$NON-NLS-1$
		data = new FormData();
		readyBtn.setLayoutData(data);
		data.top = new FormAttachment(text2, 5, SWT.BOTTOM);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(infoGroup, -5, SWT.LEFT);

		nextBtn = new Button(shell, SWT.PUSH);
		nextBtn.setText(Messages.getString("WkuwaczGUI.5")); //$NON-NLS-1$
		data = new FormData();
		nextBtn.setLayoutData(data);
		data.top = new FormAttachment(readyBtn, 5, SWT.BOTTOM);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(infoGroup, -5, SWT.LEFT);

		hintBtn = new Button(shell, SWT.PUSH);
		hintBtn.setText(Messages.getString("WkuwaczGUI.6")); //$NON-NLS-1$
		data = new FormData();
		hintBtn.setLayoutData(data);
		data.top = new FormAttachment(nextBtn, 5, SWT.BOTTOM);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(infoGroup, -5, SWT.LEFT);
		data.bottom = new FormAttachment(lettersGroup, -5, SWT.TOP);

	}

	/**
	 * Builds buttons for lettersGroup. This group has letters that are handy
	 * for users who don't have special characters on keyboard.
	 * 
	 * @param lettersGroup
	 */
	protected void initLetters() {

		lettersGroup = new Group(shell, SWT.SHADOW_NONE);
		lettersGroup.setText(Messages.getString("WkuwaczGUI.7")); //$NON-NLS-1$
		RowLayout lettersLayout = new RowLayout();
		lettersGroup.setLayout(lettersLayout);
		FormData data = new FormData();
		lettersGroup.setLayoutData(data);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(statusBar, -5, SWT.TOP);

		File lettersFile = new File("chars.txt");

		Scanner scanner;
		try {
			scanner = new Scanner(lettersFile, "UTF-8");
			ArrayList<String> letters = new ArrayList<String>();

			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();

				if (line.startsWith("#"))
					continue;
				if (!line.isEmpty())
					letters.add(line);
			}

			LetterSelectionAdapter letSelAdp = new LetterSelectionAdapter();

			for (String letter : letters) {
				Button btn = new Button(lettersGroup, SWT.PUSH);
				btn.setText(letter);
				btn.addSelectionListener(letSelAdp);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			shell.close();
		}
	}

	protected void initInfo() {

		infoGroup = new Group(shell, SWT.SHADOW_NONE);
		infoGroup.setText(Messages.getString("WkuwaczGUI.11")); //$NON-NLS-1$
		GridLayout infoLayout = new GridLayout(2, false);
		infoGroup.setLayout(infoLayout);
		FormData data = new FormData();
		infoGroup.setLayoutData(data);
		data.top = new FormAttachment(0, 5);
		data.right = new FormAttachment(100, 0);
		data.bottom = new FormAttachment(lettersGroup, -5, SWT.TOP);

		final int width = 30;
		final int gridDataStyle = GridData.HORIZONTAL_ALIGN_END;

		new Label(infoGroup, SWT.NONE).setText(Messages
				.getString("WkuwaczGUI.12")); //$NON-NLS-1$
		allWords = new Label(infoGroup, SWT.RIGHT);
		//allWords.setText(Messages.getString("WkuwaczGUI.13")); //$NON-NLS-1$
		GridData gridData = new GridData(gridDataStyle);
		allWords.setLayoutData(gridData);
		gridData.widthHint = width;

		new Label(infoGroup, SWT.NONE).setText(Messages
				.getString("WkuwaczGUI.14")); //$NON-NLS-1$
		wordsLeft = new Label(infoGroup, SWT.RIGHT);
		//wordsLeft.setText(Messages.getString("WkuwaczGUI.15")); //$NON-NLS-1$
		gridData = new GridData(gridDataStyle);
		wordsLeft.setLayoutData(gridData);
		gridData.widthHint = width;

		new Label(infoGroup, SWT.NONE).setText(Messages
				.getString("WkuwaczGUI.16")); //$NON-NLS-1$
		wrongWords = new Label(infoGroup, SWT.RIGHT);
		//wrongWords.setText(Messages.getString("WkuwaczGUI.17")); //$NON-NLS-1$
		gridData = new GridData(gridDataStyle);
		wrongWords.setLayoutData(gridData);
		gridData.widthHint = width;

		new Label(infoGroup, SWT.NONE).setText(Messages
				.getString("WkuwaczGUI.18")); //$NON-NLS-1$
		hints = new Label(infoGroup, SWT.RIGHT);
		//hints.setText(Messages.getString("WkuwaczGUI.19")); //$NON-NLS-1$
		gridData = new GridData(gridDataStyle);
		hints.setLayoutData(gridData);
		gridData.widthHint = width;

	}

	/**
	 * Builds status bar.
	 */
	protected void initStatusBar() {
		// Create a label with a border
		statusBar = new CLabel(shell, SWT.BORDER);
		statusBar.setText(Messages.getString("WkuwaczGUI.20")); //$NON-NLS-1$
		FormData data = new FormData();
		statusBar.setLayoutData(data);
		data.bottom = new FormAttachment(100, 0);
		data.left = new FormAttachment(0, 0);
		data.right = new FormAttachment(100, 0);

	}

	/**
	 * Sets all menu Items and menu structure.
	 */
	protected void initMenu() {
		// Creating MENU
		Menu menuBar = new Menu(shell, SWT.BAR);
		MenuItem cascadeFileMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeFileMenu.setText(Messages.getString("WkuwaczGUI.21")); //$NON-NLS-1$

		Menu fileMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeFileMenu.setMenu(fileMenu);

		restartItem = new MenuItem(fileMenu, SWT.PUSH);
		restartItem.setText(Messages.getString("WkuwaczGUI.22")); //$NON-NLS-1$

		exitItem = new MenuItem(fileMenu, SWT.PUSH);
		exitItem.setText(Messages.getString("WkuwaczGUI.23")); //$NON-NLS-1$

		// Menu "Settings"
		MenuItem cascadeSettingsMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeSettingsMenu.setText(Messages.getString("WkuwaczGUI.24")); //$NON-NLS-1$

		Menu settingsMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeSettingsMenu.setMenu(settingsMenu);

		firstToSecondItem = new MenuItem(settingsMenu, SWT.RADIO);
		firstToSecondItem.setText(Messages.getString("WkuwaczGUI.25")); //$NON-NLS-1$
		firstToSecondItem.setSelection(true);

		secondToFirstItem = new MenuItem(settingsMenu, SWT.RADIO);
		secondToFirstItem.setText(Messages.getString("WkuwaczGUI.26")); //$NON-NLS-1$

		new MenuItem(settingsMenu, SWT.SEPARATOR);

		final MenuItem cascadeLearningTypeMenu = new MenuItem(settingsMenu,
				SWT.CASCADE);
		cascadeLearningTypeMenu.setText(Messages.getString("WkuwaczGUI.27")); //$NON-NLS-1$

		Menu learningTypeMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeLearningTypeMenu.setMenu(learningTypeMenu);

		untilSuccesItem = new MenuItem(learningTypeMenu, SWT.RADIO);
		untilSuccesItem.setText(Messages.getString("WkuwaczGUI.28")); //$NON-NLS-1$
		untilSuccesItem.setSelection(true);

		onlyOnceItem = new MenuItem(learningTypeMenu, SWT.RADIO);
		onlyOnceItem.setText(Messages.getString("WkuwaczGUI.29")); //$NON-NLS-1$

		// Menu "Help"
		MenuItem cascadeHelpMenu = new MenuItem(menuBar, SWT.CASCADE);
		cascadeHelpMenu.setText(Messages.getString("WkuwaczGUI.30")); //$NON-NLS-1$

		Menu helpMenu = new Menu(shell, SWT.DROP_DOWN);
		cascadeHelpMenu.setMenu(helpMenu);

		aboutItem = new MenuItem(helpMenu, SWT.PUSH);
		aboutItem.setText(Messages.getString("WkuwaczGUI.31")); //$NON-NLS-1$

		shell.setMenuBar(menuBar);
	}

	private class LetterSelectionAdapter extends SelectionAdapter {
		@Override
		public void widgetSelected(SelectionEvent e) {
			super.widgetSelected(e);
			Button src = (Button) e.getSource();
			text2.setText(text2.getText() + src.getText());
		}
	}
}
