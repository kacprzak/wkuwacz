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

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 *
 * @author Marcin Kacprzak <martin.kacprzak at gmail.com>
 * @version $Revision$, $Date$
 */
public final class Messages {
    private static final String BUNDLE_NAME = "pl.kacprzak.wkuwacz.messages";
    private static final ResourceBundle RESOURCE_BUNDLE =
            ResourceBundle.getBundle(BUNDLE_NAME);

    private Messages() { }

    public static String getString(final String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}

