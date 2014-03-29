package com.example.iotd;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

public class RssService extends AsyncTask<RssParser, Void, RssItem> {
	private MainActivity activity;
	private ProgressDialog progress;

	public RssService(MainActivity activity) {
		this.activity = activity;
	}

	@Override
	protected RssItem doInBackground(RssParser... params) {

		RssParser parser = params[0];
		RssItem item = null;

		try {
			SAXParserFactory spf = SAXParserFactory.newInstance();
			SAXParser sp = spf.newSAXParser();
			XMLReader xr = sp.getXMLReader();
			xr.setContentHandler(parser);
			// Estático
			/*xr.parse(new InputSource(activity.getAssets().open(
					"image_of_the_day.xml")));*/
			// Genérico de acordo com a URL passada
			URL url = new URL("http://www.nasa.gov/rss/dyn/image_of_the_day.rss");
			xr.parse(new InputSource(url.openConnection().getInputStream()));
			item = parser.getFirstItem();
			if (item != null) {
				// Estatico
				/*item.setImagem(BitmapFactory.decodeResource(
						activity.getResources(), R.drawable.image_of_the_day));*/
				// genético
				item.setImagem(getBitmap(item.getImagemUrl()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return item;
	}
	private Bitmap getBitmap(String imagemUrl) {
		Bitmap bitmap = null;
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(imagemUrl).openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			bitmap = BitmapFactory.decodeStream(input);
			input.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bitmap;
	}

	// depois do serviço
	@Override
	protected void onPostExecute(RssItem result) {
		activity.displayData(result);
		progress.dismiss();
	}
	
	// antes do serviço
	@Override
	protected void onPreExecute() {
		progress = new ProgressDialog(activity);
		progress.setIndeterminate(true);
		progress.setMessage(activity.getText(R.string.loading));
		progress.show();
	}

}
