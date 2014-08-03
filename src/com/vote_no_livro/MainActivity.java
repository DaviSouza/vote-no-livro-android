package com.vote_no_livro;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		Intent it;
		switch (item.getItemId()) {
		case R.id.menu_ranking:
			it = new Intent(this, RakingActivity.class);
			startActivity(it);
			return true;
		case R.id.menu_users:
			it = new Intent(this, UserActivity.class);
			startActivity(it);
			return true;
		case R.id.menu_voting:
			it = new Intent(this, VotingActivity.class);
			startActivity(it);
			return true;	
		
		}
		return false;
	}

	public void participate(View v) {
		Intent it = new Intent(this, VotingActivity.class);
		startActivity(it);
	}

}
