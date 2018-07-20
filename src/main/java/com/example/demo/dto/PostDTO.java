/**
 * 
 */
package com.example.demo.dto;

import org.springframework.web.multipart.MultipartFile;

import com.afrozaar.wordpress.wpapi.v2.model.Content;
import com.afrozaar.wordpress.wpapi.v2.model.Post;
import com.afrozaar.wordpress.wpapi.v2.model.Title;

/**
 * @author zentere
 *
 */
public class PostDTO {

	private Long author;
	private Content content;
	private String status;
	private String modified;
	private Long featuredMedia;
	private Boolean sticky;
	private String password;
	private String format;
	private String link;
	private String pingStatus;
	private String modifiedGmt;
	private Long id;
	private Title title;
	private String commentStatus;
	private String type;
	private String slug;
	private String date;
	private String dateGmt;
	private MultipartFile image;

	public PostDTO() {
		
	}

	/**
	 * @return the author
	 */
	public Long getAuthor() {
		return author;
	}

	/**
	 * @param author
	 *            the author to set
	 */
	public void setAuthor(Long author) {
		this.author = author;
	}

	/**
	 * @return the content
	 */
	public Content getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(Content content) {
		this.content = content;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the modified
	 */
	public String getModified() {
		return modified;
	}

	/**
	 * @param modified
	 *            the modified to set
	 */
	public void setModified(String modified) {
		this.modified = modified;
	}

	/**
	 * @return the featuredMedia
	 */
	public Long getFeaturedMedia() {
		return featuredMedia;
	}

	/**
	 * @param featuredMedia
	 *            the featuredMedia to set
	 */
	public void setFeaturedMedia(Long featuredMedia) {
		this.featuredMedia = featuredMedia;
	}

	/**
	 * @return the sticky
	 */
	public Boolean getSticky() {
		return sticky;
	}

	/**
	 * @param sticky
	 *            the sticky to set
	 */
	public void setSticky(Boolean sticky) {
		this.sticky = sticky;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/**
	 * @param format
	 *            the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	/**
	 * @return the link
	 */
	public String getLink() {
		return link;
	}

	/**
	 * @param link
	 *            the link to set
	 */
	public void setLink(String link) {
		this.link = link;
	}

	/**
	 * @return the pingStatus
	 */
	public String getPingStatus() {
		return pingStatus;
	}

	/**
	 * @param pingStatus
	 *            the pingStatus to set
	 */
	public void setPingStatus(String pingStatus) {
		this.pingStatus = pingStatus;
	}

	/**
	 * @return the modifiedGmt
	 */
	public String getModifiedGmt() {
		return modifiedGmt;
	}

	/**
	 * @param modifiedGmt
	 *            the modifiedGmt to set
	 */
	public void setModifiedGmt(String modifiedGmt) {
		this.modifiedGmt = modifiedGmt;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the title
	 */
	public Title getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(Title title) {
		this.title = title;
	}

	/**
	 * @return the commentStatus
	 */
	public String getCommentStatus() {
		return commentStatus;
	}

	/**
	 * @param commentStatus
	 *            the commentStatus to set
	 */
	public void setCommentStatus(String commentStatus) {
		this.commentStatus = commentStatus;
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
	 * @return the slug
	 */
	public String getSlug() {
		return slug;
	}

	/**
	 * @param slug
	 *            the slug to set
	 */
	public void setSlug(String slug) {
		this.slug = slug;
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
	 * @return the dateGmt
	 */
	public String getDateGmt() {
		return dateGmt;
	}

	/**
	 * @param dateGmt
	 *            the dateGmt to set
	 */
	public void setDateGmt(String dateGmt) {
		this.dateGmt = dateGmt;
	}

	/**
	 * @return the image
	 */
	public MultipartFile getImage() {
		return image;
	}

	/**
	 * @param image
	 *            the image to set
	 */
	public void setImage(MultipartFile image) {
		this.image = image;
	}

}
