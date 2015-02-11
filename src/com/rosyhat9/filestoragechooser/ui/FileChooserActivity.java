/*****
 * Copyright 2015 rosyhat9 <mail:rosyhat9 at gmail>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.rosyhat9.filestoragechooser.ui;


import java.io.File;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.rosyhat9.filestoragechooser.R;

/**
 * This activity has two columns. Right contains fragmentList of Storages
 * (internal/external SDcards, etc.). Left contains fragmentList of files and folders.   
 * Above of them can be customized with editing "choose_storage_fragment.xml" and 
 * "choose_file_fragment.xml".
 * 
 * Colors of texts and backgrounds can be easily edited at colors.xml.
 * 
 * @author rosyhat9 (mail: rosyhat9 at google) 
 *
 */
public class FileChooserActivity extends FragmentActivity  implements StorageChooserFragment.OnStorageChangeListener {


	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_choose);
		
	}

	
	@Override
	public void onStorageChange(String path) {
		FileChooserFragment fileChooserFragment = (FileChooserFragment)
		        getSupportFragmentManager().findFragmentById(R.id.FileChooserFragment);

		    if (fileChooserFragment != null) {
		    	File f = new File( path );
		    	fileChooserFragment.currentStorageFolder = f;
		    	fileChooserFragment.fill( f );
		    }
		
	}
   

}