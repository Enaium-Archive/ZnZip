package cn.enaium.znzip.panel.menu;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.dialog.SearchContentDialog;
import cn.enaium.znzip.dialog.SettingDialog;
import cn.enaium.znzip.panel.AboutPanel;
import cn.enaium.znzip.util.LangUtil;

import javax.swing.*;

/**
 * @author Enaium
 */
public class MenuPanel extends JMenuBar {

    public final SaveFilePanel saveFilePanel = new SaveFilePanel();

    public MenuPanel() {
        JMenu fileMenu = new JMenu(LangUtil.i18n("menu.file"));

        JMenuItem setting = new JMenuItem(LangUtil.i18n("menu.file.setting"));
        setting.addActionListener(e -> {
            new SettingDialog();
        });

        fileMenu.add(new OpenFilePanel());
        fileMenu.add(saveFilePanel);
        JMenuItem searchContent = new JMenuItem(LangUtil.i18n("menu.file.searchContent"));
        searchContent.addActionListener(e -> {
            new SearchContentDialog();
        });
        fileMenu.add(searchContent);
        fileMenu.addSeparator();
        fileMenu.add(setting);

        JMenu aboutMenu = new JMenu(LangUtil.i18n("menu.about"));

        JMenuItem github = new JMenuItem("GitHUB");

        github.addActionListener(e -> {
            JOptionPane.showMessageDialog(ZnZip.INSTANCE.jFrame, new AboutPanel(), LangUtil.i18n("menu.about"), JOptionPane.INFORMATION_MESSAGE);
        });

        aboutMenu.add(github);

        add(fileMenu);
        add(aboutMenu);
    }
}
