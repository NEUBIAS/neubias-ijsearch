package eu.neubias.biii;


import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.plugin.BrowserLauncher;

@Plugin(type = Command.class, headless = true, menuPath = "Help > NEUBIAS > Bio Image Information Index")

public class BIII_website implements Command {

	@Parameter
	LogService log;
	
	public void run() {
		try { BrowserLauncher.openURL("http://biii.eu/"); }
		catch (Throwable e) { log.info("Could not open default internet browser"); }
		
	}
	
}