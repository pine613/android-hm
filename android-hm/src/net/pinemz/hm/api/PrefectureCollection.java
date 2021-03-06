package net.pinemz.hm.api;

import java.text.ParseException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.common.base.Optional;
import com.google.common.collect.ForwardingSortedMap;

public class PrefectureCollection
	extends ForwardingSortedMap<Integer, Prefecture>
{
	public static String TAG = "PrefectureCollection";
	
	private final SortedMap<Integer, Prefecture> delegate = new TreeMap<>();
	
	@Override
	protected SortedMap<Integer, Prefecture> delegate() {
		return this.delegate;
	}
	
	public PrefectureCollection(JSONArray prefecturesJson)
			throws JSONException, ParseException
	{
		int length = prefecturesJson.length();
		
		for (int i = 0; i < length; ++i) {
			JSONObject prefectureJson = prefecturesJson.getJSONObject(i);
			Prefecture prefecture = new Prefecture(prefectureJson);
			
			this.put(prefecture.getId(), prefecture);
		}
	}
	
	public Prefecture[] getPrefectures() {
		return this.values().toArray(new Prefecture[0]);
	}
	
	/**
	 * 都道府県の ID の一覧を取得する
	 * @return
	 */
	public Integer[] getIds() {
		return this.keySet().toArray(new Integer[0]);
	}
	
	/**
	 * 都道府県名の一覧を取得する
	 * @return 都道府県名の一覧
	 */
	public String[] getNames() {
		Prefecture[] prefectures = this.getPrefectures();
		String[] names = new String[prefectures.length];
		
		for (int i = 0; i < prefectures.length; ++i) {
			names[i] = prefectures[i].getName();
		}
		
		return names;
	}
	
	/**
	 * 都道府県名に対応したインスタンスを取得する
	 * @param name 都道府県名
	 * @return 都道府県名に対応したインスタンス
	 */
	public Optional<Prefecture> getByName(String name) {
		for (Map.Entry<Integer, Prefecture> entry: this.delegate.entrySet()) {
			if (entry.getValue().getName() == name) {
				return Optional.of(entry.getValue());
			}
		}
		
		return Optional.absent();
	}
	
	/**
	 * @param name 都道府県名
	 * @return 都道府県名に一致する ID
	 */
	public Optional<Integer> getIdByName(String name) {
		Optional<Prefecture> prefecture = this.getByName(name);
		
		if (prefecture.isPresent()) {
			return Optional.of(prefecture.get().getId());
		}
		
		else {
			return Optional.absent();
		}
	}
}
