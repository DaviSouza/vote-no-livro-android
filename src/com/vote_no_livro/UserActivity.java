package com.vote_no_livro;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.vote_no_livro.model.Usuar;
import com.vote_no_livro.utils.HTTPUtils;
import com.vote_no_livro.utils.Tools;

import flexjson.JSONSerializer;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class UserActivity extends Activity {

	EditText name_user;
	EditText email_user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cad_user);
	}

	public void save(View v) {
		try {
			EditText name_user = (EditText) findViewById(R.id.name_user);
			EditText email_user = (EditText) findViewById(R.id.email_user);
			Usuar usuar = new Usuar(name_user.getText().toString(), email_user
					.getText().toString());

			if (usuar.getNmUsuar() == "" || usuar.getEmUsuar() == "") {
				Tools.popUp(this, "Any field can be empty.");
			} else {
				String json = new JSONSerializer().serialize(usuar)
						+ "SEP"
						+ (String) getIntent()
								.getSerializableExtra("listVoted");
				HTTPUtils.POST("http://10.0.2.2:8080/votows/finalizar", json);
			}
		} catch (Exception e) {
			Logger.getLogger(HTTPUtils.class.getName()).log(Level.SEVERE, null,e);
		}
	}

}
