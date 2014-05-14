package com.drank.mixology;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Environment;
import android.util.Log;

public class CameraHelper {
	public static Uri getOutputMediaFile(int type){
		File outputLocation = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Mixology");
		
		if(!outputLocation.exists()){
			if(!outputLocation.mkdirs()){
				Log.d("Camera", "Couldn't make output directory");
			}
		}
		
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		File outputFile;
		if(type == 1){
			outputFile = new File(outputLocation.getPath() + File.separator + "IMG_"+timestamp+".jpg");
		}else{
			return null;
		}
		
		return Uri.fromFile(outputFile);
	}
}
