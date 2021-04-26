import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;

import java.io.IOException;

/**
 * @author Symon
 * @version 1.0
 * @className DFSTest
 * @date 2021/1/25 11:32
 */
public class DFSTest {
    public static void main(String[] args) throws Exception {
        String path = DFSTest.class.getClassLoader().getResource("Tracker.conf").getPath();
        ClientGlobal.init(path);
        // 获得tracker连接
        TrackerClient trackerClient = new TrackerClient();
        TrackerServer connection = trackerClient.getConnection();
        // 通过tracker获得storage
        StorageClient storageClient = new StorageClient(connection, null);
        // 上传文件
        String[] jpgs = storageClient.upload_file("C:\\Users\\Symon\\Desktop\\文件\\上课时间.jpg", "jpg", null);
        // 返回url
        for (String jpg : jpgs) {
            System.out.println(jpg);
        }
    }
}
