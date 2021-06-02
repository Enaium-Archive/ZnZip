package cn.enaium.znzip.struct;

/**
 * @author Enaium
 */
public class FileEntry {
    private final String name;
    private final String fullName;

    public FileEntry(String name, String fullName) {
        this.name = name;
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public String getFullName() {
        return fullName;
    }

    @Override
    public String toString() {
        return "FileEntry{" +
                "name='" + name + '\'' +
                ", fullName='" + fullName + '\'' +
                '}';
    }
}
