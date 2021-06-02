package cn.enaium.znzip.dialog;

import cn.enaium.znzip.ZnZip;

import javax.swing.*;
import java.awt.*;

/**
 * @author Enaium
 */
public class Dialog extends JFrame {
    public Dialog(String title) {
        super(title);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setVisible(true);
    }
}
