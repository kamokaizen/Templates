package com.example.springwebtemplate.dbo.base;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Store;

@MappedSuperclass
public class MappedDomainObjectBase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name = "deleted")
	@Field(index = Index.YES, analyze = Analyze.NO, store = Store.NO)
	protected boolean deleted = false;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "store_date")
	protected Date storeDate = new Date();

	public Date getStoreDate() {
		return storeDate;
	}

	public void setStoreDate(Date date) {
		this.storeDate = date;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	
	public String getDateString(Date date){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.US);
		df.setTimeZone(TimeZone.getTimeZone("Europe/Istanbul"));
		try{
			return df.format(date);
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
}
