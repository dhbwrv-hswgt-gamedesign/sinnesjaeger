package de.hrw.zoo.util;

import java.io.File;

public class FileStorage extends Storage {
	
	private File mStorePath;
	
	public FileStorage(File baseDir, String name) {
		mStorePath = new File(baseDir, name);
		init();
	}
	
	@Override
	public void init() {
        if(!mStorePath.exists()) {
        	mStorePath.mkdir();
        }
	}

	@Override
	public void delete(String fileName) {
		new File(mStorePath, fileName).delete();
	}

	@Override
	public String get(String fileName) {
		return new File(mStorePath, fileName).toString();
	}

}
