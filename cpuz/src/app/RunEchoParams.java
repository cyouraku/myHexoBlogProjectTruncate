package app;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class RunEchoParams {

	private static final String BASE_BAT_NM = "C:\\opt\\echoParams.bat";

	public static void main(String[] args) throws InterruptedException {
		System.out.println("Test start!");
        RunEchoParams rBat = new RunEchoParams();
        List<String> queue = rBat.createBatchStr();
        int lot = 1;
        for(String str : queue){
        	System.out.println(String.format("Lot = %d, BatNM = %s start on %s", lot, str, LocalTime.now().toString()));
        	rBat.run_bat(str, lot);
        	lot++;
        }
    }

	public void run_bat(String batName, int lot) throws InterruptedException {
		long start = 0L;
		long end = 0L;
		Process ps = null;
		try {
			start = System.currentTimeMillis();
			ps = Runtime.getRuntime().exec(batName);
			ps.waitFor();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}finally{
	        //销毁子进程
	        ps.destroy();
	        ps = null;
		}
		//Debug:wait 3 seconds
		Thread.sleep(3000);
		end = System.currentTimeMillis();
		System.out.println(String.format("Lot %d finished! Time spent = %d seconds.", lot, (end - start)/1000));
	}

	public List<String> createBatchStr(){
		List<String> batchQueue = new ArrayList<String>();
		String[] input = {"f1","f2","f3","f4","f5","f6"};
		String[] output = {"o1","o2","o3","o4","o5","o6"};
		for(int i=0;i<input.length;i++){
			batchQueue.add(String.format("%s %s %s",BASE_BAT_NM ,input[i],output[i]));
		}
		return batchQueue;
	}

}
