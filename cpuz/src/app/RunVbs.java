package app;

import java.io.IOException;

public class RunVbs {

	public static void main(String[] args) {
		//Hide CMD console:
//		String strcmd = "cmd /c start C:\\opt\\jar_home\\testCPUZ.vbs";
		//Show CMD console:
//		String strcmd = "cmd /c start C:\\opt\\jar_home\\runCPUZ.bat";
		//Show CMD console:
		String strcmd = "cmd /c start C:\\opt\\echoParams.bat f1 o1";
		//调用上面的run_cmd方法执行操作
		run_cmd(strcmd);
	}

    public static void run_cmd(String strcmd) {
    	//Runtime.getRuntime()返回当前应用程序的Runtime对象
        Runtime rt = Runtime.getRuntime();
        //Process可以控制该子进程的执行或获取该子进程的信息。
        Process ps = null;
        try {
        	//该对象的exec()方法指示Java虚拟机创建一个子进程执行指定的可执行程序，并返回与该子进程对应的Process对象实例。
            ps = rt.exec(strcmd);
            //等待子进程完成再往下执行。
            ps.waitFor();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //接收执行完毕的返回值
        int i = ps.exitValue();
        if (i == 0) {
            System.out.println("执行完成.");
        } else {
            System.out.println("执行失败.");
        }

        //销毁子进程
        ps.destroy();
        ps = null;
    }
}
