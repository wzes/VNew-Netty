import com.mobile.vnews.dao.Dao;
import com.mobile.vnews.module.Notice;
import org.junit.Test;

/**
 * @author Create by xuantang
 * @date on 12/8/17
 */
public class DbTest {
    @Test
    public void db() {
        Notice notice = new Notice();
        notice.setFromID("1552730");
        notice.setToID("1555555");
        notice.setNewsID(1);
        notice.setContent("hello");
        Dao.addNotice(notice);
    }
}
