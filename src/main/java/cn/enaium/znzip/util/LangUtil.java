package cn.enaium.znzip.util;

import cn.enaium.znzip.ZnZip;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

/**
 * @author Enaium
 */
public class LangUtil {
    public static String i18n(String key) {
        Locale locale = Locale.getDefault();
        String lang = locale.getLanguage() + "_" + locale.getCountry();
        if (!ZnZip.INSTANCE.config.language.getValue().equals("System")) {
            lang = ZnZip.INSTANCE.config.language.getValue();
        }
        try {
            URL url = LangUtil.class.getResource("/lang/" + lang + ".json");
            if (url == null) {
                url = LangUtil.class.getResource("/lang/en_US.json");
            }

            if (url == null) {
                RuntimeException runtimeException = new RuntimeException("Lang not Found!");
                MessageUtil.error(runtimeException);
                throw runtimeException;
            }

            String text = IOUtils.toString(url, StandardCharsets.UTF_8);
            return new Gson().fromJson(text, JsonObject.class).get(key).getAsString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
