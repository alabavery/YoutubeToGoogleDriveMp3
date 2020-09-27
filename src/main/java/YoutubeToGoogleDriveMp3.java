import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileReader; 
import java.io.FileNotFoundException;

public class YoutubeToGoogleDriveMp3 {
    public static void main(String[] args) throws IOException, GeneralSecurityException {
        YoutubeDLService downloadService = new YoutubeDLService();
        DriveService driveService = new DriveService();
        List<Video> videos = YoutubeToGoogleDriveMp3.getNamesAndUrls();
        YoutubeToGoogleDriveMp3.assignDriveDirectories(driveService, videos);
        Worker[] workers = new Worker[videos.size()];

        for (int i = 0; i < videos.size(); i++) {
            workers[i] = new Worker(driveService, downloadService, videos.get(i));
        }
        Printer printer = new Printer(videos);
        printer.start();
        for (int i = 0; i < videos.size(); i++) {
            workers[i].start();
            System.out.println("worker started");
        }

        for (int i = 0; i < workers.length; i++) {
            if (i == 1) {
                System.out.println("On the second thread");
            }
            try {
                workers[i].join();
            } catch (InterruptedException e) {
                System.out.println("interrupted");
                break;
            }
        }
        printer.interrupt();
    }

    public static void clearConsole() {
        System.out.print("\033[H\033[2J");  
        System.out.flush();
    }

    // video file should be alternating lines of url, name
    private static List<Video> getNamesAndUrls() {
        List<Video> videos = new ArrayList<>();
        try {
            java.io.File videoFile = new java.io.File("temp/videos.txt");
            Scanner reader = new Scanner(videoFile);
            int lineNumber = 0;
            Video currentVideo = new Video();
            while (reader.hasNextLine()) {
                String next = reader.nextLine();
                if (lineNumber % 2 == 0) {
                    currentVideo.setUrl(next);
                } else {
                    currentVideo.setName(next);
                    videos.add(currentVideo);
                    currentVideo = new Video();
                }
                lineNumber++;
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Problem getting base drive folder id from file");
            e.printStackTrace();
        }
        return videos;
    }

    private static void assignDriveDirectories(DriveService driveService, List<Video> videos) {
        DriveFolder[] folderOptions = new DriveFolder[0];
        try {
            folderOptions = driveService.getFolders();
        } catch (IOException e) {
            System.out.println("Problem getting Google Drive folders:");
            e.printStackTrace();
        }
        Scanner scanner = new Scanner(System.in);
        for (Video video: videos) {
            YoutubeToGoogleDriveMp3.clearConsole();
            System.out.println(video.getName() + " (" + video.getUrl() + ")");
            System.out.println();
            // print folder options
            for (DriveFolder folder : folderOptions) {
                System.out.println("\t" + folder.name);
            }
            System.out.println();
            // get selection
            while (true) {
                System.out.print("Your selection:\t");
                try {
                    video.setTargetDirectory(DriveFolder.getFolderFromName(folderOptions, scanner.nextLine()));
                    break;
                } catch (RuntimeException e) {
                    System.out.println("Please enter the name of one of the directories above...");
                }
            }
        }
        YoutubeToGoogleDriveMp3.clearConsole();
        System.out.println("Ok, here is what we got:");
        for (Video video: videos) {
            System.out.println();
            System.out.println("Name: " + video.getName());
            System.out.println("Url: " + video.getUrl());
            System.out.println("Directory: " + video.getTargetDirectory().name);
        }
        System.out.println();
        System.out.println("Press enter to continue...");
        scanner.nextLine();
        scanner.close();
    }
}
