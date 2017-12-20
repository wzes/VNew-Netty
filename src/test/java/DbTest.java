import com.mobile.vnews.dao.Dao;
import com.mobile.vnews.module.Message;
import org.junit.Test;

/**
 * @author Create by xuantang
 * @date on 12/8/17
 */
public class DbTest {
    @Test
    public void db() {
        Message message = new Message();
        message.setFromID("1552730");
        message.setToID("1555555");
        message.setNewsID(1);
        message.setContent("hello");
        Dao.addNotice(message);
    }
}
