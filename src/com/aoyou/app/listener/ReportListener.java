package com.aoyou.app.listener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.xml.XmlSuite;

import com.aoyou.app.base.Data;
import com.aoyou.app.util.Config;
import com.aoyou.app.util.Log;
import com.aoyou.app.util.Mail;


public class ReportListener implements IReporter{

	 String passedNumber="0";
	 String failedNumber="0";
	 String skippedNumber="0";
	 String passrate="0";
	 String totalNumber ="0";
	 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	 SimpleDateFormat formatter2 = new SimpleDateFormat("yyyyMMdd-HHmmss");
	 String nowTime = formatter2.format(new Date());
	 String reportPath = "";
	 String reportTargetPath = "D:/jenkins/workspace/"+Config.projectName+"/test-output/FinalReport.html";
	 
	@Override
	public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> suites, String outputDirectory) {
		
		 DecimalFormat df = new DecimalFormat("0.00");
		 
		 for (ISuite suite : suites){
			 
			  Map<String, ISuiteResult> suiteResults = suite.getResults();
			  
	          for (ISuiteResult suiteResult : suiteResults.values()) {
	                ITestContext testContext = suiteResult.getTestContext();           
	                IResultMap passedTests = testContext.getPassedTests();
	                IResultMap failedTests = testContext.getFailedTests();
	                IResultMap skippedTests = testContext.getSkippedTests();

	                passedNumber = String.valueOf(passedTests.size());
	                failedNumber = String.valueOf(failedTests.size());
	                skippedNumber = String.valueOf(skippedTests.size());
	                totalNumber = String.valueOf(passedTests.size()+failedTests.size()+skippedTests.size());
	                float passRate = 100*((float)passedTests.size()/(passedTests.size()+failedTests.size()+skippedTests.size()));
	                passrate = df.format(passRate);
	            } 
		 }
		 
		 this.sendTemplateReport();
		 this.sendTemplateMail();
	}


//    private ArrayList<ITestResult> listTestResult(IResultMap resultMap){
//        Set<ITestResult> results = resultMap.getAllResults();    
//        return new ArrayList<ITestResult>(results);
//    }
//    

//	private void sort(List<ITestResult> list){
//        Collections.sort(list, new Comparator<ITestResult>() {
//            @Override
//            public int compare(ITestResult r1, ITestResult r2) {
//                if(r1.getStartMillis()>r2.getStartMillis()){
//                    return 1;
//                }else{
//                    return -1;
//                }              
//            }
//        });
//    }
    
	private void copyFinalReport(String reportPath){
		
		 File srcFile = new File(reportPath);
		 FileChannel in = null;  
		 FileChannel out = null;  
		 FileInputStream inStream = null;  
		 FileOutputStream outStream = null; 
		 
		 try {
			inStream = new FileInputStream(srcFile);
			outStream = new FileOutputStream(reportTargetPath);  
			in = inStream.getChannel();  
			out = outStream.getChannel();  			
			in.transferTo(0, in.size(), out);
			
		 } catch (IOException e) {
				e.printStackTrace();
		 } finally {  

		        try {
					in.close();
					inStream.close();
					out.close();
					outStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

		 }  
		 
	}
	
	private void sendTemplateReport(){
		
		OutputStreamWriter pw = null;
		reportPath = "D:/jenkins/workspace/"+Config.projectName+"/test-output/IReport/"+nowTime+".html";
				
		try {
			StringBuffer text = new StringBuffer();
			pw = new OutputStreamWriter(new FileOutputStream(reportPath),"UTF-8");
			text.append("<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
			text.append("<head>");
			text.append("<meta charset=\"utf-8\">");
			text.append("<title>自动化测试平台</title>");
			text.append("</head>");
			text.append("<body>");
			text.append("<h2 style=\"margin-bottom: 5px;\">"+Config.projectName+"</h2>");
			text.append("<span style=color:red; >（测试结束时间：");
			text.append(formatter.format(new Date()));
			text.append("）</span>");
			text.append("<div id=\"main\" style=\"height:330px\"></div>");
			text.append("<script src=\"http://echarts.baidu.com/build/dist/echarts.js\"></script>");
			text.append("<script type=\"text/javascript\">");
			text.append("require.config({");
			text.append("paths: {");
			text.append("echarts: 'http://echarts.baidu.com/build/dist'");
			text.append("}");
			text.append("});");
			text.append("require");
			text.append("(");
			text.append("[");
			text.append("'echarts',");
			text.append("'echarts/chart/gauge',");
			text.append("'echarts/chart/pie'");
			text.append("],");
			text.append("function (ec)");
			text.append("{");
			text.append("var myChart = ec.init(document.getElementById('main'));");
			text.append("var option =");
			text.append("{");
			text.append("color : [");
			text.append("'rgba(55, 200, 100, 1)',");
			text.append("'rgba(255, 200, 0, 1)',");
			text.append("'rgba(255, 69, 0, 1)'");
			text.append("],");
			text.append("title : {");
			text.append("text: '用例占比统计',");
			text.append("subtext: '      悬浮查看详情',");
			text.append("x:'650'");
			text.append("},");
			text.append("tooltip : {");
			text.append("trigger: 'item',");
			text.append("formatter: \"{b} : {c} ({d}%)\"");
			text.append("},");
			text.append("legend: {");
			text.append("orient : 'vertical',");
			text.append("x : '1100',");
			text.append("data:['成功','跳过','失败']");
			text.append("},");
			text.append("toolbox: {");
			text.append("show : true,");
			text.append("feature : {");
			text.append("saveAsImage : {show: true}");
			text.append("},");
			text.append("showTitle:false");
			text.append("},");
			text.append("calculable : true,");
			text.append("series : [");
			text.append("{");
			text.append("name:'测试通过率',");
			text.append("type:'gauge',");
			text.append("title : {");
			text.append("show : 'fasle',");
			text.append("textStyle: {");
			text.append("fontWeight: 'bolder',color:'#FF0000',fontSize : '18'");
			text.append("}");
			text.append("},");
			text.append("detail : {formatter:'{value}%'},");
			text.append("center: ['20%','55%'],");
			text.append("radius:['0','85%'],");
			text.append("data:[{value: 0,name:'测试通过率'}]");
			text.append("},");
			text.append("{");
			text.append("name:'用例统计',");
			text.append("type:'pie',");
			text.append("radius : ['40%','70%'],");
			text.append("center: ['70%', '48%'],");
			text.append("itemStyle : {");
			text.append("normal : {");
			text.append("label : {");
			text.append("show : false");
			text.append("},");
			text.append("labelLine : {");
			text.append("show : false");
			text.append("}");
			text.append("},");
			text.append("emphasis : {");
			text.append("label : {");
			text.append("show : true,");
			text.append("position : 'center',");
			text.append("textStyle : {");
			text.append("fontSize : '30',");
			text.append("fontWeight : 'bold'");
			text.append("}");
			text.append("}");
			text.append("}");
			text.append("},");		
			text.append("data:[");
			text.append("{value:0, name:'成功'},");
			text.append("{value:0, name:'跳过'},");
			text.append("{value:0, name:'失败'}");
			text.append("]");
			text.append("}");
			text.append("]");
			text.append("};");
			text.append("option.series[0].data[0].value=");
			text.append(this.passrate+";");
			text.append("option.series[1].data[0].value=");
			text.append(this.passedNumber+";");
			text.append("option.series[1].data[1].value=");
			text.append(this.skippedNumber+";");
			text.append("option.series[1].data[2].value=");
			text.append(this.failedNumber+";");
			text.append("myChart.setOption(option);");
			text.append("}");
			text.append(");");
			text.append("</script>");
			text.append("<table width=\"90%\" cellpadding=\"5\" cellspacing=\"1\">");
			text.append("<tbody><tr style=\"background-color: #eaf0f7;\">");
			text.append("<td width=\"90%\">");
			text.append("<font style=\"font-weight:bold\" size=\"3\" color=\"red\" weight='bold'>测试日志信息</font>");
			text.append("</td>");
			text.append("</tr>");
			for(String log:Data.reporterLog){
				text.append("<tr style=\"background-color: #f5f5f5; font-size: 14px;\">");
				text.append("<td>"+log+"</td>");
				text.append("</tr>");
			}
			text.append("</tbody></table>");
			text.append("</body>");
		
			pw.write(text.toString());
			pw.flush();
	        this.copyFinalReport(reportPath);
	        
		} catch (IOException e) {
			Log.logError("输出html报告错误 "+ e.toString());
			
		}finally{
			
			try {
				if(pw!=null)
					pw.close();
			} catch (IOException e) {			
				Log.logError("输出html报告,关闭流异常！  "+ e.toString());
			}
		}
	}
	
	private void sendTemplateMail(){

		StringBuffer text = new StringBuffer();
		text.append("<!DOCTYPE html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"en\" lang=\"en\">");
		text.append("<head><meta charset=\"utf-8\">");
		text.append("<title>自动化测试平台</title></head>");
		text.append("<body><h2 style=\"margin-bottom: 5px;margin-left:5px\">"+Config.projectName+"</h2>");
		text.append("<span style=color:red; >(测试结束时间：");
		text.append(formatter.format(new Date())+")");
		text.append("<br />");
		text.append("<br />");
		text.append("<table width=\"80%\" cellpadding=\"5\" cellspacing=\"1\" style=\"table-layout:fixed;margin-left:5px\">");
		text.append("<tbody>");
		text.append("<tr style=\"background-color: #eaf0f7;\">");
		text.append("<td width=\"80%\" colspan=5 ><font style=\"font-weight:bold\" size=\"3\" weight='bold'>测试结果统计</font></td>");
		text.append("</tr>");
		text.append("<tr style=\"background-color: #f5f5f5; font-size: 14px;\">");
		text.append("<td style=\"text-align:center;font-weight:bold;color:#FF0000\">成功率</td>");
		text.append("<td style=\"background-color: #ADD8E6;text-align:center;\">全部</td>");
		text.append("<td style=\"background-color: lightgreen;text-align:center;\">成功</td>");
		text.append("<td style=\"background-color: #FFC0CB;text-align:center;\">失败</td>");
		text.append("<td style=\"background-color: #FFFFBB;text-align:center;\">跳过</td>");
		text.append("</tr>");
		text.append("<tr style=\"background-color: #f5f5f5; font-size: 14px;\">");
		text.append("<td style=\"text-align:center;font-weight:bold;color:#FF0000\">"+passrate+"</td>");
		text.append("<td style=\"text-align:center;font-weight:bold\">"+this.totalNumber+"</td>");
		text.append("<td style=\"text-align:center;font-weight:bold\">"+this.passedNumber+"</td>");
		text.append("<td style=\"text-align:center;font-weight:bold\">"+this.failedNumber+"</td>");
		text.append("<td style=\"text-align:center;font-weight:bold\">"+this.skippedNumber+"</td>");
		text.append("</tr>");
		text.append("</tr>");
		text.append("</tbody>");
		text.append("</table>");
		text.append("<br />");
		text.append("<br />");
		text.append("<table width=\"100%\" cellpadding=\"5\" cellspacing=\"1\" table-layout:fixed; word-break: break-all; word-wrap: break-word;style=\"margin-left:5px;margin-top:50px\">");
		text.append("<tbody>");
		text.append("<tr style=\"background-color: #eaf0f7;\">");
		text.append("<td width=\"100%\"><font style=\"font-weight:bold\" size=\"3\" color=\"red\" weight='bold'>测试日志信息</font></td>");
		text.append("</tr>");
		for(String log:Data.reporterLog){
			
			if(log.contains("【FAILED】")){
				text.append("<tr style=\"background-color: #f5f5f5; font-size: 14px; color:red\">");
			}else{text.append("<tr style=\"background-color: #f5f5f5; font-size: 14px;\">");}

			text.append("<td>"+log+"</td>");
			text.append("</tr>");
		}
		text.append("</tbody>");
		text.append("</table>");
		text.append("</body>");
		text.append("</html>");
		
		Mail.send(text.toString());
	}
	
	
}
