package com.techstorm.karaokehug.entities;

public class SongEntity {

	private int songId;
	private String name;
	private String lyric;
	private String author;
	private String source;
	private String quickSearch;
//	private boolean favourite;

	public SongEntity() {
		
	}
	
	public SongEntity(int songId,
		String name,
		String lyric,
		String author,
		String source,
		String quickSearch
//		short favourite
		) {

		this.songId = songId;
		this.name = name;
		this.lyric = lyric;
		this.author = author;
		this.source = source;
		this.quickSearch = quickSearch;
//		this.favourite = (favourite == 1);
	}

	public int getSongId() {
		return songId;
	}

	public void setSongId(int songId) {
		this.songId = songId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLyric() {
		return lyric;
	}

	public void setLyric(String lyric) {
		this.lyric = lyric;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getQuickSearch() {
		return quickSearch;
	}

	public void setQuickSearch(String quickSearch) {
		this.quickSearch = quickSearch;
	}

//	public boolean isFavourite() {
//		return favourite;
//	}
//
//	public void setFavourite(boolean favourite) {
//		this.favourite = favourite;
//	}
	
}
