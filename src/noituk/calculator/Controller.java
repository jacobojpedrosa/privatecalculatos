package noituk.calculator;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import noituk.calculator.Main.ViewCalc;
import android.app.Application;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;


public class Controller {
	private AppView view = null;
	private Calculator calculator = null;
	private Application application=null;
	SharedPreferences preferences = null;
	private File currentFile = null;
	private List<String> validExtensions = Arrays.asList("png", "jpg", "jpeg", "txt");
	
	
	private static Controller INSTANCE = null;
	
	private Controller (){}
	
	public void init(Application app){
		application = app;
		
		this.preferences = PreferenceManager.getDefaultSharedPreferences(this.application.getApplicationContext());
		
        //File f = new File(Environment.getExternalStorageDirectory().getPath()+"/PrivateCalc/");
		File f = new File(this.getDataDir());
		
        if(!f.isDirectory() || !f.exists()) {
        	f.mkdir();
        }
	}
	
	public static Controller getInstance(){
		if(INSTANCE==null)INSTANCE = new Controller();
		return INSTANCE;
	}

	private AppView getView() {
		return view;
	}

	public void setView(AppView view) {
		this.view=view;
	}

	public void memoryAction(char c) {
		this.calculator.memoryAction(c);
		
	}

	
	
	
	public void reset() {
		this.calculator = new Calculator();
		((ViewCalc)this.view).displayNum(this.calculator.getCurrent());
	}

	public void setOperation(char c) {
		if(c=='s'){
			if(!this.calculator.getCurrent().substring(0, 1).equals("-"))this.calculator.setCurrent("-"+this.calculator.getCurrent());
			else this.calculator.setCurrent(this.calculator.getCurrent().substring(1));
		}else if(c=='r'){
			this.calculator.solve();
		}else{
			calculator.setOperation(c);
		}
		((ViewCalc)this.view).displayNum(this.calculator.getCurrent());
	}

	public void fixNumber(char c) {
		calculator.addNumToCurrent(c);
		((ViewCalc)this.view).displayNum(this.calculator.getCurrent());
		/*if(this.calculator.getCurrent().equals(this.preferences.getString("appPassword", ".1234."))){
			Intent privateArea = new Intent(application, PrivateArea.class);
			application.startActivity(privateArea);
			
			//Intent privateArea = new Intent(application.getApplicationContext(), PrivateArea.class);
			//application.getApplicationContext().startActivity(privateArea);
			//startActivity(privateArea);
			//((ViewCalc)this.view).displayNum("privado");
		}*/
	}

	public void startCalculator() {
		this.calculator = new Calculator();
	}

	public boolean isPasswordOk() {
		return (this.calculator.getCurrent().equals(this.preferences.getString("appPassword", ".1234.")));
	}

	public File getCurrentFile() {
		return currentFile;
	}

	public void setCurrentFile(File currentFile) {
		this.currentFile = currentFile;
	}

	public List<String> getValidExtensions() {
		return validExtensions;
	}

	public String getDataDir() {
		String pathEncrypt = Environment.getExternalStorageDirectory().getPath()+this.preferences.getString("dataPath", "/data.dbl");
		String pathDir  = pathEncrypt.substring(0,pathEncrypt.lastIndexOf(".dbl"))+"/";
		
		File encrypt = new File(pathEncrypt);
		
		if(encrypt.exists()){
			encrypt.renameTo(new File(pathDir));
		}
		//String s = Environment.getExternalStorageDirectory().getPath()+this.preferences.getString("dataPath", "/data/");
		return pathDir;
		//return Environment.getExternalStorageDirectory().getPath()+"/PrivateCalc/";
	}

	public void encryptDataDir() {
		String pathEncrypt = Environment.getExternalStorageDirectory().getPath()+this.preferences.getString("dataPath", "/data.dbl");
		String pathDir  = pathEncrypt.substring(0,pathEncrypt.lastIndexOf(".dbl"))+"/";
		
		File dir = new File(pathDir);
		if(dir.exists()){
			dir.renameTo(new File(pathDir));
		}
	}

	
	
}
