package stockanalyzer.ui;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import download.ParallelDownloader;
import download.SequentialDownloader;
import stockanalyzer.ctrl.Controller;
import stockanalyzer.exceptions.yahooApiException;

public class UserInterface 
{

	private Controller ctrl = new Controller();

	public UserInterface() throws ParseException {
	}

	public void getDataFromCtrl1() {
		try {
			ctrl.process("AAPL");
		} catch (yahooApiException | IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl2() {
		try {
			ctrl.process("IBM");
		} catch (yahooApiException | IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl3() {
		try {
			ctrl.process("TSLA");
		} catch (yahooApiException | IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl4() {
		try {
			ctrl.process("AAPLhist");
		} catch (yahooApiException | IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl5() {
		try {
			ctrl.process("IBMhist");
		} catch (yahooApiException | IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl6() {
		try {
			ctrl.process("TSLAhist");
		} catch (yahooApiException | IOException e) {
			e.printStackTrace();
		}
	}

	public void getDataFromCtrl7() {
		var list = Arrays.asList("EBS.VI","DOC.VI","SBO.VI","RBI.VI","VIG.VI","TKA.VI","VOE.VI","FACC.VI","ANDR.VI","VER.VI",
				"WIE.VI","CAI.VI","BG.VI","POST.VI","LNZ.VI","UQA.VI","SPI.VI","ATS.VI","IIA.VI");
		SequentialDownloader sq = new SequentialDownloader();
		ParallelDownloader  pq = new ParallelDownloader();
		long time1, time2;

		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list, sq);
		time2 = System.currentTimeMillis();
		System.out.println("SQ requires " + (time2 - time1));

		time1 = System.currentTimeMillis();
		ctrl.downloadTickers(list, pq);
		time2 = System.currentTimeMillis();
		System.out.println("PQ requires " + (time2 - time1));

	}


	public void start() {
		Menu<Runnable> menu = new Menu<>("User Interfacx");
		menu.setTitel("Wählen Sie aus:");
		menu.insert("a", "Apple", this::getDataFromCtrl1);
		menu.insert("b", "IBM", this::getDataFromCtrl2);
		menu.insert("c", "TESLA", this::getDataFromCtrl3);
		menu.insert("d", "Apple (hist)",this::getDataFromCtrl4);
		menu.insert("e", "IBM (hist)",this::getDataFromCtrl5);
		menu.insert("f", "TESLA (hist)",this::getDataFromCtrl6);
		menu.insert("g", "Download tickers",this::getDataFromCtrl7);
		menu.insert("q", "Quit", null);
		Runnable choice;
		while ((choice = menu.exec()) != null) {
			 choice.run();
		}
		ctrl.closeConnection();
		System.out.println("Program finished");
	}


	protected String readLine() 
	{
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
		} catch (IOException e) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 
	{
		Double number = null;
		while(number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
			}catch(NumberFormatException e) {
				number=null;
				System.out.println("Please enter a valid number:");
				continue;
			}
			if(number<lowerlimit) {
				System.out.println("Please enter a higher number:");
				number=null;
			}else if(number>upperlimit) {
				System.out.println("Please enter a lower number:");
				number=null;
			}
		}
		return number;
	}
}
