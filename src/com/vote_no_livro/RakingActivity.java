package com.vote_no_livro;

import java.util.ArrayList;
import java.util.List;

import com.vote_no_livro.utils.HTTPUtils;
import flexjson.JSONDeserializer;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.GridView;

public class RakingActivity extends Activity {

	private final String URL = "http://voting-dsouza7.rhcloud.com/votows/";// http://voting-dsouza7.rhcloud.com/
	private Integer total;// "http://10.0.2.2:8080/votows/"
	private String json;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
	}

	@Override
	protected void onStart() {
		new Connection().execute();
		super.onStart();
	}

	private class Connection extends AsyncTask {

		@Override
		protected Object doInBackground(Object... arg0) {
			getValuesFromURL();
			return null;
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			renderList();
		}

	}

	public void getValuesFromURL() {
		json = HTTPUtils.GET(URL + "maisVotados");
		total = Integer.valueOf(HTTPUtils.GET(URL + "totalVotados"));// http://voting-dsouza7.rhcloud.com/
	}

	private void renderList() {

		List<String> listGrid = new ArrayList<String>();
		List<Object[]> list = new JSONDeserializer<List<Object[]>>()
				.deserialize(json);
		Double percent = 0d;
		Object[] obj = list.toArray();
		List<?> aux;

		GridView gridview = (GridView) findViewById(R.id.gridview);
		for (int i = 0; i < obj.length; i++) {
			// 0 = cd_livro; 1= nome; votos = 2
			aux = (List<?>) obj[i];
			percent = (Integer.valueOf(aux.get(3).toString()) * 100d)
					/ total.doubleValue();
			listGrid.add(aux.get(1).toString());
			listGrid.add(percent.intValue()+"");
		}
		gridview.setAdapter(new ArrayAdapter<String>(this,
				R.layout.listitem_view, listGrid));

	}

}
