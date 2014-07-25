/**
 * 
 */
package demo.watoud.db.mapdb;

import java.io.File;
import java.util.concurrent.ConcurrentNavigableMap;

import org.mapdb.DB;
import org.mapdb.DBMaker;

/**
 * https://github.com/jankotek/MapDB/tree/master/src/test/java/examples
 */

/**
 * @author xudong
 *
 */
public class MapDbExample
{
	private static final String MAPDB_PATH = "C:\\zfile\\db\\mapdb\\example\\testdb";

	public static void main(String[] args)
	{
		DB db = DBMaker.newFileDB(new File(MAPDB_PATH)).closeOnJvmShutdown()
				.encryptionEnable("password").make();
		try
		{
			ConcurrentNavigableMap<Integer, String> map = db
					.getTreeMap("collectionName");

			map.put(1, "jianshui");
			map.put(2, "qingfeng");
			db.commit();
		}
		finally
		{
			db.close();
		}

	}
}
