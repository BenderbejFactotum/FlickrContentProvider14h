package com.bffmedia.hour14app;
import com.bffmedia.hour14app.R;

import android.app.ListFragment;
import android.database.Cursor;
import android.os.Bundle;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
public class PhotoListFragment extends ListFragment    {
	Cursor mPhotoCursor;
	SimpleCursorAdapter mAdapter;


	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);		
		mPhotoCursor = getActivity().managedQuery(FlickrPhotoProvider.CONTENT_URI, null, null, null, null);
		mAdapter = new SimpleCursorAdapter(getActivity(), 
				android.R.layout.simple_list_item_1, 
				mPhotoCursor, //Cursor 
				new String[] {"title"},
				new int[] { android.R.id.text1 }, 0); 
		setListAdapter(mAdapter);
	}
	

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		ListViewHolder vh = v.getTag();
		mPhotoCursor.moveToPosition(position);
		FlickrPhoto selected = FlickrPhotoDbAdapter.getPhotoFromCursor(mPhotoCursor);
		ImageViewFragment flickrImageFragment = new ImageViewFragment();
		Bundle args = new Bundle();   
		args.putString("PHOTO_ID", selected.id);
		flickrImageFragment.setArguments(args);
		
  		FragmentTransaction ft = getActivity().getFragmentManager().beginTransaction();
			ft.replace(R.id.layout_container, flickrImageFragment);
			ft.addToBackStack("Image");
			ft.commit();
		Toast.makeText(this.getActivity().getApplicationContext(), selected.title, Toast.LENGTH_SHORT).show();
	}
} 
