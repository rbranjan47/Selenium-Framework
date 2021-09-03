package resources;

import java.io.File;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class extent_reports extends baseTest
{
static ExtentReports extent_report;
	
	public static ExtentReports get_reportObject()
	{
		//Creating Reports
		String report_path = System.getProperty("user.dir")+File.separator +"reports" + File.separator +"automation_report.html";
		//project report location, in .html extension
		ExtentSparkReporter reporter = new ExtentSparkReporter(report_path); 
		
		//report name
		String reportName = properties.getProperty("reportName");
		reporter.config().setReportName(reportName);
		
		//document title
		String document_title = properties.getProperty("documenttitle");
		reporter.config().setDocumentTitle(document_title);
		
		//setting theme
		reporter.config().setTheme(Theme.DARK);
		
		//setting format of file
		reporter.config().setEncoding("utf-8");
		
	    extent_report = new ExtentReports();
		 
	    extent_report.attachReporter(reporter);
	    
	    return extent_report;
	}
}
