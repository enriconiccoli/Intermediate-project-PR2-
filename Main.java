package myPackage;

import java.util.*;
import java.util.Iterator;

import myPackage.SecureDataContainer.DataNotFoundException;
import myPackage.SecureDataContainer.NoDuplicateException;
import myPackage.SecureDataContainer.UserNotFoundException;
@SuppressWarnings({ "rawtypes", "unchecked" })


public class Main {

	
	public static void main(String[] args){
		
		//TEST CASE UTILIZZANDO DATI DI TIPO INTERO
		
		Application str = new Application();
		try {
			str.AcreateU("Rossi", "290896");
			str.AcreateU("Bianchi", "240295");
			str.AcreateU("Verdi", "300662");
			str.AcreateU("Black", "0187");
			str.Aput("Rossi", "290896", 55);
			str.Aput("Rossi", "290896", 43);
			str.Aput("Bianchi", "240295", 95);
			str.Aput("Bianchi", "240295", 99);
			str.Aput("Verdi", "300662", 22);
			str.Aput("Verdi", "300662", 00);
			str.Aput("Verdi", "300662", 77);
			str.Aput("Black", "0187", 999);
			
			
			str.Aremove("Verdi", "300662", 00);
			str.Acopy("Bianchi", "240295", 95);
			str.AremoveU("Black", "0187");
			str.Ashare("Bianchi", "240295", "Rossi", 95);
			str.AgetSize("Rossi", "290896");
			str.AgetSize("Bianchi", "240295");
			str.AgetSize("Verdi", "300662");
		
			str.printData("Rossi", "290896");
			str.printData("Bianchi", "240295");
			str.printData("Verdi", "300662");
			
		} catch (NullPointerException | NoDuplicateException | UserNotFoundException | DataNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
