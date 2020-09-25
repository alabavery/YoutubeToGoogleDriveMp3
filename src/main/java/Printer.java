public class Printer extends Thread {
    Video[] toPrint;

    public Printer(Video[] toPrintArr) {
        toPrint = toPrintArr;
    }

    public void run() {
        while (true) {
            if (Thread.currentThread().isInterrupted()) {
                break;
            }
            try {
             Thread.sleep(200);   
            } catch (InterruptedException e) {
                break;
            }
            System.out.print("\033[H\033[2J");  
            System.out.flush();
            System.out.println("values now");
            for (int i = 0; i < toPrint.length; i++) {
                System.out.println(toPrint[i].getName());
                System.out.println("------------------------------");
                System.out.println("Download: " + toPrint[i].getDownloadProgress() + "%\tUpload: " + toPrint[i].getUploadProgress() + "%");
            }
        }
    }
}