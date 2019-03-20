package app;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

import app.util.ForkJoinDemoFindMaxValue;

public class Application {

	private static Logger logger = Logger.getLogger(Application.class.getName());
	private static final String SINGLE_THREAD = "1";
	private static final String MULTI_THREAD = "2";

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {

		if(args.length >= 2){
			if(MULTI_THREAD.equals(args[0])){
				//Test Multi Thread
				testMultiThread(Integer.parseInt(args[1]));
			}else if(SINGLE_THREAD.equals(args[0])){
				//Test Single Thread
				testSinglyThread(Integer.parseInt(args[1]));
			}
		}
	}

	private static void testMultiThread(int round) throws InterruptedException, ExecutionException, TimeoutException{
		  long start01 = System.currentTimeMillis();
		  logger.info(String.format("MultiThread Process start on %d \n", start01));
		  long[] array = new long[round];
		  String result = "";
		  for(int i=0;i<round;i++){
		      //Part 2: Test Single Effiency
		      long start = System.currentTimeMillis();
			  result = ForkJoinDemoFindMaxValue.findMaxValue(1000000, 6, 100);
		      long end = System.currentTimeMillis();
		      array[i] = end - start;
		  }
		  long end01 = System.currentTimeMillis();
		  logger.info(result);
		  logger.info(ForkJoinDemoFindMaxValue.findMaxValueLong(array));
		  logger.info(ForkJoinDemoFindMaxValue.findMinValueLong(array));
		  logger.info(String.format("Total MultiThread Process end on %d; total time spent = %d miliseconds. \n", end01, (end01 - start01)));
	}

	private static void testSinglyThread(int round){
		  long start01 = System.currentTimeMillis();
		  logger.info(String.format("SingleThread Process start on %d \n", start01));
		  long[] array = new long[round];
		  String result = "";
		  for(int i=0;i<round;i++){
		      //Part 2: Test Single Effiency
		      long start = System.currentTimeMillis();
			  result = ForkJoinDemoFindMaxValue.findMaxValueSingleThread(1000000, 6, 100);
		      long end = System.currentTimeMillis();
		      array[i] = end - start;
		  }
		  long end01 = System.currentTimeMillis();
		  logger.info(result);
		  logger.info(ForkJoinDemoFindMaxValue.findMaxValueLong(array));
		  logger.info(ForkJoinDemoFindMaxValue.findMinValueLong(array));
		  logger.info(String.format("Total SingleThread Process end on %d; total time spent = %d miliseconds. \n", end01, (end01 - start01)));
	}

}
