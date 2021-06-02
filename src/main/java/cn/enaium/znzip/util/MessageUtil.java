package cn.enaium.znzip.util;

import javax.swing.*;

/**
 * @author Enaium
 */
public class MessageUtil {
    public static void error(Throwable e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, e.getMessage(), "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
