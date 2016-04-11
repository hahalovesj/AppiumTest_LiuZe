package com.aoyou.app.base;

import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.mina.util.AvailablePortFinder;
import com.aoyou.app.util.Config;
import com.aoyou.app.util.Log;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
	
public class AppiumServer {

	private static final long startTimeout = Config.appiumServerWaitTime;
	private static final HttpClient httpClient = HttpClients.createDefault();
	private static final String statusPath = "/wd/hub/status";
	private static final String serverPath = "/wd/hub";
    private String udid = "-1";
	private int appiumPort = -1;
	private int bootstrapPort = -1;
	private String ip = "localhost";
	private String cmds;
		
			
	public AppiumServer(int appiumPort){
		this.appiumPort = appiumPort;
		this.bootstrapPort = AvailablePortFinder.getNextAvailable();
		Log.logInfo("初始化Appium服务");
		stopService();
	}
	
	public AppiumServer(String udid){
		this.appiumPort = AvailablePortFinder.getNextAvailable();
		this.bootstrapPort = AvailablePortFinder.getNextAvailable();
		this.udid = udid;
		Log.logInfo("初始化Appium服务");
		stopService();
	}
	
	public AppiumServer(int appiumPort,String udid){
		this.appiumPort = appiumPort;
		this.udid = udid;
		this.bootstrapPort = AvailablePortFinder.getNextAvailable();
		Log.logInfo("初始化Appium服务");
		stopService();
	}
	
	public AppiumServer(){
		this.appiumPort = AvailablePortFinder.getNextAvailable();
		this.bootstrapPort = AvailablePortFinder.getNextAvailable();
		Log.logInfo("初始化Appium服务");
		stopService();
	}
	
		
		
	/**
	* 开启Appium服务
	*/
	public void startService() {
	    try {
	          getCmds();
	          Runtime.getRuntime().exec(cmds);
	        } catch (Exception e) {
	            Log.logError("启动Appium服务异常:"+e.getMessage());
	        }

	        long start = System.currentTimeMillis();    //获取等待服务的起始时间
	        boolean state = isRunning();    //判断服务是否正常运行
	        while (!state) {
	            long end = System.currentTimeMillis();   //获取当前时间
	            
	            if (end - start > startTimeout*1000) {       //判断是否超过Config中的等待时长
	                Log.logWarn("Appium 服务无法在规定时间内启动： "+ startTimeout + " seconds 尝试重新启动！");
	                startService();             
	            }
	            state = isRunning();		//在循环结尾再获取运行状态
	        }
	        Log.logInfo("Appium服务已启动 ："+this.toString());
	}
		
	
		/**
		 * 终止Appium服务
		 */
		public static void stopService() {		
			try {
				Runtime.getRuntime().exec("taskkill /f /im node.exe");
				Log.logInfo("已停止正在运行的Appium服务 ");
			} catch (Exception e) {
				Log.logError("关闭Appium Server失败:"+e.getMessage());
			}
		}
		
		/**
		 * 重启Appium服务
		 */
		 public void restartAppium() {
		    stopService();
		    this.startService();
		 }
		 
//		/**
//		 * 一次性装载cmd命令行参数
//		 * @return List<String> cmds
//		 */
//	    private List<String> buildCmds() {
//	      	
//	        appiumPort = AvailablePortFinder.getNextAvailable();    //利用AvailablePortFinder类的方法，获取当前可用的系统端口号
//	        chromeDriverPort = AvailablePortFinder.getNextAvailable();
//	        bootstrapPort = AvailablePortFinder.getNextAvailable();
//	        selendroidPort = AvailablePortFinder.getNextAvailable();
	//
//	        List<String> cmds = new LinkedList<>();
//	        cmds.add("cmd /k start appium.cmd");
//	        cmds.add(String.format("--port=%d", appiumPort));
//	        cmds.add(String.format("--chromedriver-port=%d", chromeDriverPort));
//	        cmds.add(String.format("--selendroid-port=%d", selendroidPort));
//	        cmds.add(String.format("--bootstrap-port=%d", bootstrapPort));
	//
//	        return cmds;
//	    }
	    
	    /*
	     * 通过发送http请求，检查回参json中的status字段判断Appium服务是否正常运行
	     */
	    public boolean isRunning() {
	        try {
	            URI uri = new URIBuilder().setScheme("http").setHost(ip)
	                    	  .setPort(appiumPort).setPath(statusPath).build();
	            HttpGet httpget;
	            HttpResponse response;
	            httpget = new HttpGet(uri);
	            response = httpClient.execute(httpget);
	            HttpEntity entity = response.getEntity();
	            String rs = EntityUtils.toString(entity);
	            JsonElement json = new JsonParser().parse(rs);
	            int status = json.getAsJsonObject().get("status").getAsInt();
	            return status == 0;

	        } catch (Exception e) {
	            return false;
	        }
	    }
	    
	    private String getCmds(){  	
	    	if(!udid.equals("-1")){
	    		cmds = "cmd /c start appium.cmd -p "+appiumPort+
	    				" -bp " + bootstrapPort + " --session-override"+" -U "+udid;
	    	}else{
	    		cmds = "cmd /c start appium.cmd -p "+appiumPort+
	    				" -bp " + bootstrapPort + " --session-override";
	    	}
			return cmds;	
	    }
	    
	    public int getAppiumPort(){
			return appiumPort;
	    }
	    
	    public int getBootstrapPort(){
			return bootstrapPort;
	    }
	    
	    @Override
	    public String toString() {
	        return "AppiumServer [address=http://" + ip +":"+appiumPort+serverPath +
	                " , udid=" + udid+" ]";
	    }
	    
}
