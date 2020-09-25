import java.io.IOException;
import java.security.GeneralSecurityException;

public class YoutubeToGoogleDriveMp3 {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        YoutubeDLService downloadService = new YoutubeDLService();
        downloadService.downloadAndConvertToMp3();

        // YoutubeDLService downloadService = new YoutubeDLService();
        // DriveService driveService = new DriveService();
        // Video[] videos = YoutubeToGoogleDriveMp3.getVideoData(driveService);
        // Worker[] workers = new Worker[videos.length];

        // for (int i = 0; i < videos.length; i++) {
        //     workers[i] = new Worker(driveService, downloadService, videos[i]);
        //     workers[i].run();
        // }
        // Printer printer = new Printer(videos);
        // printer.run();

        // for (int i = 0; i < videos.length; i++) {
        //     try {
        //         workers[i].join();
        //     } catch (InterruptedException e) {
        //         break;
        //     }
        // }
    }

    public static Video[] getVideoData(DriveService driveService) {
        Video[] videos = new Video[1];
        for (int i = 0; i < 1; i++) {
            Video video = new Video();
            video.setName("ff");
            video.setUrl("fff");
        }        
        return videos;
    }
}
