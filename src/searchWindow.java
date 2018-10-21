import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

public class searchWindow {
	
	WebDriver driver;
	int currpos = -1;
	int rounds = 0;
	
	
	
	public searchWindow(String frm, String kw) {
		
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
		
		   
			String exePath = "F://chromedriver.exe";
			System.setProperty("webdriver.chrome.driver", exePath);
		//	RemoteWebDriver driver = new RemoteWebDriver(new URL("http://google.com"), desiredCapabilities);

			
			driver = new ChromeDriver(options);
			driver.get("https://www.google.com/maps/search/"+kw);
			driver.manage().window().setSize(new Dimension(0,0));
		   
		   //   System.setProperty("webdriver.chrome.driver", "/path/to/chromedriver");
		   Timer timer = new Timer();
		   Timer timer1 = new Timer();
		   
		
		   
		   timer.schedule(new TimerTask(){
			   
		        @Override
		        public void run() {
		        
		        	System.out.println(links.size()+"");
		        
		        	rounds +=1;
		  
		        		
		        	if(rounds>10) {
		        	//	timer1.cancel();
		        	//	driver.quit();
		        	}
		        	
		    		currpos +=1;
		    		
		        		try {
		        			
		        			Process process = new ProcessBuilder("F:\\web\\GoogleScrapper\\gscrapper.exe",links.get(currpos)).start();
			        		
			        		InputStream is = process.getInputStream();
			        		InputStreamReader isr = new InputStreamReader(is);
			        		BufferedReader br = new BufferedReader(isr);
			        		String line;

			        		String bdy = "";
			        		while ((line = br.readLine()) != null) {
			        		 
			        			bdy += line+"%";
			        		}
			        		
			        		test.d.insert(frm, bdy);
		        		}
		        		
		        		catch(Exception e){
		        			
		        			currpos -=1;
		        			
		        		}
		        		

		        	
		        	for (LogEntry entry : driver.manage().logs().get(LogType.PERFORMANCE)) {
		        		String txt = entry.toString();

		        		if(txt.contains("search?tbm")) {
		        			
		        			System.out.println(txt);
		        			
		        			
		        			Boolean start = false;
		    				String built = "";
		    				
		    				for(int i=0; i<txt.length(); i++) {
		    					
		    					if(txt.charAt(i)=='u' && txt.charAt(i+1)=='r' &&  txt.charAt(i+2)=='l'
		    							&& txt.charAt(i+3)=='"' && txt.charAt(i+4)==':')
		    					{
		    						
		    						start = true;
		    						
		    						i+= 5;
		    					}
		    					
		    					if(start) {
		    						built += txt.charAt(i);
		    					//	System.out.println("<><><"+built);
		    					}
		    					
		    					if(start && txt.charAt(i+1)=='"') {
		    						
		    						if(!built.contains("/maps/_/js/k=maps.m.en")) {
		    						links.add(built);
		    						}
		    						
		    					//	System.out.println("<><><"+built);
		    						break;	    					
		    						
		    					}
		    					
		    				}
		    				
		    			
		    		//	System.out.println(entry.toString());
		    			
		    		
		    		        /*
		    		        JSONArray arr = obj.getJSONArray("posts");
		    		        for (int i = 0; i < arr.length(); i++) {
		    		            String post_id = arr.getJSONObject(i).getString("post_id");
		    		            System.out.println(post_id);
		    		        }
		    			*/
		    				
		    			}
		    		}
		        	
		        
		     
		        
		        }
		    },1000*9,1000*9); 
		   
		   
		   
		   timer1.schedule(new TimerTask(){
		        @Override
		        public void run() {
		        
		        
		        	try {
		    	
		      WebElement ele = driver.findElement(By.id("section-pagination-button-next"));
		       	ele.click();
		        	}
		        	catch(Exception e)
		        	{
		        		
		        	}
		     
		        
		        }
		    },1000*20,1000*20);    
		   
		   
		   
	
		
	}

}
