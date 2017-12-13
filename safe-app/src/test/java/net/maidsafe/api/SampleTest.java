package net.maidsafe.api;

import net.maidsafe.ffi.app.AuthReq;
import net.maidsafe.model.App;
import net.maidsafe.model.Request;
import org.junit.Assert;
import org.junit.Test;

public class SampleTest {
    @Test
    public void addTest() throws Exception {
        try{
            Client client = new Client();
            Request request = client.getUnregisteredSessionRequest(new App("net.maidsafe.java.test", "sample", "MaidSafe Ltd",  "0.1.0")).get();
////        As(getClass().getResource("safe_app.dll"));
            Assert.assertNotNull(request);
            Assert.assertTrue(request.getReqId() != 0);

        } catch(Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}
