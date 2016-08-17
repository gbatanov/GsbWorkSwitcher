/*
 * 
 *  $Id: WorkProjectsDialog.java 1999 2016-05-06 13:22:54Z gsb $
 */
package ru.gsbhost.workswitcher;

import java.io.File;
import java.util.ArrayList;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.ImageIcon;
import javax.swing.JRadioButton;
import org.netbeans.api.project.Project;
import org.netbeans.api.project.ui.OpenProjects;

/**
 *
 * @author gbatanov
 */
public class WorkProjectsDialog extends javax.swing.JDialog {

    public static WorkProjectsDialog dialog = null;
    public static ArrayList<JRadioButton> rbList = new ArrayList<>();
    public static int index = -1;
    private javax.swing.JButton btSelect;//, btDebug, btJob;
    private javax.swing.ButtonGroup buttonGroup1;

    /**
     * Creates new form WorkProjectsDialog
     */
    public WorkProjectsDialog(java.awt.Frame parent, boolean modal, ArrayList<File> listWithFile) {
        super(parent, modal);

        initMyComponents(listWithFile);
    }

    private void initMyComponents(ArrayList<File> listWithFile) {

        index = -1;
        setTitle(org.openide.util.NbBundle.getMessage(WorkProjectsDialog.class, "WorkProjectsDialog.title")); // NOI18N
        Project[] projects = OpenProjects.getDefault().getOpenProjects();
        // TODO: use a project's directory
        ImageIcon im = new ImageIcon("C:\\work\\WorkSwitcher\\src\\ru\\gsbhost\\workswitcher\\gsb.png");
        setIconImage(im.getImage());

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup1.clearSelection();
        for (int i = 0; i < listWithFile.size(); ++i) {
            javax.swing.JRadioButton rbButton = new javax.swing.JRadioButton();
            org.openide.awt.Mnemonics.setLocalizedText(rbButton, listWithFile.get(i).getName().replace("switch_project_", "").replace(".bat", "")); // NOI18N
            rbButton.addActionListener(new java.awt.event.ActionListener() {
                @Override
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    rbButtonActionPerformed(evt);
                }
            });

            rbList.add(rbButton);
            buttonGroup1.add(rbButton);
        }

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btSelect = new javax.swing.JButton();
        org.openide.awt.Mnemonics.setLocalizedText(btSelect, org.openide.util.NbBundle.getMessage(WorkProjectsDialog.class, "WorkProjectsDialog.btSelect.text")); // NOI18N
        btSelect.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btSelectActionPerformed(evt);
            }
        });
        /*
		btDebug = new javax.swing.JButton();
		org.openide.awt.Mnemonics.setLocalizedText(btDebug, org.openide.util.NbBundle.getMessage(WorkProjectsDialog.class, "WorkProjectsDialog.btDebug.text")); // NOI18N
		btDebug.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btDebugActionPerformed(evt);
			}
		});

		btJob = new javax.swing.JButton();
		org.openide.awt.Mnemonics.setLocalizedText(btJob, org.openide.util.NbBundle.getMessage(WorkProjectsDialog.class, "WorkProjectsDialog.btJob.text")); // NOI18N
		btJob.addActionListener(new java.awt.event.ActionListener() {
			@Override
			public void actionPerformed(java.awt.event.ActionEvent evt) {
				btJobActionPerformed(evt);
			}
		});
         */
        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        ParallelGroup gph = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        for (int i = 0; i < rbList.size(); ++i) {

            GroupLayout.SequentialGroup sgh = layout.createSequentialGroup();
            sgh.addGap(4, 4, 4);
            sgh.addComponent(rbList.get(i));
            gph.addGroup(sgh);
        }

        GroupLayout.SequentialGroup sgbh = layout.createSequentialGroup().addGap(16, 16, 16)// Отжимает группу кнопок слева
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        //						.addGroup(layout.createSequentialGroup()
                        //								.addComponent(btDebug)
                        //								.addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        //								.addComponent(btJob))
                        .addComponent(btSelect))
                .addContainerGap(215, Short.MAX_VALUE);

        gph.addGroup(javax.swing.GroupLayout.Alignment.TRAILING, sgbh);
        layout.setHorizontalGroup(gph);

        ParallelGroup gpv = layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING);
        int GapButton = 24;
        for (int i = 0; i < rbList.size(); ++i) {
            GroupLayout.SequentialGroup sgv = layout.createSequentialGroup();
            sgv.addGap(16 + 16 * i, 16 + 16 * i, 24 + 24 * i);
            sgv.addComponent(rbList.get(i));
            gpv.addGroup(sgv);
            GapButton += 24;
        }

        GroupLayout.SequentialGroup sgbv = layout.createSequentialGroup()
                .addContainerGap(GapButton, Short.MAX_VALUE) // Отжимает кнопки от радиобаттонов
                .addComponent(btSelect)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                //				.addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                //						.addComponent(btDebug)
                //						.addComponent(btJob))
                .addGap(18, 18, 18);// Отжимает группу кнопок снизу

        gpv.addGroup(sgbv);

        layout.setVerticalGroup(gpv);

        pack();
    }

    private void rbButtonActionPerformed(java.awt.event.ActionEvent evt) {
        index = rbList.indexOf(evt.getSource());
        System.out.println("rbButton selected: " + index);
    }

    private void btSelectActionPerformed(java.awt.event.ActionEvent evt) {
        if (index < 0) {
            System.out.println("index -1");
        } else {
            System.out.println(Switcher.listWithFileNames.get(index));
            dialog.setVisible(false);
            Switcher.switchProject(index, 0);
        }
    }

    /*
	private void btDebugActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("with Debug");
		if (index < 0) {
			System.out.println("index -1");
		} else {
			System.out.println(Switcher.listWithFileNames.get(index));
			dialog.setVisible(false);
			Switcher.switchProject(index, 1);
		}

	}

	private void btJobActionPerformed(java.awt.event.ActionEvent evt) {
		System.out.println("with Job");
		if (index < 0) {
			System.out.println("index -1");
		} else {
			System.out.println(Switcher.listWithFileNames.get(index));
			dialog.setVisible(false);
			Switcher.switchProject(index, 2);
		}

	}
     */
    /**
     *
     */
    public static void showDialog(ArrayList<File> listWithFile) {

        java.awt.EventQueue.invokeLater(() -> {
            if (dialog == null) {
                dialog = new WorkProjectsDialog(new javax.swing.JFrame(), true, listWithFile);
                dialog.addWindowListener(
                        new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        dialog.setVisible(false);
                    }
                }
                );
                dialog.setLocationRelativeTo(null);
            }
            dialog.setVisible(true);
        });
    }

}
