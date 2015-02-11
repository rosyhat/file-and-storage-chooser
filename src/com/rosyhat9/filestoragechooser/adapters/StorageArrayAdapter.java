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
package com.rosyhat9.filestoragechooser.adapters;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rosyhat9.filestoragechooser.R;
import com.rosyhat9.filestoragechooser.StorageUtils.StorageInfo;

/**
 * Uses by StorageChooserFragment.
 * Nothing special here. Redraws background of active row when changed.
 * 
 * @author rosyhat9 (mail: rosyhat9 at google) 
 *
 */
public class StorageArrayAdapter extends ArrayAdapter<StorageInfo> {

	private List<StorageInfo> items;
	private LayoutInflater layoutInflater;
	
	private int selectedPosition = 0;

	public StorageArrayAdapter(Context context, int textViewResourceId,
			List<StorageInfo> objects) {
		super(context, textViewResourceId, objects);
		this.items = objects;
		this.layoutInflater = LayoutInflater.from(context);
	}

	public StorageInfo getItem(int i) {
		// Update chosen row. Redraw ListView.
		selectedPosition = i;
		this.notifyDataSetChanged();
		
		return items.get(i);
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView == null) {
			convertView = layoutInflater.inflate(R.layout.storage_row, null);
			holder = new ViewHolder();
			holder.main = (TextView) convertView.findViewById(R.id.rowDate);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		// Your row item.
		holder.main.setText( items.get(position).getDisplayName() );
		
		// Set background color for item.
		Resources myResources = holder.main.getResources();    
		if (position == selectedPosition) {
			holder.main.setBackgroundColor(myResources.getColor(R.color.storagesBackgroundChosen));
		} else {
			holder.main.setBackgroundColor(myResources.getColor(R.color.storagesBackground));		
		}
				
		
		return convertView;
	}
	
	static class ViewHolder {
		TextView main;
	}


	
}