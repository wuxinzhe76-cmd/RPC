import com.charles.netty.annotation.RemoteInvoke;
import com.charles.netty.util.Response;
import com.charles.user.bean.User;
import com.charles.user.remote.UserRemote;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = RemoteInvokingTest.class)
@ComponentScan("com.charles")
public class RemoteInvokingTest {
    @RemoteInvoke
    private UserRemote userRemote;
    @Test
    public void testSaveUser(){
        User user = new User();
        user.setId(1);
        user.setName("zhangSan");
        Response response = userRemote.saveUser(user);
        System.out.println(response.getResult());

    }

}
