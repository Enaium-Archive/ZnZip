package cn.enaium.znzip.panel.file;

import cn.enaium.znzip.struct.FileEntry;

import javax.swing.table.DefaultTableModel;

/**
 * @author Enaium
 */
public class FileTableNode extends DefaultTableModel {

    private final FileEntry fileEntry;

    public FileTableNode(FileEntry fileEntry) {
        this.fileEntry = fileEntry;
    }

    public FileEntry getFileEntry() {
        return fileEntry;
    }

    @Override
    public String toString() {
        return fileEntry.getName();
    }
}
