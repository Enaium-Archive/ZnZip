package cn.enaium.znzip.panel.file;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.panel.menu.OpenFilePanel;
import cn.enaium.znzip.struct.FileEntry;
import cn.enaium.znzip.util.LangUtil;
import cn.enaium.znzip.util.MessageUtil;
import cn.enaium.znzip.util.PathUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Enaium
 */
public class FileTreePanel extends JTree {

    public static final DefaultMutableTreeNode root = new DefaultMutableTreeNode("/");

    public final List<String> added = new ArrayList<>();

    JPopupMenu jPopupMenu = new JPopupMenu();
    JMenuItem newFolder = new JMenuItem(LangUtil.i18n("panel.filePanel.fileTreePanel.newFolder"));

    public FileTreePanel(TreeNode root) {
        super(root);

        addTreeSelectionListener(e -> {
            DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode) e.getPath().getLastPathComponent();

            String path = PathUtil.getPath(e.getPath().getPath());

            for (FileEntry fileEntry : PathUtil.getList(path)) {

                if (added.contains(fileEntry.toString())) continue;

                added.add(fileEntry.toString());

                ((DefaultTreeModel) ZnZip.INSTANCE.filePanel.fileTree.getModel()).insertNodeInto(new FileTreeNode(fileEntry), lastPathComponent, lastPathComponent.getChildCount());
            }

            DefaultTableModel model = (DefaultTableModel) ZnZip.INSTANCE.filePanel.fileTable.getModel();

            model.setRowCount(0);


            for (FileEntry fileEntry : PathUtil.getList(path)) {
                model.addRow(new Object[]{new FileTableNode(fileEntry), ""});
            }

            ZnZip.INSTANCE.files.forEach((k, v) -> {
                if (k.startsWith(path)) {
                    FileEntry fileEntry = new FileEntry(k.substring(k.lastIndexOf("/") + 1), k);

                    if (!PathUtil.isSelectDir(k, path)) {
                        String size = "";

                        if (ZnZip.INSTANCE.files.containsKey(fileEntry.getFullName())) {
                            size = String.valueOf(ZnZip.INSTANCE.files.get(fileEntry.getFullName()).length);
                        }
                        model.addRow(new Object[]{new FileTableNode(fileEntry), size});
                    }
                }
            });
        });

        newFolder.addActionListener(e -> {
            DefaultMutableTreeNode lastPathComponent;
            if (getSelectionPath() == null) {
                lastPathComponent = FileTreePanel.root;
            } else {
                lastPathComponent = (DefaultMutableTreeNode) getSelectionPath().getLastPathComponent();
            }

            String folderName = JOptionPane.showInputDialog(null, LangUtil.i18n("panel.filePanel.fileTablePanel.folderName"), LangUtil.i18n("panel.filePanel.fileTablePanel.newFolder"));

            ((DefaultTreeModel) getModel()).insertNodeInto(new DefaultMutableTreeNode(folderName), lastPathComponent, lastPathComponent.getChildCount());
        });

        jPopupMenu.add(newFolder);

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    jPopupMenu.show(FileTreePanel.this, e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

        setTransferHandler(new TransferHandler() {
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    String transferData = t.getTransferData(DataFlavor.javaFileListFlavor).toString();
                    String s = transferData.substring(transferData.indexOf("[") + 1, transferData.indexOf("]")).split(",")[0];

                    File file = new File(s);

                    if (file.isFile()) {
                        OpenFilePanel.open(file);
                    }
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    MessageUtil.error(e);
                }
                return false;
            }

            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {
                for (DataFlavor flavor : flavors) {
                    if (DataFlavor.javaFileListFlavor.equals(flavor)) {
                        return true;
                    }
                }
                return false;
            }
        });
    }
}
