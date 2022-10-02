package pole;

import gnu.io.*;
import source.FMain;

import java.io.*;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Display {

    @SuppressWarnings("rawtypes")
	static  Enumeration portList;
    static CommPortIdentifier portId;
    static SerialPort serialPort;
    static OutputStream outputStream;
    static boolean outputBufferEmptyFlag = false;
	public boolean portFound = false;
 
    public void StartDisplay() {
	    String  defaultPort = FMain.PORT;
	    portList = CommPortIdentifier.getPortIdentifiers();
	 
	    while (portList.hasMoreElements()) {
		    portId = (CommPortIdentifier) portList.nextElement();
		    if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
		    	if (portId.getName().equals(defaultPort)) {
		    		System.out.println("Found port " + defaultPort);
	                portFound = true;
	                try {
	                	serialPort = (SerialPort) portId.open("SimpleWrite", 2000);
	                        
	                } catch (PortInUseException e) {
	                	System.out.println("Port is offline now.");
			    	continue;
				    } 
				    try {
				    	outputStream = serialPort.getOutputStream();     
				    } catch (IOException e) {}
		 
				    try {
				    	serialPort.setSerialPortParams(FMain.BIT_PER_SECOND, 
					       FMain.DATA_BITS, 
					       FMain.STOP_BITS, 
					       FMain.PARITY);
		                System.out.println("Display is online now");
				    } catch (UnsupportedCommOperationException e) {}
				    
				    try {
				    	serialPort.notifyOnOutputEmpty(true);
				    } catch (Exception e) {
						System.out.println("Error setting event notification");
						System.out.println(e.toString());
						System.exit(-1);
				    }
		 
		                    
				    try {
				       Thread.sleep(2000);                          // Be sure data is xferred before closing
				    } catch (Exception e) {}
		    	} 
		    } 
		} 
	 
		if (!portFound) {
		    System.out.println("port " + defaultPort + " not found.");
		} 
    }   
    
	public void ClearDisplay(){
        try{
        	outputStream.write(ESCPOS.SELECT_DISPLAY);
	        outputStream.write(ESCPOS.VISOR_CLEAR);
	        outputStream.write(ESCPOS.VISOR_HOME);
	        outputStream.flush();
        }
        catch(IOException e){}
	}
	
	public void PrintFirstLine(String text){
	    try{
		    ClearDisplay();
		    if(text.length()>10)            //Display can hold only 20 characters per line.Most of displays have 2 lines.
		        text=text.substring(0,10);
		    outputStream.write(text.getBytes());
		    outputStream.flush();
	    }catch(IOException r){}
	    
	}
	
	public void PrintSecondLine(String text){
	    try{
	    	outputStream.write(ESCPOS.HT);
	    if(text.length()>10)
	        text=text.substring(0,10);
	        outputStream.write(text.getBytes());
	        outputStream.flush();
	    }
	    catch(IOException y){
	        System.out.println("Failed to print second line because of :"+y);
	    }
	}
	
	public void PrintThirdLine(String text){
	    try{
	    outputStream.write(ESCPOS.SELECT_DISPLAY);
	    outputStream.write(ESCPOS.Down_Line);
	    outputStream.write(ESCPOS.Left_Line);
	    outputStream.write(ESCPOS.HT);
	    outputStream.write(ESCPOS.HT);
	    if(text.length()>20)
	        text=text.substring(0,20);
	        outputStream.write(text.getBytes());
	        outputStream.flush();
	    }
	    catch(IOException y){
	        System.out.println("Failed to print second line because of :"+y);
	    }
	}
	
	public void ShowOpening(String text1, String text2){    
	    try {
		    ClearDisplay();
		    if(text1.length()>20)
		        text1=text1.substring(0,20);
		    outputStream.write(text1.getBytes());
		    outputStream.flush();
			outputStream.write(ESCPOS.SELECT_DISPLAY);
			outputStream.write(ESCPOS.Down_Line);
			outputStream.write(ESCPOS.Left_Line);
			if(text2.length()>20)
		        text2=text2.substring(0,20);
		    outputStream.write(text2.getBytes());
		    outputStream.flush(); 
	    } catch(Exception e) {}
	}
	
	public void ShowGreeting(){
	    String text1="     JAZAKALLAH/    ";                              // 20 characters
	    String text2="  JAZAKILLAH KHAIR  ";                              //20 characters
	    try {
		    ClearDisplay();
		    if(text1.length()>20)
		        text1=text1.substring(0,20);
		    outputStream.write(text1.getBytes());
		    outputStream.flush();
			outputStream.write(ESCPOS.SELECT_DISPLAY);
			outputStream.write(ESCPOS.Down_Line);
			outputStream.write(ESCPOS.Left_Line);
			if(text2.length()>20)
		        text2=text2.substring(0,20);
		    outputStream.write(text2.getBytes());
		    outputStream.flush(); 
	    } catch(Exception e) {}
		
        try {
            Thread.sleep(5000);                                   //Greeting will dislpay 5 seconds.
        } catch (InterruptedException ex) {
            Logger.getLogger(Display.class.getName()).log(Level.SEVERE, null, ex);
        }
        ClearDisplay();
	}
	
	public void init(){
		try{
			outputStream.write(ESCPOS.Anim);
	    } catch(IOException i){}
	}
	
	public void close(){
	     serialPort.close();
	}
}