package rs.ac.uns.ftn.pprt.ctecdev.process.listeners;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.springframework.beans.factory.annotation.Autowired;

import rs.ac.uns.ftn.pprt.ctecdev.service.OcenaService;
import rs.ac.uns.ftn.pprt.ctecdev.utils.LoggerLogger;

public class KlijentOcenjujeStartTaskListener implements TaskListener {
	
	@Autowired
	OcenaService ocenaService;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		LoggerLogger LOGGER = new LoggerLogger(KlijentOcenjujeStartTaskListener.class);
		LOGGER.info("UserTask # Klijent Ocenjuje - start");
		LOGGER.outputVariableMapTask(delegateTask);
		
		//LOGGER.info("UserTask # Klijent Ocenjuje - end");
	}

}
