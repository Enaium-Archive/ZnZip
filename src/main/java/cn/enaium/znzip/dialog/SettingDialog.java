package cn.enaium.znzip.dialog;

import cn.enaium.znzip.panel.setting.SettingPanel;
import cn.enaium.znzip.util.LangUtil;

/**
 * @author Enaium
 */
public class SettingDialog extends Dialog {
    public SettingDialog() {
        super(LangUtil.i18n("menu.file.setting"));
        setContentPane(new SettingPanel());
    }
}
