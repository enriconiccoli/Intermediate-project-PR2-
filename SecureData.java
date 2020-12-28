package myPackage;
import java.util.*;


//AF(c) = <owner_i, data_i, {user_0,...,user_n}>
//IR = data != null, Owner != null, authorizedUsers != null, Per ogni i authorizedUsers_i != null
	   	




public class SecureData<E> {

	/* Un oggetto di tipo SecureData rappresenta un dato di tipo E associato al suo proprietario
	 * ed alla lista degli utenti con cui il dato è stato condiviso
	 */
	
	private E data;
	private String Owner;
	private ArrayList<String> authorizedUsers;
	
	public SecureData(String Owner, E data){
		this.Owner = Owner;
		this.data = data;
		authorizedUsers = new ArrayList<String>();
	}
	
	
	public E getData() {
		return data;
	}
	
	public String getOwner() {
		return Owner;
	}
	
	public ArrayList<String> getAuthorizedUsers(){
		return authorizedUsers;
	}
	
	public void setAuthorizedUsers(String user) {
		authorizedUsers.add(user);
	}
	
	
	
	
	@Override
	public String toString() {
		return data.toString();
	}
		
	
}
