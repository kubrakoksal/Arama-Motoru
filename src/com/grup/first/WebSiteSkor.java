package com.grup.first;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Servlet implementation class WebSiteSkor
 */
@WebServlet("/WebSiteSkor")
public class WebSiteSkor extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public WebSiteSkor() {
		super();
		// TODO Auto-generated constructor stub
	}

	static ArrayList<String[]> aranacakkelimeler;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponseresponse)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		request.setCharacterEncoding("UTF-8");
		//request.getRequestDispatcher("deneme.jsp").forward(request, response);

        String kelime = request.getParameter("kelime");
	    String url= request.getParameter("url");

	    if(kelime.equals("")||url.equals(""))
	    {
	    	//System.out.println("drgdfhgfgh");
	    	 out.println("<script type=\"text/javascript\">");
	    	   out.println("alert('Kelime veya url alanları boş olamaz!');");
	    	   out.println("location='websitesirala.jsp';");
	    	   out.println("</script>");
	    	return ;
	    }
	    
	    
	    
		byte[] bytes = kelime.getBytes(StandardCharsets.ISO_8859_1);
		kelime = new String(bytes, StandardCharsets.UTF_8);
		String[] temp1 = kelime.split("[ \\r\\n]+");
		String[] kelimegonder=new String[temp1.length];
    	for (int part=0;part<temp1.length;part++) {
           kelimegonder[part]=new String(temp1[part]);
    	}
		
		//kelime cesitleri bulma

		aranacakkelimeler = new ArrayList<String[]>();

		int sayac = 0;
		char c = 0;

		for (int kelimesay = 0; kelimesay < temp1.length; kelimesay++) {
			temp1[kelimesay] = temp1[kelimesay].toLowerCase();
			int kelime_uzunluk = temp1[kelimesay].length();
			sayac = 0;

			// System.out.println("kelime:"+ temp1[kelimesay]);

			for (int a = 0; a < kelime_uzunluk; a++) {
				c = temp1[kelimesay].charAt(a);

					if(c=='u'||c=='o'||c=='i'||c==252||c==246||c==305)sayac++;
			}

			int us = 0;
			if (sayac != 0)
				us = (int) Math.pow(2, sayac);
			// System.out.println("kelime:"+ us);
			int m = 0;
			int rastgele = 0;
			int var = 0;

			if (sayac == 0) {
				aranacakkelimeler.add(new String[1]);
				aranacakkelimeler.get(kelimesay)[0] = temp1[kelimesay];
			} else
				aranacakkelimeler.add(new String[us]);

			while (m < us) {
				rastgele = (int) (Math.random() * kelime_uzunluk);
				c = temp1[kelimesay].charAt(rastgele);

				while (c != 'u' && c != 'ü' && c != 'o' && c != 'ö' && c != 'ı'
						&& c != 'i') {
					rastgele = (int) (Math.random() * kelime_uzunluk);
					// System.out.println("rastgele:"+ rastgele);
					c = temp1[kelimesay].charAt(rastgele);
				}
				if (c == 'u')
					temp1[kelimesay] = temp1[kelimesay].substring(0, rastgele)
							+ 'ü' + temp1[kelimesay].substring(rastgele + 1);
				else if (c == 'ü')
					temp1[kelimesay] = temp1[kelimesay].substring(0, rastgele)
							+ 'u' + temp1[kelimesay].substring(rastgele + 1);
				else if (c == 'o')
					temp1[kelimesay] = temp1[kelimesay].substring(0, rastgele)
							+ 'ö' + temp1[kelimesay].substring(rastgele + 1);
				else if (c == 'ö')
					temp1[kelimesay] = temp1[kelimesay].substring(0, rastgele)
							+ 'o' + temp1[kelimesay].substring(rastgele + 1);
				else if (c == 'ı')
					temp1[kelimesay] = temp1[kelimesay].substring(0, rastgele)
							+ 'i' + temp1[kelimesay].substring(rastgele + 1);
				else if (c == 'i')
					temp1[kelimesay] = temp1[kelimesay].substring(0, rastgele)
							+ 'ı' + temp1[kelimesay].substring(rastgele + 1);

				var = 0;

				// System.out.println("kelime:"+ temp1[kelimesay]);
				// System.out.println("sayim:"+ m);

				for (int s = 0; s < m; s++) {
					if (aranacakkelimeler.get(kelimesay)[s].equals(temp1[kelimesay])) {
						var = 1;
					}
				}

				if (var == 0) {

					aranacakkelimeler.get(kelimesay)[m] = new String(temp1[kelimesay]);
					m++;
				}

			}

		}

		String cikti1=new String();
		
		for(int x=0;x<temp1.length;x++)
    	{
           if(x==0)cikti1=cikti1+kelimegonder[x];
           else
    		cikti1=cikti1+" "+kelimegonder[x];
    		
    	}
    	//System.out.println("cikti1: "+cikti1);
	
		// url de kelime sayisi bulma
		
		ArrayList urller = new ArrayList<>(); // url dizisi
	

		//System.setProperty("http.proxyHost", "192.168.5.1");
		//System.setProperty("http.proxyPort", "1080");
		
		Document doc;
		
		String alanadi;
		int indis = url.indexOf("http", 0);
		 //System.out.println("URL :"+url);
		int bas = indis;
		
		
		int var=0;
		
		int say=0;
		
		    double sonuc[]=new double[aranacakkelimeler.size()];
		    double [] cikar=new double[aranacakkelimeler.size()];
            double [] tempcikar=new double[aranacakkelimeler.size()];
            
            String metin2;
            websitesirala yazdir = null;
            
            int bas_indis=0;
            
        ArrayList derinlikiki;
            
		while (indis > -1) {
			
			int indis2 = url.indexOf("http", ++indis);
			

			//System.out.println("indissd :"+indis);
			
			if(indis2==-1)
			alanadi = url.substring(bas).trim();

			
			else
				alanadi = url.substring(bas, indis2).trim();
		
			Set urladlari=new HashSet<>();
            derinlikiki=new ArrayList();
            
			int derinlik_id=3;

			urller.add(new websitesirala(alanadi,1));
			urladlari.add(alanadi);
			//System.out.println(alanadi);
			

			
			doc = Jsoup.connect(alanadi).ignoreContentType(true).ignoreHttpErrors(true).timeout(0).get();
			sonuc=kelime_Say(doc.body().text().toLowerCase());
			
		
			
			yazdir = (websitesirala) urller.get(bas_indis);
            yazdir.kelimeler=sonuc;
			metin2=doc.select("[class=alert alert-success]").text();
            metin2=metin2.toLowerCase();
            tempcikar=kelime_Say(metin2);
            

            
            for(int i=0;i<aranacakkelimeler.size();i++)
            	{cikar[i]+=tempcikar[i];
            	}
            
             metin2=doc.select("[class=alert alert-info]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[class=alert alert-warning]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[class=alert alert-danger]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[type=button]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[class=panel-collapse collapse]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[class=dropdown-menu]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[class=collapse]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[class=carousel-caption]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[class=modal-dialog]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}             
             metin2=doc.select("[data-toggle=tooltip]").text();
             metin2=metin2.toLowerCase();
             tempcikar=kelime_Say(metin2);
             
             for(int i=0;i<aranacakkelimeler.size();i++)
             	{cikar[i]+=tempcikar[i];
             	}

				for(int i=0;i<sonuc.length;i++)
				{
					yazdir.kelimeler[i]-=cikar[i];
					cikar[i]=0;
				}
			


			try {
			

				Elements elements = doc.select("a");
				//System.out.println(elements.size());
				for (Element element : elements) {
					if (element.absUrl("href").startsWith(alanadi)&&!element.absUrl("href").endsWith(".zip")&&!element.absUrl("href").endsWith(".rar")&&
							!element.absUrl("href").endsWith(".pdf")&&!element.absUrl("href").endsWith(".exe")&&!element.absUrl("href").endsWith(".jpeg")&&
							!element.absUrl("href").endsWith(".jpg")&&!element.absUrl("href").endsWith(".png")&&!element.absUrl("href").endsWith(".bmp")&&
							!element.absUrl("href").endsWith(".mp3")&&!element.absUrl("href").endsWith(".flv")&&!element.absUrl("href").endsWith(".ppt")
							&&!element.absUrl("href").endsWith(".pptx")&&!element.absUrl("href").endsWith(".xlsx")&&!element.absUrl("href").endsWith(".doc")
							&&!element.absUrl("href").endsWith(".docx")&&!element.absUrl("href").endsWith("#")) {
						//System.out.println("2" + element.absUrl("href"));
						//if(element.absUrl("href").contains("#"))System.out.println(element.text());
						
							if(urladlari.contains(element.absUrl("href")))
							{
								var=1;
							}
						
					
						
					if(var==0){
						System.out.println("2 esit degil"+element.absUrl("href"));

						
					say++;	
					urller.add(new websitesirala(element.absUrl("href"), 2));
					urladlari.add(element.absUrl("href"));
					doc = Jsoup.connect(element.absUrl("href")).ignoreContentType(true).ignoreHttpErrors(true).timeout(0).get();
					
					Elements elements2 = doc.select("a");
					for (Element element2 : elements2) {
					
						derinlikiki.add(new websitesirala(element2.absUrl("href"),derinlik_id));
						
					}
					
					

					
					derinlik_id++;
					sonuc=kelime_Say(doc.body().text().toLowerCase());
					yazdir=(websitesirala) urller.get(urller.size()-1);
					yazdir.kelimeler=sonuc;
					
					metin2=doc.select("[class=alert alert-success]").text();
		            metin2=metin2.toLowerCase();
		            tempcikar=kelime_Say(metin2);
		            
		            for(int i=0;i<aranacakkelimeler.size();i++)
		            	{cikar[i]+=tempcikar[i];
		            	}
		            
		             metin2=doc.select("[class=alert alert-info]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[class=alert alert-warning]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[class=alert alert-danger]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[type=button]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[class=panel-collapse collapse]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[class=dropdown-menu]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[class=collapse]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[class=carousel-caption]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[class=modal-dialog]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}             
		             metin2=doc.select("[data-toggle=tooltip]").text();
		             metin2=metin2.toLowerCase();
		             tempcikar=kelime_Say(metin2);
		             
		             for(int i=0;i<aranacakkelimeler.size();i++)
		             	{cikar[i]+=tempcikar[i];
		             	}
		            
					    for(int i=0;i<sonuc.length;i++)
						{
							yazdir.kelimeler[i]-=cikar[i];
							cikar[i]=0;
						}
					
					}
					
					var=0;
					
					}
					
				}

				//int sinir=urller.size();
				
				//System.out.println(sinir-say+" "+sinir);		

				//for(int a=sinir-say;a<sinir;a++)
				//{					
					//System.out.println(sinir+" "+urller.size());		
					//websitesirala temp = (websitesirala) urller.get(a);
					//System.out.println("cekilen url:"+temp.url);	
				  	//doc = (Document) Jsoup.connect(temp.url).ignoreHttpErrors(true).ignoreContentType(true).timeout(0).get();
						//Elements elements2 = doc.select("a");
				//System.out.println("elemsayisi: "+derinlikiki.size());

						for (int derinliksay=0;derinliksay<derinlikiki.size();derinliksay++) {
							websitesirala eleman=(websitesirala) derinlikiki.get(derinliksay);
							if (eleman.url.startsWith(alanadi)&&!eleman.url.endsWith(".zip")&&!eleman.url.endsWith(".rar")&&
									!eleman.url.endsWith(".pdf")&&!eleman.url.endsWith(".exe")&&!eleman.url.endsWith(".jpeg")&&
									!eleman.url.endsWith(".jpg")&&!eleman.url.endsWith(".png")&&!eleman.url.endsWith(".bmp")&&
									!eleman.url.endsWith(".mp3")&&!eleman.url.endsWith(".flv")&&!eleman.url.endsWith(".ppt")
								  &&!eleman.url.endsWith(".pptx")&&!eleman.url.endsWith(".xlsx")&&!eleman.url.endsWith(".doc")
								  &&!eleman.url.endsWith(".docx")&&!eleman.url.endsWith("#")) {
								
								//System.out.println("***************"+eleman.url);

								if(urladlari.contains(eleman.url))
								{
									var=1;

								}
							
						
								
								if(var==0){
									
									//System.out.println("urll"+temp.url);
									System.out.println("3esitdegil"+eleman.url);

									//urller.add(new websitesirala(element2.absUrl("href"), derinlik_id));
									urller.add(eleman);
									urladlari.add(eleman.url);

									doc = Jsoup.connect(eleman.url).ignoreHttpErrors(true).ignoreHttpErrors(true).ignoreContentType(true).timeout(0).get();
									sonuc=kelime_Say(doc.body().text().toLowerCase());
									yazdir=(websitesirala) urller.get(urller.size()-1);
									yazdir.kelimeler=sonuc;
									
									metin2=doc.select("[class=alert alert-success]").text();
						            metin2=metin2.toLowerCase();
						            tempcikar=kelime_Say(metin2);
						            
						            for(int i=0;i<aranacakkelimeler.size();i++)
						            	{cikar[i]+=tempcikar[i];
						            	}
						            
						             metin2=doc.select("[class=alert alert-info]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[class=alert alert-warning]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[class=alert alert-danger]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[type=button]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[class=panel-collapse collapse]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[class=dropdown-menu]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[class=collapse]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[class=carousel-caption]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[class=modal-dialog]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}             
						             metin2=doc.select("[data-toggle=tooltip]").text();
						             metin2=metin2.toLowerCase();
						             tempcikar=kelime_Say(metin2);
						             
						             for(int i=0;i<aranacakkelimeler.size();i++)
						             	{cikar[i]+=tempcikar[i];
						             	}
						            
										for(int i=0;i<sonuc.length;i++)
										{
											yazdir.kelimeler[i]=sonuc[i];
											yazdir.kelimeler[i]-=cikar[i];
											cikar[i]=0;
										}
									
									}
								var=0;
							}
						
						}

				//}
				 
				
				
				
			}
			catch (IOException e) {
				e.printStackTrace();
			}

			bas_indis=urller.size();
			
			if (indis2 <=-1)
				break;
			
			indis = indis2;
			bas = indis;
			// System.out.println("indis:"+indis);

		}
		
		//System.out.println("Bittiti");

		
		
		
		
		int fg;
        
		//System.out.println("kelime:" + urller.size()+"..............................");
		for (fg = 0; fg < urller.size(); fg++) {

			 yazdir = (websitesirala) urller.get(fg);
			//System.out.println("kelime:" + yazdir.id + "-" + yazdir.url + "-");
		}
		
		//System.out.println("urller.size: "+urller.size());
		
		//Skor hesaplama
		double[] yuzde=new double[aranacakkelimeler.size()];
		double[] hesap=new double[aranacakkelimeler.size()];
		double[] standarttoplama=new double[aranacakkelimeler.size()];

		
		
		int baslangic=0,son;
		double toplam=0,toplam2=0;
		ArrayList<double[]> skorlar=new ArrayList<double[]>();
		int sonuncu=0,bitti=0;
		
		for(int i=0;i<urller.size();i++)
		{
			websitesirala skorla=(websitesirala) urller.get(i);
			//System.out.println("i: "+i);

			
			if((skorla.id==1&&i!=0)||sonuncu==1)
			{
			    son=i;
			    if(i==urller.size()-1)
			    	{son++;
			    	 i++;
			    	}
				for(int a=baslangic;a<son;a++)
				{
					websitesirala hesapla=(websitesirala) urller.get(a);

					for(int x=0;x<aranacakkelimeler.size();x++)
					{
					     if(a==0)hesap[x]=hesap[x]/yuzde[x];
					     standarttoplama[x]+=(Math.pow(hesap[x]-hesapla.kelimeler[x],2));
						  
					}
					
				
				}
				toplam=0; toplam2=0;
				int y;
				for(y=0;y<aranacakkelimeler.size();y++)
				{
					standarttoplama[y]=standarttoplama[y]/(yuzde[y]-1);
					hesap[y]=Math.sqrt(standarttoplama[y]);
					yuzde[y]=(yuzde[y]*100)/(son-baslangic);
					if(hesap[y]!=0)hesap[y]=yuzde[y]/hesap[y];
					else hesap[y]=yuzde[y]/0.57735026918963;
					toplam+=hesap[y];
				}
				
				toplam=toplam/hesap.length;
				for( y=0;y<hesap.length;y++)
				{
					toplam2+=(Math.pow(toplam-hesap[y],2));
					
				}
				toplam=toplam2/(hesap.length-1);
				
				double[] eklenecek=new double[2];
				eklenecek[0]=Math.sqrt(toplam);
				eklenecek[1]=baslangic;
				skorlar.add(eklenecek);
				bitti=1;
				baslangic=i;
				
			}
			
			//System.out.println("hatalı yer i: "+i);

			
			for(int x=0;x<aranacakkelimeler.size();x++)
			{
			

				if(skorla.kelimeler[x]!=0)
				  yuzde[x]++;
			     
			  hesap[x]+=skorla.kelimeler[x];
			  
			}
		
			
			if(i==urller.size()-1)
			{
				sonuncu=1;
				i--;
			}
			
			
			if(bitti==1)
			{

				Arrays.fill(yuzde, 0);
			    
			    Arrays.fill(hesap, 0);
				
			    Arrays.fill(standarttoplama, 0);
			    bitti=0;
				
			}
			
			
		}
		
		
		
		double yedek;

    	for(int b=skorlar.size()-1;b>=0;b--)
    	{
    		for(int d=0;d<b;d++)
    		{
    			
    			if(skorlar.get(d)[0]>skorlar.get(d+1)[0])
    			{
    				yedek=skorlar.get(d)[0];
    				skorlar.get(d)[0]=skorlar.get(d+1)[0];
    				skorlar.get(d+1)[0]=yedek;
    			}
    			
    		}
		
    	}
    	
    	
    	for(int i=0;i<urller.size();i++)
    	{
    	websitesirala x=(websitesirala) urller.get(i);
    		System.out.println("urller"+x.id);
    	}
    	
    	
		
    	String cikti2=new String();
    	for(int i=0;i<skorlar.size();i++){
    		
    		cikti2=cikti2+"\n 1--> "+((websitesirala)urller.get((int) skorlar.get(i)[1])).url+" "+skorlar.get(i)[0];
    		
    		for(int x=0;x<temp1.length;x++)
    		{
    			cikti2=cikti2+" "+((websitesirala)urller.get((int) skorlar.get(i)[1])).kelimeler[x];
    		}
    		
    		int derinkliksay=0;
    		int derinlikbas=0;
    		
    	    while(true){
    	    	if((derinkliksay+1)==urller.size())
    	    	{
        	    	derinlikbas=urller.size();

    	    		break;
    	    	}
    	    		websitesirala al=((websitesirala)urller.get((int) skorlar.get(i)[1]+derinkliksay+1));
    	    	if(al.id==2)
    	    		derinkliksay++;
    	    	else if(al.id==1||al.id>2)
    	    		{
    	    		derinlikbas=(int) (skorlar.get(i)[1]+1+derinkliksay);
    	    		break;
    	    		}
    	    
    	    }
    	    System.out.println("derinlikbas : "+ derinlikbas+ " "+ derinkliksay+" "+skorlar.get(i)[1]);
    	    
    	    int idsayac=1;
    	    for(int h=(int) (skorlar.get(i)[1]+1);h<derinlikbas;h++)
    	    {
    	    	websitesirala al=((websitesirala)urller.get(h));

    	    	cikti2=cikti2+"\n 2-->"+al.url;

        		for(int x=0;x<temp1.length;x++)
        		{
        			cikti2=cikti2+" "+al.kelimeler[x];
        			
        		}
    	    	
    	    	 for(int k=derinlikbas;;k++)
    	    	    {
    	    		 
    	    		 if(k==urller.size())
    	    		 break;
 	    	    	websitesirala al2=((websitesirala)urller.get(k));

    	    	    	if(al2.id==1||al2.id>(2+idsayac))
    	    	    	{
    	    	    		//System.out.println("buradaaaaaaaa");
    	    	    		break;
    	    	    	}
    	    	    	else if(al2.id==(2+idsayac))
    	    	    	{

    	        	    	cikti2=cikti2+"\n3-->"+al2.url;

    	            		for(int x=0;x<temp1.length;x++)
    	            		{
    	            			cikti2=cikti2+" "+al2.kelimeler[x];
    	            			
    	            		}
    	    	    		
    	    	    	}
    	    	    	
    	    	    }
    	    	 idsayac++;
    	    }
    	   
    		
    	}
    	
    	
    	 request.setAttribute("kelime",cikti1);
         request.setAttribute("sonuc",cikti2);
         
         request.getRequestDispatcher("websitecikti.jsp").forward(request,response); 
         
  	
    	
		//System.out.println("cikti2: "+cikti2);

		//System.out.println("algoritma bitti");
		
		

	}

	//static int f=0;
	public static double[] kelime_Say(String metin) {
		

		int sayac = 0, indis;
		char karakter = 0;
		double[] kelime_dizisi = new double[aranacakkelimeler.size()];
		for (int i = 0; i < aranacakkelimeler.size(); i++) {
			for (int j = 0; j < aranacakkelimeler.get(i).length; j++) {
				
				indis = metin.indexOf(aranacakkelimeler.get(i)[j], 0);
				
				while (indis != -1) {

					if (indis != 0) {
						karakter = metin.charAt(indis - 1);

						if ((karakter >= 65 && karakter <= 90)
								|| (karakter >= 97 && karakter <= 122)
								|| karakter == '\''
								|| (karakter >= 48 && karakter <= 57)
								|| (karakter==231||karakter==305||karakter==287||karakter==246||karakter==351||karakter==252));

							
						else
							sayac++;
					} else
						sayac++;
					
			
					indis = metin.indexOf(aranacakkelimeler.get(i)[j],indis + 1);

				}

			}

			kelime_dizisi[i] = sayac;
			sayac=0;

		}
		
		return kelime_dizisi;

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

	}

}

