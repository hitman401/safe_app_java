package net.maidsafe.api;

import net.maidsafe.api.utils.OSInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class Client extends BaseClient {

    public Client() throws Exception {
        String libName = "libsafe_app";
        String extension = ".so";
        switch (OSInfo.getOs()) {
            case WINDOWS:
                libName = "safe_app";
                extension = ".dll";
                break;
            case MAC:
                extension = ".dylib";
                break;
            default:
                break;
        }

        File tempFile = File.createTempFile(libName, extension);
        tempFile.deleteOnExit();
        InputStream inputStream = getClass().getResourceAsStream("/native/".concat(libName).concat(extension));
        FileOutputStream fileOutputstream = new FileOutputStream(tempFile.getPath());
        byte[] data = new byte[inputStream.available()];
        inputStream.read(data);
        fileOutputstream.write(data);
        inputStream.close();
        fileOutputstream.close();
        System.load(tempFile.getAbsolutePath());
    }

}
