import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.client.http.FileContent;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class DriveService {
    private static final String BASE_DRIVE_FOLDER_ID_FILE = "secrets/BASE_DRIVE_FOLDER_ID.txt";

    private Drive drive;
    private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";
    /**
     * Global instance of the scopes required by this quickstart.
     * If modifying these scopes, delete your previously saved tokens/ folder.
     */
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
    private static final String CREDENTIALS_FILE_PATH = "/credentials.json";


    public DriveService() throws IOException, GeneralSecurityException {
        // Build a new authorized API client service.
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public DriveFolder[] getFolders() throws IOException {
        String baseFolderId = DriveService.getBaseFolderId();
        // Print the names and IDs for up to 10 files.
        FileList result = this.drive.files().list()
                .setQ("'" + baseFolderId + "' in parents and mimeType = 'application/vnd.google-apps.folder'")
                .setPageSize(50)
                // the fields set here determine what comes back in the response - and therefore which getters will return non-null data
                .setFields("nextPageToken, files(id, name)")
                .execute();
        List<File> files = result.getFiles();
        DriveFolder[] folders = new DriveFolder[files.size()];

        if (files == null || files.isEmpty()) {
            System.out.println("No files found.");
        } else {
            for (int i = 0; i < files.size(); i++) {
                folders[i] = new DriveFolder(files.get(i).getId(), files.get(i).getName());
            }
        }
        return folders;
    }

    public void upload(String audioFilePath, String desiredName, String folderId) throws IOException, GeneralSecurityException {
        File fileMetadata = new File();
        fileMetadata.setName(desiredName + ".mp3");
        fileMetadata.setParents(Collections.singletonList(folderId));
        java.io.File filePath = new java.io.File(audioFilePath);
        FileContent mediaContent = new FileContent("audio/mpeg", filePath);
        drive.files().create(fileMetadata, mediaContent)
            .setFields("id, parents")
            .execute();
    }

    private static String getBaseFolderId() {
        String id = "";
        try {
            java.io.File baseFolderIdFile = new java.io.File(DriveService.BASE_DRIVE_FOLDER_ID_FILE);
            Scanner reader = new Scanner(baseFolderIdFile);
            id = reader.nextLine();
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Problem getting base drive folder id from file");
            e.printStackTrace();
        }
        return id;
    }

    /**
     * Creates an authorized Credential object.
     * @param HTTP_TRANSPORT The network HTTP Transport.
     * @return An authorized Credential object.
     * @throws IOException If the credentials.json file cannot be found.
     */
    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        InputStream in = DriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

        // Build flow and trigger user authorization request.
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
}