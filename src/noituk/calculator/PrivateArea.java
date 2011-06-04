package noituk.calculator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class PrivateArea extends ListActivity {
	private List<String> items = null;
	private String dir = "";
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.private_area);
        
        //this.dir = Environment.getExternalStorageDirectory().getPath()+"/PrivateCalc/";
        this.dir = Controller.getInstance().getDataDir();
        
        
        File f = new File(this.dir);
        if(f.isDirectory() && f.exists() && f.canWrite()) {
        	getFiles(new File(this.dir).listFiles());
        }else{
        	f.mkdir();
        	Toast toast = Toast.makeText(getApplicationContext(), "No se ha podido acceder al directorio:"+this.dir+". Intentelo de nuevo.", Toast.LENGTH_LONG);
			toast.show();
        }
    }
	
	
	
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.private_area_menu, menu);
        return true;
    }

	
    
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.goBrowser:
        	Intent webBrowser = new Intent(getApplicationContext(), PrivateWebBrowser.class);
			startActivity(webBrowser);
			return true;
        case R.id.goImport:
        	Intent importFiles = new Intent(getApplicationContext(), ImportFile.class);
			startActivity(importFiles);
			return true;
        case R.id.goSettings:
        	Intent preferences = new Intent(getApplicationContext(), Preferences.class);
			startActivity(preferences);
        	return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
	
	
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id){
        int selectedRow = (int)id;
        /*if(selectedRow == 0){
            getFiles(new File(getApplicationContext().getFilesDir 
            		().getAbsolutePath()).listFiles());
        }else{*/
            File file = new File(items.get(selectedRow));
            if(file.isDirectory()){
                getFiles(file.listFiles());
            }else{
            	this.openFile(file);
            }
        //}
    }
    private void getFiles(File[] files){
        items = new ArrayList<String>();
        for(File file : files){
            items.add(file.getPath());
        }
        setListAdapter(new ArrayAdapter<String>(this, R.layout.file_list_row, R.id.text1, items));
    }
	
    
    private void openFile(File file){
    	Controller.getInstance().setCurrentFile(file);
    	
    	Intent vpf = new Intent(getApplicationContext(), ViewPrivateFile.class);
		startActivity(vpf);
    }
    

	@Override
	protected void onResume() {
		super.onResume();
		getFiles(new File(this.dir).listFiles());
	}




	@Override
	protected void onPause() {
		super.onPause();
		Controller.getInstance().encryptDataDir();
	}
    
	@Override
	protected void onStop() {
		super.onStop();
		Controller.getInstance().encryptDataDir();
	}
    
	@Override
	protected void onDestroy() {
		super.onDestroy();
		Controller.getInstance().encryptDataDir();
	}    
}
