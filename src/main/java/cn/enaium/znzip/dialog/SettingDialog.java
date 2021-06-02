package cn.enaium.znzip.dialog;

import cn.enaium.znzip.panel.setting.SettingPanel;

/**
 * @author Enaium
 */
public class SettingDialog extends Dialog {
    public SettingDialog() {
        super("Setting");
        setContentPane(new SettingPanel());
    }
}
