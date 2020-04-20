package music.entities;

import java.text.NumberFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="Product")
public class Product{
	@Id
	@GeneratedValue
	@Column(name="ProductID")
	private Long productID;
	
	@Column(name="ProductCode")
	private String code;
	
	@Column(name="ProductDescription")
	private String description;
	
	@Column(name="ProductPrice")
	private double price;
	
	public Long getProductID() {
		return productID;
	}
	public void setProductID(Long productID) {
		this.productID = productID;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	
	public String getArtistName() {
		String artisName=description.substring(0, description.indexOf(" - "));
		return artisName;
	}
	public String getAlbumName() {
		String albumName=description.substring(description.indexOf(" - ")+3);
		return albumName;
	}
	public String getPriceCurrencyFormat() {
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		return currency.format(price);
	}
	public String getImageURL() {
		String image="/images/"+this.code+"/1.jpg";
		return image;
	}
	public String getProductType() {
		return "Audio CD";
	}
	public String getSoundURL() {
		String sound="/sound/"+this.code+"/1.mp3";
		return sound;
	}

}
