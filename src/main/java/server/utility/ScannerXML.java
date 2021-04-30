package server.utility;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class that scans a file and saves its contents to a BufferedInputStream.
 */
public class ScannerXML {
    private final File file;
    private BufferedInputStream bufferedIS;

    public ScannerXML(File file) {
        this.file = file;
    }

    public void scan() throws FileNotFoundException {
        try (Scanner scanner = new Scanner(file)) {
            ArrayList<Byte> fileBytes = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String str = scanner.nextLine();
                byte[] strBytes = str.getBytes();
                for (byte i : strBytes) {
                    fileBytes.add(i);
                }
            }
            byte[] buffer = new byte[fileBytes.size()];
            for (int i = 0; i < fileBytes.size(); i++) {
                buffer[i] = fileBytes.get(i);
            }
            ByteArrayInputStream byteArrayIS = new ByteArrayInputStream(buffer);
            this.bufferedIS = new BufferedInputStream(byteArrayIS);
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("Unfortunately, file with the specified pathname does not exist or there is no read permission for this file.\n" +
                    "Fill in the collection manually or restart the program with the correct file name.");
        }
    }

    public BufferedInputStream getBufferedIS() {
        return bufferedIS;
    }
}
