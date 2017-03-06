package rs.ac.uns.ftn.pprt.ctecdev.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.identity.User;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import rs.ac.uns.ftn.pprt.ctecdev.model.Ocena;
import rs.ac.uns.ftn.pprt.ctecdev.service.OcenaService;

@Controller
@RequestMapping("/")
public class ProcessTaskController {

	@Autowired
    RuntimeService runtimeService;
	@Autowired
	IdentityService identityService;
	@Autowired
	TaskService taskService;
	@Autowired
	FormService formService;
	@Autowired
	OcenaService ocenaService;
	
	private static String processKey = "myProcess";

	/**
	 * This method will start process".
	 */
	@RequestMapping(value = { "/process" }, method = RequestMethod.GET)
	public String prepareProcess(HttpSession session, ModelMap model) {
		
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		//Nece dodeli zadatak ukoliko ne se AuthenticadUserId ne postavi pre pokretanja procesa
		identityService.setAuthenticatedUserId(userId);
		//Process Start
		runtimeService.startProcessInstanceByKey(processKey);
		System.out.println("Ukupan broj pokrenutih instanci: "
				+ runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey).count());
		
		return "home";
	}
	
	
	/**
	 * This method will open task list.
	 */
	@RequestMapping(value = { "/tasklist" }, method = RequestMethod.GET)
	public String taskList(HttpSession session, ModelMap model) {
		
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		List<Task> assignedTasks = taskService.createTaskQuery().taskAssignee(userId).list();
		if(assignedTasks.size()!=0){
			model.addAttribute("assigned", assignedTasks);
		}
		
		List<Task> candidateTasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		if(candidateTasks.size()!=0){
			model.addAttribute("candidate", candidateTasks);
		}
		return "tasks";
	}
	
	/**
	 * This method will prepare data for task.
	 */
	@RequestMapping(value = { "/task/{id}/deals" }, method = RequestMethod.GET)
	public String taskDeals(HttpSession session, ModelMap model,
			@PathVariable String id) {
		
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		String taskId = id;
		model.addAttribute("taskId", taskId);
				
		// Dodeljeni zadaci
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		
		for (Task task : tasks) {
			if(task.getId().equals(taskId)){
				
				String taskName = task.getName();
				model.addAttribute("taskName", taskName);
				
				TaskFormData taskFormData = formService.getTaskFormData(task.getId());
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				System.out.println("########### FormProperties ");
				for (FormProperty property : formProperties) {
					System.out.println("PropertyName: "+property.getId());
					
					model.addAttribute("formProperties", formProperties);
				}
				
				@SuppressWarnings("unchecked")
				Map<String, Long> ppfMapSL = (Map<String, Long>) taskService.getVariable(taskId, "ponuduPrihvatileFirmeMap");
				Map<User, Long> ppfMapUL = new HashMap<User, Long>();
				for (String key : ppfMapSL.keySet()){
					ppfMapUL.put(identityService.createUserQuery().userId(key).singleResult(), ppfMapSL.get(key));
				}
				model.addAttribute("firme", ppfMapUL);
			}
		}
		return "deals";
	}
	
	/**
	 * This method will prepare data for task.
	 */
	@RequestMapping(value = { "/task/{id}" }, method = RequestMethod.GET)
	public String taskEnter(HttpSession session, ModelMap model,
			@PathVariable String id) {
		
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		HashMap<String, String> kategorijePoslovaEnum = new HashMap<String, String>();
		HashMap<String, String> brojPonudaEnum = new HashMap<String, String>();
		HashMap<String, String> ocenaPoslaEnum = new HashMap<String, String>();
		
		// Dodeljeni zadaci
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		String taskId = id;
		for (Task task : tasks) {
			if(task.getId().equals(taskId)){
				
				String taskName = task.getName();
				
				if(taskName.equals("Odluka Klijenta")){
					return "redirect:/task/{id}/deals";
				}
				model.addAttribute("taskId", taskId);
				model.addAttribute("taskName", taskName);
				
				TaskFormData taskFormData = formService.getTaskFormData(task.getId());
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				System.out.println("########### FormProperties ");
				for (FormProperty property : formProperties) {
					System.out.println("PropertyName: "+property.getId());
					if(property.getType().getName().equals("enum")){	
						
						if(property.getId().equals("kategorijaPosla")){
							
							@SuppressWarnings("unchecked")
							HashMap<String, String> values = (HashMap<String, String>) property.getType().getInformation("values");
							for(Entry<String, String> entry : values.entrySet()) {
								if(entry.getValue() != null){
									kategorijePoslovaEnum.put(entry.getKey(), entry.getValue());
								}
							}
							model.addAttribute("kategorijePoslova", kategorijePoslovaEnum);
							
						}
						if(property.getId().equals("brojPonuda")){
							
							@SuppressWarnings("unchecked")
							HashMap<String, String> values = (HashMap<String, String>) property.getType().getInformation("values");
							for (Entry<String, String> entry : values.entrySet()) {
								if(entry.getValue() != null){
									brojPonudaEnum.put(entry.getKey(), entry.getValue());
								}
							}
							model.addAttribute("brojPonuda", brojPonudaEnum);
							
						}
						if(property.getId().equals("ocenaPosla")){
							
							@SuppressWarnings("unchecked")
							HashMap<String, String> values = (HashMap<String, String>) property.getType().getInformation("values");
							for (Entry<String, String> entry : values.entrySet()) {
								if(entry.getValue() != null){
									ocenaPoslaEnum.put(entry.getKey(), entry.getValue());
								}
							}
							model.addAttribute("ocenePosla", ocenaPoslaEnum);
							
						}
					}
					model.addAttribute("formProperties", formProperties);
				}
			}
		}
		return "form";
	}
	
	
	/**
	 * This method will redirect to task form if task is assigned.
	 */
	@RequestMapping(value = { "/exec/{id}", "/exec/{id}/1", "/exec/{id}/2", "/exec/{id}/3", "/exec/{id}/4", "/exec/{id}/5", "/exec/{id}/6" }, method = RequestMethod.GET)
	public String execTaskGet1(HttpSession session, ModelMap model,
			@PathVariable String id) {
		
		String taskId = id;
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks) {
			if(task.getId().equals(taskId)){
				return "redirect:/task/{id}";
			}
		}
		return "redirect:/tasklist";
	}
	
	
	/**
	 * This method will execute task "Unos Zahteva".
	 */
	@RequestMapping(value = { "/exec/{id}/1" }, method = RequestMethod.POST)
	public String execTask1(HttpSession session, ModelMap model,
			@PathVariable String id, 
			@RequestParam("ime")String ime,
			@RequestParam("prezime")String prezime,
			@RequestParam("email")String email,
			@RequestParam("telefon")String telefon,
			@RequestParam("kategorijaPosla")String kategorijaPosla,
			@RequestParam("krajnjiRok")String krajnjiRok,
			@RequestParam("lokacija")String lokacija,
			@RequestParam("brojPonuda")String brojPonuda,
			@RequestParam("opisPosla")String opisPosla) {
		
		String taskId = id;
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks){
			if (task.getId().equals(taskId)){
				Map<String,String> enteredValues = new HashMap<String,String>();
				enteredValues.put("ime", ime);
				enteredValues.put("prezime", prezime);
				enteredValues.put("email", email);
				enteredValues.put("telefon", telefon);
				enteredValues.put("kategorijaPosla", kategorijaPosla);
				enteredValues.put("krajnjiRok", krajnjiRok);
				enteredValues.put("lokacija", lokacija);
				enteredValues.put("brojPonuda", brojPonuda);
				enteredValues.put("opisPosla", opisPosla);
				
				TaskFormData taskFormData  = formService.getTaskFormData(taskId);
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				if(formProperties!=null && !formProperties.isEmpty()){
					
					Map<String,String> enteredValuesOK = new HashMap<String,String>();
					for(FormProperty property : formProperties){
						for(Entry<String, String> entry : enteredValues.entrySet()){
							
							if(property.isWritable()){
								if(property.getId().equalsIgnoreCase(entry.getKey())){
									//enteredValues.put(formProperty.getId(), newValue);
									enteredValuesOK.put(property.getId(), entry.getValue());
								}
							}else{
								System.out.println(property.getName()+"("+property.getValue()+")");
							}
							
						}
					}
					formService.submitTaskFormData(taskId, enteredValuesOK);
					
				}else{
					taskService.complete(taskId);
					System.out.println("Zadatak izvrsen");
				}
			}
		}
		return "redirect:/tasklist";
	}
	
	/**
	 * This method will execute task "Odluka Agenta o Ponudi".
	 */
	@RequestMapping(value = { "/exec/{id}/2" }, method = RequestMethod.POST)
	public String execTask2(HttpSession session, ModelMap model,
			@PathVariable String id, 
			@RequestParam("ponudaPrihvacena")String ponudaPrihvacenaString,
			@RequestParam("ponudjenaCena")String ponudjenaCenaString) {
		
		String taskId = id;
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		System.out.println("ponuda prihvacena: " + ponudaPrihvacenaString);
		System.out.println("cena: " + ponudjenaCenaString);
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks){
			if (task.getId().equals(taskId)){
				Map<String,String> enteredValues = new HashMap<String,String>();
				enteredValues.put("ponudaPrihvacena", ponudaPrihvacenaString);
				enteredValues.put("ponudjenaCena", ponudjenaCenaString);
				
				TaskFormData taskFormData  = formService.getTaskFormData(taskId);
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				if(formProperties!=null && !formProperties.isEmpty()){
					
					Map<String,String> enteredValuesOK = new HashMap<String,String>();
					
					for(FormProperty property : formProperties){
						for(Entry<String, String> entry : enteredValues.entrySet()){
							
							if(property.isWritable()){
								if(property.getId().equalsIgnoreCase(entry.getKey())){
									//enteredValues.put(formProperty.getId(), newValue);
									enteredValuesOK.put(property.getId(), entry.getValue());
								}
							}else{
								System.out.println(property.getName()+"("+property.getValue()+")");
							}
							
						}
					}
					formService.submitTaskFormData(taskId, enteredValuesOK);
					
				}else{
					taskService.complete(taskId);
					System.out.println("Zadatak izvrsen");
				}
			}
		}
		return "redirect:/tasklist";
	}
	
	/**
	 * This method will execute task "Odluka Klijenta".
	 */
	@RequestMapping(value = { "/exec/{id}/3/{izbor}/{firmaID}" }, method = RequestMethod.GET)
	public String execTask3(HttpSession session, ModelMap model,
			@PathVariable String id,
			@PathVariable String izbor,
			@PathVariable String firmaID) {
		
		String taskId = id;
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks){
			if (task.getId().equals(taskId)){
				Map<String,String> enteredValues = new HashMap<String,String>();
				enteredValues.put("izborKlijenta", izbor);
				enteredValues.put("izabranaFirmaID", firmaID);
				if(izbor.equalsIgnoreCase("3")){
					enteredValues.put("zahtevajNovePonude", "true");	
				}
				
				TaskFormData taskFormData  = formService.getTaskFormData(taskId);
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				if(formProperties!=null && !formProperties.isEmpty()){
					
					Map<String,String> enteredValuesOK = new HashMap<String,String>();
					
					for(FormProperty property : formProperties){
						for(Entry<String, String> entry : enteredValues.entrySet()){
							
							if(property.isWritable()){
								if(property.getId().equalsIgnoreCase(entry.getKey())){
									//enteredValues.put(formProperty.getId(), newValue);
									enteredValuesOK.put(property.getId(), entry.getValue());
								}
							}else{
								System.out.println(property.getName()+"("+property.getValue()+")");
							}
							
						}
					}
					formService.submitTaskFormData(taskId, enteredValuesOK);
					
				}else{
					taskService.complete(taskId);
					System.out.println("Zadatak izvrsen");
				}
			}
		}
		return "redirect:/tasklist";
	}
	
	/**
	 * This method will execute task "Agent Odgovara".
	 */
	@RequestMapping(value = { "/exec/{id}/4" }, method = RequestMethod.POST)
	public String execTask4(HttpSession session, ModelMap model,
			@PathVariable String id, 
			@RequestParam("obrazlozenjePonude")String obrazlozenjePonude) {
		
		String taskId = id;
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		System.out.println("Obrazlozenje ponude: " + obrazlozenjePonude);
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks){
			if (task.getId().equals(taskId)){
				Map<String,String> enteredValues = new HashMap<String,String>();
				enteredValues.put("obrazlozenjePonude", obrazlozenjePonude);
				
				TaskFormData taskFormData  = formService.getTaskFormData(taskId);
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				if(formProperties!=null && !formProperties.isEmpty()){
					
					Map<String,String> enteredValuesOK = new HashMap<String,String>();
					
					for(FormProperty property : formProperties){
						for(Entry<String, String> entry : enteredValues.entrySet()){
							
							if(property.isWritable()){
								if(property.getId().equalsIgnoreCase(entry.getKey())){
									//enteredValues.put(formProperty.getId(), newValue);
									enteredValuesOK.put(property.getId(), entry.getValue());
								}
							}else{
								System.out.println(property.getName()+"("+property.getValue()+")");
							}
							
						}
					}
					formService.submitTaskFormData(taskId, enteredValuesOK);
					
				}else{
					taskService.complete(taskId);
					System.out.println("Zadatak izvrsen");
				}
			}
		}
		return "redirect:/tasklist";
	}
	
	/**
	 * This method will execute task "Klijent Donosi Odluku".
	 */
	@RequestMapping(value = { "/exec/{id}/5/{izbor}" }, method = RequestMethod.GET)
	public String execTask5(HttpSession session, ModelMap model,
			@PathVariable String id,
			@PathVariable String izbor) {
		
		String taskId = id;
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks){
			if (task.getId().equals(taskId)){
				Map<String,String> enteredValues = new HashMap<String,String>();
				enteredValues.put("odlukaKlijenta", izbor);
				
				TaskFormData taskFormData  = formService.getTaskFormData(taskId);
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				if(formProperties!=null && !formProperties.isEmpty()){
					
					Map<String,String> enteredValuesOK = new HashMap<String,String>();
					
					for(FormProperty property : formProperties){
						for(Entry<String, String> entry : enteredValues.entrySet()){
							
							if(property.isWritable()){
								if(property.getId().equalsIgnoreCase(entry.getKey())){
									//enteredValues.put(formProperty.getId(), newValue);
									enteredValuesOK.put(property.getId(), entry.getValue());
								}
							}else{
								System.out.println(property.getName()+"("+property.getValue()+")");
							}
							
						}
					}
					formService.submitTaskFormData(taskId, enteredValuesOK);
					
				}else{
					taskService.complete(taskId);
					System.out.println("Zadatak izvrsen");
				}
			}
		}
		return "redirect:/tasklist";
	}
	

	/**
	 * This method will execute task "Klijent Ocenjuje".
	 */
	@RequestMapping(value = { "/exec/{id}/6" }, method = RequestMethod.POST)
	public String execTask6(HttpSession session, ModelMap model,
			@PathVariable String id, 
			@RequestParam("ocenaPosla")String ocenaPosla) {
		
		String taskId = id;
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		for (Task task : tasks){
			if (task.getId().equals(taskId)){
				Map<String,String> enteredValues = new HashMap<String,String>();
				enteredValues.put("ocenaPosla", ocenaPosla);
				
				TaskFormData taskFormData  = formService.getTaskFormData(taskId);
				List<FormProperty> formProperties = taskFormData.getFormProperties();
				if(formProperties!=null && !formProperties.isEmpty()){
					
					Map<String,String> enteredValuesOK = new HashMap<String,String>();
					
					for(FormProperty property : formProperties){
						
						for(Entry<String, String> entry : enteredValues.entrySet()){
							
							if(property.isWritable()){
								if(property.getId().equals("izabranaFirmaID")){
									ocenaService.save(new Ocena(userId, property.getValue(), Integer.parseInt(ocenaPosla)));
								}
								if(property.getId().equalsIgnoreCase(entry.getKey())){
									//enteredValues.put(formProperty.getId(), newValue);
									enteredValuesOK.put(property.getId(), entry.getValue());
								}
							}else{
								System.out.println(property.getName()+"("+property.getValue()+")");
							}
							
						}
					}
					formService.submitTaskFormData(taskId, enteredValuesOK);
					
				}else{
					taskService.complete(taskId);
					System.out.println("Zadatak izvrsen");
				}
			}
		}
		return "redirect:/tasklist";
	}
	
}
