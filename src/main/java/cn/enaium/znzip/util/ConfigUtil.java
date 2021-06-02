package cn.enaium.znzip.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;

/**
 * @author Enaium
 */
public class ConfigUtil {
    public static <T> T read(Class<?> t) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            return gson.fromJson(FileUtils.readFileToString(new File(".", "config.json"), StandardCharsets.UTF_8), (Type) t);
        } catch (IOException ioException) {
            try {
                save(t.newInstance());
            } catch (InstantiationException | IllegalAccessException reflectiveOperationException) {
                MessageUtil.error(reflectiveOperationException);
            }
        }
        return read(t);
    }

    public static <T> void save(T t) {
        try {
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();
            FileUtils.writeByteArrayToFile(new File(".", "config.json"), gson.toJson(t).getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            MessageUtil.error(e);
        }
    }
}
