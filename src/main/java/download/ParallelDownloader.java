package download;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class ParallelDownloader extends Downloader{
    @Override
    public int process(List<String> urls) {
        int count =0;
        int threads = Runtime.getRuntime().availableProcessors();
        ExecutorService parallelExecuter = Executors.newFixedThreadPool(threads);

        List <Future<String>> parDown = new ArrayList<>();
        for (String url:urls){
            parDown.add(parallelExecuter.submit(()->saveJson2File(url)));
        }
        for (Future future : parDown){
            try{ future.get();
            }
            catch (InterruptedException | ExecutionException e){
                e.printStackTrace();
            }
            finally {
                count ++;
            }
        }
        return 0;
    }
}