package at.ac.fhcampuswien.newsapi;

import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;

import java.util.List;

public class NewsAPIExample {

    public static final String APIKEY = "697fa3a0d57f46e3b39cc714f8aa7f17";    //TODO add your api key

    public static void main(String[] args){

        NewsApi newsApi = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("corona")
                .setEndPoint(Endpoint.TOP_HEADLINES)// example of how to use enums
                .setSourceCountry(Country.at)       // example of how to use enums
                .setSourceCategory(Category.health) // example of how to use enums
                .createNewsApi();

            NewsResponse newsResponse = null;
            try {
                newsResponse = newsApi.getNews();
            }
            catch (NewsApiException e){
                System.out.println("Unexpected Error!?!?!");
        }
            if(newsResponse != null){
                List<Article> articles = newsResponse.getArticles();
                articles.stream().forEach(article -> System.out.println(article.toString()));
            }

        newsApi = new NewsApiBuilder()
                .setApiKey(APIKEY)
                .setQ("corona")
                .setEndPoint(Endpoint.EVERYTHING)
                .setFrom("2020-03-20")
                .setExcludeDomains("Lifehacker.com")
                .createNewsApi();

            newsResponse = null;

        try {
            newsResponse = newsApi.getNews();
        }
        catch (NewsApiException e){
            System.out.println("Unexpected Error!?!?!");
        }

        if(newsResponse != null){
            List<Article> articles = newsResponse.getArticles();
            articles.stream().forEach(article -> System.out.println(article.toString()));
        }

    }
}
