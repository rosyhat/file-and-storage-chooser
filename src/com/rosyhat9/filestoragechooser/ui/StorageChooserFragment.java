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

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.ListFragmentLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.rosyhat9.filestoragechooser.R;
import com.rosyhat9.filestoragechooser.StorageUtils;
import com.rosyhat9.filestoragechooser.StorageUtils.StorageInfo;
import com.rosyhat9.filestoragechooser.adapters.StorageArrayAdapter;

/**
 * Fragment which contains ListView with external storages (SDCards).
 * It has overloaded layout for ListFragment. 
 * 
 * @author rosyhat9 (mail: rosyhat9 at google) 
 *
 */
public class StorageChooserFragment extends ListFragment {
	
	private OnStorageChangeListener onStorageChangeListener;
	private StorageArrayAdapter aaStorages;
	
	public interface OnStorageChangeListener {
		public void onStorageChange(String path);
	}
	
	
	/**
	 * Set custom container view for ListFragment.
	 * More info here:
	 * http://stackoverflow.com/questions/11770773/listfragment-layout-from-xml
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
	    View view = inflater.inflate(R.layout.choose_storage_fragment, container, false);
	    ListFragmentLayout.setupIds(view);
	    return view;
	}
	
	
	/**
	 * Fill left navigation menu with storages.
	 * Firstly, get those storages with special function getStorageList.
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);
	  	    
		List<StorageInfo> storages = StorageUtils.getStorageList();
		if ( storages.isEmpty() ) {
			// Device has no any storages.
			// In that case let's try to use app's data folder.
			String path = getActivity().getFilesDir().getAbsolutePath();
			StorageInfo s = new StorageInfo(path);
			storages.add( s );
			
		} else {
			// Exists at least one SD storage.
			for( StorageInfo storage : storages ) {
				Log.d( "Storage found: ", storage.path );
			}
		}
		
		// Send message to FileChooserFragment through Activity.
		// Is shows content of this Storage at FileChooserFragment.
		onStorageChangeListener.onStorageChange( storages.get(0).path );
		
		// Adapter for the right menu of Storages.
		aaStorages = new StorageArrayAdapter( getActivity(), R.layout.storage_row, storages);
		this.setListAdapter(aaStorages);
		
	}
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			onStorageChangeListener = (OnStorageChangeListener) activity;
		} catch (ClassCastException e) {
			throw new  ClassCastException(activity.toString() +
					" must implement OnStorageChange");
		}
		
	}
	
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		StorageInfo s = aaStorages.getItem(position);

		onStorageChangeListener.onStorageChange( s.path );
	}
	
}

