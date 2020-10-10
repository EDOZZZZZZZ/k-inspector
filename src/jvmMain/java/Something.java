import jdk.nashorn.internal.runtime.logging.Logger;

import javax.annotation.PostConstruct;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import static com.edoz.CommonKt.passwordRF;

/**
 * @author ZHUANG Yiwei <zhuangyiwei@situdata.com> on 2020/9/7
 */

public class Something {
    private static String uuid = UUID.randomUUID().toString();
    private static final Timer t = new Timer();

    public static void timerTaskHandler() {
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                uuid = UUID.randomUUID().toString();
                System.out.println("uuid:" + uuid);
            }
        }, 0, passwordRF * 1000);
    }

    public static String getUuid() {
        return uuid;
    }
}
