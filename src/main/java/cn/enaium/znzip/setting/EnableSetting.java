package cn.enaium.znzip.setting;

import com.google.gson.annotations.Expose;

/**
 * @author Enaium
 */
public class EnableSetting extends Setting {

    @Expose
    private boolean enable;

    public EnableSetting(boolean enable) {
        this.enable = enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public boolean isEnable() {
        return enable;
    }
}
