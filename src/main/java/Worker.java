import java.io.IOException;

public class Worker extends Thread {
    private DriveService driveService;
    private YoutubeDLService youtubeDLService;
    private Video video;

    public Worker(DriveService driveService, YoutubeDLService youtubeDLService, Video video) {
        this.driveService = driveService;
        this.youtubeDLService = youtubeDLService;
        this.video = video;
    }

    public void run() {
        // todo how do we get output progress from this?
        youtubeDLService.downloadAndConvertToMp3();
        try {
            // todo how do we get output progress from this?
            driveService.upload(video.getPathToMp3(), video.getName(), video.getTargetDirectory());
        } catch (Exception e) {

        }
    }
}
