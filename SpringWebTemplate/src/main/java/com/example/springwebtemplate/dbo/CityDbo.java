package com.example.springwebtemplate.dbo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.example.springwebtemplate.dbo.base.MappedDomainObjectBase;

/**
 * The Class CityDBO.
 * 
 * @author Kamil inal
 * 
 */
@Entity
@Table(name = "tbl_city")
public class CityDbo extends MappedDomainObjectBase {
	private static final long serialVersionUID = 7646199656664251195L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "city_id", nullable=false)
	protected long cityId;

	@Column(name = "name", length = 255)
	private String name;

	@Column(name = "plate")
	private String plate;

	public CityDbo() {
		// TODO Auto-generated constructor stub
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPlate() {
		return plate;
	}

	public void setPlate(String plate) {
		this.plate = plate;
	}

	public long getCityId() {
		return cityId;
	}

	public void setCityId(long cityId) {
		this.cityId = cityId;
	}
	
	@Override
	public int hashCode() {
		return Long.valueOf(cityId).hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof CityDbo) {
			return cityId == ((CityDbo) obj).getCityId();
		}
		return super.equals(obj);
	}
}
