/**
 * 
 */
package net.xudong.common.json;

import javax.json.JsonObject;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author xudong
 *
 */
public class JsonUtilsTest
{
	@Test
	public void stringParseToJsonTest()
	{
		String value = "{\"name\":\"jianshui\"}";
		JsonObject json = JsonUtils.fromStringToJson(value);

		String expected = "jianshui";
		String actual = json.getString("name");

		Assert.assertEquals(expected, actual);
	}

	@Test(expected = JsonException.class)
	public void failedToParseStringToJsonTest()
	{
		String value = "{\"name\":\"jianshui\"";
		JsonUtils.fromStringToJson(value);
		Assert.fail("Expected an JsonException to be thrown");
	}
}
