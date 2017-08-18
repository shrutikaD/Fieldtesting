package Utilities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LogGenerator {
	java.util.Date date;
	
	// get Time stamp
	public Timestamp getTimestamp() {
		date = new java.util.Date();
		return new Timestamp(date.getTime());
	}

	// Log Exception
	public void logException(BufferedWriter bw, String exception) throws IOException {
		if (bw != null) {
			System.out.println(getTimestamp() + "	Exception: " + exception);
			bw.write(getTimestamp() + "	Exception: " + exception + "\n");
			bw.newLine();
		}	
		
	}
	
	// Log Error
		public void logError(BufferedWriter bw, String error) throws IOException {
			if (bw != null) {
				System.out.println(getTimestamp() + "	Error: " + error);
				bw.write(getTimestamp() + "	Exception: " + error + "\n");
				bw.newLine();
			}	
			
		}

	// Log INFO
	public void logInfo(BufferedWriter bw, String info) throws IOException {		
				System.out.println(getTimestamp() + "	INFO: " + info);
				bw.write(getTimestamp() + "	INFO: " + info + "\n");
				bw.newLine();					
	}

	// Print pattern
	public void print(BufferedWriter bw) throws IOException {
			bw.write("_________________________________________________________________________");
			bw.newLine();
		}
}
