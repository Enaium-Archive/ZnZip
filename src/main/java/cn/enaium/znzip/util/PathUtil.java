package cn.enaium.znzip.util;

import cn.enaium.znzip.ZnZip;
import cn.enaium.znzip.struct.FileEntry;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Enaium
 */
public class PathUtil {
    public static List<String> getList() {
        Set<String> list = new HashSet<>();
        for (String s : ZnZip.INSTANCE.dirs) {
            if (s.contains("/")) {
                list.add(s.substring(0, s.indexOf("/")));
            }
        }
        return new ArrayList<>(list);
    }

    public static List<FileEntry> getList(String name) {
        Map<String, String> list = new HashMap<>();
        for (String s : ZnZip.INSTANCE.dirs) {
            if (s.startsWith(name)) {
                String substring = s.substring(name.length());

                if (substring.contains("/")) {
                    substring = substring.substring(0, substring.indexOf("/"));
                }

                if (substring.isEmpty()) {
                    continue;
                }

                list.put(substring, s);
            }
        }

        List<FileEntry> fileEntries = new ArrayList<>();
        list.forEach((k, v) -> {
            fileEntries.add(new FileEntry(k, v));
        });

        return new ArrayList<>(fileEntries);
    }

    public static String getPath(Object[] path) {
        StringBuilder stringBuffer = new StringBuilder();



        for (int i = 1, objectsLength = path.length; i < objectsLength; i++) {
            Object o = path[i];
            stringBuffer.append(o.toString()).append("/");
        }

        return stringBuffer.toString();
    }

    public static boolean isSelectDir(String fullName, String path) {
        return fullName.replace(path, "").split("/").length != 1;
    }
}
