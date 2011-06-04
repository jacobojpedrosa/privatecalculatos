package noituk.calculator;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Toast;

public class Preferences extends PreferenceActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // load the XML preferences file
        addPreferencesFromResource(R.xml.preferences);
    }

	@Override
	public void onContentChanged() {
		super.onContentChanged();

		String key = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("appPassword", ".1234.");
		if(!(key).substring(key.length()-1, key.length()).equals(".")){
			//TODO i18n string
			Toast toast = Toast.makeText(getApplicationContext(), "Error, the password must finish in: '.'", Toast.LENGTH_LONG);
			toast.show();
		}
	}
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.private_area_menu, menu);
        return true;
    }
	
	
}
