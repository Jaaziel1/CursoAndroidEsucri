package com.example.todolist.model;


public class TodoItem {
	private Long id;
	private String texto;
	private int done;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getText() {
		return texto;
	}

	public void setText(String text) {
		this.texto = text;
	}

	public int getDone() {
		return done;
	}

	public void setDone(int done) {
		this.done = done;
	}

	
}
