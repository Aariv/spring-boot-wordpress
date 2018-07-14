/**
 * 
 */
package com.example.demo.domain;

/**
 * @author zentere
 *
 */
public class WordPress {

	private Integer id;

	private String type;

	private String date;

	private Guid guid;

	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @return the guid
	 */
	public Guid getGuid() {
		return guid;
	}

	/**
	 * @param guid
	 *            the guid to set
	 */
	public void setGuid(Guid guid) {
		this.guid = guid;
	}

}

class Guid {
	private String rendered;

	/**
	 * @return the rendered
	 */
	public String getRendered() {
		return rendered;
	}

	/**
	 * @param rendered
	 *            the rendered to set
	 */
	public void setRendered(String rendered) {
		this.rendered = rendered;
	}

}
