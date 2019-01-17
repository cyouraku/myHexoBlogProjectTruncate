package test.lzj.springbatch.tasklet;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.ExitCodeMapper;
import org.springframework.batch.core.launch.support.SimpleJvmExitCodeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.lzj.TestApplication;
import com.lzj.util.DateTimeUtil;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class MyTaskletForRemoteSqlTestRun {

	protected static final Logger logger = Logger.getLogger(MyTaskletForRemoteSqlTestRun.class.getName());
	private ExitCodeMapper exitCodeMapper = new SimpleJvmExitCodeMapper();
    @Autowired
    @Qualifier("jobLauncher")
    protected JobLauncher jobLauncher;
    @Autowired
    @Qualifier("myJob4RemoteSql")
    protected Job job;

	@SuppressWarnings("deprecation")
	@Test
	public void TestMyTaskletForRemoteSql(){
	logger.info("Test Start!");
	Map<String,JobParameter> parameters = new HashMap<String,JobParameter>();
	String dateTime = DateTimeUtil.getNow().toGMTString();
	logger.info(dateTime);
       try {
    	    parameters.put("daily job",new JobParameter(dateTime));
            String ret = jobLauncher.run(job, new JobParameters(parameters)).getExitStatus().getExitCode();
            int initRes = exitCodeMapper.intValue(ret);
    		if(initRes == 0){
    			// バッチの実行が成功しました。
    			logger.info("■CASE1: Execute batch success ! [BATCH: " + "myJob4RemoteSql" +" ]");
            }
       } catch (Exception e) {
       e.printStackTrace();
       }
	}

}
