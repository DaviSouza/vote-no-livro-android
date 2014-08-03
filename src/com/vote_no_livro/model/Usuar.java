package com.vote_no_livro.model;

public class Usuar {
	private Integer cdUsuar;
	private String nmUsuar;
	private String emUsuar;
	
	public Usuar() {
		super();
	}
	
	public Usuar(String nmUsuar, String emUsuar) {
		super();
		this.nmUsuar = nmUsuar;
		this.emUsuar = emUsuar;
	}

	public Usuar(Integer cdUsuar, String nmUsuar, String emUsuar) {
		super();
		this.cdUsuar = cdUsuar;
		this.nmUsuar = nmUsuar;
		this.emUsuar = emUsuar;
	}

	public Integer getCdUsuar() {
		return cdUsuar;
	}

	public void setCdUsuar(Integer cdUsuar) {
		this.cdUsuar = cdUsuar;
	}

	public String getNmUsuar() {
		return nmUsuar;
	}

	public void setNmUsuar(String nmUsuar) {
		this.nmUsuar = nmUsuar;
	}

	public String getEmUsuar() {
		return emUsuar;
	}

	public void setEmUsuar(String emUsuar) {
		this.emUsuar = emUsuar;
	}

}
