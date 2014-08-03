package com.vote_no_livro.utils;

import android.app.AlertDialog;
import android.content.Context;

public class Tools {
	public static void popUp(Context context, String msg) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(msg);
		builder.setNegativeButton("Ok", null);
		builder.create().show();
	}
}
