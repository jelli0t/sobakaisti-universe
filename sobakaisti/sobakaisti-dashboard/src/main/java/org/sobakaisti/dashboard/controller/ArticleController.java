package org.sobakaisti.dashboard.controller;

import java.util.List;

import javax.validation.Valid;

import org.sobakaisti.core.dao.CategoryDao;
import org.sobakaisti.core.model.Article;
import org.sobakaisti.core.model.ResponseInfo;
import org.sobakaisti.core.model.Tag;
import org.sobakaisti.core.service.ArticleService;
import org.sobakaisti.core.service.CategoryService;
import org.sobakaisti.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author jelles
 * controller za zahteve koji rade sa clancima
 */
@Controller
@RequestMapping(value="/articles")
public class ArticleController {
	
	@Autowired
	private ArticleService articleService;
	
	@Autowired
	private CategoryService categoryService;
		
	@RequestMapping(method=RequestMethod.GET)
	public String displayAllArticles(Model model){
		model.addAttribute("articles", articleService.getArticlesOrderByDate());
		return "articles";
	}
	
	@RequestMapping(value="/new", method=RequestMethod.GET)
	public String createNewArticle(Model model){
		model.addAttribute("categories", categoryService.findAllCategories());
		return "article";
	}
	
	@RequestMapping(value="/new/slug", method=RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> createNewArticleSlugFromTitle(@RequestBody String title){
		System.out.println("title: "+title);
		if(title==null || title.equals("NO_DATA")){
			return new ResponseEntity<String>("Naslov poruke je prazan!", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		try{
			String slug = StringUtil.makeSlugFromTitle(title);
			return new ResponseEntity<String>(slug, HttpStatus.OK);
		}catch (Exception e) {
			return new ResponseEntity<String>("Neuspesna konverzija u slug", HttpStatus.SERVICE_UNAVAILABLE);
		}
	}
	
	@RequestMapping(value="/new/save", method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<Object> saveNewArticle(@Valid @RequestBody Article article, BindingResult result){
		if(!result.hasErrors()){
			ResponseInfo response = articleService.saveArticle(article);	
			if(response.isSuccessful()){
				/* poruka o uspesnom cuvanju clanka */
				return new ResponseEntity<Object>(response.getMessage(), HttpStatus.OK);
			}else {
				/* poruka o neuspelom cuvanju */
				return new ResponseEntity<Object>(new ObjectError("outcome", response.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}else
			return new ResponseEntity<Object>(result.getFieldError(), HttpStatus.INTERNAL_SERVER_ERROR);	
	}
			
	@RequestMapping(value="/delete/{id}", method=RequestMethod.DELETE)
	@ResponseBody
	public ResponseEntity<String> deleteArticle(@PathVariable("id") int id){	
		ResponseInfo response = articleService.deleteArticleById(id);
		if(response.isSuccessful())
			return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
		else
			return new ResponseEntity<String>(response.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}
		
	@RequestMapping(value="/change_status/{id}", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<String> switchArticleStatus(@PathVariable("id") int id) {	
		ResponseInfo response = articleService.switchArticleStatus(id);
		if(response.isSuccessful()){
			return new ResponseEntity<String>(response.getMessage(), HttpStatus.OK);
		}		
		return new ResponseEntity<String>(response.getMessage(), HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@RequestMapping(value="/tag/search", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<List<Tag>> searchArticleTags(@RequestParam("phrase") String phrase){
		List<Tag> tags = articleService.getTagSerachResult(phrase);
		return new ResponseEntity<List<Tag>>(tags, HttpStatus.OK);
	}
}
