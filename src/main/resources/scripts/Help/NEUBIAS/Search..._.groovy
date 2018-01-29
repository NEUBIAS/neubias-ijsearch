#@ PlatformService platformService
#@ String (visibility = "MESSAGE", value = "The result will be displayed in your browser", persist = false) message
#@ String (label = "Search term", value = "Search") searchTerm
import java.net.URL
platformService.open(new URL("http://biii.eu/search?search_api_fulltext=$searchTerm"))
