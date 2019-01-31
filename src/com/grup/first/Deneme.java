package com.grup.first;

import java.awt.Desktop;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


@WebServlet("/kou")
public class Deneme extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Deneme() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    static ArrayList<String> listele; 

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	       response.setContentType("text/html; charset=utf-8");
	       PrintWriter out=response.getWriter();
	       request.setCharacterEncoding("UTF-8");
       //request.getRequestDispatcher("deneme.jsp").forward(request, response);

        String kelime = request.getParameter("kelime");
	    String url= request.getParameter("url");
	    
	    String kelimegonder=new String(kelime);
	    String urlgonder=new String(url);


	    if(kelime.equals("")||url.equals(""))
	    {
	    	 out.println("<script type=\"text/javascript\">");
	    	   out.println("alert('Kelime veya url alanları boş olamaz!');");
	    	   out.println("location='deneme.jsp';");
	    	   out.println("</script>");
	    	return ;
	    }
	    
	    byte[] bytes = kelime.getBytes(StandardCharsets.ISO_8859_1);
	    kelime = new String(bytes, StandardCharsets.UTF_8);
	    
	    
	    bytes = kelimegonder.getBytes(StandardCharsets.ISO_8859_1);
	    kelimegonder = new String(bytes, StandardCharsets.UTF_8);
	    
        Document doc = Jsoup.connect(url).timeout(0).get();
        
        String metin=doc.select("body").text();
        
        byte[] bytes2 = metin.getBytes(StandardCharsets.UTF_8);
	    metin = new String(bytes2, StandardCharsets.UTF_8);
        
    
        int sayac=0;
        
        metin=metin.toLowerCase();
        kelime=kelime.toLowerCase();
        
        int kelime_uzunluk=kelime.length();

        char c;
        
        for(int a=0;a<kelime_uzunluk;a++)
        {
        	c=kelime.charAt(a);
        	
        	if(c=='u'||c=='o'||c=='i'||c==252||c==246||c==305)
        	sayac++;
        	
        }
        

        int us=(int) Math.pow(2, sayac);
        
        int m=0;
        
        int rastgele;
        
        listele= new ArrayList<>();

        int var=0;
        
        while(m<us)
        {
        	rastgele=(int )(Math.random()*kelime_uzunluk);
        	
        	c=kelime.charAt(rastgele);

        	if(c=='u')kelime= kelime.substring(0,rastgele)+'ü'+kelime.substring(rastgele+1);
        	else if(c=='ü')kelime=kelime.substring(0,rastgele)+'u'+kelime.substring(rastgele+1);
        	else if(c=='o')kelime=kelime.substring(0,rastgele)+'ö'+kelime.substring(rastgele+1);
        	else if(c=='ö')kelime=kelime.substring(0,rastgele)+'o'+kelime.substring(rastgele+1);
        	else if(c=='ı')kelime=kelime.substring(0,rastgele)+'i'+kelime.substring(rastgele+1);
        	else if(c=='i')kelime=kelime.substring(0,rastgele)+'ı'+kelime.substring(rastgele+1);

        	var=0;
        	
        	for(int s=0;s<listele.size();s++)
        	{
        	   if(listele.get(s).equals(kelime))
        	   {
        		   var=1;
        	   }
        	}
        	
        	if(var==0)
        	{
        		m++;
        		listele.add(kelime);
        	}
        	
        }
        int cikar=0;
        String metin2=doc.select("[class=alert alert-success]").text();
        metin2=metin2.toLowerCase();
        cikar+=kelime_Say(metin2);
        
         metin2=doc.select("[class=alert alert-info]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[class=alert alert-warning]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[class=alert alert-danger]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[type=button]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[class=panel-collapse collapse]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[class=dropdown-menu]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[class=collapse]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[class=carousel-caption]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[class=modal-dialog]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);
         
         metin2=doc.select("[data-toggle=tooltip]").text();
         metin2=metin2.toLowerCase();
         cikar+=kelime_Say(metin2);

        
        
        int kelimeler=0;
        kelimeler=kelime_Say(metin);
        
        kelimeler=kelimeler-cikar;
        
        request.setAttribute("kelimesayisi",kelimeler);
        request.setAttribute("kelime",kelimegonder);
        request.setAttribute("url",urlgonder);
        
        request.getRequestDispatcher("deneme.jsp").forward(request,response); 
        
        
        	
	}
	
	public static int  kelime_Say(String metin) {
		int sayac = 0, indis;
		char karakter = 0;
			for (int j = 0; j < listele.size(); j++) {
				indis = metin.indexOf(listele.get(j), 0);
				while (indis != -1) {

					if (indis != 0) {
						karakter = metin.charAt(indis - 1);
						
						if ((karakter >= 65 && karakter <= 90)
								|| (karakter >= 97 && karakter <= 122)
								|| karakter == '\''
								|| (karakter >= 48 && karakter <= 57)
								||karakter==231||karakter==305||karakter==287||karakter==246||karakter==351||karakter==252);
						else
							sayac++;
					} else
						sayac++;

					indis = metin.indexOf(listele.get(j),indis + 1);

						}
			}
		return sayac;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}