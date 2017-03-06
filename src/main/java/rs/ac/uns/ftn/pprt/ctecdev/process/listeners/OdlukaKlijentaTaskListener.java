package rs.ac.uns.ftn.pprt.ctecdev.process.listeners;

import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.identity.User;

import rs.ac.uns.ftn.pprt.ctecdev.utils.LoggerLogger;

public class OdlukaKlijentaTaskListener implements TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		LoggerLogger LOGGER = new LoggerLogger(OdlukaKlijentaTaskListener.class);
		//LOGGER.info("UserTask # Odluka klijenta - start");
		LOGGER.outputVariableMapTask(delegateTask);
		
		
		/**
		 * izabranaFirmaID ako je "null" preskoci kod ispod
		 */
		String izabranaFirmaID = (String) delegateTask.getVariable("izabranaFirmaID");
		System.out.println(izabranaFirmaID);
		
		if (!izabranaFirmaID.equalsIgnoreCase("null")){
			/**
			 * OdlukaAgentaTaskListener.ponuduPrihvatileFirmeMap
			 */
			@SuppressWarnings("unchecked")
			Map<String, Long> ponuduPrihvatileFirmeMap = (Map<String, Long>) delegateTask.getVariable("ponuduPrihvatileFirmeMap");
			
			User izabranaFirma = delegateTask.getExecution().getEngineServices().getIdentityService().createUserQuery().userId(izabranaFirmaID).singleResult();
			Long izabranaFirmaCena = ponuduPrihvatileFirmeMap.get(izabranaFirmaID);
			String izabranaFirmaCenaString = String.valueOf(izabranaFirmaCena);
			
			delegateTask.setVariable("izabranaFirma", izabranaFirma);
			delegateTask.setVariable("izabranaFirmaNaziv", izabranaFirma.getFirstName() +" "+ izabranaFirma.getLastName());
			delegateTask.setVariable("izabranaFirmaCenaString", izabranaFirmaCenaString);
		}
		
		LOGGER.info("UserTask # Odluka klijenta - end");
	}

}
