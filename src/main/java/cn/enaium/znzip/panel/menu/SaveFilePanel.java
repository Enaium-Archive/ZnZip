package cn.enaium.znzip.panel.menu;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.util.JFileChooserUtil;
import cn.enaium.znzip.util.LangUtil;
import cn.enaium.znzip.util.MessageUtil;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

/**
 * @author Enaium
 */
public class SaveFilePanel extends JMenuItem {

    public SaveFilePanel() {
        super(LangUtil.i18n("menu.file.save"));
        setEnabled(false);
        addActionListener(e -> {
            File selectFile = JFileChooserUtil.show(JFileChooserUtil.Type.SAVE);

            if (selectFile == null) {
                return;
            }

            try {
                ZipOutputStream outputStream = new ZipOutputStream(new BufferedOutputStream(new FileOutputStream(selectFile)), StandardCharsets.UTF_8);

                for (Map.Entry<String, byte[]> entry : ZnZip.INSTANCE.files.entrySet()) {
                    outputStream.putNextEntry(new ZipEntry(entry.getKey()));
                    outputStream.write(entry.getValue());
                }
                outputStream.closeEntry();
                outputStream.close();
            } catch (IOException ioException) {
                MessageUtil.error(ioException);
            }

            JOptionPane.showMessageDialog(ZnZip.INSTANCE.jFrame, LangUtil.i18n("panel.message.saveSuccess"));
        });
    }
}
