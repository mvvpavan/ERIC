package com.spark;
import java.io.File;
import java.io.PrintStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


import org.eclipse.jetty.util.RolloverFileOutputStream;
import org.eclipse.jetty.util.log.Log;

import static spark.Spark.*;
import spark.Request;
import spark.Response;
import spark.Route;
import spark.Spark;
import freemarker.cache.FileTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class Helloworld {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		

        //We are configuring a RolloverFileOutputStream with file name pattern  and appending property

        RolloverFileOutputStream os = new RolloverFileOutputStream("/Users/pavan.matta/Documents/workspace/ERIC/web/templates/yyyy_mm_dd_jcglogging.log", true);

        //We are creating a print stream based on our RolloverFileOutputStream

        PrintStream logStream = new PrintStream(os);

        //We are redirecting system out and system error to our print stream.

        System.setOut(logStream);

        System.setErr(logStream); 
       // port(getHerokuAssignedPort());

       

        Log.getRootLogger().info("JCG Embedded Jetty logging started.", new Object[]{});
      
		externalStaticFileLocation("web/templates/web/public");
		
		final Configuration conf=new Configuration();
		conf.setClassForTemplateLoading(Helloworld.class, "/");
		Spark.get(new Route("/"){
			public Object handle(final Request request, final Response response){
				StringWriter writer =new StringWriter();
		try{
			//Spark.staticFileLocation("/public");
			  FileTemplateLoader templateLoader = new FileTemplateLoader(new File("web/templates/web"));
		        conf.setTemplateLoader(templateLoader);
		Template helloTempate=conf.getTemplate("home.ftl");
		
		Map<String, Object> hellomap=new HashMap<String, Object>();
		hellomap.put("name","freemarker");
		helloTempate.process(hellomap, writer);
		
		}
		catch(Exception e)
		{
			halt(500);
			e.printStackTrace();
			
		}
		return writer;
			}});
		
		//server.stop();

	}
	

}