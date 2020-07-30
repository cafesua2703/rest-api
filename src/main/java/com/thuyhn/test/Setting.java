package com.thuyhn.test;

public class Setting
{
	
	public String utilityFilePath = getUtilityFilePath();
	String[] envprevMonth1={"prev","Prev"}; // Specify a class name through which the framework can identify the image representing the previous month
	String[] envnextMonth1={"next","Next"};// Specify a class name through which the framework can identify the image representing the next month
	String[] envtitleMonth={"month"};// Specify a class name through which we can identify the title month element in calendar control element
	String[] envtitleYear={"year"};// Specjava.lang.AssertionErrorify a class name through which we can identify the title year element in calendar control element
	
	/**
	 * @return the utilityFilePath
	 */
	public String getUtilityFilePath() {
		String os = System.getProperty("os.name");
		if(os.contains("Window")) {
//			this.utilityFilePath = "webapps-exploded\\AutoTest\\webapp\\WEB-INF\\classes\\SampleScriptW\\Utility\\SeleniumUtility.xlsx";
			this.utilityFilePath= "C:\\SampleScriptW\\Utility\\SeleniumUtility.xlsx";
			// to debug
//			this.utilityFilePath="SampleScriptW\\Utility\\SeleniumUtility.xlsx";
		}
		if(os.contains("Linux")) {
			// to build product
			this.utilityFilePath="webapps-exploded/AutoTest/webapp/WEB-INF/classes/SampleScript/Utility/SeleniumUtility.xlsx";
			// to debug
			//this.utilityFilePath="SampleScript/Utility/SeleniumUtility.xlsx";
		}
		return utilityFilePath;
	}

	public void setUtilityFilePath(String utilityFilePath) {
		this.utilityFilePath = utilityFilePath;
	}
	
}
