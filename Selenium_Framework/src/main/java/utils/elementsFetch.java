package utils;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import resources.baseTest;

public class elementsFetch {
	public WebElement getwebelement(String identifierType, String identifierValue) {
		switch (identifierType) {
		case "ID":
			return baseTest.driver.findElement(By.id(identifierValue));
		case "XPATH":
			return baseTest.driver.findElement(By.xpath(identifierValue));
		case "CSS":
			return baseTest.driver.findElement(By.cssSelector(identifierValue));
		case "TAGNAME":
			return baseTest.driver.findElement(By.tagName(identifierValue));
		case "CLASSNAME":
			return baseTest.driver.findElement(By.className(identifierValue));
		case "NAME":
			return baseTest.driver.findElement(By.name(identifierValue));
		default:
			return null;
		}
	}

	public List<WebElement> getwebelements(String identifierType, String identifierValue) {
		switch (identifierType) {
		case "ID":
			return baseTest.driver.findElements(By.id(identifierValue));
		case "XPATH":
			return baseTest.driver.findElements(By.xpath(identifierValue));
		case "CSS":
			return baseTest.driver.findElements(By.cssSelector(identifierValue));
		case "TAGNAME":
			return baseTest.driver.findElements(By.tagName(identifierValue));
		case "CLASSNAME":
			return baseTest.driver.findElements(By.className(identifierValue));
		case "NAME":
			return baseTest.driver.findElements(By.name(identifierValue));
		default:
			return null;
		}
	}
}