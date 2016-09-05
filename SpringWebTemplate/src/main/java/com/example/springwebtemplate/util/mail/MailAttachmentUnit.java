package com.example.springwebtemplate.util.mail;

import java.io.IOException;
import java.io.InputStream;

import javax.mail.util.ByteArrayDataSource;

public class MailAttachmentUnit {
	private String cid;
	private ByteArrayDataSource dataSource;
	private String filename;
	private String filepath;

	public MailAttachmentUnit() {
		this.cid = ContentIdGenerator.getContentId();
	}

	public MailAttachmentUnit(String filename) {
		this.cid = ContentIdGenerator.getContentId();
		this.filename = filename;
	}

	public MailAttachmentUnit(String filename, String filepath) {
		this.cid = ContentIdGenerator.getContentId();
		this.filename = filename;
		this.filepath = filepath;
	}

	public MailAttachmentUnit(InputStream is, String filename) {
		this.cid = ContentIdGenerator.getContentId();
		this.filename = filename;
		if (is != null) {
			try {
				this.dataSource = new ByteArrayDataSource(is, "text/plain");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public MailAttachmentUnit(byte[] dataContent, String filename) {
		this.cid = ContentIdGenerator.getContentId();
		this.filename = filename;
		if (dataContent != null && dataContent.length > 0) {
			try {
				this.dataSource = new ByteArrayDataSource(dataContent,
						"text/plain");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public ByteArrayDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(ByteArrayDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
}
