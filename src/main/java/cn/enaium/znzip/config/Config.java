package cn.enaium.znzip.config;

import cn.enaium.znzip.setting.EnableSetting;
import cn.enaium.znzip.setting.ModeSetting;
import com.google.gson.annotations.Expose;

import java.util.Arrays;

/**
 * @author Enaium
 */
public class Config {
    @Expose
    public EnableSetting listCenter = new EnableSetting(false);
    @Expose
    public ModeSetting language = new ModeSetting("System");
    @Expose
    public ModeSetting theme = new ModeSetting("Light");

    public void init() {
        listCenter.setName("File list centered");

        language.setName("Language");
        language.setMode(Arrays.asList("System", "en_US", "zh_CN"));

        theme.setName("Theme");
        theme.setMode(Arrays.asList("Light", "Dark"));
    }
}
