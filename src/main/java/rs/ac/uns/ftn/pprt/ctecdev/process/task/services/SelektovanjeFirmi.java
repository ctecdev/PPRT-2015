package rs.ac.uns.ftn.pprt.ctecdev.process.task.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.identity.User;

import rs.ac.uns.ftn.pprt.ctecdev.utils.LoggerLogger;

public class SelektovanjeFirmi implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LoggerLogger LOGGER = new LoggerLogger(SelektovanjeFirmi.class);
		LOGGER.info("ServiceTask # Selektovanje Firmi - start");
		LOGGER.outputVariableMapExecution(execution);


		
		/**
		 *
		 */
		List<User> selektovaneFirme = new ArrayList<User>();
		
		/**
		 * TrazenjeFirmi.nadjeneFirme
		 */
		@SuppressWarnings("unchecked")
		List<User> nadjeneFirme = (List<User>) execution.getVariable("nadjeneFirme");
		
		/**
		 * TrazenjeFirmi.brojPronadjenihFirmi
		 */
		Integer brojPronadjenihFirmi = (Integer) execution.getVariable("brojPronadjenihFirmi");
		
		/**
		 * MyProcess.UnosZahteva formProperty: brojPonuda
		 */
		Integer brojPonuda = (Integer) execution.getVariable("brojPonudaInt");
		if (brojPonuda == null){
			String brojPonudaEntry = (String) execution.getVariable("brojPonuda");
			brojPonuda = Integer.parseInt(brojPonudaEntry);
		}
		
		/**
		 * ProveraBrojaPrihvacenihPonuda.selektujJosFirmi
		 * ProveraBrojaPrihvacenihPonuda.selektujJosFirmiBroj
		 */
		Boolean selektujJosFirmi = (Boolean) execution.getVariable("selektujJosFirmi");
		Integer selektujJosFirmiBroj = (Integer) execution.getVariable("selektujJosFirmiBroj");
		
		/**
		 * Ukoliko se sve firme ne daju ponudu za posao,
		 * potrebno je od preostalih firmi, dodati potreban broj do broja trazenih ponuda
		 */
		@SuppressWarnings("unchecked")
		List<User> neselektovaneFirme = (List<User>) execution.getVariable("neselektovaneFirme");
		if (neselektovaneFirme == null){
			neselektovaneFirme = new ArrayList<User>();
		}
		
		/**
		 * Potrebna radi multi-instance MailTask-a
		 */
		@SuppressWarnings("unchecked")
		List<String> emails = (List<String>) execution.getVariable("emails");
		if (emails == null){
			emails = new ArrayList<String>();
		}
		/**
		 * Prvi prolazak kroz ServiceTask
		 */
		if (selektujJosFirmi == null){
			
			if (brojPronadjenihFirmi < brojPonuda){
				brojPonuda = brojPronadjenihFirmi;
			}
			
			/**
			 * Alogaritam za nasumicno biranje firmi :)
			 */
			selektovaneFirme.addAll(nadjeneFirme.subList(0, brojPonuda));
			
			neselektovaneFirme.addAll(nadjeneFirme);
			neselektovaneFirme.removeAll(selektovaneFirme);
		
		/**
		 *  Nisu sve firme prihvatile posao
		 */
		} else {
			
			emails.clear();
			selektovaneFirme.clear();
			
			Boolean zahtevajNovePonude = (Boolean) execution.getVariable("zahtevajNovePonude");
			if (zahtevajNovePonude != null && zahtevajNovePonude.equals(true)){
				
				Integer brojPrihvacenihPonuda = (Integer) execution.getVariable("brojPrihvacenihPonuda");
				brojPrihvacenihPonuda = 0;
				execution.setVariable("brojPrihvacenihPonuda", brojPrihvacenihPonuda);
				
				/**
				 * ...OdlukaAgentaTaskListener.ponuduPrihvatileFirmeMap
				 */
				@SuppressWarnings("unchecked")
				Map<String, Long> ponuduPrihvatileFirmeMap = (Map<String, Long>) execution.getVariable("ponuduPrihvatileFirmeMap");
				ponuduPrihvatileFirmeMap.clear();
				execution.setVariable("ponuduPrihvatileFirmeMap", ponuduPrihvatileFirmeMap);
				
				if (brojPonuda >= neselektovaneFirme.size()){
					
					brojPonuda = 0;
					selektovaneFirme.addAll(neselektovaneFirme);
					neselektovaneFirme.clear();
					
				} else {
					
					/**
					 * Alogaritam za nasumicno biranje firmi :)
					 */
					selektovaneFirme.addAll(neselektovaneFirme.subList(0, selektujJosFirmiBroj));
					neselektovaneFirme.removeAll(selektovaneFirme);
					
				}
			}else{
				if (selektujJosFirmiBroj >= neselektovaneFirme.size()){
					
					selektujJosFirmiBroj = neselektovaneFirme.size();
					selektovaneFirme.addAll(neselektovaneFirme);
					neselektovaneFirme.clear();
					
				/**
				 * 	selektujJosFirmiBroj < neselektovaneFirme.size()
				 */
				} else {
					
					/**
					 * Alogaritam za nasumicno biranje firmi :)
					 */
					selektovaneFirme.addAll(neselektovaneFirme.subList(0, selektujJosFirmiBroj));
					neselektovaneFirme.removeAll(selektovaneFirme);

				}
			}
			
		}
		
		/**
		 * Dodaj email adrese
		 */
		for (User firma : selektovaneFirme){
			emails.add(firma.getEmail());
			LOGGER.info("Firma Email: " + firma.getEmail());
		}
		
		
		
		execution.setVariable("brojPonudaInt", brojPonuda);
		execution.setVariable("selektovaneFirmeEmails", emails);
		execution.setVariable("selektovaneFirme", selektovaneFirme);
		execution.setVariable("neselektovaneFirme", neselektovaneFirme);
		
		
		
		LOGGER.info("ServiceTask # Selektovanje Firmi - end");
	}

}
