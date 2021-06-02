package cn.enaium.znzip.panel.file;

import cn.enaium.znzip.struct.FileEntry;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author Enaium
 */
public class FileTreeNode extends DefaultMutableTreeNode {

    private final FileEntry fileEntry;

    public FileTreeNode(FileEntry fileEntry) {
        this.fileEntry = fileEntry;
    }

    @Override
    public String toString() {
        return fileEntry.getName();
    }
}
