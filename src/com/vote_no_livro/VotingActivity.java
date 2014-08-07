package com.vote_no_livro;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vote_no_livro.model.Livro;
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
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class VotingActivity extends Activity {

	private List<Livro> listBook;
	private List<Integer> listVoted = new ArrayList<Integer>();
	private final String URI = "http://voting-dsouza7.rhcloud.com/img/";
	private TextView textView1 = (TextView) findViewById(R.id.nameBook1);
	private TextView textView2 = (TextView) findViewById(R.id.nameBook2);
	private TextView descBook1 = (TextView) findViewById(R.id.descBook1);
	private TextView descBook2 = (TextView) findViewById(R.id.descBook2);
	private ImageView imageBook1 = (ImageView) findViewById(R.id.imageBook1);
	private ImageView imageBook2 = (ImageView) findViewById(R.id.imageBook2);
	private Bitmap bmp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_voting);

		String json = HTTPUtils.GET("http://voting-dsouza7.rhcloud.com/votows/todosLivros");
		listBook = new JSONDeserializer<List<Livro>>().deserialize(json);
		Collections.shuffle(listBook);
		fillFieldsView();
	}

	public void fillFieldsView() {
		textView1.setText(listBook.get(0).getNmLivro());
		textView2.setText(listBook.get(1).getNmLivro());

		bmp = BitmapFactory.decodeStream(HTTPUtils.getInputStream(URI
				+ listBook.get(0).getNlLivro()));
		imageBook1.setImageBitmap(bmp);
		imageBook1.setTag(listBook.get(0));

		bmp = BitmapFactory.decodeStream(HTTPUtils.getInputStream(URI
				+ listBook.get(1).getNlLivro()));
		imageBook2.setImageBitmap(bmp);
		imageBook2.setTag(listBook.get(1));

		descBook1.setText(R.string.label_book_description + " "
				+ listBook.get(0).getNmLivro());
		descBook2.setText(R.string.label_book_description + " "
				+ listBook.get(1).getNmLivro());
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
