package eu.neubias.biii;


import net.imagej.ImageJ;

import org.scijava.ItemVisibility;
import org.scijava.command.Command;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;

import ij.plugin.BrowserLauncher;

/** Demonstration of various parameter types and their widgets. */
@Plugin(type = Command.class, headless = true,
	menuPath = "Help > NEUBIAS > Search ...")
public class Search implements Command {
	
	private String MainURL = "http://biii.eu/search?search_api_fulltext=";

	@Parameter
	private LogService log;


	@Parameter(visibility = ItemVisibility.MESSAGE)
	private final String header =
		"The result will be displayed in your browser.";

	@Parameter
	private String searchTerm = "Search";



//	@Parameter(label = "Results", type = ItemIO.OUTPUT)
//	private String result;


	@Override
	public void run() {		
		try { BrowserLauncher.openURL(MainURL +  searchTerm.toString() ); }
		
		catch (Throwable e) { log.info("Could not open default internet browser"); }

	}

	/** Launches the widget demo. */
	public static void main(final String... args) throws Exception {
		// Launch ImageJ as usual.
		final ImageJ ij = net.imagej.Main.launch(args);

		
	}

}