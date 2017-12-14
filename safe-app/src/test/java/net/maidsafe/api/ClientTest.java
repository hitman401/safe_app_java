package net.maidsafe.api;

import net.maidsafe.model.App;
import net.maidsafe.model.Request;
import net.maidsafe.safe_app.ContainersReq;
import org.junit.Assert;
import org.junit.Test;

public class ClientTest {
    @Test
    public void unregisteredAccessTest() throws Exception {
        Client client = new Client();
        App app = new App("net.maidsafe.java.test", "sample", "MaidSafe.net Ltd",  "0.1.0");
        Request request = client.getUnregisteredSessionRequest(app).get();
        Assert.assertTrue(request.getReqId() != 0);
    }
}
