package rs.ac.uns.ftn.pprt.ctecdev.process.listeners;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

import rs.ac.uns.ftn.pprt.ctecdev.utils.LoggerLogger;

public class RokIstekaoBooleanTaskListener implements TaskListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		LoggerLogger LOGGER = new LoggerLogger(RokIstekaoBooleanTaskListener.class);
		//LOGGER.info("MailTask # Obavestenje Firme Posao Prihvacen - start");
		LOGGER.outputVariableMapTask(delegateTask);
		
		/**
		 * OdlukaKlijenta formProperty: izborKlijenta
		 */
		String izborKlijenta = (String) delegateTask.getVariable("izborKlijenta");
		if (izborKlijenta == null){
			izborKlijenta = "null";
		}
		/**
		 * KlijentDonosiOdluku formProperty: odlukaKlijenta
		 */
		String odlukaKlijenta = (String) delegateTask.getVariable("odlukaKlijenta");
		if (odlukaKlijenta == null){
			odlukaKlijenta = "null";
		}
		
		if (izborKlijenta.equals("1") || odlukaKlijenta.equals("1")){
			/**
			 * UnosZahteva formProperty: rokIstekao
			 */
			Boolean rokIstekao =  (Boolean) delegateTask.getVariable("rokIstekao");
			rokIstekao = false;
			delegateTask.setVariable("rokIstekao", rokIstekao);
		}
		
		
		
		String log = "Rok Istekao Boolean Listener";
		if(izborKlijenta.equals("1")){
			log = "Odluka klijenta";
		} else if (odlukaKlijenta.equals("1")){
			log = "Klijent donosi odluku";
		}
		LOGGER.info("UserTask # "+ log +" - end");
	}

}
