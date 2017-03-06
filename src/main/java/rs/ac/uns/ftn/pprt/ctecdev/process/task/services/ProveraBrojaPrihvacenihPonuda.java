package rs.ac.uns.ftn.pprt.ctecdev.process.task.services;

import java.util.List;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.identity.User;

import rs.ac.uns.ftn.pprt.ctecdev.utils.LoggerLogger;

public class ProveraBrojaPrihvacenihPonuda implements JavaDelegate {

	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LoggerLogger LOGGER = new LoggerLogger(TrazenjeFirmi.class);
		LOGGER.info("ServiceTask # Provera broja prihvacenih Ponuda - start");
		LOGGER.outputVariableMapExecution(execution);
		


		/**
		 * AgentTaskListener.brojPrihvacenihPonuda
		 */
		Integer brojPrihvacenihPonuda = (Integer) execution.getVariable("brojPrihvacenihPonuda");
		if (brojPrihvacenihPonuda == null){
			brojPrihvacenihPonuda = 0;
		}
		
		/**
		 * SelektovanjeFirmi.neselektovaneFirme
		 */
		@SuppressWarnings("unchecked")
		List<User> neselektovaneFirme = (List<User>) execution.getVariable("neselektovaneFirme");
		
		/**
		 * SelektovanjeFirmi.brojponuda
		 */
		Integer brojPonuda = (Integer) execution.getVariable("brojPonudaInt");
		
		/**
		 * this.selektujJosFirmi
		 */
		Boolean selektujJosFirmi = (Boolean) execution.getVariable("selektujJosFirmi");
		if (selektujJosFirmi == null){
			selektujJosFirmi = new Boolean(false);
		}
		
		/**
		 * Ako nema firmi za selektovanje, proces ide dalje
		 */
		if (neselektovaneFirme.size() == 0){	
			selektujJosFirmi = false;
		}else {
			
			/**
			 * this.selektujJosFirmiBroj
			 */
			Integer selektujJosFirmiBroj = (Integer) execution.getVariable("selektujJosFirmiBroj");
			if (selektujJosFirmiBroj == null){
				selektujJosFirmiBroj = new Integer(0);
			} else {
				if (brojPrihvacenihPonuda == brojPonuda){
					selektujJosFirmi = false;
				// brojPrihvacenihPonuda < brojPonuda
				} else {
					selektujJosFirmi = true;
					selektujJosFirmiBroj = brojPonuda - brojPrihvacenihPonuda;
				}
			}
			
			execution.setVariable("selektujJosFirmiBroj", selektujJosFirmiBroj);
		}
		execution.setVariable("brojPrihvacenihPonuda", brojPrihvacenihPonuda);
		execution.setVariable("selektujJosFirmi", selektujJosFirmi);
		
		
		
		LOGGER.info("ServiceTask # Provera broja prihvacenih Ponuda - end");
	}

}
