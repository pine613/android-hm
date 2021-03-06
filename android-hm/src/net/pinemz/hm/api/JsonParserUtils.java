package net.pinemz.hm.api;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;

class JsonParserUtils {
	/**
	 * JSON 形式の日時を解析する
	 * @param dateText 解析する日時
	 * @return 解析結果
	 * @throws ParseException 解析失敗
	 */
	public static Date parseDate(String dateText) throws ParseException {
		final FastDateFormat fastDateFormat =
				FastDateFormat.getInstance("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		
		final String[] patterns = new String[]{ fastDateFormat.getPattern() };
		
		return DateUtils.parseDate(dateText, patterns);
	}
}
