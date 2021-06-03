package cn.enaium.znzip.setting;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author Enaium
 */
public class ModeSetting extends Setting<String> {

    private List<String> mode;

    public ModeSetting(String value) {
        super(value);
    }

    public List<String> getMode() {
        return mode;
    }

    public void setMode(List<String> mode) {
        this.mode = mode;
    }
}
