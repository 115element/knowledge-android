package com.example.knowledge_android.comparator;

import java.io.File;
import java.io.Serializable;
import java.util.Comparator;

public class FileNameComparator implements Comparator, Serializable {
	private static final long serialVersionUID = 1L;
	public int compare(Object o1, Object o2) {
		String name1 = ((File) o1).getName();
		String name2 = ((File) o2).getName();
		return name1.compareTo(name2);
	}

	public boolean equals(Object obj) {
		return super.equals(obj);
	}
}