/*
 * 
 *  $Id: Switcher.java 847 2015-06-24 09:33:07Z gsb $
 */
package ru.gsbhost.workswitcher;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ProjectManager;
import org.netbeans.api.project.ui.OpenProjects;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;

@ActionID(
        category = "Edit",
        id = "ru.gsbhost.workswitcher.Switcher"
)
@ActionRegistration(
        iconBase = "ru/gsbhost/workswitcher/gsb.png",
        displayName = "#CTL_Switcher"
)
@ActionReference(path = "Menu/Tools", position = 1600, separatorAfter = 1625)

public final class Switcher implements ActionListener {

    public static ArrayList<File> listWithFile = new ArrayList<>();
    public static ArrayList<String> listWithFileNames = new ArrayList<>();

    @Override
    public void actionPerformed(ActionEvent e) {
        if (listWithFile.isEmpty()) {
            getListFiles();
        }
        WorkProjectsDialog.showDialog(listWithFile);
    }

    public static void getListFiles() {
        File f = new File("C:/work/BAT_files");
        for (File s : f.listFiles()) {
            if (s.isFile()) {
                String name = s.getName();
                if (name.indexOf("switch_project") == 0) {
                    listWithFile.add(s);
                    name = name.replace("switch_project_", "");
                    name = name.replace(".bat", "");
                    listWithFileNames.add(name);
                }
            }

        }

    }

    /**
     *
     * @param index
     * @param debug 0-not change, 1 - with debug mode, 2 - with job mode
     */
    public static void switchProject(int index, int debug) {
        String line = "";
        Project[] projects = OpenProjects.getDefault().getOpenProjects();
        if (projects.length > 0) {
            // Close opened projects
            OpenProjects.getDefault().close(projects);
        }
        try {
            if (debug > 0) {
                String pathToIni = "C:/Zend/ZendServer/etc/php.ini";
                File IniFile = new File(pathToIni);
                ArrayList<String> lines = new ArrayList<>();
                int i = 0;
                int extStringIndex = -1;
                try (BufferedReader br = new BufferedReader(new FileReader(IniFile))) {
                    line = br.readLine();
                    while (line != null) {
                        if (line.contains("ZendExtensionManager")) {
                            if (debug == 1) { //debug mode
                                line = ";" + line;
                            } else if (debug == 2) {//job mode
                                line = line.replaceAll("^;+", "");
                            }
                            extStringIndex = i;
                            System.out.println(line);
                        }
                        lines.add(line);
                        line = br.readLine();
                        i++;
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
                try (FileWriter fw = new FileWriter(pathToIni)) {
                    for (i = 0; i < lines.size(); i++) {
                        fw.write(lines.get(i));
                        fw.write("\r\n");
                    }
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
            String foldername = "C:/work/" + listWithFileNames.get(index);
            foldername = foldername.replace("_Test", "");// Test projects have same folder
            System.out.println(foldername);
            File projectToBeOpenedFile = new File(foldername);
            FileObject projectToBeOpened = FileUtil.toFileObject(projectToBeOpenedFile);
            Project project = ProjectManager.getDefault().findProject(projectToBeOpened);
            Project[] array = new Project[1];
            array[0] = project;
            OpenProjects.getDefault().open(array, false);
            String[] cmd = new String[1];
            cmd[0] = listWithFile.get(index).getAbsolutePath();
            System.out.println(cmd[0]);
            try {
                Runtime r = Runtime.getRuntime();
                Process p = r.exec(cmd);
                int exitVal = p.waitFor();
                System.out.println("Exit code:" + exitVal);
            } catch (InterruptedException iex) {
                System.out.println(iex);
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /*	
	 public void performAction() {
	 try {
	 JFileChooser projectChooser = new JFileChooser();
	 projectChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	 projectChooser.showOpenDialog(null);
	 File projectToBeOpenedFile = projectChooser.getSelectedFile(); 
	 FileObject projectToBeOpened = FileUtil.toFileObject(projectToBeOpenedFile);
	 Project project = ProjectManager.getDefault().findProject(projectToBeOpened);
	 Project[[ | ]] array = new Project[1];
	 array[0] = project;
	 OpenProjects.getDefault().open(array, false);

	 } catch (IOException ex) {
	 Exceptions.printStackTrace(ex);
	 }
	 }
	
     */
 /*
	 private String getProjectDirectory() {
	 try {
	 Project project = OpenProjects.getDefault().getOpenProjects()[0];
	 FileObject projectDir = project.getProjectDirectory();
	 String cd = projectDir.getPath();
	 if (cd == null) {
	 cd = "";
	 }
	 return cd;
	 } catch (Exception e) {
	 //ignore the exception
	 return "";
	 }
	 }
     */
}