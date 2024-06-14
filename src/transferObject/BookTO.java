package transferObject;

public class BookTO {
	String bookID;
	String title;
	String author;
	String date;
	public BookTO() {
		super();
		this.bookID = "";
		this.title = "";
		this.author = "";
		this.date = "";
	}
	public BookTO(String bookID, String title, String author, String date) {
		super();
		this.bookID = bookID;
		this.title = title;
		this.author = author;
		this.date = date;
	}
	public String getBookID() {
		return bookID;
	}
	public void setBookID(String bookID) {
		this.bookID = bookID;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	
}
