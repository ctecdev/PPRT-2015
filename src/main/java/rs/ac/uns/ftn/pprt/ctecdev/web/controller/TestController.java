package rs.ac.uns.ftn.pprt.ctecdev.web.controller;

import java.util.List;
import javax.servlet.http.HttpSession;

import org.activiti.engine.FormService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value="api/test")
public class TestController {
	
	@Autowired
    RuntimeService runtimeService;
	
	@Autowired
	IdentityService identityService;
	
	@Autowired
	TaskService taskService;
	
	@Autowired
	FormService formService;
	
	private static String processKey = "myProcess";
	private static String username = "pera";
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	String string(){
		return "StartPage";
	}
	
	@RequestMapping(value="/start", method=RequestMethod.GET)
	String startProcess(HttpSession session){
		
		System.out.println();
		System.out.println("Prijava korisnika");
		session.setAttribute("loggedUserId",username);
		identityService.setAuthenticatedUserId(username);
		System.out.println("Korisnik pera prijavljen");
		System.out.println();
		
		runtimeService.startProcessInstanceByKey(processKey);
		System.out.println("Ukupan broj pokrenutih instanci: "
				+ runtimeService.createProcessInstanceQuery().processDefinitionKey(processKey).count());
		
		return "Test App Spring Boot - Proces Pokrenut";
		
	}
	
	@RequestMapping(value="/listMy", method=RequestMethod.GET)
	String listMy(HttpSession session){
		
		String userId = (String) session.getAttribute("loggedUserId");
		List<Task> tasks = taskService.createTaskQuery().taskAssignee(userId).list();
		if (tasks.size() == 0){
			return "Nema direktno dodeljenih zadataka";
		}
		else{
			System.out.println("Dodeljeni zadaci: ");
			for (Task t : tasks){
				System.out.println(t.getName() + ", id: " + t.getId());
				System.out.println("Da biste prihvatili zadatak ukucajte: /exec-" + t.getId());
			}
		return "Dodeljeni zadaci >>>";
		}
	}
	
	@RequestMapping(value="/listCandidate", method=RequestMethod.GET)
	String listCandidate(HttpSession session){
		
		String userId = (String) session.getAttribute("loggedUserId");
		List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userId).list();
		if (tasks.size() == 0){
			return "Nema zadataka za koje ste kandidat";
		}
		else{
			System.out.println("Zadaci koje mozete prihvatiti: ");
			for (Task t : tasks){
				System.out.println(t.getName() + ", id: " + t.getId());
				System.out.println("Da biste prihvatili zadatak ukucajte: /claim-" + t.getId());
			}
			
			return "Zadaci koje mozete prihvatiti:";
		}
	}
	
	@RequestMapping(value="/claim-{id}", method=RequestMethod.GET)
	String claim(@PathVariable String id, HttpSession session){
		
		String userId = (String) session.getAttribute("loggedUserId");
		taskService.claim(id, userId);
		return "Prihvatili ste zadatak";
	}
	
	@RequestMapping(value="/exec-{id}", method=RequestMethod.GET)
	String exec(@PathVariable String id, HttpSession session){
		
		String userId = (String) session.getAttribute("loggedUserId");
		//String assigned = id; //id processa
		
		System.out.println("### id = "+id );
		
		for (Task t : taskService.createTaskQuery().taskAssignee(userId).list())
			if (t.getId().equals(id)){
				taskService.complete(id);
			}
		return "Zadatak izvrsen";
	}
	
}
