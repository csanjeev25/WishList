package com.insomniac.wishlistmanager;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.insomniac.wishlistmanager.db.DatabaseManager;
import com.insomniac.wishlistmanager.model.WishList;


public class WishListManagerActivity extends Activity {
	ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DatabaseManager.init(this);

        ViewGroup contentView = (ViewGroup) getLayoutInflater().inflate(R.layout.main, null);
        listView = (ListView) contentView.findViewById(R.id.list_view);
        
        Button btn = (Button) contentView.findViewById(R.id.button_add);
        setupButton(btn);
        setContentView(contentView);
    }

    @Override
    protected void onStart() {
    	super.onStart();
        setupListView(listView);
    }

    private void setupListView(ListView lv) {
    	final List<WishList> wishLists = DatabaseManager.getInstance().getAllWishLists();
    	
    	List<String> titles = new ArrayList<String>();
    	for (WishList wl : wishLists) {
    		titles.add(wl.getName());
    	}

    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
    	lv.setAdapter(adapter);

    	final Activity activity = this;
    	lv.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				WishList wishList = wishLists.get(position);
				Intent intent = new Intent(activity, WishItemListActivity.class);
				intent.putExtra(Constants.keyWishListId, wishList.getId());
				startActivity(intent);
			}
		});
    }
    
    private void setupButton(Button btn) {
    	final Activity activity = this;
    	btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Intent intent = new Intent(activity,AddWishListActivity.class);
				startActivity (intent);
			}
		});
    }
}