
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;

//THis is the class of the media Item
    public class MediaItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String format;
	private String title;
	private String borrowed;
	private String borrowedOn;
	Boolean isOnLoan = false;
	
	//Constructor for Media Item
	public MediaItem(String title, String format) {
		this.title = title;
		this.format = format;
	}
	
	
	
	
	//setOnLoan Method sets the the name of the borrower and the date
		// that the item was borrowed on
	public void setOnLoan(String borrower, String borrowedOn) {
			if(this.borrowed == null || this.borrowedOn == null) {
				this.borrowed = borrower;
				this.borrowedOn = borrowedOn;
			}		
		}
	
	public void setReturn(String r) {
		if(this.borrowed == null || this.borrowedOn == null) {
			JOptionPane.showMessageDialog( null, "This item was not on loan");
		}
		else {
			this.borrowed = null;
			this.borrowedOn = null;
		}
		
	}
	

	
	
	public String getTitle() {
		return title;
	}
	
	public String getFormat() {
		return format;
	}

	
	// overrides toString()
	public String toString() {
		return  "Title: " +  this.title + " " + " Format: " + this.format + " " + "Borrowed: " + this.borrowed + " " + "Borrowed Date: " + this.borrowedOn + " \n";
	}

}