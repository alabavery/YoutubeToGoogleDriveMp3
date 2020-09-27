import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeDLService {
    private Pattern percentPattern;
    private Pattern wholeNumberInPercentPattern;

    public YoutubeDLService() {
        this.percentPattern = Pattern.compile("\\d+.\\d%");
        this.wholeNumberInPercentPattern = Pattern.compile("\\d+(?=[\\.%])");
    }

    public void downloadAndConvertToMp3(Video video) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
            "python3",
            "/usr/local/bin/youtube-dl",
            video.getUrl(),
            "--extract-audio",
            "--audio-format=mp3",
            "--output=" + Video.BASE_DIRECTORY + "/" + java.util.UUID.randomUUID() + ".mp3"
        );
        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher percentMatcher = percentPattern.matcher(line);
                if (percentMatcher.find()) {
                    String entirePercent = percentMatcher.group(0);
                    Matcher integerMatcher = wholeNumberInPercentPattern.matcher(entirePercent);
                    if (integerMatcher.find()) {
                        video.setDownloadProgress(Integer.parseInt(integerMatcher.group(0)));
                    }
                }
            }
            int exitVal = process.waitFor();
            if (exitVal == 0) {
                return;
            } else {
                //abnormal... 
                System.out.println("Something went wrong");
                System.out.println(output);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}