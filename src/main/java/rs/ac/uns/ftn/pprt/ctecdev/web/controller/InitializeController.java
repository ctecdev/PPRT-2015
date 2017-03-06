package rs.ac.uns.ftn.pprt.ctecdev.web.controller;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="/")
public class InitializeController {
	
	@Autowired
	IdentityService identityService;
	
	@RequestMapping(value="/init", method=RequestMethod.GET)
	String string(){
		
		//DODAVANJE KORISNIKA
			//KLIJENTI
				System.out.println("Dodavanje korisnika...");
				//Pera
					User pera = identityService.newUser("pera");
					pera.setFirstName("pera");
					pera.setLastName("peric");
					pera.setEmail("pera@localhost");
					pera.setPassword("pera");
					identityService.saveUser(pera);
				//Mika
					User mika = identityService.newUser("mika");
					mika.setFirstName("mika");
					mika.setLastName("becic");
					mika.setEmail("mika@localhost");
					mika.setPassword("mika");
					identityService.saveUser(mika);
				//Zika
					User zika = identityService.newUser("zika");
					zika.setFirstName("zika");
					zika.setLastName("jovanovic");
					zika.setEmail("zika@localhost");
					zika.setPassword("zika");
					identityService.saveUser(zika);
			//FIRME
				//Mostogradnja
					//MarkoMost
						User markoMost = identityService.newUser("markomost");
						markoMost.setFirstName("MarkoMost"); //Naziv Firme
						markoMost.setLastName("Mostogradnja"); //Oblast rada (Primarna delatnost)
						markoMost.setEmail("markomost@localhost");
						markoMost.setPassword("markomost");
						identityService.saveUser(markoMost);
					//BataMilojka
						User bataMilojka = identityService.newUser("batamilojka");
						bataMilojka.setFirstName("BataMilojka");
						bataMilojka.setLastName("Mostogradnja");
						bataMilojka.setEmail("batamilojka@localhost");
						bataMilojka.setPassword("batamilojka");
						identityService.saveUser(bataMilojka);
					//ZarkoSkela
						User zarkoSkela = identityService.newUser("zarkoskela");
						zarkoSkela.setFirstName("ZarkoSkela");
						zarkoSkela.setLastName("Mostogradnja");
						zarkoSkela.setEmail("zarkoskela@localhost");
						zarkoSkela.setPassword("zarkoskela");
						identityService.saveUser(zarkoSkela);
					//PaljovkaZidar
						User paljovkaZidar = identityService.newUser("paljovkazidar");
						paljovkaZidar.setFirstName("PaljovkaZidar");
						paljovkaZidar.setLastName("Mostogradnja");
						paljovkaZidar.setEmail("paljovkazidar@localhost");
						paljovkaZidar.setPassword("paljovkazidar");
						identityService.saveUser(paljovkaZidar);
					//BudaKesonac
						User budaKesonac = identityService.newUser("budakesonac");
						budaKesonac.setFirstName("BudaKesonac");
						budaKesonac.setLastName("Mostogradnja");
						budaKesonac.setEmail("budakesonac@localhost");
						budaKesonac.setPassword("budakesonac");
						identityService.saveUser(budaKesonac);
				//Transport
					//KaranTrans
						User karanTrans = identityService.newUser("karantrans");
						karanTrans.setFirstName("KaranTrans");
						karanTrans.setLastName("Transport");
						karanTrans.setEmail("karantrans@localhost");
						karanTrans.setPassword("karantrans");
						identityService.saveUser(karanTrans);
					//ZeljkoBusPlus
						User zeljkoBusPlus = identityService.newUser("zeljkobusplus");
						zeljkoBusPlus.setFirstName("ZeljkoBusPlus");
						zeljkoBusPlus.setLastName("Transport");
						zeljkoBusPlus.setEmail("zeljkobusplus@localhost");
						zeljkoBusPlus.setPassword("zeljkobusplus");
						identityService.saveUser(zeljkoBusPlus);
				//Bravarija
					//KovacMont
						User kovacMont = identityService.newUser("kovacmont");
						kovacMont.setFirstName("KovacMont");
						kovacMont.setLastName("Bravarija");
						kovacMont.setEmail("kovacmont@localhost");
						kovacMont.setPassword("kovacmont");
						identityService.saveUser(kovacMont);
					//BaneBrava
						User baneBrava = identityService.newUser("banebrava");
						baneBrava.setFirstName("BaneBrava");
						baneBrava.setLastName("Bravarija");
						baneBrava.setEmail("banebrava@localhost");
						baneBrava.setPassword("banebrava");
						identityService.saveUser(baneBrava);
				//Ratarstvo
					//Ratar
						User ratar = identityService.newUser("ratar");
						ratar.setFirstName("Toma");
						ratar.setLastName("Ratar");
						ratar.setEmail("ratar@localhost");
						ratar.setPassword("ratar");
						identityService.saveUser(ratar);
		//KORISNICI DODATI
		

		//DODAVANJE GRUPA
			System.out.println("Dodavanje grupa...");
			//Osoba
				Group osobaGroup = identityService.newGroup("osobaGroup");
				osobaGroup.setType("assignement");
				identityService.saveGroup(osobaGroup);
			//Firma
				Group firmaGroup = identityService.newGroup("firmaGroup");
				firmaGroup.setType("assignement");
				identityService.saveGroup(firmaGroup);
			
			//KATEGORIJE POSLOVA
				//Mostogradnj
					Group mostogradnja = identityService.newGroup("mostogradnja");
					mostogradnja.setType("kategorijaPosla");
					identityService.saveGroup(mostogradnja);
				//Transport
					Group transport = identityService.newGroup("transport");
					transport.setType("kategorijaPosla");
					identityService.saveGroup(transport);
				
				//Bravarija
					Group bravarija = identityService.newGroup("bravarija");
					bravarija.setType("kategorijaPosla");
					identityService.saveGroup(bravarija);
				//Ratarstvo
					Group ratarstvo = identityService.newGroup("ratarstvo");
					ratarstvo.setType("kategorijaPosla");
					identityService.saveGroup(ratarstvo);
				//PodStecajem
					Group nemafirmi = identityService.newGroup("nemafirmi");
					nemafirmi.setType("kategorijaPosla");
					identityService.saveGroup(nemafirmi);
		//GRUPE DODATE
		
		//DODAVANJE MEMBERSHIP-A Dodavanje korisnika u grupe
			System.out.println("Dodavanje korisnika u grupu...");
			//Osobe
				identityService.createMembership(pera.getId(), osobaGroup.getId());
				identityService.createMembership(mika.getId(), osobaGroup.getId());
				identityService.createMembership(zika.getId(), osobaGroup.getId());
			//Firme
				//Mostogradnja
					identityService.createMembership(markoMost.getId(), firmaGroup.getId());
					identityService.createMembership(bataMilojka.getId(), firmaGroup.getId());
					identityService.createMembership(zarkoSkela.getId(), firmaGroup.getId());
					identityService.createMembership(paljovkaZidar.getId(), firmaGroup.getId());
					identityService.createMembership(budaKesonac.getId(), firmaGroup.getId());
					
					identityService.createMembership(markoMost.getId(), mostogradnja.getId());
					identityService.createMembership(bataMilojka.getId(), mostogradnja.getId());
					identityService.createMembership(zarkoSkela.getId(), mostogradnja.getId());
					identityService.createMembership(paljovkaZidar.getId(), mostogradnja.getId());
					identityService.createMembership(budaKesonac.getId(), mostogradnja.getId());
				//Transport
					identityService.createMembership(karanTrans.getId(), firmaGroup.getId());
					identityService.createMembership(zeljkoBusPlus.getId(), firmaGroup.getId());
					
					identityService.createMembership(karanTrans.getId(), transport.getId());
					identityService.createMembership(zeljkoBusPlus.getId(), transport.getId());
				//Bravarija
					identityService.createMembership(kovacMont.getId(), firmaGroup.getId());
					identityService.createMembership(baneBrava.getId(), firmaGroup.getId());
					
					identityService.createMembership(kovacMont.getId(), bravarija.getId());
					identityService.createMembership(baneBrava.getId(), bravarija.getId());
				//Ratarstvo
					identityService.createMembership(ratar.getId(), firmaGroup.getId());
					identityService.createMembership(ratar.getId(), ratarstvo.getId());
					
		//MEMBERSHIP-I DODATI
		
		return "Uspesno upisivanje u bazu... Identity Service: "+ identityService;
	}

}
