package com.spider.demo;

import java.util.Map;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.JsonFilePipeline;
import us.codecraft.webmagic.processor.PageProcessor;

public class GithubRepoPageProcessor implements PageProcessor {
	  private Site site = Site.me().setRetryTimes(3).setSleepTime(100);
	    @Override
	    public void process(Page page) {
	       /* page.addTargetRequests(page.getHtml().links().regex("(https://github\\.com/\\w+/\\w+)").all());
	        page.putField("author", page.getUrl().regex("https://github\\.com/(\\w+)/.*").toString());*/
	    	 page.addTargetRequests(page.getHtml().links().regex("(http://media\\.163\\.com/18/\\d+/\\d+/\\w+\\.html)").all());
		    //page.putField("author", page.getUrl().regex("http://media\\.com/(\\w+)/.*").toString());
	        page.putField("title", page.getHtml().xpath("//div[@class='post_crumb']/text()"));
	        page.putField("name", page.getHtml().xpath("//div[@class='post_content_main']/h1/text()").toString());
	        page.putField("readme", page.getHtml().xpath("//div[@class=\"post_text\"]/p/tidyText()"));
	    	Map<String, Object> map = page.getResultItems().getAll();
        	System.out.println(map.toString());
	        if (page.getResultItems().get("name")==null){
	            //skip this page
	            page.setSkip(true);
	        }
	    }

	    @Override
	    public Site getSite() {
	        return site;
	    }
	    public static void main(String[] args) {
	    	// 初始化爬虫，添加开始url，添加最终结果存储路径
	        Spider.create(new GithubRepoPageProcessor())
	        	.addUrl("http://media.163.com")
	        	//.addPipeline(new ConsolePipeline())
	
	        	.addPipeline(new JsonFilePipeline("d:/webmagic"))
	        	.thread(5)
	        	.run();
	    }
}
