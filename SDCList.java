package myPackage;
import java.util.*;

import myPackage.SecureDataContainer.DataNotFoundException;
import myPackage.SecureDataContainer.NoDuplicateException;
import myPackage.SecureDataContainer.UserNotFoundException;

//AF(c) = <{user_0,...,user_n}, {pw_0,...,pw_n}, {SecureData_0,...,SecureData_j}>
//IR = owners != null, passws != null, datas != null, per ogni i owners_i != null, passws_i != null,
//     datas_i != null. owners.size == passws.size. Per ogni i,j owners_i != owners_j,
//     Per ogni i, dataSD_i.authorizedUsers.size < owners.size
//	   Per ogni i, esiste j tale che dataSD_i.owner == owner_j
//	   Per ogni i, per ogni j, esiste k tale che dataSD_i.getAuthorizedUsers_j == owner_k





public class SDCList<E> {

	/*Tre Array: il primo contiene gli id, il secondo le passwords e il terzo oggetti di tipo
	 * SecureData
	 */
	
	private ArrayList<String> owners;
	private ArrayList<String> passws;
	private ArrayList<SecureData<E>> dataSD;
	
	public SDCList() {
		owners = new ArrayList<String>();
		passws = new ArrayList<String>();
		dataSD = new ArrayList<SecureData<E>>();
	}
	
	
	
	/* Questo metodo controlla che un dato sia presente nella collezione di un utente. Se lo è
	 * restituisce l'indice dell'array in cui si trova, altrimenti -1
	 */	
	private int checkData(String Owner, E data) {
		for(int i = 0; i<dataSD.size(); i++) {
			if(dataSD.get(i).getData().equals(data) && dataSD.get(i).getOwner().equals(Owner)) {
				return i;
			}
		}
		return -1;
	}
	
	
	/* Questo metodo rimuove un utente dalla lista degli account
	 * condivisi associata ad un dato
	 */
	private void removeFromDataCollection(String id) {
		for(int i=0; i<dataSD.size();i++) {
			if(dataSD.get(i).getAuthorizedUsers().contains(id)) {
				dataSD.get(i).getAuthorizedUsers().remove(id);
			}
			if(dataSD.get(i).getOwner().equals(id)) dataSD.remove(i--);
		}
			
	}		
	
	
	
	
	
	
	
	
	public void createUser(String id, String passw) throws NullPointerException, NoDuplicateException {
		if(id == null || passw == null) throw new NullPointerException();
		if(owners.contains(id)) throw new NoDuplicateException();
		owners.add(id);
		passws.add(passw);
	}
	
	public void removeUser(String id, String passw) throws NullPointerException, UserNotFoundException {
		if(id == null || passw == null) throw new NullPointerException();
		if(!owners.contains(id) || !passw.contains(passw)) throw new UserNotFoundException();
		owners.remove(id);
		passws.remove(passw);
		this.removeFromDataCollection(id);
				
	}
	
	public int getSize(String Owner, String passw) throws NullPointerException, UserNotFoundException {
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		int count=0;
		for(int i = 0; i<dataSD.size(); i++) {
			if(dataSD.get(i).getOwner().equals(Owner)) {
				count++;
			}
		}

		return count;	
	}
	
	
	public boolean put(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException {
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		dataSD.add(new SecureData<E>(Owner, data));
		return true;
	}
	
	
	public E get(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		return dataSD.get(index).getData();
	}
	
	
	public E remove(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || passw == null || data == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		return dataSD.remove(index).getData();
	}
	
	public void copy(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || passw == null || data == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		dataSD.add(new SecureData<E>(Owner,data));
	}
	
	public void share(String Owner, String passw, String Other, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || Other == null || passw == null || data == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw) || !owners.contains(Other)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		dataSD.get(index).setAuthorizedUsers(Other);
	}
	
	public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, UserNotFoundException{
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!passws.contains(passw) || !owners.contains(Owner)) throw new UserNotFoundException();
		
		
		Iterator<E> iterator = new Iterator<E>(){
		
					
		
		private E[] data = userData(Owner);
		private int counter = 0;//
		
		
		//Questo metodo copia in un array statico tutti i dati di un determinato utente
	
		private E[] userData(String Owner) {
			int j = 0;
			E[] data= (E[]) new Object[ownerSize(Owner)];
			for(int k=0; k<dataSD.size(); k++) {
				if(dataSD.get(k).getOwner().equals(Owner)) {
					data[j] = dataSD.get(k).getData();
					j++;
				}
			}
			return data;
		}
	
		//Questo metodo conta quanti elementi ci sono nella collezione di un utente
		
		private int ownerSize(String Owner) {
			int count = 0;
			for(int i=0; i<dataSD.size();i++) {
				if(dataSD.get(i).getOwner().equals(Owner)) {
					count++;
				}
			}
			return count;
		}
		
		
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return (counter<data.length);
		}	
			
			
		public E next() {
			
			return data[counter++];
		}	
		
	};
		return iterator;
}
	
	
	
	
}