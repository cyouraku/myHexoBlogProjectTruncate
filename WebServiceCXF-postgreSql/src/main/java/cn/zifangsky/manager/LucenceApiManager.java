package cn.zifangsky.manager;

import java.io.IOException;

import org.apache.lucene.queryparser.classic.ParseException;

public interface LucenceApiManager {

	/**
	 * Read from csv files and create index file
	 */
	int readCSVFileAndCreateIndexFile() throws IOException, ParseException;

}
