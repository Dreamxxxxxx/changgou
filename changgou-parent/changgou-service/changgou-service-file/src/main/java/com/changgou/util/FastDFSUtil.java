package com.changgou.util;


import com.changgou.file.FastDFSFile;
import org.csource.common.MyException;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.*;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 实现FastDFS文件管理
 *      文件上传
 *      文件删除
 *      文件下载
 *      文件信息获取
 *      Storage信息获取
 *      Tracker信息获取
 */
public class FastDFSUtil {

    /**
     * 加载Tracker链接信息
     */
    static {
        try {
            //查找classpath下的文件路径
            String filename=new ClassPathResource("fdfs_client.conf").getPath();
            //加载Tracker链接信息
            //String filename="D:\\学习的测试\\Spring的学习\\changgou\\changgou-parent\\changgou-service\\changgou-service-file\\src\\main\\resources\\fdfs_client.conf";
            ClientGlobal.init(filename);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件上传
     * @Param fastDFSFile
     * @return
     */
    public static String[] upload(FastDFSFile fastDFSFile) throws Exception{
        //附加参数
        NameValuePair[] meta_list=new NameValuePair[1];
        meta_list[0]=new NameValuePair("author",fastDFSFile.getAuthor());

        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        /**
         * 通过StorageClient访问Storage,实现文件上传，并且获取文件上传后的存储信息
         * 1.上传的文件字节数
         * 2.文件的扩展名 jpg
         * 3.附加参数   比如：拍摄地址 北京
         *
         * uploads[]
         *      uploads[0]:文件上传所存储的Storage的组名字 group1
         *      uploads[1]:文件存储到Storage上的文件名字  M00/02/44/itheima.jpg
         */
        String[] uploads = storageClient.upload_file(fastDFSFile.getContent(), fastDFSFile.getExt(), meta_list);


        return uploads;
    }

    /**
     * 获取文件信息
     * @param groupName : 文件的组名
     * @param remoteFileName : 文件的存储路径名
     * @return
     */
    public static FileInfo getFile(String groupName, String remoteFileName) throws Exception {
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);
        //获取文件信息
        return storageClient.get_file_info(groupName, remoteFileName);
    }


    /**
     * 文件下载
     */
    public static InputStream downFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);
        //文件下载
        byte[] buffer = storageClient.download_file(groupName, remoteFileName);
        return new ByteArrayInputStream(buffer);
    }

    /**
     * 文件删除
     */
    public static void deleteFile(String groupName, String remoteFileName) throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();

        //获取StorageClient
        StorageClient storageClient = getStorageClient(trackerServer);

        storageClient.delete_file(groupName,remoteFileName);
    }

    /**
     * 获取Storage信息
     * @return
     * @throws Exception
     */
    public static StorageServer getStorages() throws Exception{
        //创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //获取Storage信息
        return trackerClient.getStoreStorage(trackerServer);
    }

    /**
     * 获取Storage的IP和端口信息
     * @param groupName
     * @param remoteFileName
     * @throws Exception
     * @return
     */
    public static ServerInfo[] getServerInfo(String groupName, String remoteFileName) throws Exception{
        //创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();

        //获取Storage的IP和端口信息
        return trackerClient.getFetchStorages(trackerServer,groupName,remoteFileName);
    }

    /**
     * 获取TrackerInfo信息
     * @return
     * @throws Exception
     */
    public static String getTrackerInfo() throws Exception{
        //获取TrackerServer
        TrackerServer trackerServer = getTrackerServer();
        //Tracker的IP，HTTP端口
        String ip=trackerServer.getInetSocketAddress().getHostString();
        int tracker_http_port=ClientGlobal.getG_tracker_http_port();    //8080
        String url="http:/"+ip+":"+tracker_http_port;
        return url;
    }

    /**
     * 获取Tracker
     * @return
     * @throws Exception
     */
    public static TrackerServer getTrackerServer() throws Exception{
        //创建一个TrackerClient对象，通过TrackerClient对象访问TrackerServer
        TrackerClient trackerClient = new TrackerClient();
        //通过TrackerClient获取TrackerServer对象
        TrackerServer trackerServer = trackerClient.getConnection();
        return trackerServer;
    }

    /**
     * 获取StorageClient
     * @param trackerServer
     * @return
     */
    public static StorageClient getStorageClient(TrackerServer trackerServer){
        StorageClient storageClient = new StorageClient(trackerServer, null);
        return storageClient;
    }




    public static void main(String[] args) throws Exception {
        // FileInfo file = getFile("group1", "M00/00/00/wKg9g17PJtCAcFy1ABy28hKt3dI733.jpg");
        // System.out.println(file.getSourceIpAddr());
        // System.out.println(file.getFileSize());

        // //文件下载
        // InputStream inputStream = downFile("group1", "M00/00/00/wKg9g17PJtCAcFy1ABy28hKt3dI733.jpg");
        //
        // //将文件写入到本地磁盘
        // FileOutputStream os=new FileOutputStream("D:/1.jpg");
        //
        // //定义一个缓冲区
        // byte[] buffer=new byte[1024];
        // while (inputStream.read(buffer)!=-1){
        //     os.write(buffer);
        // }
        // os.flush();
        // os.close();
        // inputStream.close();

        //文件删除
        //deleteFile("group1", "M00/00/00/wKg9g17PJtCAcFy1ABy28hKt3dI733.jpg");

        //获取Storage信息
        // StorageServer storageServer=getStorages();
        // System.out.println(storageServer.getStorePathIndex());
        // System.out.println(storageServer.getInetSocketAddress().getHostString());   //IP信息

        //获取Storage组的IP和端口信息
        // ServerInfo[] groups = getServerInfo("group1", "M00/00/00/wKg9g17PVriAVI9LAC96ypbM9E8986.jpg");
        // for (ServerInfo group : groups) {
        //     System.out.println(group.getIpAddr());
        //     System.out.println(group.getPort());
        //}
        //获取Tracker端口信息
        System.out.println(getTrackerInfo());
    }

}
