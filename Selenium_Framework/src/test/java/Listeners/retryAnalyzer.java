package Listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class retryAnalyzer implements IRetryAnalyzer
{
	//taking count which start from 0
	int count = 0;
	//test case retry count 
	int retryCount = 1;
	@Override
	public boolean retry(ITestResult result) {
		while (count<retryCount)
		{
			count++;
			return true;
		}
		return false;
	}

}
