package edu.unsw.comp9321.assign2;

public class AuctionBean {
	
	private int id;
	private String username;
	private String startTime;
	private int auctionLength;
	private String status;	
	private String title;
	private String category;
	private String pictureUrl;
	private String description;
	private String postageDetails;
	private int startingPrice;
	private int reservePrice;	
	private int biddingIncrement;
	
	
	public AuctionBean(){
		
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getStartTime() {
		return startTime;
	}


	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}


	public int getAuctionLength() {
		return auctionLength;
	}


	public void setAuctionLength(int auctionLength) {
		this.auctionLength = auctionLength;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getCategory() {
		return category;
	}


	public void setCategory(String category) {
		this.category = category;
	}


	public String getPictureUrl() {
		return pictureUrl;
	}


	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public String getPostageDetails() {
		return postageDetails;
	}


	public void setPostageDetails(String postageDetails) {
		this.postageDetails = postageDetails;
	}


	public int getStartingPrice() {
		return startingPrice;
	}


	public void setStartingPrice(int startingPrice) {
		this.startingPrice = startingPrice;
	}


	public int getReservePrice() {
		return reservePrice;
	}


	public void setReservePrice(int reservePrice) {
		this.reservePrice = reservePrice;
	}


	public int getBiddingIncrement() {
		return biddingIncrement;
	}


	public void setBiddingIncrement(int biddingIncrement) {
		this.biddingIncrement = biddingIncrement;
	}
	

}
