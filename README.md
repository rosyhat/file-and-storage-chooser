file-and-storage-chooser
===================

Library for choosing file from any SDcard at android 2.3+ 


HOW TO USE
-------------------
-- Add the library reference to your project --


<code> public class MainActivity extends Activity {

	final int FILE_CHOOSER=1; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = new Intent(MainActivity.this, FileChooserActivity.class);
		startActivityForResult(intent, FILE_CHOOSER);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    if ((requestCode == FILE_CHOOSER) && (resultCode == RESULT_OK)) {
	        String fileSelected = data.getStringExtra(Constants.KEY_FILE_SELECTED);
	        Toast.makeText(this, "file selected "+fileSelected, Toast.LENGTH_SHORT).show();
	    }                   
	}

}

</code> 


-- Declare FileChooserActivity in your AndroidManifest.xml --

```xml
<activity
	android:label="@string/app_name"
	android:name="com.rosyhat9.filestoragechooser.ui.FileChooserActivity" >    
</activity>
```
