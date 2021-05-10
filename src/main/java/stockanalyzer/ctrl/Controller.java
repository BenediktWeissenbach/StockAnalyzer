package stockanalyzer.ctrl;

import download.Downloader;
import download.SequentialDownloader;
import stockanalyzer.exceptions.yahooApiException;
import yahooApi.YahooFinance;
import yahooApi.beans.QuoteResponse;
import yahooApi.beans.YahooResponse;
import yahoofinance.Stock;
// import stockanalyzer.exception.yahooApiException;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class Controller {

	YahooFinance yahooFinance = new YahooFinance();

	public Controller() throws ParseException {
	}

	public void process(String ticker) throws IOException, yahooApiException {
		System.out.println("Start process");

		//TODO implement Error handling

		//TODO implement methods for
		//1) Daten laden
		//2) Daten Analyse

		Stock stock = null;

		switch(ticker){
			case "AAPL":
				getData("AAPL");
				break;

			case "IBM":
				getData("IBM");
				break;

			case "TSLA":
				getData("TSLA");
				break;

			case "AAPLhist":
				stock = yahoofinance.YahooFinance.get("AAPL");
				stock.getHistory().forEach(System.out::println);
				System.out.println("DataCount: "+ countData(stock));
				System.out.println("Minimum: " + minimum(stock));
				System.out.println("Maximum: " + maximum(stock));
				System.out.println("Average: "+ average(stock));
				break;

			case "IBMhist":
				stock = yahoofinance.YahooFinance.get("IBM");
				stock.getHistory().forEach(System.out::println);
				System.out.println("DataCount: "+ countData(stock));
				System.out.println("Minimum: " + minimum(stock));
				System.out.println("Maximum: " + maximum(stock));
				System.out.println("Average: "+ average(stock));
				break;

			case "TSLAhist":
				stock = yahoofinance.YahooFinance.get("TSLA");
				stock.getHistory().forEach(System.out::println);
				System.out.println("DataCount: "+ countData(stock));
				System.out.println("Minimum: " + minimum(stock));
				System.out.println("Maximum: " + maximum(stock));
				System.out.println("Average: "+ average(stock));
				break;

			default:
				System.out.println("ERROR");
		}

	}


	public Object getData(String searchString) throws yahooApiException {
		try {
			List<String> ticker = Arrays.asList(searchString);
			YahooResponse response = yahooFinance.getCurrentData(ticker);
			QuoteResponse quotes = response.getQuoteResponse();
			quotes.getResult().stream().forEach(quote -> System.out.println(quote.getAsk() + "; " + quote.getLongName() + "; " + quote.getFinancialCurrency() + "; " + quote.getFiftyDayAverage()));
			return null;
		} catch (yahooApiException e) {
			throw new yahooApiException("When accessing the data an error occured!" + e.getMessage());
		}
	}


	public void closeConnection() {

	}

	public long countData(Stock stock) throws IOException {
			return stock.getHistory(Interval.WEEKLY).size();
	}

	public double average(Stock stock) throws IOException {
		return stock.getHistory(Interval.WEEKLY).stream().mapToDouble(q -> q.getClose().doubleValue()).average().orElse(0.0);
	}


	public double minimum(Stock stock) throws IOException {
		return stock.getHistory(Interval.WEEKLY).stream().mapToDouble(q->q.getClose().doubleValue()).average().orElse(0.0);
	}

	public double maximum(Stock stock) throws IOException {
		return stock.getHistory(Interval.WEEKLY).stream().mapToDouble(q->q.getClose().doubleValue()).average().orElse(0.0);
	}

	public void downloadTickers (List<String> tickers, Downloader downloader) {
		downloader.process(tickers);
	};

}

