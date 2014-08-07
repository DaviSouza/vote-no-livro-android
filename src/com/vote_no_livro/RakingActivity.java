package com.vote_no_livro;

import java.util.List;

import com.vote_no_livro.utils.HTTPUtils;

import flexjson.JSONDeserializer;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class RakingActivity extends Activity {
	
	private final String URL = "http://voting-dsouza7.rhcloud.com/votows/";
	private List<Object[]> list;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ranking);
		
	}
	
	@Override
	protected void onStart() {
		renderList();
		super.onStart();
	}
	
	private void renderList(){
		String json = HTTPUtils.GET(URL +"maisVotados");
		list = new JSONDeserializer<List<Object[]>>().deserialize(json);
		Integer total = Integer.valueOf(HTTPUtils.GET(URL +"totalVotados"));//new VotliDaoImp().totalRegistros(Votli.class);
        Double percent = 0d;
        Object obj[];
        TextView textView;
        TableRow row;
        TableLayout table = (TableLayout)findViewById(R.id.tableLayout);
		for(int i = 0;i<list.size();i++){
			// 0 = cd_livro; 1= nome;  votos = 2
			obj = list.get(i);
			percent = (Integer.valueOf(obj[2].toString()) * 100d) / total.doubleValue();
			row = new TableRow(this);
			textView = new TextView(this);
			textView.setText(obj[1].toString());
			textView.setGravity(Gravity.RIGHT);
			row.addView(textView);
			
			row = new TableRow(this);
			textView = new TextView(this);
			textView.setText(percent.toString());
			textView.setGravity(Gravity.RIGHT);
			row.addView(textView);
			
			table.addView(row);
		}
		
	}
	
}
