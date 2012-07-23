package client;

import java.util.Date;
import client.core.MapleObject;

/**
 * A <code>MapleObject</code> representing a user's account.
 * @author tomato
 * @version 1.0
 * @since alpha2
 */
public class MapleAccount extends MapleObject {
	private int accountId;
	private String accountName;
	private byte gender;
	private byte gmLevel;
	public Date creationDate;
	private boolean tradeBlock;
	private Date tradeBlockExpiration;
	private long conAuth;
	
	/**
	 * Creates a <code>MapleAccount</code> with a calculated <code>conAuth</code>.
	 */
	protected MapleAccount() {
		conAuth = (long) (Math.random() * Long.MAX_VALUE);
	}
	
	/**
	 * Gets the account ID number from the account.
	 * @return the account ID of the account
	 */
	public int getAccountId() {
		return accountId;
	}
	
	/**
	 * Gets the account name from the account.
	 * @return the name of the account
	 */
	public String getAccountName() {
		return accountName;
	}
	
	/**
	 * Gets the gender of the account.
	 * @return the gender of the account
	 */
	public byte getGender() {
		return gender;
	}
	
	/**
	 * Gets whether or not the account is a GM account.
	 * @return whether or not the account is a GM
	 */
	public boolean isGM() {
		return (gmLevel >= 2);
	}
	
	/**
	 * Gets the account's GM level.
	 * @return the account's GM level
	 */
	public byte getGMLevel() {
		return gmLevel;
	}
	
	/**
	 * Gets the account's creation date as a Java <code>Date</code>.
	 * @return the creation date as a Java <code>Date</code>
	 */
	public Date getCreationDate() {
		return creationDate;
	}
	
	/**
	 * Gets the account's creation date as a Unix timestamp.
	 * @return the creation date as a Unix timestamp
	 */
	public long getUnixCreationDate() {
		return creationDate.getTime();
	}
	
	/**
	 * Gets whether or not the character is blocked from trade.
	 * @return whether or not the character is blocked from trade
	 */
	public boolean isTradeBlocked() {
		return tradeBlock;
	}
	
	/**
	 * Gets the date that the trade block will expire as a Java <code>Date</code>.
	 * @return the date of the trade block's expiration as a Java <code>Date</code>.
	 */
	public Date getTradeBlockExpiration() {
		return tradeBlockExpiration;
	}
	
	/**
	 * Gets the date that the trade block will expire as a Unix timestamp.
	 * @return the date of the trade block's expiration as a Unix timestamp
	 */
	public long getUnixTradeBlockExpiration() {
		return tradeBlockExpiration.getTime();
	}
	
	/**
	 * Gets the calculated <code>conAuth</code> for the account.
	 * @return the account's randomly generated long for connection authentication
	 */
	public long getConnectionAuthentication() {
		return conAuth;
	}
	
	/**
	 * Creates a <code>MapleAccount</code> representing rice.
	 * @return rice as a <code>MapleAccount</code>
	 */
	public static MapleAccount rice() {
		MapleAccount ret = new MapleAccount();
		ret.accountId = 1;
		ret.accountName = "rice";
		ret.gender = 1;
		ret.gmLevel = 0;
		ret.creationDate = new Date();
		ret.tradeBlock = false;
		ret.tradeBlockExpiration = new Date(0);
		return ret;
	}
}
