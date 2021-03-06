package com.vote_no_livro;

import java.util.logging.Level;
import java.util.logging.Logger;

import votows.model.Usuar;

import com.vote_no_livro.utils.HTTPUtils;
import com.vote_no_livro.utils.Tools;

import flexjson.JSONSerializer;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UserActivity extends Activity {

	private Usuar usuar;
	private Connection connection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cad_user);
	}

	public void save(View v) {
		try {
			EditText name_user = (EditText) findViewById(R.id.name_user);
			EditText email_user = (EditText) findViewById(R.id.email_user);
			usuar = new Usuar(name_user.getText().toString(), email_user
					.getText().toString());

			if (usuar.getNmUsuar().equals("") || usuar.getEmUsuar().equals("")) {
				Tools.popUp(this, "Any field can be empty.");
			} else {
				connection = new Connection();
				connection.execute(v);
			}
		} catch (Exception e) {
			Logger.getLogger(HTTPUtils.class.getName()).log(Level.SEVERE, null,e);
		}
	}

	private class Connection extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... arg0) {

			String json = new JSONSerializer().serialize(usuar) + "SEP"
					+ (String) getIntent().getSerializableExtra("listVoted");
			HTTPUtils.POST("http://voting-dsouza7.rhcloud.com/votows/finalizar", json);

			connection.cancel(true);
			return arg0[0];
		}
		
		@Override
		protected void onCancelled(Object result) {
			try {
				connection.finalize();
				View v = (View) result;
				Intent it = new Intent(v.getContext(), MainActivity.class);
				startActivity(it);
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
