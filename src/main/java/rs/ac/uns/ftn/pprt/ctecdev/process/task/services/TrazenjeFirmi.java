package rs.ac.uns.ftn.pprt.ctecdev.process.task.services;

import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.identity.User;
import org.activiti.engine.identity.UserQuery;

import rs.ac.uns.ftn.pprt.ctecdev.utils.LoggerLogger;

public class TrazenjeFirmi implements JavaDelegate {
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		LoggerLogger LOGGER = new LoggerLogger(TrazenjeFirmi.class);
		LOGGER.info("ServiceTask # Trazenje Firmi - start");
		LOGGER.outputVariableMapExecution(execution);
		
		
		/**
		 *
		 */
		IdentityService identityService = execution.getEngineServices().getIdentityService();
		
		/**
		 * KategorijaId == GroupId
		 * MyProcess.UnosZahteva formProperty: kategorijaPosla
		 */
		String kategorijaPosla = (String) execution.getVariable("kategorijaPosla");
		
		/**
		 * Nalazimo firme za trazenu kategorijuPosla
		 */
		UserQuery nadjeneFirmeQuery = identityService.createUserQuery().memberOfGroup(kategorijaPosla);
		List<User> nadjeneFirme = nadjeneFirmeQuery.list();
		
		/**
		 *
		 */
		Integer brojPronadjenihFirmi = nadjeneFirme.size();
		
		execution.setVariable("kategorijaPoslaString", kategorijaPosla);
		execution.setVariable("brojPronadjenihFirmi", brojPronadjenihFirmi);
		execution.setVariable("nadjeneFirme", nadjeneFirme);
		
		Long krajnjiRok = (Long) execution.getVariable("krajnjiRok");
		String krajnjiRokString = String.valueOf(krajnjiRok);
		execution.setVariable("krajnjiRokString", krajnjiRokString);
		
		LOGGER.info("ServiceTask # Trazenje Firmi - end");
	}
	
}
