package app;

import java.io.IOException;

public class RunCalc {

	public static void main(String[] args) {
		System.out.println("Test start!");
//        String batName = "C:\\opt\\jar_home\\runCalc.bat";
        String batName = "C:\\opt\\jar_home\\runCPUZ.bat";
        RunCalc rBat = new RunCalc();
        rBat.run_bat(batName);
    }

	public void run_bat(String batName) {
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
		end = System.currentTimeMillis();
		System.out.println(String.format("Test finished! Time spent = %d seconds.", (end - start)/1000));
	}

}
