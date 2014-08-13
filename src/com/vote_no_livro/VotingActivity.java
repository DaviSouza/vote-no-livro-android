package com.vote_no_livro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import votows.model.Livro;

import com.vote_no_livro.utils.HTTPUtils;
import com.vote_no_livro.utils.Tools;

import flexjson.JSONDeserializer;
import flexjson.JSONSerializer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VotingActivity extends Activity {

	private List<Livro> listBook;
	private List<Integer> listVoted = new ArrayList<Integer>();
	private final String URI = "http://voting-dsouza7.rhcloud.com/img/"; // http://voting-dsouza7.rhcloud.com/
	private TextView textView1;
	private TextView textView2;
	private TextView descBook1;
	private TextView descBook2;
	private ImageView imageBook1;
	private ImageView imageBook2;
	private Bitmap bmp, bmp2;

	private Integer total;// "http://10.0.2.2:8080/votows/"
	private String json;
	private Connection connection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		try {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_voting);
			setComponents();
		} catch (Exception e) {
			Logger.getLogger(VotingActivity.class.getName()).log(Level.SEVERE,
					null, e);
		}
	}

	private void setComponents() {
		textView1 = (TextView) findViewById(R.id.nameBook1);
		textView2 = (TextView) findViewById(R.id.nameBook2);
		descBook1 = (TextView) findViewById(R.id.descBook1);
		descBook2 = (TextView) findViewById(R.id.descBook2);
		imageBook1 = (ImageView) findViewById(R.id.imageBook1);
		imageBook2 = (ImageView) findViewById(R.id.imageBook2);
	}

	@Override
	protected void onStart() {
		connection = new Connection();
		connection.execute();
		super.onStart();
	}

	private class Connection extends AsyncTask<Object, Object, Object> {

		@Override
		protected Object doInBackground(Object... arg0) {
			
			if (arg0.length == 0) {
				String URL = "http://voting-dsouza7.rhcloud.com/votows/";
				json = HTTPUtils.GET(URL + "todosLivros");
				String totalVotados = HTTPUtils.GET(URL + "totalVotados");
				total = Integer.valueOf(totalVotados);// http://voting-dsouza7.rhcloud.com/

				listBook = new JSONDeserializer<List<Livro>>()
						.deserialize(json);
				Collections.shuffle(listBook);
			}
			bmp = BitmapFactory.decodeStream(HTTPUtils.getInputStream(URI
					+ listBook.get(0).getNlLivro()));
			bmp2 = BitmapFactory.decodeStream(HTTPUtils.getInputStream(URI
					+ listBook.get(1).getNlLivro()));
			
			connection.cancel(true);
			
			return null;
		}

		@Override
		protected void onCancelled() {
			// TODO Auto-generated method stub
			fillFieldsView();
			try {
				connection.finalize();
			} catch (Throwable e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

//		@Override
//		protected void onPreExecute() {
//			// TODO Auto-generated method stub
//			super.onPreExecute();
//			
//		}

//		connection.cancel(true);
//		fillFieldsView();
	}

	public void fillFieldsView() {
		textView1.setText(listBook.get(0).getNmLivro());
		textView2.setText(listBook.get(1).getNmLivro());

		imageBook1.setImageBitmap(bmp);
		imageBook1.setTag(listBook.get(0));

		imageBook2.setImageBitmap(bmp2);
		imageBook2.setTag(listBook.get(1));

		descBook1.setText(getResources().getString(
				R.string.label_book_description)
				+ " " + listBook.get(0).getNmLivro());
		descBook2.setText(getResources().getString(
				R.string.label_book_description)
				+ " " + listBook.get(1).getNmLivro());
	}

	public void showDescrpition1(View v) {
		buildDialogDescr(listBook.get(0).getNmLivro(), listBook.get(0)
				.getTxLivro());
	}

	public void showDescrpition2(View v) {
		buildDialogDescr(listBook.get(1).getNmLivro(), listBook.get(1)
				.getTxLivro());
	}

	public void buildDialogDescr(String name, String text) {
		AlertDialog description;
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		
		builder.setMessage(text).setTitle(name);
		builder.setPositiveButton(R.string.label_close,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int id) {
						// User clicked OK button
						dialog.dismiss();
					}
				});
		
		description = builder.create();
		description.show();
	}

	public void toVote(View v) {
		Livro book = null;

		switch (v.getId()) {
		case R.id.imageBook1:
			book = (Livro) imageBook1.getTag();
			listVoted.add(book.getCdLivro());
			break;
		case R.id.imageBook2:
			book = (Livro) imageBook2.getTag();
			listVoted.add(book.getCdLivro());
			break;
		}

		listBook.remove(book);
		Collections.shuffle(listBook);

		if (listBook.size() == 1) {
			finalizeVote(v);
		}
		connection = new Connection();
		connection.execute(1);
		//fillFieldsView();
		
	}

	public void finalizeVote(View v) {

		if (listVoted.isEmpty()) {
			Tools.popUp(this, "You should vote in any book.");
		} else {
			Intent it = new Intent(this, UserActivity.class);
			it.putExtra("listVoted", new JSONSerializer().serialize(listVoted));
			startActivity(it);
		}
	}

}
