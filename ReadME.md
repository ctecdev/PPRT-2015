# PPRT-2016-Projektni-Zadatak

### Fakultet tehnickih nauka - Novi Sad
### Naziv kursa:
	Tehnologije i platforme za upravljanje poslovnim procesima i radnim tokovima 2015
### Profesori:
	Miroslav Zaric - http://www.ftn.uns.ac.rs/n1319391202/miroslav-zaric
	
## Kratak Opis 

Aplikacija za administraciju nastavnog procesa u obrazovnoj instituciji. 

## Organizacija

Klijent - Server Web aplikacija

### Realizacija i tehnologije

Klijentski deo - JSP ( src/main/webapp/static/... )  
Serverski deo - Java platforma # SpringBoot radni okvir
Baza podataka - MySQL

## Pokretanje Aplikacije

1. Preuzeti aplikaciju
2. Import-ovati u eclipse IDE
	- dodati plugin 'Activiti BPMN Designer' - https://activiti.org/designer/update/ 
3. Desni-klik na projekat > Maven > Update Project
4. Podesiti bazu podataka i podatke za pristup istoj
	podaci za pristup >>> /src/main/resources/application.properties
	- kreirati bazu sa odgovarajucim nazivom
5. Potrebno je dodati
	u 'Java Build Path' (desni-klik na projekat > Properties > Java Build Path)
	Apache Tomcat biblioteku ( inace nece moci da pronadje jsp stranice )
6. Zatim nam treba 'Apache James Server'
	preuzeti ga sa sajta - http://james.apache.org/server/index.html
	koristio sam 3.0-beta4 verziju
	- U james-read-me.txt fajlu je opisano kako podesiti server 
7. Pokretanje aplikacije
	- unutar paketa rs.ac.uns.ftn.tseo.ctecdev (/src/main/java/) 
	  nalazi se klasa PPRT_2015_App.java
	- desni-klik na klasu > Run As > Java Application
8. Inicijalizacija baze podataka
	- u InitializeControleru nalaze se podaci za bazu podataka
	- potrebno je samo pokrenuti kontroler kroz browser
	- tako sto unesemo adresu http://localhost:8080/init
9. Obrisemo init iz adrese
	- sada kada smo na pocetnoj stranici mozemo se prijaviti
	- prijava > username: ratar password: ratar
10. Aplikacija se najbrze moze testirati pomocu korisnika ratar
	- tako sto angazuje samog sebe
	- pripada grupi poslova Ratarstvo
	- treba imati u vidu da je proces ogranicen vremenski na mali vremenski period, radi testiranja, to se moze promeniti unutar samog procesa src/main/resources/processes/MyProcess.bpmn
	

	