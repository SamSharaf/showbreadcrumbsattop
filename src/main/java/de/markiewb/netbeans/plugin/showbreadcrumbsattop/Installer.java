/*
 * Copyright 2016 markiewb.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.markiewb.netbeans.plugin.showbreadcrumbsattop;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.modules.ModuleInstall;

public class Installer extends ModuleInstall {

    @Override
    public void close() {
        super.close(); 
	// reset to default state
	updatePosition("South");
    }

    @Override
    public void restored() {
        super.restored();
	updatePosition("North");
    }
    

    public static void updatePosition(String location) {

	List<String> paths = Arrays.asList("Editors/text/%s/SideBar/breadcrumbs.instance",
		"Editors/text/%s/SideBar/org-netbeans-modules-editor-breadcrumbs-spi-BreadcrumbsController-createSideBarFactory.instance");
	try {

	    FileObject textDirectory = FileUtil.getConfigFile("Editors/text/");
	    FileObject[] mimeTypeDirectories = textDirectory.getChildren();
	    for (FileObject mimeTypeDirectory : mimeTypeDirectories) {
		boolean folder = mimeTypeDirectory.isFolder();
		if (folder) {
		    for (String path : paths) {
			FileObject config = FileUtil.getConfigFile(String.format(path, mimeTypeDirectory.getName()));
			if (null != config && config.canWrite()) {
			    config.setAttribute("location", location);
			}
		    }
		}
	    }

	} catch (IOException iOException) {
	}

    }

    @Override
    public void uninstalled() {
        super.uninstalled(); 
	// reset to default state
	updatePosition("South");
    }

}
