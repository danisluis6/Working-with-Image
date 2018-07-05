package tutorial.lorence.template.other;

import javax.inject.Inject;

/**
 * Created by vuongluis on 4/14/2018.
 *
 * @author vuongluis
 * @version 0.0.1
 */

public class GenerateWebsite {

    @Inject
    public GenerateWebsite() {
    }

    /**
     * URL for jsoup
     */
    public String jsoup_URL() {
        return "https://bongda24h.vn/vck-world-cup-2018/lich-thi-dau-41.html";
    }
}
