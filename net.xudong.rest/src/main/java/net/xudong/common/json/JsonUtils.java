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
	 * �ѷ���json��ʽ��@{String}ת����@{JsonObject}
	 * 
	 * @param value
	 * @return
	 * @throws JsonException
	 *             ����ʧ��ʱ�׳�@{JsonException}�쳣
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
