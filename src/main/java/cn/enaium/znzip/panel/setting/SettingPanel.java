package cn.enaium.znzip.panel.setting;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.config.Config;
import cn.enaium.znzip.setting.EnableSetting;
import cn.enaium.znzip.setting.ModeSetting;
import cn.enaium.znzip.setting.Setting;
import cn.enaium.znzip.util.MessageUtil;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Field;

/**
 * @author Enaium
 */
public class SettingPanel extends JPanel {
    public SettingPanel() {
        setLayout(new BorderLayout());
        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridLayout(0, 1));
        Config config = ZnZip.INSTANCE.config;

        for (Field field : config.getClass().getFields()) {
            try {
                Setting o = ((Setting) field.get(config));
                if (o instanceof EnableSetting) {
                    JCheckBox jCheckBox = new JCheckBox(o.getName());

                    jCheckBox.setSelected(((EnableSetting) o).isEnable());

                    jCheckBox.addActionListener(e -> {
                        ((EnableSetting) o).setEnable(jCheckBox.isSelected());
                    });

                    jPanel.add(jCheckBox);
                } else if (o instanceof ModeSetting) {

                    JPanel checkPanel = new JPanel(new GridLayout(1, 2));
                    JComboBox<String> jComboBox = new JComboBox<>();
                    ((ModeSetting) o).getMode().forEach(jComboBox::addItem);
                    jComboBox.setSelectedItem(((ModeSetting) o).getCurrent());
                    jComboBox.addActionListener(e -> {
                        ((ModeSetting) o).setCurrent((String) jComboBox.getSelectedItem());
                    });

                    checkPanel.add(new JLabel(o.getName()));
                    checkPanel.add(jComboBox);
                    jPanel.add(checkPanel);
                }
            } catch (IllegalAccessException e) {
                MessageUtil.error(e);
            }
        }

        JScrollPane jScrollBar = new JScrollPane(jPanel);
        add(jScrollBar, BorderLayout.CENTER);
    }
}
