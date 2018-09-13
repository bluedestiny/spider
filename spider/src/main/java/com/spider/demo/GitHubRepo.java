package com.spider.demo;

import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.example.GithubRepo;
import us.codecraft.webmagic.model.ConsolePageModelPipeline;
import us.codecraft.webmagic.model.OOSpider;
import us.codecraft.webmagic.model.annotation.ExtractBy;
import us.codecraft.webmagic.model.annotation.ExtractByUrl;
import us.codecraft.webmagic.model.annotation.HelpUrl;
import us.codecraft.webmagic.model.annotation.TargetUrl;

@TargetUrl("https://item.jd.com/\\d+.html")
@HelpUrl("https://sale.jd.com/act/\\w.html")
public class GitHubRepo {

    @ExtractBy(value = "//div[@class='sku-name']/text()", notNull = true)
    private String name;

    @ExtractByUrl("//div[@class='w']/tidyText()")
    private String author;

    @ExtractBy("//div[@class='summary-service']/tidyText()")
    private String readme;

    public static void main(String[] args) {
        OOSpider.create(Site.me().setSleepTime(1000)
                , new ConsolePageModelPipeline(), GitHubRepo.class)
                .addUrl("https://sale.jd.com").thread(5).run();
    }
}
