package com.techstorm.karaokehug.entities;

public class SaveEntity {

	private String media;
	private String language;
	public SaveEntity() {
	}
	public SaveEntity(
		String media,
		String language
		) {
		this.setMedia(media);
		this.setLanguage(language);
	}
	public String getMedia() {
		return media;
	}
	public void setMedia(String media) {
		this.media = media;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}

	

}
