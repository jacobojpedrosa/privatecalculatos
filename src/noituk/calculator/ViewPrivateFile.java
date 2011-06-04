package noituk.calculator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class ViewPrivateFile extends Activity {
	private AppView view;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_file);
        
        this.view = new ViewPrivateFileView();
        
        File file = Controller.getInstance().getCurrentFile();
        
        int mid= file.getAbsolutePath().lastIndexOf(".");
    	String ext=file.getAbsolutePath().substring(mid+1,file.getAbsolutePath().length());
    	
    	
    	if(ext.equalsIgnoreCase("pdf")){
    		this.openPdfFile(file);
    	}else if(ext.equalsIgnoreCase("jpg") || ext.equalsIgnoreCase("jpeg") || ext.equalsIgnoreCase("png")){
    		this.openImageFile(file);
    	}else if(ext.equalsIgnoreCase("txt")){
    		this.openTextFile(file);
    	}else{
    		Toast.makeText(getApplicationContext(),"File format ("+ext+") not suported.", Toast.LENGTH_SHORT).show();
    	}
    	
    }

	private void openPdfFile(File file){
    	Uri path = Uri.fromFile(file);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setDataAndType(path, "application/pdf");
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		try {
			startActivity(intent);
		}
		catch (ActivityNotFoundException e) {
			Toast.makeText(getApplicationContext(),"No Application Available to View PDF", Toast.LENGTH_SHORT).show();
		}
    }
	
	private void openTextFile(File file) {
		// TODO Auto-generated method stub
		StringBuilder text = new StringBuilder();

		try {
		    BufferedReader br = new BufferedReader(new FileReader(file));
		    String line;

		    while ((line = br.readLine()) != null) {
		        text.append(line);
		        text.append('\n');
		    }
		}
		catch (IOException e) {
		    //You'll need to add proper error handling here
		}
		((ViewPrivateFileView)view).showText(text);
	}

	private void openImageFile(File file) {
		((ViewPrivateFileView)view).showImage(file.getPath());
		
	}
	
	
	private class ViewPrivateFileView implements AppView{
		ImageView imageFile = null;
		TextView textFile = null; 
			
		public ViewPrivateFileView(){
			this.imageFile = (ImageView)findViewById(R.id.show_imagefile);
			this.textFile = (TextView)findViewById(R.id.show_textfile);
		} 
		
		public void showText(StringBuilder text) {
			this.textFile.setText(text);
			this.textFile.setVisibility(0);
		}

		protected void showImage(String path){
			
			File imgFile = new  File(path);
			if(imgFile.exists()){
				Bitmap bMap = BitmapFactory.decodeFile(path);
		        this.imageFile.setImageBitmap(bMap);

			} else{
				showMessage("File " + imgFile.getPath() + "not found");
			}
			this.imageFile.setVisibility(0);
		}
	
		@Override
		public void showError(Exception e) {
			Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
			toast.show();
			
		}

		@Override
		public void showMessage(String string) {
			Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_LONG);
			toast.show();
			
		}
		
	}
}
