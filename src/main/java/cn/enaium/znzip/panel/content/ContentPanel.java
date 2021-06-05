package cn.enaium.znzip.panel.content;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 */
public class ContentPanel extends JPanel {
    public ContentPanel(String content) {
        setLayout(new BorderLayout());
        JTextArea jTextArea = new JTextArea(content);
        jTextArea.setEditable(false);
        add(new JScrollPane(jTextArea), BorderLayout.CENTER);
    }
}
