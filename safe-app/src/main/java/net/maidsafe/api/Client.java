package net.maidsafe.api;

import net.maidsafe.utils.BaseApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class Client extends BaseClient {

    public Client() throws Exception {

//			 TODO extract files based on os.arch and os
//			 https://stackoverflow.com/questions/20856694/how-to-find-the-os-bit-type
//        String tempPath = System.getProperty("java.io.tmpdir");

        File winpThread = File.createTempFile("libwinpthread-1", ".dll");
        InputStream is = getClass().getResourceAsStream("/native/libwinpthread-1.dll");
        System.out.println(getClass().getResource("/native/libwinpthread-1.dll"));
        System.out.println(is == null);
        FileOutputStream fos = new FileOutputStream(winpThread);
        byte[] data = new byte[is.available()];
        is.read(data);
        fos.write(data);
        is.close();
        fos.close();
        winpThread.deleteOnExit();
        File tempFile = File.createTempFile("safe_app", ".dll");
        is = getClass().getResourceAsStream("/native/safe_app.dll");
        fos = new FileOutputStream(tempFile.getPath());
        data = new byte[is.available()];
        is.read(data);
        fos.write(data);
        is.close();
        fos.close();
        tempFile.deleteOnExit();
        System.out.println(winpThread.getAbsolutePath());
        System.out.println(tempFile.getAbsolutePath());

        System.load(winpThread.getAbsolutePath());
        System.load(tempFile.getAbsolutePath());
    }

}
