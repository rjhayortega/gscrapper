import org.openqa.selenium.chrome.*;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.logging.*;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.awt.List;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.*;


public class test {
	
   public static WebDriver driver;
   static int currpos = -1;
   static int rounds = 0;
   static java.util.List<searchWindow> allWin = new java.util.ArrayList<searchWindow>();
   public static database d;
   
   
   
   public static void main(String[] args) throws Exception {
	   
	   d = new database();
	  	   
      // Instatiate the RC Server
	  java.util.List<String> links = new java.util.ArrayList<String>();
	  
	   LoggingPreferences logs = new LoggingPreferences();
	   logs.enable(LogType.BROWSER, Level.ALL);
	   logs.enable(LogType.CLIENT, Level.ALL);
	   logs.enable(LogType.DRIVER, Level.ALL);
	   logs.enable(LogType.PERFORMANCE, Level.ALL);
	   logs.enable(LogType.PROFILER, Level.ALL);
	   logs.enable(LogType.SERVER, Level.ALL);

	   DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
	   desiredCapabilities.setCapability(CapabilityType.LOGGING_PREFS, logs);
	 
	   ChromeOptions options = new ChromeOptions();
	   options.setCapability(CapabilityType.LOGGING_PREFS, logs);
	
	   //   System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
	   Timer timer = new Timer();
	   Timer timer1 = new Timer();
	   
	   timer.schedule(new TimerTask(){
		   
	        @Override
	        public void run() {
	        
	       try {
	    	   ResultSet r = d.readDataBase();
	    	  // if(r.getFetchSize()>0) {
	    	     while (r.next()) {
	    	        String frm =r.getString("frm");
	    	        String kw = r.getString("kword");
	    	        int id= r.getInt("id");
	    	        d.update(id);
	    	        searchWindow sw = new searchWindow(frm,kw);
	    	        allWin.add(sw);
			 //  }
	    	   }
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	        	
	        }
	        
	    },1000*9,1000*9); 
	   
	   
	   
/*
		String exePath = "F://chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exePath);
	//	RemoteWebDriver driver = new RemoteWebDriver(new URL("http://google.com"), desiredCapabilities);
	
		driver = new ChromeDriver(options);
		driver.get("https://www.google.com/maps/search/plumbers+fargo++nd/@46.6012503,-101.4067624,6z/data=!3m1!4b1");
		 
		WebDriver driver1 = new ChromeDriver(options);
		driver1.get("https://www.google.com/maps/search/plumbers+fargo++nd/@46.6012503,-101.4067624,6z/data=!3m1!4b1");
		
		

		
		
		EventFiringWebDriver eventFiring = new EventFiringWebDriver(driver);
        TrackActivity activity = new TrackActivity();
        eventFiring.register(activity);
    */
   
   }
}