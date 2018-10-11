package com.keeboot;

import com.keeboot.utils.TestReporter;
import com.keeboot.web.KeebootDriver;

public class Common {
	
	
	 private KeebootDriver driver = null;
	 // ----------------------------------------------------
    // Class Constructor
    // ----------------------------------------------------
	 
	 
    public Common(KeebootDriver driver) {
        // Build the page area.
        this.driver = driver;
    }
    
    
    
	 public static void validateStatusCode(int actStatusCode, int expStatusCode) {
	        TestReporter.assertTrue(actStatusCode == expStatusCode, "Actual Status code[ " + actStatusCode + " ] is equal to Expected status Code[ " + expStatusCode + " ]");
	    }
	 
	 public String replaceSpecialCharacterInString(String jsonString) {
	        return jsonString.toString().replaceAll(",", "&").replaceAll("/", "%2F").replaceAll("\"", "").replaceAll(":", "=").replaceAll("\\{", "").replaceAll("\\}", "").replaceAll("@", "%40");
	    }
}
