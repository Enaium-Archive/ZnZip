package cn.enaium.znzip.panel.file;

import cn.enaium.znzip.ZnZip;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

/**
 * @author Enaium
 */
public class FilePanel extends JSplitPane {

    public FileTreePanel fileTree = new FileTreePanel(FileTreePanel.root);
    public FileTablePanel fileTable = new FileTablePanel(FileTablePanel.model);

    public FilePanel() {
        super(JSplitPane.HORIZONTAL_SPLIT);
        setDividerLocation(300);
        setLeftComponent(new JScrollPane(fileTree));
        setRightComponent(new JScrollPane(fileTable));
        setContinuousLayout(true);
        setOneTouchExpandable(true);
    }

    public void reset() {

        ((DefaultTableModel) fileTable.getModel()).setRowCount(0);

        ((DefaultMutableTreeNode) fileTree.getModel().getRoot()).removeAllChildren();
        ((DefaultTreeModel) fileTree.getModel()).reload();

        fileTree.added.clear();
        ZnZip.INSTANCE.files.clear();
        ZnZip.INSTANCE.dirs.clear();
    }
}
