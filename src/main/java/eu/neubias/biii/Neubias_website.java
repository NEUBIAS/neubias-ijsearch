package eu.neubias.biii;


import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.plugin.BrowserLauncher;

@Plugin(type = Command.class, headless = true, menuPath = "Help > NEUBIAS > NEUBIAS")

public class Neubias_website implements Command {

	@Parameter
	LogService log;
	
	public void run() {
		try { BrowserLauncher.openURL("http://eubias.org/NEUBIAS/"); }
		catch (Throwable e) { log.info("Could not open default internet browser"); }
		
	}
	
}