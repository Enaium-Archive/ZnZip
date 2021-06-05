package cn.enaium.znzip.panel.content;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.dialog.ContentDialog;
import cn.enaium.znzip.util.LangUtil;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author Enaium
 */
public class SearchContentPanel extends JPanel {
    public SearchContentPanel() {
        setLayout(new BorderLayout());

        DefaultListModel<String> stringDefaultListModel = new DefaultListModel<>();
        JList<String> stringJList = new JList<>(stringDefaultListModel);

        JTextField jTextField = new JTextField();

        class Action {
            public void action() {
                stringDefaultListModel.clear();
                for (Map.Entry<String, byte[]> stringEntry : ZnZip.INSTANCE.files.entrySet()) {
                    String content = new String(stringEntry.getValue(), StandardCharsets.UTF_8);
                    if (content.toLowerCase().contains(jTextField.getText().toLowerCase())) {
                        stringDefaultListModel.addElement(stringEntry.getKey());
                    }
                }
            }
        }

        Action action = new Action();

        jTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                action.action();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                action.action();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                action.action();
            }
        });

        JPopupMenu jPopupMenu = new JPopupMenu();
        JMenuItem jMenuItem = new JMenuItem(LangUtil.i18n("panel.content.search.view"));
        jMenuItem.addActionListener(e -> {
            new ContentDialog(new String(ZnZip.INSTANCE.files.get(stringJList.getSelectedValue()), StandardCharsets.UTF_8));
        });
        jPopupMenu.add(jMenuItem);

        stringJList.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    if (stringJList.getSelectedValue() != null) {
                        jPopupMenu.show(SearchContentPanel.this, e.getX(), e.getY());
                    }
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

        add(jTextField, BorderLayout.NORTH);
        add(new JScrollPane(stringJList), BorderLayout.CENTER);
    }
}
