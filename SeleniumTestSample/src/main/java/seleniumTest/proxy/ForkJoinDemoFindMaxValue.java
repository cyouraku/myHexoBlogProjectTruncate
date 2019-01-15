package seleniumTest.proxy;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

/**
* Compare MultiThread/SingleThread to find Max value in int array
* fork/join demo
*/
public class ForkJoinDemoFindMaxValue {

	private static Logger logger = Logger.getLogger(ForkJoinDemoFindMaxValue.class.getName());

  /**
   * how to find the max number in array by Fork/Join
   */
  private static class MaxNumber extends RecursiveTask<Integer> {
      private int threshold = 8;
      private int[] array; // the data array
      private int index0 = 0;
      private int index1 = 0;

      public MaxNumber(int[] array, int index0, int index1) {
          this.array = array;
          this.index0 = index0;
          this.index1 = index1;
      }

      @Override
      protected Integer compute() {
          int max = Integer.MIN_VALUE;
          if ((index1 - index0) <= threshold) {
              for (int i = index0;i <= index1; i ++) {
                  max = Math.max(max, array[i]);
              }
          } else {
              //fork/join
              int mid = index0 + (index1 - index0) / threshold;
              MaxNumber lMax = new MaxNumber(array, index0, mid);
              MaxNumber rMax = new MaxNumber(array, mid + 1, index1);
              lMax.fork();
              rMax.fork();
              int lm = lMax.join();
              int rm = rMax.join();
              max = Math.max(lm, rm);
          }
          return max;
      }
  }

  public static void main(String ... args) throws ExecutionException, InterruptedException, TimeoutException {
	  String result = "";
	  //Test 3 times to warm up JVM
	  int cnt = 3;
	  while(cnt>0){
		  cnt-=1;
		  //Part 1: Test MultiThread Effiency
		  long start = System.currentTimeMillis();
		  logger.info(String.format("MultiThread Process start on %d \n", start));
		  result = findMaxValue(1000000, 6, 100);
	      long end = System.currentTimeMillis();
	      logger.info(result);
	      logger.info(String.format("MultiThread Process end on %d; total time spent = %d miliseconds. \n", end, (end - start)));
	      //Part 2: Test Single Effiency
	      start = System.currentTimeMillis();
		  logger.info(String.format("SingleThread Process start on %d \n", start));
		  result = findMaxValueSingleThread(1000000, 6, 100);
	      end = System.currentTimeMillis();
	      logger.info(result);
	      logger.info(String.format("SingleThread Process end on %d; total time spent = %d miliseconds. \n", end, (end - start)));
	  }
  }

  /**
   * find max value.
   * param[0]:int array length
   * param[1]:Arithmetic param for index
   * param[2]:Arithmetic param for value related to index of param[1]
   * @param param
   * @throws InterruptedException
   * @throws ExecutionException
   * @throws TimeoutException
   */
  private static String findMaxValue(int... param) throws InterruptedException, ExecutionException, TimeoutException{
	  Map<Integer,Integer> data = new HashMap<Integer,Integer>();
      ForkJoinPool pool = new ForkJoinPool();
      int[] array = createIntArray(data, param[0], param[1], param[2]);
      MaxNumber task = new MaxNumber(array, 0, array.length - 1);
      Future<Integer> future = pool.submit(task);
//      while(!task.isDone()){
//    	  System.out.println(String.format("Remaining threads = %s \n", pool.getActiveThreadCount()));
//      }
      int result = future.get(1, TimeUnit.SECONDS);
//      System.out.println(String.format("Max = %d; Index = %d. \n", result, getIndex(data,result)));
      return String.format("MultiThread Process Result --> Max = %d; Index = %d. \n", result, getIndex(data,result));

  }

  /**
   * Create int array and record index into Map.
   * @param data
   * @param param
   * @return
   */
  private static int[] createIntArray(Map<Integer,Integer> data,int...param) {
      int[] array = new int[param[0]];
      for(int i=0; i< param[0]; i++){
    	  array[i] = i+1;
    	  data.put(i, array[i]);
    	  if(i%param[1]==0){
    		  array[i] = i*param[2];
    		  data.put(i, array[i]);
    	  }
      }
      return array;
  }

  /**
   * Get index of the max value
   * @param data
   * @param max
   * @return
   */
  private static Integer getIndex(Map<Integer, Integer> data, int max){
	  for(int i=0; i<data.size(); i++){
		  if(max==data.get(i)){
			  return i;
		  }
	  }
	  return 0;
  }


  private static String findMaxValueSingleThread(int...param){
	  Map<Integer,Integer> data = new HashMap<Integer,Integer>();
	  int[] array = createIntArray(data, param[0], param[1], param[2]);
      int max = Integer.MIN_VALUE;
      int index0 = 0;
      int index1 = array.length - 1;
      for (int i = index0;i <= index1; i ++) {
          max = Math.max(max, array[i]);
      }
//      System.out.println(String.format("Max = %d; Index = %d. \n", max, getIndex(data,max)));
      return String.format("SingleThread Process Result --> Max = %d; Index = %d. \n", max, getIndex(data,max));
  }


}

