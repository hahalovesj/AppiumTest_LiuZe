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
		Log.logInfo("��ʼ��Appium����");
		stopService();
	}
	
	public AppiumServer(String udid){
		this.appiumPort = AvailablePortFinder.getNextAvailable();
		this.bootstrapPort = AvailablePortFinder.getNextAvailable();
		this.udid = udid;
		Log.logInfo("��ʼ��Appium����");
		stopService();
	}
	
	public AppiumServer(int appiumPort,String udid){
		this.appiumPort = appiumPort;
		this.udid = udid;
		this.bootstrapPort = AvailablePortFinder.getNextAvailable();
		Log.logInfo("��ʼ��Appium����");
		stopService();
	}
	
	public AppiumServer(){
		this.appiumPort = AvailablePortFinder.getNextAvailable();
		this.bootstrapPort = AvailablePortFinder.getNextAvailable();
		Log.logInfo("��ʼ��Appium����");
		stopService();
	}
	
		
		
	/**
	* ����Appium����
	*/
	public void startService() {
	    try {
	          getCmds();
	          Runtime.getRuntime().exec(cmds);
	        } catch (Exception e) {
	            Log.logError("����Appium�����쳣:"+e.getMessage());
	        }

	        long start = System.currentTimeMillis();    //��ȡ�ȴ��������ʼʱ��
	        boolean state = isRunning();    //�жϷ����Ƿ���������
	        while (!state) {
	            long end = System.currentTimeMillis();   //��ȡ��ǰʱ��
	            
	            if (end - start > startTimeout*1000) {       //�ж��Ƿ񳬹�Config�еĵȴ�ʱ��
	                Log.logWarn("Appium �����޷��ڹ涨ʱ���������� "+ startTimeout + " seconds ��������������");
	                startService();             
	            }
	            state = isRunning();		//��ѭ����β�ٻ�ȡ����״̬
	        }
	        Log.logInfo("Appium���������� ��"+this.toString());
	}
		
	
		/**
		 * ��ֹAppium����
		 */
		public static void stopService() {		
			try {
				Runtime.getRuntime().exec("taskkill /f /im node.exe");
				Log.logInfo("��ֹͣ�������е�Appium���� ");
			} catch (Exception e) {
				Log.logError("�ر�Appium Serverʧ��:"+e.getMessage());
			}
		}
		
		/**
		 * ����Appium����
		 */
		 public void restartAppium() {
		    stopService();
		    this.startService();
		 }
		 
//		/**
//		 * һ����װ��cmd�����в���
//		 * @return List<String> cmds
//		 */
//	    private List<String> buildCmds() {
//	      	
//	        appiumPort = AvailablePortFinder.getNextAvailable();    //����AvailablePortFinder��ķ�������ȡ��ǰ���õ�ϵͳ�˿ں�
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
	     * ͨ������http���󣬼��ز�json�е�status�ֶ��ж�Appium�����Ƿ���������
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
