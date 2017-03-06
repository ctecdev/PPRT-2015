package rs.ac.uns.ftn.pprt.ctecdev.process.listeners;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.activiti.engine.identity.User;

import rs.ac.uns.ftn.pprt.ctecdev.utils.LoggerLogger;

public class OdlukaAgentaTaskListener implements TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		LoggerLogger LOGGER = new LoggerLogger(OdlukaAgentaTaskListener.class);
		//LOGGER.info("UserTask # Odluka agenta o Ponudi - start");
		LOGGER.outputVariableMapTask(delegateTask);
		
		
		/**
		 * this.brojPrihvacenihPonuda
		 */
		Integer brojPrihvacenihPonuda = (Integer) delegateTask.getVariable("brojPrihvacenihPonuda");
		if (brojPrihvacenihPonuda == null){
			brojPrihvacenihPonuda = 0;
		}
		
		/**
		 * this.ponuduPrihvatileFirmeMap
		 */
		@SuppressWarnings("unchecked")
		Map<String, Long> ponuduPrihvatileFirmeMap = (Map<String, Long>) delegateTask.getVariable("ponuduPrihvatileFirmeMap");
		if (ponuduPrihvatileFirmeMap == null){
			ponuduPrihvatileFirmeMap = new HashMap<String, Long>();
		}
		
		/**
		 * MyProcess.OdlukaAgentaOPonudi formProperty: ponudaPrihvacena
		 */
		Boolean ponudaPrihvacena = (Boolean) delegateTask.getVariable("ponudaPrihvacena");
		if (ponudaPrihvacena == true){
			
			/**
			 * MyProcess.OdlukaAgentaOPonudi formProperty: ponudjenaCena
			 */
			Long ponudjenaCena = (Long) delegateTask.getVariable("ponudjenaCena");
			if (ponudjenaCena > 0){
				
				/**
				 * MyProcess.OdlukaAgentaOPonudi assignee
				 */
				User firma = (User) delegateTask.getVariable("selektovanaFirma");
				ponuduPrihvatileFirmeMap.put(firma.getId(), ponudjenaCena);
				
				delegateTask.setVariable("ponuduPrihvatileFirmeMap", ponuduPrihvatileFirmeMap);
				
				brojPrihvacenihPonuda++;
			}
		}
		delegateTask.setVariable("brojPrihvacenihPonuda", brojPrihvacenihPonuda);
		
		LOGGER.info("UserTask # Odluka agenta o Ponudi - end");
	}

}
