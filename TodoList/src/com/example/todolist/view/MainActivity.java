package com.example.todolist.view;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.SpannableString;
import android.text.style.StrikethroughSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.BufferType;

import com.example.todolist.R;
import com.example.todolist.model.TodoItem;

public class MainActivity extends Activity implements View.OnKeyListener {
	private TodoListAdapter listAdapter;
	private List<TodoItem> listTodo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		listTodo = new ArrayList<TodoItem>();
		listAdapter = new TodoListAdapter(this, R.layout.list_row, listTodo);
		ListView listView = (ListView) findViewById(R.id.listaTodo);
		listView.setAdapter(listAdapter);
		EditText edit = (EditText) findViewById(R.id.title);
		edit.setOnKeyListener(this);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
	String listName = prefs.getString("pref_listname", "");
	setTitle(listName);
	}
	
	
	public boolean onOptionsItemSelected(MenuItem item){
			switch (item.getItemId()) {
			case R.id.action_settings:
				startActivity(new Intent(this, SettingsActivity.class));
				return true;
			default:
				return super.onOptionsItemSelected(item);
			}
		}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onKey(View v, int KeyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			if (KeyCode == KeyEvent.KEYCODE_DPAD_CENTER
					|| KeyCode == KeyEvent.KEYCODE_ENTER) {
				EditText edit = (EditText) findViewById(R.id.title);
				if (edit.getText().length() > 0) {
					TodoItem item = new TodoItem();
					item.setText(edit.getText().toString());
					listTodo.add(item);
					listAdapter.notifyDataSetChanged();
					edit.setText("");
				}
				return true;
			}
		}
		return false;
	}

	private class TodoListAdapter extends ArrayAdapter<TodoItem> implements
			View.OnClickListener {
		public TodoListAdapter(Context context, int textViewResourceId,
				List<TodoItem> objects) {
			super(context, textViewResourceId, objects);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			TodoItem item = (TodoItem) v.getTag();
			if(item != null) {
				if (v.getId() == R.id.done) {
					if (item.getDone()== 0 ){
						item.setDone(1);
					} else
						item.setDone(0);
					
				} else if (v.getId() == R.id.delete){
					listTodo.remove(item);
				}
			}
			listAdapter.notifyDataSetChanged();
		}

		@Override
		// lincar o item com a tela
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				convertView = inflater.inflate(R.layout.list_row, null);

			}
			TodoItem todoItem = getItem(position);
			CheckBox done = (CheckBox) convertView.findViewById(R.id.done);
			done.setOnClickListener(this);
			done.setTag(todoItem);
			TextView text = (TextView) convertView.findViewById(R.id.text);
			text.setOnClickListener(this);
			text.setTag(todoItem);

			ImageView delete = (ImageView) convertView.findViewById(R.id.delete);
			delete.setOnClickListener(this);
			delete.setTag(todoItem);

			done.setChecked(todoItem.getDone() == 1);
			if(todoItem.getDone() == 1) {
				SpannableString span = new SpannableString(todoItem.getText());
				span.setSpan(new StrikethroughSpan(), 0, todoItem.getText().length(), 0);
				text.setText(span,BufferType.SPANNABLE);
			} else {
				text.setText(todoItem.getText());
			}
			/*
			 * if(todoItem.getDone() == 1) { done }
			 */
			return convertView;
		}

	}
}