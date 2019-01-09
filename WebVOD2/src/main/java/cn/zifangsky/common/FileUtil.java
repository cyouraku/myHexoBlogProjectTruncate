package cn.zifangsky.common;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

	private static final String FILE_PATH = "c:\\temp\\stationList_Config_12";

	/**
	 * Get first and second level city list
	 * @return
	 */
	public static List<String> getFistAndSecondLvCityList(){
		return readFile(FILE_PATH);
	}

    /**
     * 追加文件：使用FileOutputStream，在构造FileOutputStream时，把第二个参数设为true
     *
     * @param fileName
     * @param content
     */
	public static void saveAsFile(String file, String conent) {
		BufferedWriter out = null;
		try {
			out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(file, true)));
			out.write(conent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Read from file and add to List<String>
	 * @param filePath
	 * @return
	 */
	public static List<String> readFile(String filePath){
		List<String> result = new ArrayList<String>();
		try {
			FileReader reader = new FileReader(filePath);
			BufferedReader bf = new BufferedReader(reader);
			String str = "";
			while((str = bf.readLine()) != null) {
					result.add(str);
			}
			bf.close();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
}
