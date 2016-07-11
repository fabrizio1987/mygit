package rates.controller;

import java.util.Properties;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
@RequestMapping("/hello")
public class HelloWorldController{
 
   @RequestMapping(method = RequestMethod.GET)
   public String printHello(ModelMap model) {
      model.addAttribute("msg", "Hello Spring MVC Framework!");
      JobOperator jobOperator = BatchRuntime.getJobOperator();
      long jobId = jobOperator.start("ratesProcessJob", new Properties());
      model.addAttribute("msg1", "job " + jobId);
      return "/WEB-INF/pages/hello.jsp";
   }

}