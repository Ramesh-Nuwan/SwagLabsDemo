package org.swaglabs.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
public class LoggerHelper implements ITestListener {

    private static final Logger logger = LogManager.getLogger(LoggerHelper.class);

    @Override
    public void onStart(ITestContext context) {
        logger.info("TEST SUITE START :" + context.getName());
    }
    @Override
    public void onTestStart(ITestResult result) {
        logger.info("STARTING TEST :" + result.getName());
    }
    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("TEST PASSED :" + result.getName());
    }
    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("TEST FAILED :" + result.getName()+"\n"+result.getThrowable().getMessage());
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        logger.warn("TEST SKIPPED :" + result.getName());
    }
    @Override
    public void onFinish(ITestContext context) {
        logger.info("TEST FINISH :" + context.getName());
    }

}
