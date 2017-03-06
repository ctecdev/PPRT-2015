package rs.ac.uns.ftn.pprt.ctecdev.web.controller;

import javax.servlet.http.HttpSession;

import org.activiti.engine.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")
public class HomeController {

	@Autowired
	IdentityService identityService;


	/**
	 * This method will open home page - home.jsp.
	 */
	@RequestMapping(value = { "/home" }, method = RequestMethod.GET)
	public String home(HttpSession session, ModelMap model) {
		String userId = (String) session.getAttribute("loggedUserId");
		if(userId == null){
			System.out.println("### loggedUserId: "+ userId);
			return "redirect:/login";
		}
		return "home";
	}
	
}
