/**
 * ListFiles.java
 * 2015Äê11ÔÂ15ÈÕ
 */
package net.watoud.demo.nio.file;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

/**
 * @author lixudong
 *
 */
public class ListFiles
{
	public static void main(String[] args)
	{

	}

	public static List<String> listFiles(String path)
	{
		if (StringUtils.isEmpty(path))
		{
			return Lists.newArrayList();
		}

		Path filePath = Paths.get(path);
		File file = filePath.toFile();

		if (!file.exists())
		{
			throw new IllegalArgumentException("the path[" + path + "] is invalid.");
		}

		if (file.isDirectory())
		{
			return Arrays.asList(file.list());
		}
		else
		{
			return Arrays.asList(file.getName());

		}
	}
}
