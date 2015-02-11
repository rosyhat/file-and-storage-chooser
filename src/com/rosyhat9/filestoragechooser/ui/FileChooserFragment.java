package com.rosyhat9.filestoragechooser.ui;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.ListFragmentLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import com.rosyhat9.filestoragechooser.R;
import com.rosyhat9.filestoragechooser.Constants;
import com.rosyhat9.filestoragechooser.FileInfo;
import com.rosyhat9.filestoragechooser.adapters.FileArrayAdapter;

/**
 * https://github.com/ingyesid/simple-file-chooser
 * @author Yesid Lázaro Mayoriano <lazaro.yesid@gmail.com>
 * Modified by rosyhat9 <rosyhat9 at gmail>. 
 * 
 */
public class FileChooserFragment extends ListFragment {
	
	private File currentFolder;
	private FileArrayAdapter fileArrayListAdapter;
	private FileFilter fileFilter;
	private File fileSelected;
	private ArrayList<String> extensions;
	
	// It is a workaround for some checks with "root" folder from current Storage (SDcard0, SDcard1 etc.).
	// It have to be set from Activity. 
	public File currentStorageFolder;
	
	/**
	 * Set custom container view for ListFragment.
	 * More info here:
	 * http://stackoverflow.com/questions/11770773/listfragment-layout-from-xml
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
				
	    View view = inflater.inflate(R.layout.choose_file_fragment, container, false);
	    ListFragmentLayout.setupIds(view);
	    return view;
	}
	
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
	    super.onActivityCreated(savedInstanceState);

		Bundle extras = getActivity().getIntent().getExtras();
		if (extras != null) {
			if (extras
					.getStringArrayList(Constants.KEY_FILTER_FILES_EXTENSIONS) != null) {
				extensions = extras
						.getStringArrayList(Constants.KEY_FILTER_FILES_EXTENSIONS);
				fileFilter = new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						return ((pathname.isDirectory()) || (pathname.getName()
								.contains(".") ? extensions.contains(pathname
								.getName().substring(
										pathname.getName().lastIndexOf(".")))
								: false));
					}
				};
			}
		}
		
		
	}
	
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			
			/* TODO: Check this code.
			** I don't think this code works.
			** By the way I don't sure that clicked backButton which leads to 
			** getting previous/parent directory is good idea. */
			if ((!currentFolder.getName().equals( 
					currentStorageFolder.getName()))
					&& (currentFolder.getParentFile() != null)) {
				currentFolder = currentFolder.getParentFile();
				fill(currentFolder);
			} else {
				Log.i("FILE CHOOSER", "canceled");
				getActivity().setResult(Activity.RESULT_CANCELED);				
				getActivity().finish();
			}
			return false;
		}
		return getActivity().onKeyDown(keyCode, event);
	}

	public void fill(File f) {
		File[] folders = null;
		if (fileFilter != null)
			folders = f.listFiles(fileFilter);
		else
			folders = f.listFiles();

		getActivity().setTitle(getString(R.string.currentDir) + ": " + f.getName());
		List<FileInfo> dirs = new ArrayList<FileInfo>();
		List<FileInfo> files = new ArrayList<FileInfo>();
		try {
			for (File file : folders) {
				if (file.isDirectory() && !file.isHidden())
					//si es un directorio en el data se ponemos la contante folder
					dirs.add(new FileInfo(file.getName(),
							Constants.FOLDER, file.getAbsolutePath(),
							true, false));
				else {
					if (!file.isHidden())
						files.add(new FileInfo(file.getName(),
								getString(R.string.fileSize) + ": "
										+ file.length(),
								file.getAbsolutePath(), false, false));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Collections.sort(dirs);
		Collections.sort(files);
		dirs.addAll(files);
		if (!f.getName().equalsIgnoreCase(
				currentStorageFolder.getName())) {
			if (f.getParentFile() != null)
			//si es un directorio padre en el data se ponemos la contante adeacuada
				dirs.add(0, new FileInfo("..",
						Constants.PARENT_FOLDER, f.getParent(),
						false, true));
		}
		fileArrayListAdapter = new FileArrayAdapter( getActivity(),
				R.layout.file_row, dirs);
		this.setListAdapter(fileArrayListAdapter);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {

		super.onListItemClick(l, v, position, id);
		FileInfo fileDescriptor = fileArrayListAdapter.getItem(position);
		if (fileDescriptor.isFolder() || fileDescriptor.isParent()) {
			currentFolder = new File(fileDescriptor.getPath());
			fill(currentFolder);
		} else {

			fileSelected = new File(fileDescriptor.getPath());
			Intent intent = new Intent();
			intent.putExtra(Constants.KEY_FILE_SELECTED,
					fileSelected.getAbsolutePath());
			getActivity().setResult(Activity.RESULT_OK, intent);
			Log.i("FILE CHOOSER", "result ok");
			getActivity().finish();
		}
	}

	
		
}