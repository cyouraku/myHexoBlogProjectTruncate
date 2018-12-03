package seleniumTest.Util;

import java.time.LocalDateTime;


public class TestDateUtil {

	private static final String formatYMD = "%s/%s/%s ";
    /**開始時間*/
    private static final String MYCARTE_START_TIME = " 00:00:00";
    /**終了時間*/
    private static final String MYCARTE_END_TIME = "23:59:59";
    /**  掲載開始日 年*/
    private static final String openYear = "2018";

    /**  掲載開始日 月*/
    private static final String openMonth = "10";

    /**  掲載開始日 日*/
    private static final String openDay = "31";

    /**  掲載終了日 年*/
    private static final String closeYear = null;

    /**  掲載終了日 月*/
    private static final String closeMonth = null;

    /**  掲載終了日 日*/
    private static final String closeDay = null;

	public static void main(String[] args) {

		System.out.println(createFromTime(formatYMD, openYear,openMonth,openDay));

	}


	private static String createFromTime(String formatYMD, String openYear,String openMonth,String openDay){
		return String.format(formatYMD, openYear,openMonth,openDay).concat(MYCARTE_START_TIME);
	}


	private void calcDateTime(){
		LocalDateTime.now();
//		LocalDate.of(year, month, dayOfMonth)
	}
}
