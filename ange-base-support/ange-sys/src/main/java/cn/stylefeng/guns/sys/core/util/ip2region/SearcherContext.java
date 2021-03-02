package cn.stylefeng.guns.sys.core.util.ip2region;

import java.io.*;

import cn.hutool.core.io.resource.ResourceUtil;
import org.apache.commons.io.IOUtils;

/**
 * 加载ip转地址数据库（单例）
 */
public class SearcherContext {
    private static DbSearcher searcher = null;

    public static DbSearcher getInstance() {
        if (searcher == null) {
            try {
                InputStream asStream = new FileInputStream("D:\\project\\ip2region\\data\\ip2region.db");
                searcher = new DbSearcher(new DbConfig(), IOUtils.toByteArray(asStream));
            } catch (DbMakerConfigException | IOException e) {
                e.printStackTrace();
            }
        }
        return searcher;
    }
}
