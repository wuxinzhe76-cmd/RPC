import com.charles.netty.client.ClientRequest;
import com.charles.netty.util.Response;
import com.charles.netty.client.TcpClient;
import com.charles.user.bean.User;
import org.junit.Test;

public class TestTCP {
    @Test
    public void testTCP() throws Exception {
        ClientRequest request = new ClientRequest();
        User u = new User();
        u.setId(1);
        u.setName("zhangSan");
        request.setCommand("com.charles.user.controller.UserController.saveUser");
        request.setContent(u);
        Response response = TcpClient.send(request);
        System.out.println(response.getResult());
    }
}
