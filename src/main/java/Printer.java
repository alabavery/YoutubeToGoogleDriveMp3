import java.util.List;

public class Printer extends Thread {
    List<Video> toPrint;

    public Printer(List<Video> toPrint) {
        this.toPrint = toPrint;
    }

    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {
             Thread.sleep(2000);   
            } catch (InterruptedException e) {
                break;
            }
            YoutubeToGoogleDriveMp3.clearConsole();
            for (int i = 0; i < this.toPrint.size(); i++) {
                System.out.println("------------------------------");
                System.out.println(this.toPrint.get(i).getName());
                String downloadString = "Download: " + this.toPrint.get(i).getDownloadProgress() + "%";
                String uploadString = "Upload: " + this.toPrint.get(i).getUploadProgress() + "%";
                System.out.println(downloadString + "\t" + uploadString);
                System.out.println("------------------------------");
            }
        }
    }
}