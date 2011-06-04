package noituk.calculator;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ImportFile extends ListActivity implements Runnable {
	private List<String> items = null;
	private String dir = "/sdcard";
	private ProgressDialog pd;
	private File cpFile = null;
	
	

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_import);
        
        //this.dir = Environment.getExternalStorageDirectory().getPath()+"/PrivateCalc/";
        
        File f = new File(this.dir);
       	getFiles(new File(this.dir).listFiles());
    }
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        int selectedRow = (int)id;
        if(selectedRow == 0){
            getFiles(new File(getApplicationContext().getFilesDir().getAbsolutePath()).listFiles());
        }else if((items.get(selectedRow)).equals("..")){
        	//this.showMessage("hola");
        	//TODO subir un nivel
        }else{
        	//this.showMessage(items.get(selectedRow));
            File file = new File(items.get(selectedRow));
            if(file.isDirectory()){
                getFiles(file.listFiles());
            }else{
            	this.importSelectedFile(file);
            }
        }
    }
    private void importSelectedFile(File file){
		// TODO Auto-generated method stub
    	if (!file.exists())
			this.showError(new Exception("FileCopy: " + "no such source file: " + file.getName()));
    	else if (!file.isFile())
	    	this.showError(new Exception("FileCopy: " + "can't copy directory: " + file.getName()));
    	else if (!file.canRead())
	    	this.showError(new Exception("FileCopy: " + "source file is unreadable: " +  file.getName()));
    	else if(!Controller.getInstance().getValidExtensions().contains(this.getFileEstension(file)))
    		this.showError(new Exception("FileCopy: " + "file extension not suported: " +  file.getName()));
    	else {
		    	
		    this.cpFile = file;
		    //pd = ProgressDialog.show(this, "Working..", "Importing file", true, false);
		    //pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		    pd = new ProgressDialog(this);
		    pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		    pd.setMessage("Loading...");
		    pd.setCancelable(false);
		    pd.show();
	
			Thread thread = new Thread(this);
			thread.start();
    	}
	}
    

	@Override
	public void run() {
		try{
	    	/*FileReader in = new FileReader(cpFile.getPath());
		    FileWriter out = new FileWriter("/sdcard/PrivateCalc/"+cpFile.getName());
		    int c;
		    while ((c = in.read()) != -1){
		      out.write(c);
		      pd.incrementProgressBy(1);
		    }
		    in.close();
		    out.close();*/
			
			File f1 = new File(cpFile.getPath());
			File f2 = new File("/sdcard/PrivateCalc/"+cpFile.getName());
			InputStream in = new FileInputStream(f1);
			  
			//For Append the file.
			//		      OutputStream out = new FileOutputStream(f2,true);
			
			//For Overwrite the file.
			OutputStream out = new FileOutputStream(f2);
			int inc = (int)(f1.length()/1024);
			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0){
				out.write(buf, 0, len);
				pd.incrementProgressBy(inc);
			}
			in.close();
			out.close();
		    pd.dismiss();
	    }catch(Exception e){
	    	this.showError(e);
	    }
	    handler.sendEmptyMessage(0);

	}
	
	 private Handler handler = new Handler() {
         @Override
         public void handleMessage(Message msg) {
                 pd.dismiss();
                 

         }
	 };
    

	private void getFiles(File[] files){
        items = new ArrayList<String>();
        items.add(".");
        items.add("..");
        for(File file : files){
            items.add(file.getPath());
        }
        setListAdapter(new ArrayAdapter<String>(this, R.layout.file_list_row, R.id.text1, items));
    }
	
    
    public void showError(Exception e) {
		Toast toast = Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT);
		toast.show();
		
	}

	public void showMessage(String string) {
		Toast toast = Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT);
		toast.show();
		
	}
	
	
	private String getFileEstension(File file){
		int mid= file.getAbsolutePath().lastIndexOf(".");
    	String ext=file.getAbsolutePath().substring(mid+1,file.getAbsolutePath().length());
    	
    	return ext;
	}

}
