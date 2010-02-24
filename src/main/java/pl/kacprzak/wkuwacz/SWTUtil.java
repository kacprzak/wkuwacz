package pl.kacprzak.wkuwacz;

import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Shell;

/**
 * @author Marcin Kacprzak
 */
public class SWTUtil {

    private SWTUtil() {}

    /**
     * Centers the window.
     * 
     * @param shell
     */
    public static void center(Shell shell) {
        Rectangle bds = shell.getDisplay().getBounds();
        Point p = shell.getSize();
        int nLeft = (bds.width - p.x) / 2;
        int nTop = (bds.height - p.y) / 2;
        shell.setBounds(nLeft, nTop, p.x, p.y);
    }
}
