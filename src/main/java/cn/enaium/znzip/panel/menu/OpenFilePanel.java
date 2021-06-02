package cn.enaium.znzip.panel.menu;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.panel.file.FileTreePanel;
import cn.enaium.znzip.util.JFileChooserUtil;
import cn.enaium.znzip.util.LangUtil;
import cn.enaium.znzip.util.MessageUtil;
import org.apache.commons.io.IOUtils;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.TreePath;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Enaium
 */
public class OpenFilePanel extends JMenuItem {
    public OpenFilePanel() {
        super(LangUtil.i18n("menu.file.open"));

        addActionListener(e -> {
            File selectFile = JFileChooserUtil.show(JFileChooserUtil.Type.OPEN);

            if (selectFile == null) {
                return;
            }

            open(selectFile);
        });
    }

    public static void open(File file) {
        ZnZip.INSTANCE.menubar.saveFilePanel.setEnabled(true);
        ZnZip.INSTANCE.files.clear();
        ZnZip.INSTANCE.filePanel.reset();

        try {
            ZipFile zipFile = new ZipFile(file);

            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry zipEntry = entries.nextElement();

                if (zipEntry.isDirectory()) {
                    ZnZip.INSTANCE.dirs.add(zipEntry.getName());
                } else {
                    ZnZip.INSTANCE.files.put(zipEntry.getName(), IOUtils.toByteArray(zipFile.getInputStream(zipEntry)));
                }
            }
            zipFile.close();

            ZnZip.INSTANCE.filePanel.fileTree.setSelectionPath(new TreePath(ZnZip.INSTANCE.filePanel.fileTree.getModel().getRoot()));
            ZnZip.INSTANCE.filePanel.fileTree.expandPath(new TreePath(ZnZip.INSTANCE.filePanel.fileTree.getModel().getRoot()));
        } catch (IOException exception) {
            MessageUtil.error(exception);
        }
    }
}
