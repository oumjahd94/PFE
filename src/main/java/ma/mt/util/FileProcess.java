package ma.mt.util;

import java.io.File;

public final class FileProcess {
	public static boolean exist(String source)
	  {
	    File in = new File(source);
	    return in.exists();
	  }

}
