package org.dizem.common;

import java.io.*;
import java.util.Scanner;

/**
 * User: dizem
 * Time: 11-4-23 下午1:54
 */
public class ProjectInfo {
	static int cntFile = 0;
	static int cntLine = 0;

	private static int countLine(File file) {
		int line = 0;
		Scanner cin = null;
		try {
			cin = new Scanner(file);
			while (cin.hasNextLine()) {
				if (cin.nextLine().trim().length() > 0)
					line++;
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (cin != null)
				cin.close();
			return line;
		}
	}

	private static void walk(File dir) {
		for (File file : dir.listFiles()) {
			if (file.isDirectory()) {
				walk(file);

			} else if (file.getName().endsWith("java")) {
				System.out.println(file.getAbsolutePath());
				cntFile++;
				cntLine += countLine(file);
			}
		}
	}

	public static void main(String[] args) {
		walk(new File("src"));
		System.out.println("文件总数:" + cntFile);
		System.out.println("非空行数:" + cntLine);
	}
}
