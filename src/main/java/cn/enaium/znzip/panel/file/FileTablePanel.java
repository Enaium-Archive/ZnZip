package cn.enaium.znzip.panel.file;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.struct.FileEntry;
import cn.enaium.znzip.util.JFileChooserUtil;
import cn.enaium.znzip.util.LangUtil;
import cn.enaium.znzip.util.MessageUtil;
import cn.enaium.znzip.util.PathUtil;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.DirectoryFileFilter;
import org.apache.commons.io.filefilter.TrueFileFilter;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;
import javax.swing.tree.TreePath;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * @author Enaium
 */
public class FileTablePanel extends JTable {

    public static final DefaultTableModel model = new DefaultTableModel(new Object[][]{}, new String[]{LangUtil.i18n("panel.filePanel.fileTablePanel.name"), LangUtil.i18n("panel.filePanel.fileTablePanel.size")});

    JPopupMenu jPopupMenu = new JPopupMenu();
    JMenuItem newFolder = new JMenuItem(LangUtil.i18n("panel.filePanel.fileTablePanel.unzip"));


    public FileTablePanel(TableModel dm) {
        super(dm);
        getTableHeader().setReorderingAllowed(false);

        newFolder.addActionListener(e -> {

            if (getSelectedRows().length == 1) {

                File selectFile = JFileChooserUtil.show(JFileChooserUtil.Type.SAVE, new JFileChooser().getAcceptAllFileFilter());

                if (selectFile != null) {
                    try {
                        FileUtils.writeByteArrayToFile(selectFile, ZnZip.INSTANCE.files.get(((FileTableNode) getValueAt(getSelectedRow(), 0)).getFileEntry().getFullName()));
                    } catch (IOException exception) {
                        exception.printStackTrace();
                        MessageUtil.error(exception);
                    }
                    JOptionPane.showMessageDialog(ZnZip.INSTANCE.jFrame, LangUtil.i18n("panel.message.unzipSuccess"));
                }

                return;
            }

            File selectDir = JFileChooserUtil.show(JFileChooserUtil.Type.SAVE, new FileFilter() {
                @Override
                public boolean accept(File f) {
                    return true;
                }

                @Override
                public String getDescription() {
                    return "Folder";
                }
            }, JFileChooser.DIRECTORIES_ONLY);
            if (selectDir != null) {
                for (int selectedRow : getSelectedRows()) {
                    Object valueAt = getValueAt(selectedRow, 0);
                    if (valueAt instanceof FileTableNode) {
                        FileEntry fileEntry = ((FileTableNode) valueAt).getFileEntry();
                        try {
                            if (fileEntry.getFullName().endsWith("/")) {
                                for (Map.Entry<String, byte[]> entry : ZnZip.INSTANCE.files.entrySet()) {
                                    if (entry.getKey().startsWith(fileEntry.getFullName())) {
                                        FileUtils.writeByteArrayToFile(new File(selectDir, fileEntry.getName() + File.separator + entry.getKey()), entry.getValue());
                                    }
                                }
                            } else {
                                FileUtils.writeByteArrayToFile(new File(selectDir, fileEntry.getName()), ZnZip.INSTANCE.files.get(fileEntry.getFullName()));
                            }
                        } catch (IOException exception) {
                            exception.printStackTrace();
                            MessageUtil.error(exception);
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(ZnZip.INSTANCE.jFrame, LangUtil.i18n("panel.message.unzipSuccess"));
            }
        });

        jPopupMenu.add(newFolder);

        getTableHeader().setTransferHandler(new TransferHandler() {
            @Override
            public boolean importData(JComponent comp, Transferable t) {
                try {
                    String transferData = t.getTransferData(DataFlavor.javaFileListFlavor).toString();
                    String[] filePath = transferData.substring(transferData.indexOf("[") + 1, transferData.indexOf("]")).split(",");

                    TreePath selectionPath = ZnZip.INSTANCE.filePanel.fileTree.getSelectionPath();

                    if (selectionPath == null) {
                        selectionPath = new TreePath(FileTreePanel.root);
                    }

                    for (String path : filePath) {
                        File file = new File(path);
                        if (file.isFile()) {
                            ZnZip.INSTANCE.files.put(PathUtil.getPath(selectionPath.getPath()) + file.getName(), FileUtils.readFileToByteArray(file));
                        } else if (file.isDirectory()) {
                            for (File listFilesAndDir : FileUtils.listFilesAndDirs(file, TrueFileFilter.INSTANCE, DirectoryFileFilter.INSTANCE)) {

                                String listFilePath = listFilesAndDir.getAbsolutePath().substring(file.getAbsolutePath().length());
                                if (listFilePath.startsWith(File.separator)) {
                                    listFilePath = listFilePath.substring(1);
                                }

                                if (listFilePath.contains("\\")) {
                                    listFilePath = listFilePath.replace("\\", "/");
                                }

                                if (listFilesAndDir.isDirectory()) {
                                    ZnZip.INSTANCE.dirs.add(PathUtil.getPath(selectionPath.getPath()) + listFilePath + "/");
                                } else if (listFilesAndDir.isFile()) {
                                    ZnZip.INSTANCE.files.put(PathUtil.getPath(selectionPath.getPath()) + listFilePath, FileUtils.readFileToByteArray(listFilesAndDir));
                                }
                            }
                        }
                    }

                    ZnZip.INSTANCE.filePanel.fileTree.setSelectionPath(null);
                    ZnZip.INSTANCE.filePanel.fileTree.setSelectionPath(selectionPath);
                    ZnZip.INSTANCE.filePanel.fileTree.expandPath(selectionPath);
                    return true;
                } catch (UnsupportedFlavorException | IOException e) {
                    MessageUtil.error(e);
                }
                return false;
            }

            @Override
            public boolean canImport(JComponent comp, DataFlavor[] flavors) {

                if (ZnZip.INSTANCE.filePanel.fileTree.getSelectionPath() == null) {
                    return false;
                }

                for (DataFlavor flavor : flavors) {
                    if (DataFlavor.javaFileListFlavor.equals(flavor)) {
                        return true;
                    }
                }
                return false;
            }
        });

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (!SwingUtilities.isRightMouseButton(e) || getSelectedRow() == -1) return;

                jPopupMenu.show(FileTablePanel.this, e.getX(), e.getY());
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
    }

    @Override
    public TableCellRenderer getDefaultRenderer(Class<?> columnClass) {
        DefaultTableCellRenderer defaultRenderer = (DefaultTableCellRenderer) super.getDefaultRenderer(columnClass);
        if (ZnZip.INSTANCE.config.listCenter.isEnable()) {
            defaultRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        } else {
            defaultRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        }

        return defaultRenderer;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
}
