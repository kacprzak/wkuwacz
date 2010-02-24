package pl.kacprzak.wkuwacz;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CLabel;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AboutDialog extends Dialog {

    private Font font;

    public AboutDialog(Shell parent) {
        this(parent, SWT.DIALOG_TRIM | SWT.APPLICATION_MODAL);
    }

    public AboutDialog(Shell parent, int style) {
        super(parent, style);
        setText(Messages.getString("AboutDialog.0")); //$NON-NLS-1$
    }

    public void open() {
        FontData fd = getParent().getDisplay().getSystemFont().getFontData()[0];
        FontData fontData = new FontData(fd.getName(), 18, SWT.BOLD);
        font = new Font(getParent().getDisplay(), fontData);
        // Create the dialog window
        Shell shell = new Shell(getParent(), getStyle());
        shell.setText(getText());
        createContents(shell);
        shell.pack();
        shell.open();
        Display display = getParent().getDisplay();
        while (!shell.isDisposed()) {
            if (!display.readAndDispatch()) {
                display.sleep();
            }
        }
        font.dispose();
    }

    private void createContents(final Shell shell) {

        GridLayout layout = new GridLayout();
        layout.numColumns = 1;
        layout.marginHeight = 12;
        layout.marginWidth = 12;
        layout.verticalSpacing = 12;
        shell.setLayout(layout);

        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);

        CLabel title = new CLabel(shell, SWT.CENTER);
        title.setFont(font);
        title.setText(Messages.getString("APP_NAME") + " " + Messages.getString("APP_VERSION")); //$NON-NLS-1$
        title.setLayoutData(data);

        data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        Label descr = new Label(shell, SWT.CENTER);
        descr.setText(Messages.getString("AboutDialog.2")); //$NON-NLS-1$
        descr.setLayoutData(data);

        data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        Label author = new Label(shell, SWT.CENTER);
        author.setText(Messages.getString("AboutDialog.3")); //$NON-NLS-1$
        author.setLayoutData(data);

        data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
        Label site = new Label(shell, SWT.CENTER);
        site.setText(Messages.getString("AboutDialog.4")); //$NON-NLS-1$
        site.setLayoutData(data);

        // Create the cancel button and add a handler
        // so that pressing it will set input to null
        data = new GridData(GridData.HORIZONTAL_ALIGN_END);
        Button cancel = new Button(shell, SWT.PUSH);
        cancel.setText(Messages.getString("AboutDialog.5")); //$NON-NLS-1$
        cancel.setLayoutData(data);
        cancel.addSelectionListener(new SelectionAdapter() {

            public void widgetSelected(SelectionEvent event) {
                shell.close();
            }
        });
        shell.setDefaultButton(cancel);
    }
}
