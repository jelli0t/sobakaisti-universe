package org.sobakaisti.dashboard.controller;

import javax.validation.Valid;

import org.sobakaisti.core.model.Author;
import org.sobakaisti.core.model.ResponseInfo;
import org.sobakaisti.core.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/sobakaisti")
public class SobakaistiController {
	
	@Autowired
	private AuthorService authorService;
	
	@RequestMapping(method=RequestMethod.GET)
	public String showSobakaistiHome(Model model){
		model.addAttribute("name", "Sobakaisti");
		model.addAttribute("authors", authorService.findAllActiveAuthors());		
		return "authors";
	}
	
	@RequestMapping(value="/add", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<Object[]> addNewSoakais(@Valid @RequestBody Author author, BindingResult result){
		Object[] authors = new Author[1];
		if(result.hasErrors()){
			Object[] errors = result.getFieldErrors().toArray();
			return new ResponseEntity<Object[]>(errors, HttpStatus.BAD_REQUEST);			
		}else{
			authors[0] = authorService.saveAndReturnAuthor(author);
		}				
		return new ResponseEntity<Object[]>(authors, HttpStatus.OK);
	}
	

	@RequestMapping(value="/delete/{id}", method = RequestMethod.DELETE)
	@ResponseBody	
	public ResponseEntity<String> deleteAuthor(@PathVariable("id") int id){
		try{
			ResponseInfo response = authorService.deleteAuthorById(id);
			if(response.isSuccessful()){
				return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
			}else {
				return new ResponseEntity<String>(response.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
			}
		}catch (Exception e) {
			return new ResponseEntity<String>("Neuspela operacija", HttpStatus.SERVICE_UNAVAILABLE);
		}	
	}
	
}
