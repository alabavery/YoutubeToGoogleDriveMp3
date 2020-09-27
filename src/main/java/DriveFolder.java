public class DriveFolder {
    public String id;
    public String name;

    public DriveFolder(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DriveFolder getFolderFromName(DriveFolder[] toSearch, String name) {
        for (DriveFolder folder : toSearch) {
            if (folder.name.equals(name)) {
                return folder;
            }
        }
        throw new RuntimeException();
    }
}
