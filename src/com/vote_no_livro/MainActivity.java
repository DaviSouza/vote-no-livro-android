package com.vote_no_livro;

import java.util.logging.Level;
import java.util.logging.Logger;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends Activity {
//	private TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
//			textView1 = (TextView) findViewById(R.id.textView1);
//			textView1.setText(HTTPUtils.GET("http://10.0.2.2:8080/votows/totalVotados"));//http://voting-dsouza7.rhcloud.com/
		} catch (Exception e) {
			Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE,null, e);
		}
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
		try {
			Intent it = new Intent(this, VotingActivity.class);
			startActivity(it);
		} catch (Exception e) {
			Logger.getLogger(MainActivity.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

}
