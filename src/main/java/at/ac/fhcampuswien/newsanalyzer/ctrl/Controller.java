package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiException;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.beans.Source;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Controller {

	public static final String APIKEY = "697fa3a0d57f46e3b39cc714f8aa7f17";

	public void process(NewsApi newsApi) {
		System.out.println("Start process");

		NewsResponse newsResponse = (NewsResponse) getData(newsApi);

		if (newsResponse != null) {
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
			System.out.println();

			sortByLengthAndAlphabet(articles);
			numberOfArticles(articles);
			providerWithMostArticles(articles);
			shortestName(articles);
		}
		System.out.println("End process");
	}


	public Object getData(NewsApi newsApi) {
		NewsResponse newsResponse = null;
		try {
			newsResponse = newsApi.getNews();
		} catch (NewsApiException e) {
			System.out.println("Error:" + e.getMessage());
			if (e.getCause() != null) {
				System.out.println("cause: " + e.getCause().getMessage());
			}
		}
		return newsResponse;
	}



	public static void numberOfArticles(List<Article> articles) {


		long amount = articles.stream()
				.count();

		System.out.println("The amount of articles is: " + amount);
		System.out.println();
	}


	public static void providerWithMostArticles(List<Article> articles) {

		String provider =
				articles.stream()
						.map(Article::getSource)
						.map(Source::getName)
						.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
						.entrySet().stream().max(Map.Entry.comparingByValue())
						.map(Map.Entry::getKey).orElse(null);

		System.out.println("The provider with the most articles is " + provider);
	}



		public static void shortestName(List<Article> articles) {

			String shortestName = articles.stream()
					.filter(article -> article.getAuthor() != null)
					.map(Article::getAuthor)
					.min(Comparator.comparing(String::length))
					.orElse(null);

			System.out.println("The author with the shortest name is: " + shortestName);
			System.out.println();
		}


		public static void sortByLengthAndAlphabet(List<Article> articles) {
			System.out.println("Articles sorted by length and alphabet");
			System.out.println();

			articles.stream()
					.map(Article::getTitle)
					.sorted(Comparator.comparing(String::length).reversed())
					.forEach(System.out::println);
		}
}
