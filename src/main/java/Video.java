public class Video {
    String url;    
    String name;
    String targetDirectory;
    String pathToLocalMp3;

    int downloadProgress;
    int uploadProgress;

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
    public String getTargetDirectory() {
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
    public void setTargetDirectory(String targetDirectoryToSetTo) {
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