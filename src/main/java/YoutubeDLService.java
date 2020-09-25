import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class YoutubeDLService {
    Pattern percentPattern;

    public YoutubeDLService() {
        percentPattern = Pattern.compile("\\d+.\\d%");
    }

    public void downloadAndConvertToMp3() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        processBuilder.command(
            "python3",
            "/usr/local/bin/youtube-dl",
            "https://www.youtube.com/watch?v=SDUDlAEv5pU",
            "--extract-audio",
            "--audio-format=mp3",
            "--output=~/bin/temp.mp3"
        );
        try {
            Process process = processBuilder.start();
            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                Matcher percentMatcher = percentPattern.matcher(line);
                if (percentMatcher.find()) {
                    String group = percentMatcher.group(0);
                    String withoutPercent = group.substring(0, group.length() - 1);
                    System.out.println(Double.parseDouble(withoutPercent));
                }
            }

            int exitVal = process.waitFor();
            if (exitVal == 0) {
                System.out.println("Success!");
                System.out.println(output);
                System.exit(0);
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