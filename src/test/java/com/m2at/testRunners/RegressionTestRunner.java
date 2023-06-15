package com.m2at.testRunners;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = "classpath:features", 
		glue = { "com.m2at.glue"}, 
	    plugin = { "html:target/cucumber-pretty-report/regression-tests.html"}, 
        tags = "@Regression"
)

public class RegressionTestRunner {

}
