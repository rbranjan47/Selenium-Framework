package Listeners;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.Class;
import java.util.Arrays;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import org.testng.internal.annotations.IAnnotationTransformer;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.Markup;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import resources.baseTest;
import resources.extent_reports;
import utils.utilsTest;

public class ListenersTest extends baseTest implements ITestListener, IAnnotationTransformer {
	utilsTest utils;
	ExtentTest test;
	Markup markup;

	// calling the extent reports method
	ExtentReports extent_report = extent_reports.get_reportObject();

	ThreadLocal<ExtentTest> extentTest = new ThreadLocal<ExtentTest>();

	// IT WILL EXECUTE ON TEST START
	@Override
	public void onTestStart(ITestResult result) {

		test = extent_report.createTest(result.getTestClass().getName() + "->" + result.getMethod().getMethodName());
		extentTest.set(test);
	}

	// IT WILL ON TEST SUCCESS
	@Override
	public void onTestSuccess(ITestResult result) {
		String testcaseMethod_Name = result.getMethod().getMethodName();
		String log_text = "<b> Test Case " + testcaseMethod_Name + "  has passed!</b>";

		markup = MarkupHelper.createLabel(log_text, ExtentColor.GREEN);
		extentTest.get().log(Status.PASS, markup);
	}

	// IT WILL EXECUTE TEST FAILURE
	@Override
	public void onTestFailure(ITestResult result) {
		WebDriver driver = null;
		String throwable_message = Arrays.deepToString(result.getThrowable().getStackTrace());
		extentTest.get().fail("<details><summary><b><font color='red'>" + "Exception Occured!" + "</font></b></summary>"
				+ throwable_message.replaceAll(",", "<br>") + "</details> \n");

		String testcaseMethod_Name = result.getMethod().getMethodName();
		// log fail test
		String log_text = "<b> Test Case " + testcaseMethod_Name + "  has faild</b>";

		markup = MarkupHelper.createLabel(log_text, ExtentColor.RED);
		extentTest.get().log(Status.FAIL, markup);

		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver")
					.get(result.getInstance());
		} catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException e1) {

			e1.printStackTrace();
		}
		try {
			extentTest.get().addScreenCaptureFromPath(utils.takescreenshot_driver(testcaseMethod_Name, driver),
					testcaseMethod_Name);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}

	@Override
	public void onTestSkipped(ITestResult result) {
		String testcaseMethod_Name = result.getMethod().getMethodName();
		String log_text = "<b> Test Case " + testcaseMethod_Name + "  has Skipped!</b>";

		markup = MarkupHelper.createLabel(log_text, ExtentColor.YELLOW);
		extentTest.get().log(Status.SKIP, markup);

		// taking screenshot
		try {
			extentTest.get().addScreenCaptureFromPath(utils.takescreenshot_driver(testcaseMethod_Name, driver),
					testcaseMethod_Name);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.out.println(e.getCause());
			e.printStackTrace();
		}
	}

	// IT WILL EXECUTE BY GIVING RESULT IN PERCENTAGE WITH SUCCESS PERCENT
	@Override
	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

	}

	@Override
	public void onTestFailedWithTimeout(ITestResult result) {

	}

	@Override
	public void onStart(ITestContext context) {

	}

	@Override
	public void onFinish(ITestContext context) {
		// to notify extent, reporting is completed
		extent_report.flush();
	}

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
		annotation.setRetryAnalyzer(retryAnalyzer.class);
	}
}
