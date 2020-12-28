package myPackage;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import myPackage.SecureDataContainer.DataNotFoundException;
import myPackage.SecureDataContainer.NoDuplicateException;
import myPackage.SecureDataContainer.UserNotFoundException;


//AF(c) = <{user_0,...,user_n}, {pw_0,...,pw_n}, {<user_0, {SecureData_00,..., SecureData0n}>,..., <user_n, {SecureData_n0,..., SecureData_nn}>}>
//IR = owners != null, passws != null, hashData != null, per ogni i owners_i != null, passws_i != null,
//		hashData.get(i) != null. owners.size == passws.size. Per ogni i,j owners_i != owners_j,
//		Per ogni i,j hashData.get(i).get(j).authorizedUsers.size <= owners.size
//		Per ogni i, per ogni j esiste k tale che hashData.get(i).get(j).owner == owner_k
//		Per ogni i, per ogni j, per ogni k esiste q tale che hashData.get(i).get(j).authorizedUsers_k == owner_q


public class SDCHash<E> implements SecureDataContainer<E> {
	
	private ArrayList<String> owners;
	private ArrayList<String> passws;
	private HashMap<String, ArrayList<SecureData<E>>> hashData;
	
	public SDCHash(){
		
		owners = new ArrayList<String>();
		passws = new ArrayList<String>();
		hashData = new HashMap<>();
		
	}
	
	
	
	private void removeFromDataCollection(String id) {
		
		for(int i = 0; i<owners.size(); i++) {
			for(int j=0; j<hashData.get(owners.get(i)).size(); j++) {
				if(hashData.get(owners.get(i)).get(j).getAuthorizedUsers().contains(id)) {
					hashData.get(owners.get(i)).get(j).getAuthorizedUsers().remove(id);
				}
			}
		}
			
	}
	

	private int checkData(String Owner, E data) {
		 
		for(int i=0; i<hashData.get(Owner).size(); i++) {
			if(hashData.get(Owner).get(i).getData().equals(data) && hashData.get(Owner).get(i).getOwner().equals(Owner)) {
				return i;
			}
		}
		return -1;
	}
	
	
	
	
	

	@Override
	public void createUser(String id, String passw) throws NullPointerException, NoDuplicateException {
		if(id == null || passw == null) throw new NullPointerException();
		if(owners.contains(id)) throw new NoDuplicateException();
		owners.add(id);
		passws.add(passw);
		hashData.put(id, new ArrayList<SecureData<E>>());
	}

	@Override
	public void removeUser(String id, String passw) throws NullPointerException, UserNotFoundException {
		if(id == null || passw == null) throw new NullPointerException();
		if(!owners.contains(id) || !passw.contains(passw)) throw new UserNotFoundException();
		owners.remove(id);
		passws.remove(passw);
		this.removeFromDataCollection(id);
		hashData.remove(passw);
	}

	@Override
	public int getSize(String Owner, String passw) throws NullPointerException, UserNotFoundException {
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		return hashData.get(Owner).size();
	}

	@Override
	public boolean put(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException {
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		hashData.get(Owner).add(new SecureData<E>(Owner,data));
		return true;
	}

	@Override
	public E get(String Owner, String passw, E data)
			throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		return hashData.get(Owner).get(index).getData();
	}

	@Override
	public E remove(String Owner, String passw, E data)
			throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || passw == null || data == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		hashData.get(Owner).remove(index);
		return data;
	}

	@Override
	public void copy(String Owner, String passw, E data)
			throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || passw == null || data == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		hashData.get(Owner).add(new SecureData<E>(Owner, data));
	}

	@Override
	public void share(String Owner, String passw, String Other, E data)
			throws NullPointerException, UserNotFoundException, DataNotFoundException {
		if(Owner == null || Other == null || passw == null || data == null) throw new NullPointerException();
		if(!owners.contains(Owner) || !passw.contains(passw) || !owners.contains(Other)) throw new UserNotFoundException();
		int index = checkData(Owner, data);
		if( index == -1) throw new DataNotFoundException();
		hashData.get(Owner).get(index).setAuthorizedUsers(Other);
		
	}

	@Override
	public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, UserNotFoundException {
		if(Owner == null || passw == null) throw new NullPointerException();
		if(!passws.contains(passw) || !owners.contains(Owner)) throw new UserNotFoundException();
		
		
		Iterator<E> iterator = new Iterator<E>() {

			private E[] data = (E[]) hashData.get(Owner).toArray();
			private int counter = 0;
			
			
			
			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return counter<hashData.get(Owner).size();
			}

			@Override
			public E next() {
				// TODO Auto-generated method stub
				return data[counter++];
			}
				
			
		};
		
		return iterator;
	}

}
