package br.com.carrental;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TesteConexao {
    public static WebDriver driver;

    public static WebDriverWait wait;

    @Before
    public void before(){
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.get("https://google.com.br/");
        driver.manage().window().maximize();
    }

    @After
    public void after(){
        driver.quit();
    }

    @Test
    public void teste(){

    }

}
