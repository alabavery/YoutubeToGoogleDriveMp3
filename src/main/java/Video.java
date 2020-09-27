public class Video {
    public static final String BASE_DIRECTORY = "downloads_temp";

    private String url;    
    private String name;
    private DriveFolder targetDirectory;
    private String pathToLocalMp3;

    private int downloadProgress;
    private int uploadProgress;

    public Video() {
        downloadProgress = 0;
        uploadProgress = 0;
    }

    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }
    public DriveFolder getTargetDirectory() {
        return targetDirectory;
    }
    public String getPathToMp3() {
        return pathToLocalMp3;
    }
    public int getDownloadProgress() {
        return downloadProgress;
    }
    public int getUploadProgress() {
        return uploadProgress;
    }

    public void setUrl(String urlToSetTo) {
        url = urlToSetTo;
    }
    public void setName(String nameToSetTo) {
        name = nameToSetTo;
    }
    public void setTargetDirectory(DriveFolder targetDirectoryToSetTo) {
        targetDirectory = targetDirectoryToSetTo;
    }
    public void setPathToLocalMp3(String path) {
        pathToLocalMp3 = path;
    }

    public void setDownloadProgress(int progress) {
        downloadProgress = progress;
    }
    public void setUploadProgress(int progress) {
        uploadProgress = progress;
    }

}