package com.grup.first;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Servlet implementation class Urlskor
 */
@WebServlet("/skor")
public class Urlskor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Urlskor() {
        super();
        // TODO Auto-generated constructor stub
    }

    static ArrayList<String[]> aranacakkelimeler;
    
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

		    if(kelime.equals("")||url.equals(""))
		    {
		    	 out.println("<script type=\"text/javascript\">");
		    	   out.println("alert('Kelime veya url alanları boş olamaz!');");
		    	   out.println("location='urlsirala.jsp';");
		    	   out.println("</script>");
		    	return ;
		    }
		    
	    
		byte[] bytes = kelime.getBytes(StandardCharsets.ISO_8859_1);
	    kelime = new String(bytes, StandardCharsets.UTF_8);
	    
		//**************************************************
		ArrayList<String> urller=new ArrayList<String>();
		
    	String[] temp1= kelime.split("[ \\r\\n]+");
    	String[] kelimegonder=new String[temp1.length];
    	for (int part=0;part<temp1.length;part++) {
           kelimegonder[part]=new String(temp1[part]);
    	}
    	
    	int indis=url.indexOf("http",0);
          int bas=indis;
    	while(indis>-1)
    	{
    		int indis2=url.indexOf("http",++indis);
    		if(indis2<=-1)
    			break;
    	    urller.add(url.substring(bas, indis2).trim());
    	    indis=indis2;
    		bas=indis;
            //System.out.println("indis:"+indis);

    	}
	    urller.add(url.substring(bas).trim());
	    
		
    	aranacakkelimeler=new ArrayList<String[]>();
    	
    	int sayac=0;
        char c=0;

        for(int kelimesay=0;kelimesay<temp1.length;kelimesay++)
        {
             temp1[kelimesay]=temp1[kelimesay].toLowerCase();
             int kelime_uzunluk=temp1[kelimesay].length();
             sayac=0;
             
             
             for(int a=0;a<kelime_uzunluk;a++)
             {
             	c=temp1[kelimesay].charAt(a);
            	if(c=='u'||c=='o'||c=='i'||c==252||c==246||c==305)
            		sayac++;
             }

             int us=0;
             if(sayac!=0)us=(int) Math.pow(2, sayac);
             int m=0;
             int rastgele=0;
             int var=0;
             
             
             if(sayac==0){
            	 aranacakkelimeler.add(new String[1]);
            	 aranacakkelimeler.get(kelimesay)[0]=temp1[kelimesay];}
             else   aranacakkelimeler.add(new String[us]);
             
             
             while(m<us)
             {
            	 rastgele=(int )(Math.random()* kelime_uzunluk);
          		c=temp1[kelimesay].charAt(rastgele);
             	
             	while( c!='u' && c!='ü' && c!='o' && c!='ö' && c!='ı' && c!='i')
             	{
             		rastgele=(int )(Math.random()* kelime_uzunluk);
             		c=temp1[kelimesay].charAt(rastgele);
             	}
             	if(c=='u')temp1[kelimesay]= temp1[kelimesay].substring(0,rastgele)+'ü'+temp1[kelimesay].substring(rastgele+1);
             	else if(c=='ü')temp1[kelimesay]=temp1[kelimesay].substring(0,rastgele)+'u'+temp1[kelimesay].substring(rastgele+1);
             	else if(c=='o')temp1[kelimesay]=temp1[kelimesay].substring(0,rastgele)+'ö'+temp1[kelimesay].substring(rastgele+1);
             	else if(c=='ö')temp1[kelimesay]=temp1[kelimesay].substring(0,rastgele)+'o'+temp1[kelimesay].substring(rastgele+1);
             	else if(c=='ı')temp1[kelimesay]=temp1[kelimesay].substring(0,rastgele)+'i'+temp1[kelimesay].substring(rastgele+1);
             	else if(c=='i')temp1[kelimesay]=temp1[kelimesay].substring(0,rastgele)+'ı'+temp1[kelimesay].substring(rastgele+1);

             	var=0;
             	
                //System.out.println("kelime:"+ temp1[kelimesay]);
                //System.out.println("sayim:"+ m);

             	for(int s=0;s<m;s++)
             	{
             	   if(aranacakkelimeler.get(kelimesay)[s].equals(temp1[kelimesay]))
             	   {
             		   var=1;
             	   }
             	}
             	
             	if(var==0)
             	{
             		
             		aranacakkelimeler.get(kelimesay)[m]=new String(temp1[kelimesay]);
             		m++;
             	}
             	
             }

             
        }
        
       
        
    	ArrayList<double[]> sirala=new ArrayList<double[]>();

    	   double [] cikar=new double[temp1.length];
           double [] tempcikar=new double[temp1.length];

    	for( int part=0;part<urller.size();part++)//url say�yor
    	{
    		double[] eklenecek=new double[temp1.length+3];
    		
    		
    		//System.out.println("url adi : "+urller.get(part));
    		eklenecek[temp1.length+2]=part;
    		
    		Document doc = Jsoup.connect(urller.get(part)).ignoreHttpErrors(true).timeout(0).get();
            String metin=doc.select("body").text();
            metin=metin.toLowerCase();
            double []kelimesayilari=kelime_Say(metin);
            
            int toplam=0;
         
            String metin2=doc.select("[class=alert alert-success]").text();
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
            
            
            
            
            for(int i=0;i<aranacakkelimeler.size();i++)
            	{
            	  kelimesayilari[i]-=cikar[i];
            	  cikar[i]=0;
            	eklenecek[i]=kelimesayilari[i];
                  toplam+=eklenecek[i];
            	}
            int kelimetoplam=toplam;
            toplam=toplam/(temp1.length);
            
            double toplam2=0;
            sirala.add(eklenecek);
            
            for(int d=0;d<temp1.length;d++)
            {
            	
            	toplam2+=Math.pow(toplam-sirala.get(part)[d],2);
                //System.out.println("girdim : "+toplam2);

            }
            
            if(temp1.length!=1)toplam2=toplam2/(temp1.length-1);
            
            if(Math.sqrt(toplam2)!=0){
            
			sirala.get(part)[temp1.length]=Math.sqrt(toplam2);
			sirala.get(part)[temp1.length+1]=kelimetoplam/Math.sqrt(toplam2);
            }
            else 
            {
                System.out.println(urller.get(part));
    			sirala.get(part)[temp1.length]=0.0;
    			sirala.get(part)[temp1.length+1]=-1.0;
            }
			
    	}
    	
    	//System.out.println("#####################");
    	
    	String cikti1 =new String();
    	String cikti2=new String();
    	
    	for(int x=0;x<temp1.length;x++)
    	{
           if(x==0)cikti1=cikti1+kelimegonder[x];
           else
    		cikti1=cikti1+" "+kelimegonder[x];
    		
    	}
    	//System.out.println("cikti1: "+cikti1);
    	
    	int sinir=sirala.get(0).length;
       /* 
    	for(int d=0;d<sirala.size();d++)
    	{
    		for(int e=0;e<sinir;e++)
    		{
    			System.out.println(sirala.get(d)[e]+" ");
    		}
    		System.out.println();
    	}
    	
    	System.out.println("#####################");
*/
  
    	
    	ArrayList<double[]> ekle=new ArrayList<double[]>();
    	
    	
    	
    	for(int b=sirala.size()-1;b>=0;b--)
    	{
    		sayac=0;
    		
    		for(int d=0;d<b;d++)
    		{
    			//System.out.print(d+"-"+b);
    			if(sirala.get(d)[sinir-2]!=(-1.0)&&sirala.get(d+1)[sinir-2]!=-1.0){
    			
    			if(sirala.get(d)[sinir-2]<sirala.get(d+1)[sinir-2])
    			{
    				double [] yedek= sirala.get(d+1);
    				sirala.set(d+1, sirala.get(d));
    				sirala.set(d,yedek);
    				
    			}
    			
    			}
    			else
    			{
    				if(sirala.get(d)[sinir-2]==(-1.0))
    				{
    				ekle.add(sirala.get(d));
    				//System.out.println("eklenenid"+sirala.get(d)[temp1.length+2]);
    				sirala.remove(d);
    		
    				sayac++;
    				b--;
    				d--;
    				}
    				else {
        				//System.out.println("eklenenindis:" +d);

    					ekle.add(sirala.get(d+1));
        				sirala.remove(d+1);
        		
        				sayac++;
        				b--;
        				d--;
    					
    				}
    				
    				
    			}
    				
    		}
    		
    		
    		if(sirala.get(0)[temp1.length+1]==-1&&sirala.size()==1)
    		{
    			//System.out.println("son ekemen-----------");
    			ekle.add(sirala.get(0));
				sirala.remove(0);
		
    		}
    		
    		
    	}
    	/*
    	System.out.println("------------------");

    	for(int d=0;d<sirala.size();d++)
    	{
    		for(int e=0;e<sinir;e++)
    		{
    			System.out.println(sirala.get(d)[e]+" ");
    		}
    		System.out.println();
    	}
    	
    	
    	*/
    	
    	//System.out.println("Sırala"+sirala.size()+" + "+ekle.size());
    	
    	double max_sirala=-1,min_sirala;
    	if(sirala.size()!=0) min_sirala=sirala.get(sirala.size()-1)[0];
    	
    	double toplam;
    	
    	int tplm=0,index_tut=-1;
    	
    	double azaltma;
    	
    	for(int j=sirala.size()-1;j>=1;j--){
    
    		toplam=0;
    		max_sirala=-1;
    		min_sirala=sirala.get(j)[0];
    		
    		for(int y=0;y<temp1.length;y++)    			
    		{
    			
    			if(max_sirala<sirala.get(j)[y])
    			{
    				max_sirala=sirala.get(j)[y];
    			}
    			if(min_sirala>sirala.get(j)[y])
    			{
    				min_sirala=sirala.get(j)[y];
    			}
    			toplam+=sirala.get(j)[y];
    		}
    		
    		azaltma=max_sirala-max_sirala*0.85;
    		
    	
    		
    	for(int i=j-1;i>=0;i--)
    	{
    		tplm=0;
    		
    		if(azaltma<=min_sirala){
    		
    	     	for(int y=0;y<temp1.length;y++)
    	     	{
    	     		tplm+=sirala.get(i)[y];
    	     				
    	     	}
    	     	
    	     	if(tplm<=toplam && sirala.get(j)[temp1.length]>sirala.get(i)[temp1.length] && sirala.get(j)[temp1.length+1]<sirala.get(i)[temp1.length+1])
    	     	{
    	     		index_tut=i;
    	     	}
    	     	
    		}
    	}
    	
    	if(index_tut!=-1)
    	{
    		sirala.add(index_tut,sirala.get(j));
    		
     		//System.out.println("indis_tut:" +sirala.get(j)[0]);
     	//	System.out.println("indis_tut:" +sirala.get(index_tut)[0]);
    		
			sirala.remove(j+1);
			
    		int indis_tut2=-1;
    		for(int kontrol=index_tut;kontrol>=0;kontrol--)
    		{
        		//System.out.println("burada"+index_tut+"digeri"+kontrol);

    			if(sirala.get(index_tut)[temp1.length+1]>sirala.get(kontrol)[temp1.length+1])
    				indis_tut2=kontrol;
    			
    		}

    		if(indis_tut2!=-1)
    		{
    			sirala.add(indis_tut2,sirala.get(index_tut));
    			
        		//System.out.println("burada"+sirala.get(indis_tut2)[0]+"silinen"+(sirala.get(index_tut+1)[0]));

        		sirala.remove(index_tut+1);
    			
    		}
			j++;
		
    	}

    	index_tut=-1;
    	
    	}
    	
	
    	/*System.out.println("------------------");

    	for(int d=0;d<sirala.size();d++)
    	{
    		for(int e=0;e<sinir;e++)
    		{
    			System.out.println(sirala.get(d)[e]+" ");
    		}
    		System.out.println();
    	}
    	
    	*/
    	
    	double[] skor_srl=new double[sirala.size()];
    	
    	for(int y=0;y<skor_srl.length;y++)
    	{
    		skor_srl[y]=sirala.get(y)[temp1.length+1];
    	}
    	/*for(int y=0;y<skor_srl.length;y++)
    	{
    		System.out.println("skor sirala:"+sirala.get(y)[temp1.length+1]);
    	}*/
    	
    	
    	double ydk;
    	
    	for(int y=1;y<skor_srl.length;y++)
    	{
    		for(int z=0;z<skor_srl.length-1;z++)
    		{
    			if(skor_srl[z]<skor_srl[z+1])
    			{
    				ydk=skor_srl[z];
    				skor_srl[z]=skor_srl[z+1];
    				skor_srl[z+1]=ydk;
    			}
    			
    		}
    		

    		
    	}
    	
    	for(int y=0;y<skor_srl.length;y++)
    	{
    		sirala.get(y)[temp1.length+1]=skor_srl[y];
    		//System.out.println("skor sirala:"+sirala.get(y)[temp1.length+1]);
    	}
    	
    	
    	
    	
    	
    	double ekleneceksayi=0;
    	if(sirala.size()!=0)
    	{
    		 ekleneceksayi=sirala.get(sirala.size()-1)[temp1.length+2]/ekle.size();
    				
    	}
    	else   ekleneceksayi= 0.57735026918963/ekle.size();

    	double fark=0.0;
    	
    	if(sirala.size()!=0)sinir=sirala.get(0).length;
    	else sinir=0; 
    		
    	double yedek=0.0;
    	
    	int indis_tut=-1;
		
    	int sayac_tut=0;

		//System.out.println("ekleboyut"+ekle.size()+"sıralaboyut"+sirala.size());

    	for(int j=0;j<ekle.size();j++){
    	
    		//System.out.println("bu elemanda sira"+ekle.get(j)[0]);
    		
    		fark=100000000;
    		
    		indis_tut=-1;

    		sayac_tut=0;

    		
    	for(int i=0;i<sirala.size();i++)
    	{
    		yedek=0;
    	
    		sayac=0;
    		
    		
    		for(int k=0;k<sinir-3;k++)
    		{
    		  	yedek+=Math.abs(ekle.get(j)[k]-sirala.get(i)[k]);
    		  	
    		  	if(ekle.get(j)[k]>=sirala.get(i)[k])
    		  	{
    		  		sayac++;
    	        	//System.out.println("---------------------sayac_tut"+sayac+"eleman"+ekle.get(j)[k]+"-"+sirala.get(i)[k]);

    		  	}
    		  	
    		  	
    		}
    		

    		if(yedek<fark)
    		{
    			fark=yedek;
    			indis_tut=i;
    			sayac_tut=sayac;

    		}
    	}
    	
		//System.out.println("yedek : "+ sayac_tut  );

    	
    	if(sayac_tut>=(temp1.length/2))
    	{
    		
    		

    		if(indis_tut==-1) indis_tut=0;
    		sirala.add(indis_tut,ekle.get(j));
    		if(sirala.size()==1)
    		{
    			sirala.get(0)[temp1.length+1]=0.57735026918963;
    			sinir=sirala.get(0).length;
    			
    		}
    		else if(indis_tut!=0 && indis_tut!=sirala.size()-1)
    		{
    			sirala.get(indis_tut)[temp1.length+1]=(sirala.get(indis_tut-1)[temp1.length+1]+sirala.get(indis_tut+1)[temp1.length+1])/2;
    		}
    		
    		else
    		{
               if(indis_tut==0)
               {//System.out.println("burada");
            	   sirala.get(indis_tut)[temp1.length+1]=sirala.get(indis_tut+1)[temp1.length+1]+ekleneceksayi;
               }
               else
               {
            	   sirala.get(indis_tut)[temp1.length+1]=sirala.get(indis_tut-1)[temp1.length+1]-ekleneceksayi;
               }
    		}
    		
    		if(sirala.get(indis_tut)[0]==0)
    			{
    			//System.out.println("sondayımmm");
    			sirala.get(indis_tut)[temp1.length+1]=0;
    			sirala.add(sirala.get(indis_tut));
    			sirala.remove(indis_tut);
    			
    			}
    		if(fark==0)
    		{
    			sirala.get(indis_tut)[temp1.length+1]=sirala.get(indis_tut+1)[temp1.length+1];

    		}
    		ekle.remove(j);
    		
    		j--;
    	}
    	else
    	{
    		sirala.add(indis_tut+1,ekle.get(j));
    		
    		//System.out.println(",,,,,,,,,,,,,"+sirala.get(indis_tut+1)[0]);
    		
    		if(sirala.size()==1)
    		{
    			sirala.get(indis_tut+1)[temp1.length+1]=0.57735026918963;
    			//System.out.println("burada");
    			sinir=sirala.get(0).length;
    		}
    		else if((indis_tut+1)!=0 && (indis_tut+1)!=sirala.size()-1)
    		{
    			sirala.get((indis_tut+1))[temp1.length+1]=(sirala.get(indis_tut)[temp1.length+1]+sirala.get(indis_tut+2)[temp1.length+1])/2;
    		}
    		
    		else
    		{
               if((indis_tut+1)==0)
               {
            	   sirala.get((indis_tut+1))[temp1.length+1]=sirala.get(indis_tut+2)[temp1.length+1]+ekleneceksayi;
               }
               else
               {
            	   sirala.get((indis_tut+1))[temp1.length+1]=sirala.get(indis_tut)[temp1.length+1]-ekleneceksayi;
               }
    		}
    		
    		if(sirala.get(indis_tut+1)[0]==0)
    			{
    			//System.out.println("sondayımmm"+(sirala.size()-1)+"***"+(indis_tut+1));
    			
    			sirala.get(indis_tut+1)[temp1.length+1]=0;
    			sirala.add(sirala.get(indis_tut+1));
    			sirala.remove(indis_tut+1);
    			
    			}
    		if(fark==0)
    		{
    			sirala.get(indis_tut+1)[temp1.length+1]=sirala.get(indis_tut)[temp1.length+1];

    		}
    		ekle.remove(j);
    		j--;
    	}
    	
    	}
    	
    	/*for(int d=0;d<sirala.size();d++)
    	{

    		for(int e=0;e<sinir;e++)
    		{System.out.println(sirala.get(d)[e]);
    		}
    	}
    	*/
    	
    	for(int d=0;d<sirala.size();d++)
    	{
    		if(d==0) cikti2=cikti2+(d+1)+"---> "+urller.get((int)sirala.get(d)[temp1.length+2])+" ";
    		else cikti2=cikti2+"\n"+(d+1)+"---> "+urller.get((int)sirala.get(d)[temp1.length+2])+" ";
    		for(int e=0;e<sinir;e++)
    		{
    		if(e!=temp1.length&&e!=temp1.length+2)cikti2=cikti2+sirala.get(d)[e]+" ";
    			
    		}
    	}
    	
    	//System.out.println(cikti2);
    	
    	   request.setAttribute("kelime",cikti1);
           request.setAttribute("sonuc",cikti2);
           
           request.getRequestDispatcher("urlcikti.jsp").forward(request,response); 
           
    	
    	
    	
	}
	

	public static double[] kelime_Say(String metin) {
		//System.out.println(metin);
		int sayac = 0, indis;
		char karakter = 0;
		double[] kelime_dizisi = new double[aranacakkelimeler.size()+2];
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}