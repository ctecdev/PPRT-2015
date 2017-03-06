package rs.ac.uns.ftn.pprt.ctecdev.process.listeners;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;

public class ProcessListener implements ExecutionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3012591698470054691L;

	public void notify(DelegateExecution de) throws Exception {
		// TODO Auto-generated method stub
		System.out.println();
		System.out.println("##############################");
		System.out.println("Process: "+ de.getEventName()+" processID: "+de.getProcessInstanceId());
		System.out.println("##############################");
		System.out.println();
	}

}
