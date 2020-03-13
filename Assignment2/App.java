package webcrawler;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

import java.util.Scanner;

public class App 
{
	
    public static void main(String[] args) throws Exception {

        final int MAX_CRAWL_DEPTH = 2;
        final int NUMBER_OF_CRAWELRS = 1;
        final String CRAWL_STORAGE = "crawl_data";
        
        //user input
        String url = "";
    	String[] listOfWords;
    	int maxNumOfPages = 10;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	System.out.println("Enter url: ");
    	url = sc.nextLine();
    	System.out.println("Enter words space separated: \n");
    	String temp = sc.nextLine();
    	listOfWords = temp.split(" ");
    	System.out.println("Enter max number of pages to fetch: ");
    	maxNumOfPages = sc.nextInt();
    	sc.close();
        // end of user input
        
        BasicCrawler.fileWriterInit();
        BasicCrawler.getInput(listOfWords, maxNumOfPages, url);
        
        /*
         * Instantiate crawl config
         */
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(CRAWL_STORAGE);
        config.setMaxDepthOfCrawling(MAX_CRAWL_DEPTH);
        config.setPolitenessDelay(100);
        config.setMaxPagesToFetch(maxNumOfPages);
        config.setIncludeBinaryContentInCrawling(false);

        /*
         * Instantiate controller for this crawl.
         */
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);


        
        /*
         * Add seed URLs
         */
        controller.addSeed(url);

        /*
         * Start the crawl.
         */
        long timeStart = System.currentTimeMillis();
        controller.start(BasicCrawler.class, NUMBER_OF_CRAWELRS);
        long timeEnd = System.currentTimeMillis();
        System.out.println("==================End of crawler================\n");
        System.out.println("=======Time taken: "+((timeEnd-timeStart)/1000)+" seocnds");
        BasicCrawler.fileWriterClose();
        BasicCrawler.showResult();
    }
    
}