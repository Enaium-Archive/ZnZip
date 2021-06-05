package cn.enaium.znzip.dialog;

import cn.enaium.znzip.panel.content.ContentPanel;
import cn.enaium.znzip.util.LangUtil;

/**
 * @author Enaium
 */
public class ContentDialog extends Dialog {
    public ContentDialog(String content) {
        super(LangUtil.i18n("panel.content.content"));
        add(new ContentPanel(content));
    }
}
