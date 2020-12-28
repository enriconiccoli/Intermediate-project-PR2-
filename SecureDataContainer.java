package myPackage;
import java.util.Iterator;


public interface SecureDataContainer<E> {

		
	public void createUser(String id, String passw) throws NullPointerException, NoDuplicateException;
	//REQUIRES: id != null, passw != null, id non contenuto nell'insieme degli id
	//Modifies: this
	//Effects: Aggiunge l'utente id, con password passw, alla collezione, se non già presente
	//Throws: Lancia NullPointerException se id == null o passw == null. Lancia NoDuplicateException
	//        se id è già presente
	
	public void removeUser(String id, String passw) throws NullPointerException, UserNotFoundException;
	//Requires: id != null, passw != null, <id, passw> contenuta nell'insieme degli utenti
	//Modifies: this
	//Effects: Rimuove l'utente dalla collezione, se presente
	//Throws: Lancia NullPointerException se id == null o passw == null. Lancia UserNotFoundException
	//        se l'utente non esiste
	
	public int getSize(String Owner, String passw) throws NullPointerException, UserNotFoundException;
	//Requires: Owner != null, passw != null, <owner,passw> contenuto nell'insieme degli utenti
	//Modifies: ---
	//Effects: Restituisce il numero degli elementi di un utente, se esso esiste
	//Throws: Lancia NullPointerException se owner == null o passw == null. Lancia UserNotFoundException
	//        se l'utente non esiste
	
	public boolean put(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException;
	//Requires: Owner != null, passw != null, <owner,passw> contenuto nell'insieme degli utenti
	//          data != null
	//Modifies: this
	//Effects: Inserisce data nella collezione di dati dell'utente, se esso esiste
	//Throws: Lancia NullPointerException se owner == null o passw == null o data == null. 
	//        Lancia UserNotFoundException se l'utente non esiste
	
	public E get(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException;
	//Requires: Owner != null, passw != null, <owner,passw> contenuto nell'insieme degli utenti
	//          data != null, data contenuto nella collezione di dati dell'utente <Owner,pass>
	//Modifies: ---
	//Effects: Restituisce una copia del dato se l'utente esiste e se il dato è presente nella sua collezione
	//Throws: Lancia NullPointerException se owner == null o passw == null o data == null. 
	//        Lancia UserNotFoundException se l'utente non esiste.
	//        Lancia DataNotFoundException se data non è nella collezione dati dell'utente
	
	public E remove(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException;
	//Requires: Owner != null, passw != null, <owner,passw> contenuto nell'insieme degli utenti
	//          data != null, data contenuto nella collezione di dati dell'utente <Owner,pass>
	//Modifies: this
	//Effects: RImuove il dato dalla collezione, se l'utente esiste e se il dato è presente
	//Throws: Lancia NullPointerException se owner == null o passw == null o data == null. 
	//        Lancia UserNotFoundException se l'utente non esiste.
	//        Lancia DataNotFoundException se data non è nella collezione dati dell'utente
	
	public void copy(String Owner, String passw, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException;
	//Requires: Owner != null, passw != null, <owner,passw> contenuto nell'insieme degli utenti
	//          data != null, data contenuto nella collezione di dati dell'utente <Owner,pass>
	//Modifies: this
	//Effects: Duplica data se è presente nella collezione di un utente esistente
	//Throws: Lancia NullPointerException se owner == null o passw == null o data == null. 
	//        Lancia UserNotFoundException se l'utente non esiste.
	//        Lancia DataNotFoundException se data non è nella collezione dati dell'utente
	
	public void share(String Owner, String passw, String Other, E data) throws NullPointerException, UserNotFoundException, DataNotFoundException;
	//Requires: Owner != null, passw != null, <owner,passw> contenuto nell'insieme degli utenti
	//          data != null, data contenuto nella collezione di dati dell'utente <Owner,pass>
	//          Other != null, other contenuto nell'insieme degli id
	//Modifies: this
	//Effects: Condivide data, se è presente nella collezione di un utente esistente, 
	//         con Other, se è un utente esistente
	//Throws: Lancia NullPointerException se owner == null o passw == null o data == null. 
	//        Lancia UserNotFoundException se Owner non esiste o se Other non esiste.
	//        Lancia DataNotFoundException se data non è nella collezione dati dell'utente
	
	
	public Iterator<E> getIterator(String Owner, String passw) throws NullPointerException, UserNotFoundException;
	//Requires: Owner != null, passw != null, <Owner,passw> contenuto nell'insieme degli utenti
	//Modifies: ---
	//Effects: Restituisce un iteratore senza remove per la collezione se l'utente esiste
	//Throws: Lancia NullPointerException se Owner == null, passw == null
	//		  Lancia UserNotFoundException se l'utente non esiste
	
	
	
	
	
	
	
public class NoDuplicateException extends Exception {
	public NoDuplicateException(String message) {
        super(message);
    }
	public NoDuplicateException() {
        super("Il nome utente è già in uso");
    }
}
	

public class UserNotFoundException extends Exception {
	public UserNotFoundException(String message) {
        super(message);
    }
	public UserNotFoundException() {
        super("Utente non trovato");
    }
}

public class DataNotFoundException extends Exception {
	public DataNotFoundException(String message) {
        super(message);
    }
	public DataNotFoundException() {
        super("Il dato richiesto non è nella collezione");
    }
}
	
}

