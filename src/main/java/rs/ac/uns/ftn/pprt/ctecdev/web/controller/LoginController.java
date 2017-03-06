package rs.ac.uns.ftn.pprt.ctecdev.web.controller;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/")
public class LoginController {

	@Autowired
	IdentityService identityService;


	/**
	 * This method will open login page - index.jsp.
	 */
	@RequestMapping(value = { "/", "/login" }, method = RequestMethod.GET)
	public String index(HttpSession session) {
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId != null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/home";
		}
		return "index";
	}
	
	/**
	 * This method will check user login.
	 */
	@RequestMapping(value = { "/login" }, method = RequestMethod.POST)
	public String login(@RequestParam("username")String id, @RequestParam("password")String password,
			HttpSession session, ModelMap model) {
		
		if(identityService.checkPassword(id, password)){
	
			identityService.setAuthenticatedUserId(id);	
			session.setAttribute("loggedUserId", id);
			return "redirect:/home";
			
		}else{
			
			model.addAttribute("error", "loginFailed");
			return "index";
		
		}
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logOut(HttpSession session){
		session.invalidate();
		return "redirect:/login";
	}
}
