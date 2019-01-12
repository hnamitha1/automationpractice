package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class WishlistPage extends BasePage {
	public void create(String wishlistName) {
		getWait().type(newWishlistNameField, wishlistName);
		getWait().click(submitNewWishlistButton);
	}

	@FindBy(className = "page-heading")
	private WebElement myWhishlistHeader;

	@FindBy(id = "name")
	private WebElement newWishlistNameField;

	@FindBy(id = "submitWishlist")
	private WebElement submitNewWishlistButton;

	@FindBy(className = "table")
	private WebElement wishlistTable;


	public WishlistPage() {
		super("index.php?fc=module&module=blockwishlist&controller=mywishlist");
	}

	@Override
	protected void isLoaded() throws Error {
		getWait().isElementDisplayed(myWhishlistHeader);
	}

	public boolean isWishlistPresent(String wishlistName) {
		getWait().isElementDisplayed(wishlistTable);
		return getCell(wishlistName, Column.NAME) != null;
	}

	public boolean deletelWishlist(String wishlistName) {
		getWait().isElementDisplayed(wishlistTable);
		WebElement wishlistDeleteButton = getCell(wishlistName, Column.DELETE);
		wishlistDeleteButton.findElement(By.className("icon-remove")).click();
		Alert popup = getWait().switchToAlert();
		popup.accept();
		return getWait().isElementNotDisplayed(wishlistDeleteButton);
	}

	private WebElement getCell(String wishlistName, Column columnName) {
		List<WebElement> allRows = wishlistTable.findElements(By.xpath("//*[contains(@class,\"table\")]/tbody/tr[*]/td[1]"));
		int rowNumberCounter = 1;
		for (WebElement row : allRows) {
			if (row.getText().equals(wishlistName)) {
				return wishlistTable.findElement(By.xpath("//*[contains(@class,\"table\")]/tbody/tr[" + rowNumberCounter + "]/td[" + columnName.columnNumber + "]"));
			} else {
				rowNumberCounter++;
			}
		}
		throw new NoSuchElementException("Unable to locate {" + wishlistName + "} wishlist");
	}


	enum Column {
		NAME(1),
		QTY(2),
		VIEWED(3),
		CREATED(4),
		DIRECTLINK(5),
		DELETE(6);

		private final int columnNumber;

		Column(int columnNumber) {
			this.columnNumber = columnNumber;
		}
	}


}
