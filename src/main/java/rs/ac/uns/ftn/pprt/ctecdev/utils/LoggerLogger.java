package rs.ac.uns.ftn.pprt.ctecdev.utils;

import java.util.Map;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerLogger {
	
	private Logger logger;
	
	public LoggerLogger(Class<?> arg) {
		super();
		logger = LoggerFactory.getLogger(arg);
	}
	
	public void info(String string){
		logger.info(string);
	}
	
	public void outputVariableMapExecution(DelegateExecution execution){
		Map<String, Object> workflowVariableMap = execution.getVariables();
		
		for (Map.Entry<String, Object> entry : workflowVariableMap.entrySet()){
			String entryValue = "";
			String entryValueClass = "";
			if (entry.getValue() != null){
				entryValue = entry.getValue().toString();
				entryValueClass = entry.getClass().getName();
			}
			logger.info("Workflow variable: " + entry.getKey() + " |Value: " + entryValue + " |Object Class: " +entryValueClass);
		}
	}
	
	public void outputVariableMapTask(DelegateTask delegateTask){
		Map<String, Object> workflowVariableMap = delegateTask.getVariables();
		
		for (Map.Entry<String, Object> entry : workflowVariableMap.entrySet()){
			String entryValue = "";
			String entryValueClass = "";
			if (entry.getValue() != null){
				entryValue = entry.getValue().toString();
				entryValueClass = entry.getClass().getName();
			}
			logger.info("Workflow variable: " + entry.getKey() + " |Value: " + entryValue + " |Object Class: " +entryValueClass);
		}
	}
}
