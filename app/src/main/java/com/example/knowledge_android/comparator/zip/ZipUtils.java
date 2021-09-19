package com.example.knowledge_android.comparator.zip;


import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import java.io.*;
import java.util.zip.*;

public class ZipUtils {

	static final int BUFFER = 2048;
	private byte data[] = new byte[BUFFER];

	public long zip(File source, File dest, boolean recursive)
			throws IOException {
		long checkSum =  zip(source, new FileOutputStream(dest), recursive);
		System.out.println("checkSum = " + checkSum);
		return checkSum;
	}

	public long zip(File source, OutputStream outputStream,
                    boolean recursive) throws IOException {
		// calcul checksum : Adler32 CRC32
		CheckedOutputStream checksum = new CheckedOutputStream(outputStream,
				new CRC32());
		BufferedOutputStream buff = new BufferedOutputStream(checksum);
		ZipOutputStream out = new ZipOutputStream(buff);
        out.setEncoding("GBK");  
		out.setMethod(ZipOutputStream.DEFLATED);
		out.setLevel(Deflater.BEST_COMPRESSION);
		doZip(out, source, "", recursive);
		out.close();
		buff.close();
		checksum.close();
		outputStream.close();
		return checksum.getChecksum().getValue();
	}

	protected void doZip(ZipOutputStream out, File file, String base,
                         boolean recursive) throws IOException {
		if (file.isDirectory() && recursive) {
			out.putNextEntry(new ZipEntry(base + "/"));
			// System.out.println("next entry : " + base + "/");
			base = base.length() == 0 ? "" : base + "/";
			for (File subFile : file.listFiles()) {
				doZip(out, subFile, base + subFile.getName(), recursive);
			}
		} else {
			System.out.println("Zip : " + file);
			FileInputStream fi = new FileInputStream(file);
			BufferedInputStream buff = new BufferedInputStream(fi, BUFFER);
			out.putNextEntry(new ZipEntry(base));
			// System.out.println("next entry : " + base);
			int count;
			while ((count = buff.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}
			out.closeEntry();
			buff.close();
		}
	}

	public void unZip(File source, File dest) throws IOException {
		FileInputStream sourceInputStream = new FileInputStream(source);
		unZip(sourceInputStream, dest);
		sourceInputStream.close();
	}

	public void unZip(InputStream source, File dest) {
		try {
			ZipInputStream zip = new ZipInputStream(source);
			while (true) {
				ZipEntry ze = (ZipEntry) zip.getNextEntry();
				if (ze == null)
					break;
				procsesEntry(dest, zip, ze);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void procsesEntry(File dest, ZipInputStream zip,
							  ZipEntry ze) throws IOException {
		String name = ze.getName();

		if (name.endsWith("/") || name.endsWith("\\")) {
			// directory
			return;
		}

		File file = new File(dest, name);
		file.getParentFile().mkdirs();
		FileOutputStream out = new FileOutputStream(file);

		copyStream(out, zip);
		out.close();
	}

	protected void copyStream(OutputStream out, InputStream in)
			throws IOException {
		byte[] buf = new byte[256];
		int len;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		out.flush();
	}

	public long getChecksum(File source) {
		try {
			CheckedInputStream checksum = new CheckedInputStream(
					new BufferedInputStream(new FileInputStream(source)),
					new CRC32());

			// Use skip().
			while (checksum.skip(100000) > 0) {
			}
			checksum.close();
			return checksum.getChecksum().getValue();

		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void main(String[] args) {
		ZipUtils u = new ZipUtils();
		System.out.println(u.getChecksum(new File("e:/jkl/release/src/test/release05.03.03.zip")));
//		File zipFile = new File(ZipUtils.class.getResource("/adv/").getFile()
//				+ File.separator + "center.zip");
//		File centerDir = new File(ZipUtils.class.getResource("/adv/center")
//				.getFile());
//		File centerDirBackup = new File(ZipUtils.class.getResource(
//				"/adv/center").getFile()
//				+ "backup");
//		try {
//			FileUtils.copyDirectory(centerDir, centerDirBackup);
//			ZipUtils zipUtils = new ZipUtils();
//			zipUtils.unZip(zipFile, new File(ZipUtils.class
//					.getResource("/adv/").getFile()));
//			FileUtils.deleteDirectory(centerDirBackup);
//			zipFile.delete();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
