package cn.enaium.znzip;

import cn.enaium.znzip.config.Config;
import cn.enaium.znzip.panel.menu.MenuPanel;
import cn.enaium.znzip.panel.file.FilePanel;
import cn.enaium.znzip.util.ConfigUtil;
import cn.enaium.znzip.util.MessageUtil;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

/**
 * @author Enaium
 */
public enum ZnZip {

    INSTANCE;

    public Config config = new Config();

    public final LinkedHashMap<String, byte[]> files = new LinkedHashMap<>();
    public final LinkedHashSet<String> dirs = new LinkedHashSet<>();

    public JFrame jFrame;
    public MenuPanel menubar;
    public FilePanel filePanel;

    public static void main(String[] args) {

        ZnZip.INSTANCE.config = ConfigUtil.read(Config.class);
        ZnZip.INSTANCE.config.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> ConfigUtil.save(ZnZip.INSTANCE.config)));

        try {
            ZnZip.INSTANCE.init();
        } catch (UnsupportedLookAndFeelException e) {
            MessageUtil.error(e);
        }
    }

    public void init() throws UnsupportedLookAndFeelException {
        switch (config.theme.getCurrent()) {
            case "Light":
                UIManager.setLookAndFeel(new FlatLightLaf());
                break;
            case "Dark":
                UIManager.setLookAndFeel(new FlatDarkLaf());
                break;
        }

        jFrame = new JFrame("ZnZip");
        jFrame.setSize(700, 500);
        jFrame.setLocationRelativeTo(null);
        jFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        menubar = new MenuPanel();
        filePanel = new FilePanel();

        jFrame.setJMenuBar(menubar);
        jFrame.setContentPane(filePanel);

        jFrame.setVisible(true);
    }
}
