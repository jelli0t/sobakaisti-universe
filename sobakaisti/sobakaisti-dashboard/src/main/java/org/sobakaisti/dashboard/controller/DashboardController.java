/**
 * 
 */
package org.sobakaisti.dashboard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author jelles
 *
 */
@Controller
public class DashboardController {

	/* Dashboard home page */
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String displayDashHome(){
		System.out.println("Pozivam konstuktor: DashboardController");
		return "home";
	}
}
