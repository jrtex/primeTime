package de.vahrson.util;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.apache.log4j.Logger;
/**
 * Insert the type's description here.
 * Creation date: (22.03.29 22:07:10)
 * @author: 
 */
public class DateUtils {
	private static GregorianCalendar gCalendar = new GregorianCalendar();

//	private static String[][] gWeekdayNames =
//		{ { "mo", "mo", "Mo", "Montag" }, {
//			"di", "tu", "Di", "Dienstag" }, {
//			"mi", "we", "Mi", "Mittwoch" }, {
//			"do", "th", "Do", "Donnerstag" }, {
//			"fr", "fr", "Fr", "Freitag" }, {
//			"sa", "sa", "Sa", "Sonnabend" }, {
//			"so", "su", "So", "Sonntag" }
//	};
//	private final static int INDEX_SHORT_DAY = 2;
//	private static HashMap gWeekdayNamesMap;
//	static {
//		initWeekdaysMap();
//	}

	public static SimpleDateFormat gDayFormat = new SimpleDateFormat("dd");
	public static SimpleDateFormat gMonthFormat = new SimpleDateFormat("MM");
	public static SimpleDateFormat gYearFormat = new SimpleDateFormat("yyyy");
	public static SimpleDateFormat gStandardDayFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat gStandardDayDisplayFormat = new SimpleDateFormat("dd.MM.yyyy");

	private static Logger log = Logger.getLogger(DateUtils.class);

	/**
	 * DateUtils constructor comment.
	 */
	public DateUtils() {
		super();
	}

	public static String getStandardDayFormat(Date day) {
		return gStandardDayFormat.format(day);
	}

	public static Date parseStandardDayFormat(String dayStr)
		throws ParseException {
		return gStandardDayFormat.parse(dayStr);
	}
	/** 
	 * Answer minutes since 00:00
	 * @param hours
	 * @param minutes
	 * @return
	 */
	public static int asMinutes(int hours, int minutes) {
		return 60 * hours + minutes;
	}

	/**
	 * CLip seconds and milliseconds from Date, setting them to zero in a Gregorian Calendar
	 * Creation date: (07.12.02 15:00:00)
	 * @return java.util.Date
	 * @param orig java.util.Date
	 */
	public static Date clipSeconds(Date orig) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(orig);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * CLip hours,minutes, secs, millisecs from Date, setting them to zero in a Gregorian Calendar
	 * Creation date: (07.12.02 15:00:00)
	 * @return java.util.Date
	 * @param orig java.util.Date
	 */
	public static Date clipTimeOfDay(Date orig) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(orig);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (22.03.29 22:08:42)
	 * @return boolean
	 * @param d1 java.util.Date
	 * @param d2 java.util.Date
	 */
	public static int compareAsDays(Date d1, Date d2) {
		gCalendar.setTime(d1);
		int t1 =
			gCalendar.get(Calendar.DAY_OF_YEAR)
				+ (gCalendar.get(Calendar.YEAR) * 365);
		gCalendar.setTime(d2);
		int t2 =
			gCalendar.get(Calendar.DAY_OF_YEAR)
				+ (gCalendar.get(Calendar.YEAR) * 365);
		return t1 - t2;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (07.09.01 14:47:38)
	 */
	public static java.util.Date getDate(String val) {

		if (val == null) {
			log.debug("Parsing null date");
			return new Date();
		}
		// todo : (????)
		// de dd-MM-yyyy
		//2001-12-13  -> 24.05.0019 00.00.00
		Object[][] formats =
			{
				{
					Locale.GERMAN,
					"EE, dd MMM yyyy",
					"dd.MM.yyyy",
					"dd.MM.yy",
					"d.M.yyyy",
					"dd.M yyyy" },
				{
				Locale.US, "yyyy-MM-dd" }, {
				Locale.UK, "MM/dd/yy" }, {
				Locale.GERMAN, "dd-MM-yyyy", "dd/MM/yyyy", "HH:mm:ss" }

		};
		//Locale.getAvailableLocales();
		String[] times = { " HH:mm:ss", " HH.mm:ss", "" };
		//    String[] symbol = { "-", "/", "." };
		for (int locale = 0; locale < formats.length; locale++) {
			for (int format = 1; format < formats[locale].length; format++) {
				for (int k = 0; k < times.length; k++) {
					try {
						SimpleDateFormat f =
							new SimpleDateFormat(
								(String) formats[locale][format] + times[k],
								(Locale) formats[locale][0]);
						f.set2DigitYearStart(
							new GregorianCalendar(2000, 1, 1).getTime());
						f.setCalendar(new GregorianCalendar());
						//                    f.setDateFormatSymbols(new DateFormatSymbols().getMonths()"-");
						java.util.Date d = f.parse(val);
						/*System.out.println(
						    ">>>>" + formats[locale][0] + " " + formats[locale][format] + times[k]);
						
						    */
						return d;

					}
					catch (Exception e) {
						DefaultErrorHandler.handleError("DateUtils.getDate", e);
						//  System.out.println(                        formats[locale][0] + " " + formats[locale][format] + times[k]);
					}
				}
			}
		}
		log.debug("unparsable date : " + val);
		return new Date();
		/*
		G        era designator          (Text)              AD
		y        year                    (Number)            1996
		M        month in year           (Text & Number)     July & 07
		d        day in month            (Number)            10
		h        hour in am/pm (1~12)    (Number)            12
		H        hour in day (0~23)      (Number)            0
		m        minute in hour          (Number)            30
		s        second in minute        (Number)            55
		S        millisecond             (Number)            978
		E        day in week             (Text)              Tuesday
		D        day in year             (Number)            189
		F        day of week in month    (Number)            2 (2nd Wed in July)
		w        week in year            (Number)            27
		W        week in month           (Number)            2
		a        am/pm marker            (Text)              PM
		k        hour in day (1~24)      (Number)            24
		K        hour in am/pm (0~11)    (Number)            0
		z        time zone               (Text)              Pacific Standard Time
		'        escape for text         (Delimiter)
		''       single quote            (Literal)           '
		*/
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (12.09.01 11:41:44)
	 * @return java.lang.String
	 */
	public static String getDurationString(Date from, Date to) {
		long dur = 1000 * 60 * 15;
		if (from != null)
			dur = to.getTime() - from.getTime();
		int hour = (int) dur / 1000 / 60 / 60;
		int min = ((int) dur / 1000 / 60) % 60;
		String minString;
		if (min > 9)
			minString = "" + min;
		else
			minString = "0" + min;
		if (hour > 0)
			return hour + ":" + minString;
		else
			return minString;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (03.01.2002 00:32:37)
	 * @return java.lang.String
	 * @param day java.lang.String
	 */
//	public static String getWeekdayShortname(String day) {
//		String key = day.substring(0, 2).toLowerCase();
//		return (String) gWeekdayNamesMap.get(key);
//	}

	/**
	 * answer minutes for time representation like 1:45
	 * Creation date: (11.03.2002 12:03:19)
	 * @return int
	 * @param hoursMinutes java.lang.String
	 */
	public static int hoursMinutesAsMinutes(String hoursMinutes) {
		int i = 0;
		int n = hoursMinutes.length();
		char c = hoursMinutes.charAt(i);
		boolean inDigits = Character.isDigit(c);

		// skip header
		while (i < n && !inDigits) {
			i++;
			if (i < n) {
				c = hoursMinutes.charAt(i);
				inDigits = Character.isDigit(c);
			}
		}

		// hours
		StringBuffer hours = new StringBuffer();
		while (i < n && inDigits) {
			hours.append(c);
			i++;
			if (i < n) {
				c = hoursMinutes.charAt(i);
				inDigits = Character.isDigit(c);
			}
		}

		// skip separator
		while (i < n && !inDigits) {
			i++;
			if (i < n) {
				c = hoursMinutes.charAt(i);
				inDigits = Character.isDigit(c);
			}
		}

		// minutes
		StringBuffer minutes = new StringBuffer();
		while (i < n && inDigits) {
			minutes.append(c);
			i++;
			if (i < n) {
				c = hoursMinutes.charAt(i);
				inDigits = Character.isDigit(c);
			}
		}

		// evaluate
		int iHours, iMinutes;
		if (minutes.length() > 0) { // both hh:mm
			iHours = Integer.parseInt(hours.toString());
			iMinutes = Integer.parseInt(minutes.toString());
		}
		else {
			iHours = Integer.parseInt(hours.toString());
			iMinutes = 0;
			if (iHours > 9) {
				// heuristic to allow "10" to be interpreted as minutes 
				iMinutes = iHours;
				iHours = 0;
			}
		}

		return iMinutes + (60 * iHours);
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (03.01.2002 00:22:42)
	 */
//	private static void initWeekdaysMap() {
//		gWeekdayNamesMap = new HashMap();
//		String[] days;
//		final int n = gWeekdayNames.length;
//		for (int i = 0; i < n; i++) {
//			days = gWeekdayNames[i];
//			gWeekdayNamesMap.put(days[0], days[INDEX_SHORT_DAY]);
//			gWeekdayNamesMap.put(days[1], days[INDEX_SHORT_DAY]);
//		}
//	}

	/**
	 * Insert the method's description here.
	 * Creation date: (22.03.29 22:08:42)
	 * @return boolean
	 * @param d1 java.util.Date
	 * @param d2 java.util.Date
	 */
	public static boolean isSameDay(Date d1, Date d2) {
		return compareAsDays(d1, d2) == 0;
	}

	/**
	 * Answer true if d1 and d2 differ by less than epsilon milliseconds
	 * Creation date: (23.03.29 16:55:15)
	 * @return boolean
	 * @param epsilon long
	 * @param d1 java.util.Date
	 * @param d2 java.util.Date
	 */
	public static boolean isSameTime(long epsilon, Date d1, Date d2) {
		return Math.abs(d1.getTime() - d2.getTime()) < epsilon;
	}

	/**
	 * Insert the method's description here.
	 * Creation date: (27.05.03 10:19:41)
	 * @return boolean
	 * @param day java.util.Date
	 */
	public static boolean isWeekend(Date day) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(day);
		return (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)
			|| (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY);
	}
	
	public static int getDayOfWeek(Date day) {
		Calendar cal= new GregorianCalendar();
		cal.setTime(day);
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	/**
	 * Answer the later of the the two dates
	 * Creation date: (15.10.2002 18:18:15)
	 * @return java.util.Date
	 * @param d1 java.util.Date
	 * @param d2 java.util.Date
	 */
	public static Date max(Date d1, Date d2) {
		Date answer = d1;
		if (d2.getTime() > d1.getTime()) {
			answer = d2;
		}
		return answer;
	}

	/**
	 * Answer the earlier of the the two dates
	 * Creation date: (15.10.2002 18:18:15)
	 * @return java.util.Date
	 * @param d1 java.util.Date
	 * @param d2 java.util.Date
	 */
	public static Date min(Date d1, Date d2) {
		Date answer = d1;
		if (d2.getTime() < d1.getTime()) {
			answer = d2;
		}
		return answer;
	}

// DateParsing
	
	/**
	 * Try to read a year between 1900 and 2099 from str. Answer -1 if not possible
	 * possible input: (Specific->Nonspecific)
	 * (1) az 00.00.YYYY az
	 * (2) az YYYY.00.00 az
	 * (3) az 0.0.YY ay
	 * (4) az YYYY az
	 * (5) az YY az
	 * 
	 * Interpret years 0..9 as 2000..2009
	 * Interpret years 10..99 as 1910..1999
	 * 
	 * @param str
	 * @return
	 */
	/** 
	 * XX_RE are non grouping, XX_REG are grouping
	 */
	private final static String DAY_RE= "(?:(?:[0123][0-9])|[0-9])";
	private final static String DAY_REG= "((?:[0123][0-9])|[0-9])";
	private final static String DAY2_REG= "([0123][0-9])";
	private final static String MONTH_RE= "(?:(?:[01][0-9])|[0-9])";
	private final static String MONTH_REG= "((?:[01][0-9])|[0-9])";
	private final static String MONTH2_REG= "([01][0-9])";
	private final static String YEAR4_RE= "[12][90][0-9][0-9]";
	private final static String YEAR4_REG= "([12][90][0-9][0-9])";
	private final static String YEAR2_RE= "[0-9][0-9]";
	private final static String YEAR2_REG= "([0-9][0-9])";
	private final static String YEAR1_RE= "[0-9]";
	private final static String YEAR1_REG= "([0-9])";
	private final static String PUNCT_RE= "\\p{Punct}";
	private final static String NO_NUMBER= "(?:[^0-9]|\\A|^)";
	
	private final static Pattern [] YRES= {
		Pattern.compile(NO_NUMBER + DAY_RE + PUNCT_RE + MONTH_RE+ PUNCT_RE + YEAR4_REG),
		Pattern.compile(NO_NUMBER + DAY_RE + PUNCT_RE + MONTH_RE+ PUNCT_RE + YEAR2_REG),
		Pattern.compile(NO_NUMBER + DAY_RE + PUNCT_RE + MONTH_RE+ PUNCT_RE + YEAR1_REG),
		Pattern.compile(NO_NUMBER + YEAR4_REG + PUNCT_RE + MONTH_RE+ PUNCT_RE +  DAY_RE),
		Pattern.compile(NO_NUMBER + YEAR4_REG),
		Pattern.compile(NO_NUMBER + YEAR2_REG),
		Pattern.compile(NO_NUMBER + YEAR1_REG)
	};
	
	public static int parseYear(String str){	
		boolean found= false;
		int answer= -1;
		for (int i=0; !found && i<YRES.length; i++) {
			Matcher m= YRES[i].matcher(str);
			m.reset();
			found= m.find();
			if (found) {
				int groups= m.groupCount();
				String year= m.group(1);
				answer= Integer.parseInt(year);
				answer= interpretYear(answer);
			}
		}
		return answer;
	}

	/**
	 * Interpret years 0..9 as 2000..2009
	 * Interpret years 10..99 as 1910..1999
	 * @param answer
	 * @return
	 */
	private static int interpretYear(int answer) {
		if (answer<10) {
			answer+= 2000;
		}
		else if (answer<100) {
			answer+= 1900;
		}
		return answer;
	}
	
	/**
	 * Try to read a date from str. Answer -1 if not possible
	 * possible input: (Specific->Nonspecific)
	 * (-1) az ddMMYYYY az
	 * (0) az ddMMYY az
	 * (1) az d.M.YYYY az
	 * (2) az YYYY.M.d az
	 * (3) az d.M.Y ay
	 * 
	 * @param str
	 * @return
	 */
	private final static Pattern [] DMYRES= {
		Pattern.compile(NO_NUMBER + DAY2_REG + MONTH2_REG+  YEAR4_REG), // -1
		Pattern.compile(NO_NUMBER + DAY2_REG + MONTH2_REG+  YEAR2_REG), //0
		Pattern.compile(NO_NUMBER + DAY_REG + PUNCT_RE + MONTH_REG+ PUNCT_RE + YEAR4_REG), //1
		Pattern.compile(NO_NUMBER + YEAR4_REG + PUNCT_RE + MONTH_REG+ PUNCT_RE +  DAY_REG),//2
		Pattern.compile(NO_NUMBER + DAY_REG + PUNCT_RE + MONTH_REG+ PUNCT_RE + YEAR2_REG),//3
		Pattern.compile(NO_NUMBER + DAY_REG + PUNCT_RE + MONTH_REG+ PUNCT_RE + YEAR1_REG),
	};
	private static final int[][] GROUP_MAP= {  // map day, month, year to group numbers
		{1,2,3}, // -1
		{1,2,3}, // 0
		{1,2,3}, // 1
		{3,2,1}, // 2
		{1,2,3}, // 3
		{1,2,3}
	};	
	
	public static Date parseDate(String str){		
		boolean found= false;
		Date answer= null;
		for (int i=0; !found && i<DMYRES.length; i++) {
			Matcher m= DMYRES[i].matcher(str);
			found= m.find();
			if (found) {
				GregorianCalendar cal= new GregorianCalendar();
				cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(m.group(GROUP_MAP[i][0])));
				cal.set(Calendar.MONTH, Integer.parseInt(m.group(GROUP_MAP[i][1]))-1);
				int year= interpretYear(Integer.parseInt(m.group(GROUP_MAP[i][2])));
				cal.set(Calendar.YEAR, year);
				cal.set(Calendar.HOUR, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				cal.set(Calendar.MILLISECOND, 0);
				
				answer= cal.getTime();
			}
		}
		return answer;
	}
	
	public static String displayDate(Date day){
		return gStandardDayDisplayFormat.format(day);
	}
	
	public static void windBackToDayOfWeek(Calendar cal, int dayOfWeek) {
		while (cal.get(Calendar.DAY_OF_WEEK) != dayOfWeek) {
			cal.add(Calendar.DATE, -1);
		}
	}

}