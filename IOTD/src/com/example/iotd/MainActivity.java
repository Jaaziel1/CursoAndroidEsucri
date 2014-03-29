package com.example.iotd;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		displayData(null);
		RssService service = new RssService(this);
		service.execute(new RssParser());
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void displayData(RssItem item) {
		TextView titulo = (TextView) findViewById(R.id.titulo);
		TextView data = (TextView) findViewById(R.id.data);
		TextView descricao = (TextView) findViewById(R.id.descricao);
		ImageView imagem = (ImageView) findViewById(R.id.imagem);
		if(item != null) {
			titulo.setText(item.getTitulo());
			data.setText(item.getData());
			descricao.setText(item.getDescricao());
			imagem.setImageBitmap(item.getImagem());
		} else {
			titulo.setText(null);
			data.setText(null);
			descricao.setText(null);
			imagem.setImageBitmap(null);
		}
			
	}
}
