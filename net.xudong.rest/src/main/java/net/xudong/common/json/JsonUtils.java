/**
 * 
 */
package net.xudong.common.json;

import java.io.Reader;
import java.io.StringReader;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;

import org.apache.commons.io.IOUtils;

/**
 * @author xudong
 *
 */
public class JsonUtils
{
	/**
	 * 把符合json格式的@{String}转换成@{JsonObject}
	 * 
	 * @param value
	 * @return
	 * @throws JsonException
	 *             解析失败时抛出@{JsonException}异常
	 */
	public static JsonObject fromStringToJson(String value)
			throws JsonException
	{
		Reader stringReader = null;
		JsonReader jsonReader = null;
		try
		{
			stringReader = new StringReader(value);
			jsonReader = Json.createReader(stringReader);
			JsonObject json = jsonReader.readObject();
			return json;
		}
		catch (RuntimeException e)
		{
			throw new JsonException("Failed to parse string [" + value
					+ "] to json.", e);
		}
		finally
		{
			IOUtils.closeQuietly(stringReader);
			IOUtils.closeQuietly(jsonReader);
		}
	}
}
