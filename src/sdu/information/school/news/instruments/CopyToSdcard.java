package sdu.information.school.news.instruments;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;





import sdu.information.school.news.teacher.R;
import sdu.information.school.news.teacher.ImportCourseActivity;
import android.content.Context;
import android.os.Environment;
import android.util.Log;

public class CopyToSdcard {
	private  Context myContext;
	public	  int pro=0;
	
	public CopyToSdcard(Context context) {
		this.myContext = context;
	}

	public void check() throws IOException{
		String path= Environment.getExternalStorageDirectory().getAbsolutePath();
		
		File file = new File(path+"/kuangmo_sdu/","mycourse");
		if(file.exists()==false){
			File file1 = new File(path+"/kuangmo_sdu/");
			//判断文件夹是否存在,如果不存在则创建文件夹
			if (!file1.exists()) {
			file1.mkdir();
			}
			copyDataBase();
		}
		else{
			file.delete();
			copyDataBase();
		}
	}
	
	private void copyDataBase() throws IOException{
		String path= Environment.getExternalStorageDirectory().getAbsolutePath();
		 
    	//Open your local db as the input stream
    	InputStream myInput1 = myContext.getResources().openRawResource(R.raw.mycourse001);
    	InputStream myInput2 = myContext.getResources().openRawResource(R.raw.mycourse002);
 
    	// Path to the just created empty db
    	String outFileName = path+"/kuangmo_sdu/"+"mycourse/";
 
    	//Open the empty db as the output stream
    	OutputStream myOutput = new FileOutputStream(outFileName);
    	ImportCourseActivity.progressbar3.setMax(100);
    	
    	for(int iii=1;iii<=2;iii++){
    		if(iii==1){
    			byte[] buffer = new byte[1024];
    	        int length;
    	        while ((length = myInput1.read(buffer))>0){
    	        	myOutput.write(buffer, 0, length);
    	        }
    		}
    		if(iii==2){
    			byte[] buffer = new byte[1024];
    	        int length;
    	        while ((length = myInput2.read(buffer))>0){
    	        	myOutput.write(buffer, 0, length);
    	        }
    		}
    		pro=pro+50;
    		ImportCourseActivity.progressbar3.setProgress(pro);
    	}
 
    	//transfer bytes from the inputfile to the outputfile
    	/*
    	byte[] buffer = new byte[1024];
    	int length;
    	int leng = myInput.available();
    	while ((length = myInput.read(buffer))>0){
    		myOutput.write(buffer, 0, length);
    		pro = leng - length;
    		ImportCourseActivity.progressbar3.setProgress(pro);
    	}
 	    */
    	
    	
    	//Close the streams
    	myOutput.flush();
    	myOutput.close();
    	myInput1.close();
    	myInput2.close();
 
    }
}
