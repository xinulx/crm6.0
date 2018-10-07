package com.mine.App.common.util;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * ftp上传下载类（注意：路径+/表示绝对路径，否则表示相对路径）
 *
 * @author
 *
 */
public class FtpUtils {
	private Logger logger = Logger.getLogger(FtpUtils.class);
	private String ip;
	private int port;
	private String pwd;
	private String user;
	private FTPClient ftpClient;

	public FtpUtils(){
		
	}
	public FtpUtils(String ip, int port, String user, String pwd) {
		this.ip = ip;
		this.port = port;
		this.user = user;
		this.pwd = pwd;
	}

	/**
	 * 关闭FTP服务器
	 * 
	 * @throws Exception
	 */
	public void closeServer() throws Exception {
		logger.info("开始关闭服务");
		if (ftpClient != null) {
			ftpClient.logout();
			if (ftpClient.isConnected()) {
				ftpClient.disconnect();
			}
		}
		logger.info("结束关闭服务");
	}

	/**
	 * 连接FTP服务器
	 * 
	 * @throws Exception
	 */
	public boolean connectServer() throws Exception {
		boolean isSuccess = false;
		try {
			ftpClient = new FTPClient();
			ftpClient.connect(ip, port);
			ftpClient.setControlEncoding("UTF-8");// 解决中文乱码
			FTPClientConfig conf = new FTPClientConfig(FTPClientConfig.SYST_NT);
			conf.setServerLanguageCode("zh");
			ftpClient.login(user, pwd);
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftpClient.enterLocalActiveMode(); // 主动传输模式
			ftpClient.setDataTimeout(20 * 60 * 1000);
			//ftpClient.enterLocalPassiveMode();// 设置为被动模式传输，否则程序部署到服务器上后选择上传的文件保存后一直处在运行中(不动了,进度条也走得很慢)
			// ftpClient.setDataTimeout(120000);
			if (FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
				isSuccess = true;
				logger.info("连接ftp服务器" + ip + "成功！");
				// logger.info("username:" + user);
				// logger.info("password:" + pwd);
				// logger.info("当前路径为：" + ftpClient.printWorkingDirectory());
			} else {
				ftpClient.disconnect();
				logger.error("连接ftp服务器" + ip + "异常！");
				throw new Exception("连接ftp服务器" + ip + "异常！");
			}
		} catch (Exception e) {
			logger.error("连接FTP服务器" + ip + "异常..", e);
			throw new Exception("连接ftp服务器" + ip + "异常！");
		}
		return isSuccess;
	}

	/**
	 * 远程FTP下载文件,下载指定的文件
	 * 
	 * @param remotePath
	 * @param localPath
	 * @param fileNames
	 *  
	 * @return
	 * @throws Exception
	 */
	public File downloadFile(String remotePath, String localPath, String fileNames) throws Exception {
		
		File fileOut = null;
		InputStream is = null;
		FileOutputStream os = null;
		try {
			if (ftpClient.changeWorkingDirectory(remotePath)) {
				logger.info("下载文件的路径为：" + ftpClient.printWorkingDirectory());
				
					// 列出所有的文件
					FTPFile[] ftpFiles = ftpClient.listFiles();
					if (ftpFiles == null || ftpFiles.length == 0) {
						throw new Exception("目录" + remotePath + "不存在任何文件，请重新选择");
					}
					for (FTPFile file : ftpFiles) {
						// 文件名一样的下载
						String fileName=new String(file.getName().getBytes("UTF-8"),"UTF-8");
						if(fileNames.equals(fileName))
						{
							logger.info("开始下载文件：" + file.getName());
							String newStr = new String(file.getName().getBytes("UTF-8"),"ISO8859-1");
							
							is = ftpClient.retrieveFileStream(newStr);
							
							if (localPath != null && !localPath.endsWith("/")) {
								localPath = localPath + "/";
								File path = new File(localPath);
								if (!path.exists()) {
									path.mkdirs();
								}
							}
							fileOut = new File(localPath + file.getName());
							os = new FileOutputStream(fileOut);
							byte[] bytes = new byte[1024];
							int c;
							while ((c = is.read(bytes)) != -1) {
								os.write(bytes, 0, c);
							}
							
							os.flush();
							is.close();
							os.close();
							ftpClient.completePendingCommand();
							logger.info("下载文件存放地址为：" + fileOut.getAbsolutePath());
							logger.info("结束下载文件：" + file.getName());
						}
					}
					if (fileOut==null) {
						throw new Exception("目录" + remotePath + "下不存在" + fileNames + "文件，请重新选择");
					}
				
			} else {
				throw new Exception("远程路径找不到，请核查");
			}
		} catch (Exception e) {
			logger.error("从FTP服务器下载文件异常：", e);
			throw e;
		}
		return fileOut;
	}

	/**
	 * FTP上传文件，上传指定的文件
	 * 
	 * @param remotePath
	 * @param localPath
	 * @param fileNames
	 * @return
	 * @throws Exception
	 */
	public void uploadFile(String remotePath, String localPath, String fileNames) throws Exception {
		try {
			if (!this.exists(remotePath)) {
				boolean flag = this.mkdirs(remotePath);
				if (!flag) {
					throw new Exception("创建目录失败");
				}
			}
			if (ftpClient.changeWorkingDirectory(remotePath)) {
				logger.info("上传文件的路径为：" + ftpClient.printWorkingDirectory());
				// 得到本地路径下的所有文件
				if (localPath != null && !localPath.endsWith("/")) {
					localPath = localPath + "/";
				}
				File file = new File(localPath+fileNames);
			
					logger.info("开始上传文件：" + file.getName());
					OutputStream os = ftpClient.storeFileStream(new String(file.getName().getBytes("UTF-8"), "ISO8859-1"));
					FileInputStream is = new FileInputStream(file);
					byte[] bytes = new byte[1024];
					int c;
					while ((c = is.read(bytes)) != -1) {
						os.write(bytes, 0, c);
					}
					os.close();
					is.close();
					ftpClient.completePendingCommand();// 必须写，而且必须在is.close()后面
					logger.info("结束上传文件：" + file.getName());
					
				
			} else {
				throw new Exception("远程目录不存在");
			}
		} catch (Exception e) {
			logger.error("上传FTP文件异常: ", e);
			throw e;
		}
	}

	/**
	 * FTP上传文件，上传多个文件
	 * 
	 * @param remotePath
	 * @param remotePath
	 * @param files
	 * @return
	 * @throws Exception
	 */
	public void uploadFile(String remotePath, List<File> files) throws Exception {
		try {
			if (!this.exists(remotePath)) {
				boolean flag = this.mkdirs(remotePath);
				if (!flag) {
					throw new Exception("创建目录失败");
				}
			}
			if (ftpClient.changeWorkingDirectory(remotePath)) {
				logger.info("上传文件的路径为：" + ftpClient.printWorkingDirectory());
				for (File file : files) {
					logger.info("开始上传文件：" + file.getName());
					OutputStream os = ftpClient.storeFileStream(file.getName());
					FileInputStream is = new FileInputStream(file);
					byte[] bytes = new byte[1024];
					int c;
					while ((c = is.read(bytes)) != -1) {
						os.write(bytes, 0, c);
					}
					os.close();
					is.close();
					ftpClient.completePendingCommand();// 必须写，而且必须在is.close()后面
					logger.info("结束上传文件：" + file.getName());
				}
			} else {
				throw new Exception("远程目录不存在");
			}
		} catch (Exception e) {
			logger.error("上传FTP文件异常: ", e);
			throw e;
		}
	}

	/**
	 * @function:判断远程服务器目录是否存在（是相对的还是绝对的；以"/"开头的:绝对的；不以"/"开头的:相对的）
	 * @param remotePath
	 *            远程服务器文件绝对/相对当前路径
	 * @return 目录是否存在。true:存在；false:不存在
	 * @throws IOException
	 */
	public boolean exists(String remotePath) throws IOException {

		// 创建目录之前的当前指向路径
		String firstpath = ftpClient.printWorkingDirectory();
		// 处理空
		if (remotePath == null || "".equals(remotePath)) {
			throw new IOException("目录不能为null或者空字符串");
		}

		/*-------------------- 获得绝对路径---------------------*/
		StringBuffer curdir = new StringBuffer();
		// 处理开头：如果没有"/"，说明是相对当前路径的，转到绝对路径。
		if (remotePath.startsWith("/")) {
			curdir.append(remotePath);
		} else {
			if (firstpath.endsWith("/")) {
				curdir.append(firstpath).append(remotePath);
			} else {
				curdir.append(firstpath).append("/").append(remotePath);
			}
		}
		// 绝对路径
		String absolutePath = curdir.toString();
		// 处理结尾：如果有"/"，就删除这个符号
		if (absolutePath.endsWith("/") && !absolutePath.equals("/")) {
			absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
		}
		/*-------------------- 获得绝对路径---------------------*/

		/*-------------------- 判断目录存在否--------------------*/
		if (ftpClient.changeWorkingDirectory(remotePath)) {
			logger.info("目录：" + absolutePath + "存在");
		} else {
			logger.info("目录：" + absolutePath + "不存在");
			return false;
		}
		/*-------------------- 判断目录存在否 --------------------*/

		// 由指向原来的路径
		ftpClient.changeWorkingDirectory(firstpath);
		return true;
	}

	/**
	 * @function:递归创建远程服务器目录，如果存在就不创建了（是相对的还是绝对的；以"/"开头的:绝对的；不以"/"开头的:相对的）
	 * @param remotePath
	 *            远程服务器文件绝对/相对当前路径
	 * @return 目录创建是否成功。true:成功；false:失败
	 * @throws IOException
	 */
	public boolean mkdirs(String remotePath) throws IOException {
		// 创建目录之前的当前指向路径
		String firstpath = ftpClient.printWorkingDirectory();

		/*-------------------- 获得绝对路径---------------------*/
		StringBuffer curdir = new StringBuffer();
		// 处理开头：如果没有"/"，说明是相对当前路径的，转到绝对路径。
		if (remotePath.startsWith("/")) {
			curdir.append(remotePath);
		} else {
			if (firstpath.endsWith("/")) {
				curdir.append(firstpath).append(remotePath);
			} else {
				curdir.append(firstpath).append("/").append(remotePath);
			}
		}
		// 绝对路径（形式为：/resapp/test或者/:为根目录）
		String absolutePath = curdir.toString();
		// 处理结尾：如果有"/"，就删除这个符号
		if (absolutePath.endsWith("/") && !absolutePath.equals("/")) {
			absolutePath = absolutePath.substring(0, absolutePath.length() - 1);
		}
		/*-------------------- 获得绝对路径---------------------*/

		/*---------------- 获得所有子目录----------------*/
		// 最后需要返回的字符串列表
		List<String> list = new ArrayList<String>();
		// 模式
		Pattern p = Pattern.compile("/[^/]*+");
		// 匹配器
		Matcher m = p.matcher(absolutePath);
		// 循环匹配
		while (m.find()) {
			// 添加匹配到的字符串
			list.add(m.group());
		}
		/*---------------- 获得所有子目录 ----------------*/

		/*---------------- 创建目录 ----------------*/
		curdir.delete(0, curdir.length());
		for (int i = 0; i < list.size(); i++) {
			curdir.append(list.get(i));
			if (ftpClient.changeWorkingDirectory(curdir.toString())) {
				logger.info("目录：" + curdir.toString() + "存在，无需创建");
			} else {
				logger.info("创建目录：" + curdir.toString() + "开始");
				if (ftpClient.makeDirectory(curdir.toString())) {
					logger.info("创建目录：" + curdir.toString() + "完成");
				} else {
					logger.info("创建目录：" + curdir.toString() + "失败");
					return false;
				}
			}
		}
		/*---------------- 创建目录 ----------------*/

		// 由指向原来的路径
		ftpClient.changeWorkingDirectory(firstpath);
		return true;
	}
	
	/**
	 * 下载匹配正则表达式名称的文件
	 * @param remote_down_path
	 * @param local_down_path
	 * @param remote_file_regex
	 * @return
	 * @throws Exception 
	 */
	public List<File> downloadFileMatchRegex(String remote_down_path,
			String local_down_path, String remote_file_regex) throws Exception {
		List<File> result = new ArrayList<File>();
		File fileOut = null;
		InputStream is = null;
		FileOutputStream os = null;
		try {
			if (ftpClient.changeWorkingDirectory(remote_down_path)) {
				logger.info("下载文件的路径为：" + ftpClient.printWorkingDirectory());
					
				FTPFile[] ftpFiles = ftpClient.listFiles();
				if (ftpFiles == null || ftpFiles.length == 0) {
					logger.info("目录" + remote_down_path + "不存在任何文件，请重新选择");
				}
				for (FTPFile file : ftpFiles) {
					// 找到符合条件的文件，即文件名开头是给定的那些
					Pattern p = Pattern.compile(remote_file_regex);
					Matcher m = p.matcher(file.getName());
					if (m.find()) {
						logger.info("开始下载文件：" + file.getName());
						is = ftpClient.retrieveFileStream(file.getName());
						if (local_down_path != null && !local_down_path.endsWith("/")) {
							local_down_path = local_down_path + "/";
							File path = new File(local_down_path);
							if (!path.exists()) {
								path.mkdirs();
							}
						}
						fileOut = new File(local_down_path + file.getName());
						os = new FileOutputStream(fileOut);
						byte[] bytes = new byte[1024];
						int c;
						while ((c = is.read(bytes)) != -1) {
							os.write(bytes, 0, c);
						}
						result.add(fileOut);
						os.flush();
						is.close();
						os.close();
						ftpClient.completePendingCommand();
						logger.info("下载文件存放地址为：" + fileOut.getAbsolutePath());
						logger.info("结束下载文件：" + file.getName());
					}
				}
				if (result.size() == 0) {
					logger.info("目录" + remote_down_path + "下不存在符合" + remote_file_regex + "的文件");
				}
			} else {
				throw new Exception("远程路径找不到，请核查");
			}
		} catch (Exception e) {
			logger.error("FTP异常：", e);
			throw e;
		}
		return result;
	}

	/**
	 * 根据开始文件名匹配文件下载
	 * @param remote_down_path
	 * @param local_down_path
	 * @param remote_down_file_name_begin
	 * @return 
	 * @throws Exception 
	 */
	public List<File> downloadFileBeginWithName(String remote_down_path,
			String local_down_path, String remote_down_file_name_begin) throws Exception {
		List<File> result = new ArrayList<File>();
		File fileOut = null;
		InputStream is = null;
		FileOutputStream os = null;
		try {
			if (ftpClient.changeWorkingDirectory(remote_down_path)) {
				logger.info("下载文件的路径为：" + ftpClient.printWorkingDirectory());
					
				if (!ftpClient.isConnected()) {
					connectServer();
				}
				
				logger.info("ftp is connect : " + ftpClient.isConnected());
				FTPFile[] ftpFiles = ftpClient.listFiles();
				if (ftpFiles == null || ftpFiles.length == 0) {
					throw new Exception("目录" + remote_down_path + "不存在任何文件，请重新选择");
				}
				for (FTPFile file : ftpFiles) {
					// 找到符合条件的文件，即文件名开头是给定的那些
					if (file.getName().startsWith(remote_down_file_name_begin)) {
						logger.info("开始下载文件：" + file.getName());
						is = ftpClient.retrieveFileStream(file.getName());
						if (local_down_path != null && !local_down_path.endsWith("/")) {
							local_down_path = local_down_path + "/";
							File path = new File(local_down_path);
							if (!path.exists()) {
								path.mkdirs();
							}
						}
						fileOut = new File(local_down_path + file.getName());
						os = new FileOutputStream(fileOut);
						byte[] bytes = new byte[1024];
						int c;
						while ((c = is.read(bytes)) != -1) {
							os.write(bytes, 0, c);
						}
						result.add(fileOut);
						os.flush();
						is.close();
						os.close();
						ftpClient.completePendingCommand();
						logger.info("下载文件存放地址为：" + fileOut.getAbsolutePath());
						logger.info("结束下载文件：" + file.getName());
					}
				}
				if (result.size() == 0) {
					logger.info("目录" + remote_down_path + "下不存在以" + remote_down_file_name_begin + "开头的文件");
					//throw new Exception("目录" + remote_down_path + "下不存在以" + remote_down_file_name_begin + "开头的文件");
				}
			} else {
				throw new Exception("远程路径找不到，请核查");
			}
		} catch (Exception e) {
			logger.error("FTP异常：", e);
			throw e;
		}
		return result;
	} 
	
	/**
	 * 删除一个文件
	 * @param remote_path
	 * @param remote_path
	 * @throws Exception 
	 */
	public void deleteFilesMatchRegex(String remote_path,
			String fileName) throws Exception {
		try {
			if (ftpClient.changeWorkingDirectory(remote_path)) {
				logger.info("文件的路径为：" + ftpClient.printWorkingDirectory());
				
				
				if (remote_path != null && !remote_path.endsWith("/")) {
					remote_path = remote_path + "/";
				}
				logger.info("开始删除文件：" + remote_path + fileName);
				String remote_file_path=new String((remote_path + fileName).getBytes("UTF-8"), "iso-8859-1");
				boolean flag = ftpClient.deleteFile(remote_file_path);
				
				if (flag == false) {
					throw new RuntimeException("删除FTP远程目录文件失败:" + remote_path + fileName);
				}
					
			} else {
				throw new RuntimeException("远程路径找不到，请核查");
			}
		} catch (Exception e) {
			logger.error("FTP异常：", e);
			throw e;
		}
	}
	
	/**
	 * 删除文件名开头的文件
	 * @param remote_path
	 * @param remote_down_file_name_begin
	 * @throws Exception 
	 */
	public void deleteFilesBeginWithName(String remote_path, String remote_down_file_name_begin) throws Exception {
		try {
			if (ftpClient.changeWorkingDirectory(remote_path)) {
				logger.info("文件的路径为：" + ftpClient.printWorkingDirectory());
					
				if (!ftpClient.isConnected()) {
					connectServer();
				}
				
				logger.info("ftp is connect : " + ftpClient.isConnected());
				
				FTPFile[] ftpFiles = ftpClient.listFiles();
				if (ftpFiles == null || ftpFiles.length == 0) {
					throw new Exception("目录" + remote_path + "不存在任何文件，请重新选择");
				}
				
				if (remote_path != null && !remote_path.endsWith("/")) {
					remote_path = remote_path + "/";
				}
				
				for (FTPFile file : ftpFiles) {
					// 找到符合条件的文件，即文件名开头是给定的那些
					if (file.getName().startsWith(remote_down_file_name_begin)) {
						logger.info("开始删除文件：" + file.getName());
						boolean flag = ftpClient.deleteFile(remote_path + file.getName());
						if (flag == false) {
							throw new Exception("删除FTP远程目录文件失败:" + remote_path + file.getName());
						}
					}
				}
			} else {
				throw new Exception("远程路径找不到，请核查");
			}
		} catch (Exception e) {
			logger.error("FTP异常：", e);
			throw e;
		}
	}
	
	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}


	/**
	 * 上传文件名开头文件
	 * @param remote_up_path
	 * @param local_up_path
	 * @param remote_up_file_name_begin
	 * @throws Exception 
	 */
	public void uploadFileBeginWithName(String remote_up_path,
			String local_up_path, String remote_up_file_name_begin) throws Exception {
		try {
			if (!this.exists(remote_up_path)) {
				boolean flag = this.mkdirs(remote_up_path);
				if (!flag) {
					throw new Exception("创建目录失败:" + remote_up_path);
				}
			}
			if (ftpClient.changeWorkingDirectory(remote_up_path)) {
				logger.info("上传文件的路径为：" + ftpClient.printWorkingDirectory());
				// 得到本地路径下的所有文件
				File menu = new File(local_up_path);
				File[] files = menu.listFiles();
				for (File file : files) {
					if (file.getName().startsWith(remote_up_file_name_begin)) {
						logger.info("开始上传文件：" + file.getName());
						OutputStream os = ftpClient.storeFileStream(file.getName());
						FileInputStream is = new FileInputStream(file);
						byte[] bytes = new byte[1024];
						int c;
						while ((c = is.read(bytes)) != -1) {
							os.write(bytes, 0, c);
						}
						os.close();
						is.close();
						ftpClient.completePendingCommand();// 必须写，而且必须在is.close()后面
						logger.info("结束上传文件：" + file.getName());
					}
				}
			} else {
				throw new Exception("远程目录不存在");
			}
		} catch (Exception e) {
			logger.error("上传FTP文件异常: ", e);
			throw e;
		}
	}

	/**
	 * 删除一个文件
	 * @param remote_path
	 * @param remote_path
	 * @throws Exception
	 */
	public boolean fileExists(String remote_path,
									  String fileName) throws Exception {
		try {
			if (ftpClient.changeWorkingDirectory(remote_path)) {
				logger.info("文件的路径为：" + ftpClient.printWorkingDirectory());
				FTPFile[] ftpFiles = ftpClient.listFiles(fileName);
				if (ftpFiles.length == 0) {
					return false;
				}

			} else {
				throw new RuntimeException("远程路径找不到，请核查");
			}
		} catch (Exception e) {
			logger.error("FTP异常：", e);
			throw e;
		}
		return true;
	}

	public static void main(String[] args) 
	{
		FtpUtils f=new FtpUtils();
		f.setIp("172.21.1.207");
		f.setPort(21);
		f.setPwd("crmnga");
		f.setUser("crmnga");
		String remotePath="/crmnga/work/liudw/uploadFile";
		String localPath="./doc";
		String fileNames="批量开户_BOSS_liudw_1408260944.TXT";
		
		String s=fileNames.substring(0,fileNames.lastIndexOf("."));
		String x=fileNames.substring(fileNames.lastIndexOf("."));
		String rightPath="./doc/right";
		String rightName=s+"_right"+x;
		
		String upPath="/crmnga/work/liudw/checkFile";
		
		try {
			
			if (rightPath != null && !rightPath.endsWith("/")) {
				rightPath = rightPath + "/";
				File path = new File(rightPath);
				if (!path.exists()) {
					path.mkdirs();
				}
			}
			File rightTxt = new File(rightPath+rightName);
			if(rightTxt.exists()){ 
				System.out.print("文件存在"); 
				rightTxt.delete();
			}else{    
				System.out.print("文件不存在");    
				rightTxt.createNewFile();//不存在则创建   }
			}
			if(f.connectServer()){
				System.out.println("连上了");
				File file = f.downloadFile(remotePath, localPath, fileNames);
				BufferedReader br = new   BufferedReader(new InputStreamReader(new FileInputStream(file), "GBK"));
				StringBuffer right=new StringBuffer();
				StringBuffer wrong=new StringBuffer();
				String sLine = null;    
				while((sLine = br.readLine()) != null){
					 right.append(sLine+"\n");
					 wrong.append(wrong);
					 System.out.println("****");
				 }
				br.close();
				/*for(int i=0;i<10;i++){
					//right.append("\n");
					right.append(right+">>");
					System.out.println("*****");
					//wrong.append("\n");
					//wrong.append(wrong);
				}*/
				
				BufferedWriter output = new BufferedWriter(new FileWriter(rightPath+rightName));
				output.write(right.toString());
				output.close();
				
				f.uploadFile(upPath, rightPath, rightName);
				System.out.println(file.getPath()+">>>>>>>>"+file.getName());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try {
				f.closeServer();
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}finally{
			try {
				f.closeServer();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
	}
}
