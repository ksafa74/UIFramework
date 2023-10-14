package com.projectName.stepDef;



import com.projectName.utilities.ConfigurationReader;
import com.projectName.utilities.Driver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.restassured.RestAssured;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import java.time.Duration;


public class Hooks {


    @Before("@api")
    public static void init() {

        RestAssured.baseURI = ConfigurationReader.getProperty("library.baseUri");

    }

    @After("@api")
    public static void destroyAPI() {

        RestAssured.reset();
    }

    @Before("@ui")
    public void setUp() {
        Driver.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        Driver.getDriver().manage().window().maximize();
        Driver.getDriver().get(ConfigurationReader.getProperty("library_url"));

    }

    @After("@ui")
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) Driver.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "screenshot");
        }

        Driver.closeDriver();

    }



}
