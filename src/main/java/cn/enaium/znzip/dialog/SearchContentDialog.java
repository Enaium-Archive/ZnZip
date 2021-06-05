package cn.enaium.znzip.dialog;

import cn.enaium.znzip.panel.content.SearchContentPanel;
import cn.enaium.znzip.util.LangUtil;

/**
 * @author Enaium
 */
public class SearchContentDialog extends Dialog {
    public SearchContentDialog() {
        super(LangUtil.i18n("panel.content.search"));
        add(new SearchContentPanel());
    }
}
