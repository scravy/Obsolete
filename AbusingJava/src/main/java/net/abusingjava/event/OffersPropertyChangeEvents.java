/* Part of the AbusingJava-Library.
 * 
 * Source:  http://github.com/scravy/AbusingJava
 * Home:    http://www.abusingjava.net/
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package net.abusingjava.event;

import java.beans.PropertyChangeListener;

import net.abusingjava.Author;
import net.abusingjava.Stable;
import net.abusingjava.Version;


/**
 * Exports the typical methods used for dealing with PropertyChangeListeners.
 */
@Author("Julian Fleischer")
@Version("2011-07-24")
@Stable
public interface OffersPropertyChangeEvents {

	void addPropertyChangeListener(PropertyChangeListener $l);
	
	void removePropertyChangeListener(PropertyChangeListener $l);
	
	PropertyChangeListener[] getPropertyChangeListeners();
	
}
