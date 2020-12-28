package myPackage;

import java.util.Iterator;

import myPackage.SecureDataContainer.DataNotFoundException;
import myPackage.SecureDataContainer.NoDuplicateException;
import myPackage.SecureDataContainer.UserNotFoundException;

public class Application<E> {
	
	private SDCHash<E> hash;
	private SDCList<E> list;
	
	public Application() {
		hash = new SDCHash<E>();
		list = new SDCList<E>();
	}
	
	public void AcreateU(String id, String passw) throws NullPointerException, NoDuplicateException {
		hash.createUser(id, passw);
		list.createUser(id, passw);
	}
	
	public void Aput(String id, String passw, E data) throws NullPointerException, UserNotFoundException {
		hash.put(id, passw, data);
		list.put(id, passw, data);
	}
	
	public void AremoveU(String id, String passw) throws NullPointerException, UserNotFoundException {
		hash.removeUser(id, passw);
		list.removeUser(id, passw);
	}
	
	public void AgetSize(String Owner, String passw) throws NullPointerException, UserNotFoundException {
		int hashsize = hash.getSize(Owner, passw);
		System.out.println("Il numero di oggetti contenuti nella collezione implementata come Hash di " + Owner + " è " + hashsize);
		int listsize = list.getSize(Owner, passw);
		System.out.println("Il numero di oggetti contenuti nella collezione implementata come ArrayList di " + Owner + " è " + listsize);
	}
	
	public void Aget(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException{
		E tmp = hash.get(Owner, passw, data);
		System.out.println("L'oggetto" + tmp + "è presente nella collezione implementata come Hash di " + Owner);
		tmp = list.get(Owner, passw, data);
		System.out.println("L'oggetto" + tmp + "è presente nella collezione implementata come ArrayList di " + Owner);
	}
	
	public void Aremove(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException{
	
		hash.remove(Owner, passw, data);
		list.remove(Owner, passw, data);
	}
	
	public void Acopy(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException{
		hash.copy(Owner, passw, data);
		list.copy(Owner, passw, data);
		
	}
	
	public void Ashare(String Owner, String passw, String Other, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException{
		
		hash.share(Owner, passw, Other, data);
		list.share(Owner, passw, Other, data);
	}
	
	
	
	
	//Questo metodo stampa tutti i dati di cui un utente è proprietario
	
	
	public void printData(String Owner, String passw) throws NullPointerException, UserNotFoundException {
		Iterator<E> iterator = hash.getIterator(Owner, passw);
		Iterator<E> iterator2 = list.getIterator(Owner, passw);
		
		System.out.println("I dati di " + Owner + " contenuti nella HashMap sono i seguenti: ");
		while(iterator.hasNext()) {
			System.out.println(iterator.next());
		}
		
		System.out.println("I dati di " + Owner + " contenuti nell'ArrayList sono i seguenti: ");
		while(iterator2.hasNext()) {
			System.out.println(iterator2.next());
		}
		
	}
	
}
