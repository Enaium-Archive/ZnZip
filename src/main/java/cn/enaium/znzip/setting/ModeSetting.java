package cn.enaium.znzip.setting;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * @author Enaium
 */
public class ModeSetting extends Setting {

    @Expose
    private String current;

    private List<String> mode;

    public ModeSetting(String current) {

        this.current = current;
    }

    public String getCurrent() {
        return current;
    }

    public void setCurrent(String current) {
        this.current = current;
    }

    public List<String> getMode() {
        return mode;
    }

    public void setMode(List<String> mode) {
        this.mode = mode;
    }
}
