package ma.mt.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.StringTokenizer;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public final class fichierTools { 
	
	public static void afficherFichier(HttpServletResponse target, String document, String cheminFichier)
			throws IOException, Exception {
		try {
			String nomFichier = "";
			String extentionFichier = "";

			if ((document != null) && (document.compareTo("") != 0)) {
				StringTokenizer st = new StringTokenizer(document, "/");
				while (st.hasMoreTokens()) {
					int i = st.countTokens();
					switch (i) {
					case 1:
						nomFichier = st.nextToken();
						break;
					default:
						st.nextToken();
					}

				} 
				
				StringTokenizer stExt = new StringTokenizer(nomFichier, ".");
				while (stExt.hasMoreTokens()) {
					extentionFichier = stExt.nextToken();
				}

				Hashtable hashMimeType = new Hashtable();
				hashMimeType.put("ez", "application/andrew-inset");
				hashMimeType.put("hqx", "application/mac-binhex40");
				hashMimeType.put("cpt", "application/mac-compactpro");
				hashMimeType.put("doc", "application/msword");
				hashMimeType.put("bin", "application/octet-stream");
				hashMimeType.put("dms", "application/octet-stream");
				hashMimeType.put("lha", "application/octet-stream");
				hashMimeType.put("lzh", "application/octet-stream");
				hashMimeType.put("exe", "application/octet-stream");
				hashMimeType.put("class", "application/octet-stream");
				hashMimeType.put("so", "application/octet-stream");
				hashMimeType.put("dll", "application/octet-stream");
				hashMimeType.put("oda", "application/oda");
				hashMimeType.put("pdf", "application/pdf");
				hashMimeType.put("ai", "application/postscript");
				hashMimeType.put("eps", "application/postscript");
				hashMimeType.put("ps", "application/postscript");
				hashMimeType.put("smi", "application/smil");
				hashMimeType.put("smil", "application/smil");
				hashMimeType.put("mif", "application/vnd.mif");
				hashMimeType.put("xls", "application/vnd.ms-excel");
				hashMimeType.put("ppt", "application/vnd.ms-powerpoint");
				hashMimeType.put("wbxml", "application/vnd.wap.wbxml");
				hashMimeType.put("wmlc", "application/vnd.wap.wmlc");
				hashMimeType.put("wmlsc", "application/vnd.wap.wmlscriptc");
				hashMimeType.put("bcpio", "application/x-bcpio");
				hashMimeType.put("vcd", "application/x-cdlink");
				hashMimeType.put("pgn", "application/x-chess-pgn");
				hashMimeType.put("cpio", "application/x-cpio");
				hashMimeType.put("csh", "application/x-csh");
				hashMimeType.put("dcr", "application/x-director");
				hashMimeType.put("dir", "application/x-director");
				hashMimeType.put("dxr", "application/x-director");
				hashMimeType.put("dvi", "application/x-dvi");
				hashMimeType.put("spl", "application/x-futuresplash");
				hashMimeType.put("gtar", "application/x-gtar");
				hashMimeType.put("hdf", "application/x-hdf");
				hashMimeType.put("js", "application/x-javascript");
				hashMimeType.put("skp", "application/x-koan");
				hashMimeType.put("skd", "application/x-koan");
				hashMimeType.put("skt", "application/x-koan");
				hashMimeType.put("skm", "application/x-koan");
				hashMimeType.put("latex", "application/x-latex");
				hashMimeType.put("nc", "application/x-netcdf");
				hashMimeType.put("cdf", "application/x-netcdf");
				hashMimeType.put("sh", "application/x-sh");
				hashMimeType.put("shar", "application/x-shar");
				hashMimeType.put("swf", "application/x-shockwave-flash");
				hashMimeType.put("sit", "application/x-stuffit");
				hashMimeType.put("sv4cpio", "application/x-sv4cpio");
				hashMimeType.put("sv4crc", "application/x-sv4crc");
				hashMimeType.put("tar", "application/x-tar");
				hashMimeType.put("tcl", "application/x-tcl");
				hashMimeType.put("tex", "application/x-tex");
				hashMimeType.put("texinfo", "application/x-texinfo");
				hashMimeType.put("texi", "application/x-texinfo");
				hashMimeType.put("t", "application/x-troff");
				hashMimeType.put("tr", "application/x-troff");
				hashMimeType.put("roff", "application/x-troff");
				hashMimeType.put("man", "application/x-troff-man");
				hashMimeType.put("me", "application/x-troff-me");
				hashMimeType.put("ms", "application/x-troff-ms");
				hashMimeType.put("ustar", "application/x-ustar");
				hashMimeType.put("src", "application/x-wais-source");
				hashMimeType.put("zip", "application/zip");
				hashMimeType.put("au", "audio/basic");
				hashMimeType.put("snd", "audio/basic");
				hashMimeType.put("mid", "audio/midi");
				hashMimeType.put("midi", "audio/midi");
				hashMimeType.put("kar", "audio/midi");
				hashMimeType.put("mpga", "audio/mpeg");
				hashMimeType.put("mp2", "audio/mpeg");
				hashMimeType.put("mp3", "audio/mpeg");
				hashMimeType.put("aif", "audio/x-aiff");
				hashMimeType.put("aiff", "audio/x-aiff");
				hashMimeType.put("aifc", "audio/x-aiff");
				hashMimeType.put("m3u", "audio/x-mpegurl");
				hashMimeType.put("ram", "audio/x-pn-realaudio");
				hashMimeType.put("rm", "audio/x-pn-realaudio");
				hashMimeType.put("rpm", "audio/x-pn-realaudio-plugin");
				hashMimeType.put("ra", "audio/x-realaudio");
				hashMimeType.put("wav", "audio/x-wav");
				hashMimeType.put("pdb", "chemical/x-pdb");
				hashMimeType.put("xyz", "chemical/x-xyz");
				hashMimeType.put("bmp", "image/bmp");
				hashMimeType.put("gif", "image/gif");
				hashMimeType.put("ief", "image/ief");
				hashMimeType.put("jpeg", "image/jpeg");
				hashMimeType.put("jpg", "image/jpeg");
				hashMimeType.put("jpe", "image/jpeg");
				hashMimeType.put("png", "image/png");
				hashMimeType.put("tiff", "image/tiff");
				hashMimeType.put("tif", "image/tiff");
				hashMimeType.put("wbmp", "image/vnd.wap.wbmp");
				hashMimeType.put("ras", "image/x-cmu-raster");
				hashMimeType.put("pnm", "image/x-portable-anymap");
				hashMimeType.put("pbm", "image/x-portable-bitmap");
				hashMimeType.put("pgm", "image/x-portable-graymap");
				hashMimeType.put("ppm", "image/x-portable-pixmap");
				hashMimeType.put("rgb", "image/x-rgb");
				hashMimeType.put("xbm", "image/x-xbitmap");
				hashMimeType.put("xpm", "image/x-xpixmap");
				hashMimeType.put("xwd", "image/x-xwindowdump");
				hashMimeType.put("igs", "model/iges");
				hashMimeType.put("iges", "model/iges");
				hashMimeType.put("msh", "model/mesh");
				hashMimeType.put("mesh", "model/mesh");
				hashMimeType.put("silo", "model/mesh");
				hashMimeType.put("wrl", "model/vrml");
				hashMimeType.put("vrml", "model/vrml");
				hashMimeType.put("css", "text/css");
				hashMimeType.put("html", "text/html");
				hashMimeType.put("htm", "text/html");
				hashMimeType.put("asc", "text/plain");
				hashMimeType.put("txt", "text/plain");
				hashMimeType.put("rtx", "text/richtext");
				hashMimeType.put("rtf", "text/rtf");
				hashMimeType.put("sgml", "text/sgml");
				hashMimeType.put("sgm", "text/sgml");
				hashMimeType.put("tsv", "text/tab-separated-values");
				hashMimeType.put("wml", "text/vnd.wap.wml");
				hashMimeType.put("wmls", "text/vnd.wap.wmlscript");
				hashMimeType.put("etx", "text/x-setext");
				hashMimeType.put("xml", "text/xml");
				hashMimeType.put("xsl", "text/xml");
				hashMimeType.put("mpeg", "video/mpeg");
				hashMimeType.put("mpg", "video/mpeg");
				hashMimeType.put("mpe", "video/mpeg");
				hashMimeType.put("qt", "video/quicktime");
				hashMimeType.put("mov", "video/quicktime");
				hashMimeType.put("mxu", "video/vnd.mpegurl");
				hashMimeType.put("avi", "video/x-msvideo");
				hashMimeType.put("movie", "video/x-sgi-movie");
				hashMimeType.put("ice", "x-conference/x-cooltalk");

				String typeMime = (String) hashMimeType.get(extentionFichier);

				if (typeMime == null) {
					typeMime = "application/octet-stream";
				}

				target.setContentType(typeMime);

				ServletOutputStream outs = target.getOutputStream();
				File fichier = new File(cheminFichier);

				InputStream in = new FileInputStream(fichier); 
				
				int bit = 256;
				int i = 0;
				try {
					while (bit >= 0) {
						bit = in.read();
						if ((bit >= 0) && (bit <= 128)) {
							outs.write(bit);
						} else
							outs.write(bit);
					}
				} catch (IOException ioe) {
					ioe.printStackTrace(System.out);
				}

				outs.flush();
				outs.close();
				in.close();
			}
		} catch (Exception e) {
			throw new Exception("Affichage du fichier non reussi : " + e.toString());
		}
	}
}
