/*
 * Hyperbox - Virtual Infrastructure Manager
 * Copyright (C) 2015 - Maxime Dor
 * 
 * http://kamax.io/hbox/
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
 * 
 */

package io.kamax.vbox;

import io.kamax.tool.ProcessRunner;
import io.kamax.tool.logging.Logger;
import io.kamax.vbox.exception.VBoxManageNotFoundException;
import java.io.File;


public class VBoxXPCOM {

   private static final String defaultHome = "/usr/lib/virtualbox";

   public static String getDefaultHome() {
      return defaultHome;
   }

   // https://kamax.io/hbox/kb/xpcomBindingsRessourcesNotReleased.txt
   public static void triggerVBoxSVC() {
      triggerVBoxSVC(getDefaultHome());
   }

   // https://kamax.io/hbox/kb/xpcomBindingsRessourcesNotReleased.txt
   public static void triggerVBoxSVC(String homeDir) {
      File libxpcom = new File(homeDir + File.separator + "libvboxjxpcom.so");
      Logger.debug("Lib exists - " + libxpcom.getAbsolutePath() + " - " + libxpcom.isFile());
      File vboxmanage = new File(homeDir + File.separator + "VBoxManage").getAbsoluteFile();
      if (!vboxmanage.exists()) {
         throw new VBoxManageNotFoundException(vboxmanage);
      }

      triggerVBoxSVC(homeDir + File.separator + "VBoxManage", "modifyvm", "\"\"");
   }

   // https://kamax.io/hbox/kb/xpcomBindingsRessourcesNotReleased.txt
   public static void triggerVBoxSVC(String... command) {
      File trigger = new File(command[0]);
      Logger.debug("VBoxSVC trigger exec @ " + trigger.getAbsolutePath() + " is file? " + trigger.isFile());

      ProcessRunner.runAndWait(command);
   }

}
