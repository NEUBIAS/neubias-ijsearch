package eu.neubias.biii;

/*-
 * #%L
 * Search framework for SciJava applications.
 * %%
 * Copyright (C) 2017 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.scijava.log.LogService;
import org.scijava.plugin.Parameter;
import org.scijava.plugin.Plugin;
import org.scijava.search.SearchResult;
import org.scijava.search.Searcher;
import org.scijava.search.web.WebSearchResult;

/**
 * A searcher for the <a href="http://biii.eu/search">Bio-Imaging Search
 * Engine</a>.
 *
 * @author Robert Haase (MPI-CBG)
 * @author Victor Caldas (VU-NL)
 */
@Plugin(type = Searcher.class, enabled = false)
public class BISESearcher implements Searcher {

	private final ArrayList<SearchResult> searchResults = new ArrayList<>();

	private String currentHeading;
	private String currentLink;
	private String currentContent = "";

	@Parameter
	private LogService log;

	@Override
	public String title() {
		return "BISE";
	}

	@Override
	public List<SearchResult> search(final String text, final boolean fuzzy) {
		searchResults.clear();

		try {
			Document doc = Jsoup.connect("http://biii.eu/search?search_api_fulltext=" +
					URLEncoder.encode(text, "utf-8") + "&source=imagej").get();
			
			parse(doc.select("tbody"));
			
			saveLastItem();
			
		}catch (final IOException e) {
				log.debug(e);
		}
		

		return searchResults;
	}

	private void parseHeading(final Elements links) {
		
 	 	Element link = links.first();
 	 	
		if (links.first().attr("href") != null) {
			String href = link.attr("href");
			currentHeading = link.text();
			currentLink = "http://biii.eu" + href;
			
		}
		
		saveLastItem();

	}



	private void saveLastItem() {
		if (currentHeading != null && currentHeading.length() > 0) {
			searchResults.add(new WebSearchResult(currentHeading, //
				currentLink, currentContent));
		}
		currentHeading = "";
		currentLink = "";
		currentContent = "";
	}

	private void parse(final Elements elements) {
		
		Elements elementList = elements.select("tr");
		
		for (Element t :elementList.select("tr")) {
			Elements links = t.getElementsByTag("a");
        	 	
       	 	parseHeading(links);			
		}
	}
}
