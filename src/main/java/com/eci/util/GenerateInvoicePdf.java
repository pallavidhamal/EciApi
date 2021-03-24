
 package com.eci.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import com.eci.daoImp.InstallationDaoImp;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;



public class GenerateInvoicePdf
{
  InputStream inputStream = InstallationDaoImp.class.getClassLoader().getResourceAsStream("application.properties");
  
  public Properties prop = new Properties();
  

  String DEST = "";
  String ICC = "";
  String FONT = "";
  String FONTB = "";
  String IMG1 = "";
  

  public GenerateInvoicePdf() {}


  DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
  Date date = new Date();
  HttpServletRequest request = null;
  
  public String generateInvoice(String incid, String pname, String cname) throws DocumentException, IOException {
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;
    
    String TaskidD = incid;
    int TaskidDnew =  Integer.parseInt(incid)-1;

    System.out.println(" ---------generateInvoice Pdf----------- " + pname + "" + cname);
    
    String filePath = "";
    String filename = "";
    String fpath = "";
    
    String IMGL = "E:\\PDFGenerate\\Untitled.png";
    String IMG11 = "E:\\PDFGenerate\\IMG1.png";
    String IMG12 = "E:\\PDFGenerate\\IMG2.png";
    String IMG13 = "E:\\PDFGenerate\\IMG3.png";
    String IMG14 = "E:\\PDFGenerate\\IMG4.png";
    String IMG15 = "E:\\PDFGenerate\\IMG5.png";
    String IMG16 = "C:\\PDFGenerate\\eci.png";
    
    String photoIMG = "D:\\SpringJavaAsh1\\Eci\\uploadsUploadPhoto\\420_2019.04.10.14.44.32_420.jpg";
    

    Font font7 = new Font(Font.FontFamily.TIMES_ROMAN, 7.0F);
    Font font9 = new Font(Font.FontFamily.TIMES_ROMAN, 9.0F);
    Font font48b = new Font(Font.FontFamily.TIMES_ROMAN, 48.0F, 1);
    Font font9b = new Font(Font.FontFamily.TIMES_ROMAN, 9.0F, 1);
    
    try
    {
   //   Document document = new Document(PageSize.A4, 30.0F, 30.0F, 60.0F, 0.0F);
    	Document document = new Document();
    //	 String PATH = "D:/"; for local

     String PATH = System.getProperty("user.dir");//for server
      System.out.println("----UPLOAD_FOLDEdR----" + PATH); 
     // PATH=PATH.replace("\\","\\\\");
     
      String condir = PATH.concat("/webapps/ProApp"); //server
   // String condir = PATH.concat("/ATPPDF"); //local
      
      
      String directoryName = condir.concat("/GeneratePDF"); //server      
   //   String directoryName = condir.concat("/PDFGenerate"); //local
      
      System.out.println("----UPLOAD_FOLDEdR--directoryName--" + directoryName);
      
     File directory = new File(directoryName);
   //   File directory = new File(PATH);
      
      System.out.println("----UPLOAD_FOLDEdR--directory--" + directory);
      
      if (!directory.exists())
      {
        System.out.println("creating folder");
        
        directory.mkdir();
      }
      System.out.println("creating folder directory "+directory);

     PdfWriter.getInstance(document, new FileOutputStream(directory + "/ATP" + TaskidDnew + ".pdf")); //server      //prev sTaskidD 
    //  PdfWriter.getInstance(document, new FileOutputStream("C:\\Users\\admin\\workspace\\ECI_WEB\\WebContent\\PDFGenerate" + "/ATP" + TaskidD + ".pdf")); //local
      
      
      document.open();
      
      
     // document.setMargins(30.0F, 30.0F, 0.0F, 0.0F);
   
			/*
			 * Rectangle rect = new Rectangle(577.0F, 825.0F, 18.0F, 15.0F);
			 * rect.enableBorderSide(1); rect.enableBorderSide(2); rect.enableBorderSide(4);
			 * rect.enableBorderSide(8); rect.setBorderColor(BaseColor.BLACK);
			 * rect.setBorderWidth(1.0F);
			 */
      
      
      //document.add(rect);
      

      PdfPTable tblnew = new PdfPTable(1);
      tblnew.addCell(getCellh(pname + " " + cname, 2));
      
      document.add(tblnew);
      

      PdfPTable tableimghlogo = new PdfPTable(1);
      tableimghlogo.setWidthPercentage(60.0F);
      tableimghlogo.size();
      tableimghlogo.getDefaultCell().setColspan(100); 
      //tableimghlogo.addCell(createImageCell("C:\\PDFGenerate\\eci.png"));
      tableimghlogo.addCell(createImageCell(IMG16));
      
      tableimghlogo.setSpacingAfter(50.0F);
      document.add(tableimghlogo);
      

      PdfPTable tableeq11 = new PdfPTable(2);
      tableeq11.setWidthPercentage(100.0F);
      tableeq11.setSpacingAfter(50.0F);
      tableeq11.setSpacingBefore(20.0F);
    
      conn = GetDBConnection.getConnection();
      stmt = conn.createStatement();
      
      Statement stmt1 = conn.createStatement();
      

      String str026 = "SELECT * FROM eci.inctaskwiseatp inc, task_master tm, stagecategory s where inc.TaskId = tm.TaskId and tm.TaskId = StageId  and inc.IncId = " + TaskidD + " group by inc.TaskId;";
      System.out.println("str026 : "+str026);

      ResultSet rs1 = stmt.executeQuery(str026);
      
      tableeq11.setSpacingAfter(100.0F);
      
      while (rs1.next())
      {
        tableeq11.addCell(getCellBP(rs1.getString("TaskName"), 1));
        tableeq11.setSpacingBefore(20.0F);
        String strTaskId = rs1.getString("TaskId");
        String catfr = rs1.getString("CatFor");
        

        System.out.println(strTaskId);
        tableeq11.setSpacingAfter(50.0F);
        
        conn = GetDBConnection.getConnection();
        stmt = conn.createStatement();
        
        stmt = conn.createStatement();
        
        String str02 = "select * from stagecategory  where StageId =" + strTaskId + " ";
        System.out.println("str02 : "+str02);
        
        ResultSet rs2 = stmt.executeQuery(str02);
        
        while (rs2.next())
        {
          tableeq11.addCell(getCellh(rs2.getString("Category"), 0));
          
          String strtblid = rs2.getString("Id");
          String stageid = rs2.getString("StageId");
   
          tableeq11.setSpacingAfter(50.0F);
          tableeq11.setSpacingBefore(20.0F);
          
          conn = GetDBConnection.getConnection();
          stmt = conn.createStatement();
          String str03 ="";
        
          if(stageid.equals("7"))
          {
          str03 = "SELECT inc.IncId, inc.TaskId, inc.TestId, atp.TaskCategoryId, atp.parameter, inc.TestValue FROM eci.inctaskwiseatp inc, atp_tests atp where "
          		+ " inc.TestId = atp.TestId and inc.IncId = " + TaskidD + " and inc.TaskId =" + stageid + "  and atp.TaskCategoryId = " + strtblid + " and "
          		+ " ( atp.MIGType=(select Type_of_Migration from installation where id=" + TaskidD + ")  or atp.MIGType is null  ) order by inc.GroupId ,atp.Seq;";
          
          }
          else
          {
        	  str03 = "SELECT inc.IncId, inc.TaskId, inc.TestId, atp.TaskCategoryId, atp.parameter, inc.TestValue FROM eci.inctaskwiseatp inc, atp_tests atp where "
                		+ " inc.TestId = atp.TestId and inc.IncId = " + TaskidD + " and inc.TaskId =" + stageid + "  and atp.TaskCategoryId = " + strtblid + " order by inc.GroupId ,atp.Seq; ";                
          }
          System.out.println("str03 : "+str03);
          
          ResultSet rs3 = stmt.executeQuery(str03);
          
          
          tableeq11.addCell(getCellC("Parameter", 1));
          tableeq11.addCell(getCellC("Values", 1));
          
          String imageFile ="";
          
          while (rs3.next())
          {
        	  System.out.println("rs3  parameter : "+rs3.getString("parameter"));
        	  System.out.println("rs3  TestValue : "+rs3.getString("TestValue"));
        	  
        	  tableeq11.addCell(getCellH(rs3.getString("parameter"), 1));
        	  // tableeq11.addCell(getCellH(rs3.getString("TestValue"), 1));
            
        	  if(rs3.getString("parameter").equalsIgnoreCase("Photo 1")) {
        		 
	            	 System.out.println("this is Photo 1 url "+rs3.getString("TestValue"));
	            	 if(rs3.getString("TestValue")!=null) {
		            		imageFile= rs3.getString("TestValue");    
		            	}
	            	 
	            	 System.out.println("this is Photo imageFile  "+imageFile);
	            	// Image image1 = Image.getInstance(imageFile);
	            	 Image image1 = null;
	            	 File file = new File(imageFile);
	            	 if(!file.exists()) {
	            		// image1 = Image.getInstance("D:\\NOIMG.png");
	            		 
	            		 image1 = Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/ProApp/GeneratePDF/NOIMG.png");
	            		 
	            	 }else {
	            		 image1 = Image.getInstance(imageFile);
	            	 }
	            	 
	            	 tableeq11.addCell(image1);
            	
        	  }  else if(rs3.getString("parameter").equalsIgnoreCase("Photo 2")) {
						  
	        		  System.out.println("this is Photo 2 url "+rs3.getString("TestValue"));
							  
	        		  if(rs3.getString("TestValue")!=null) {
		            		imageFile= rs3.getString("TestValue");    
		            	}
	        		  System.out.println("this is Photo imageFile  "+imageFile);
					//  Image image1 = Image.getInstance(imageFile);
					  
					// Image image1 = Image.getInstance(imageFile);
		            	 Image image2 = null;
		            	 File file = new File(imageFile);
		            	 if(!file.exists()) {
		            	//	 image2 = Image.getInstance("D:\\NOIMG.png");
		            		 
		            		 image2 = Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/ProApp/GeneratePDF/NOIMG.png");
		            		 
		            		 
		            	 }else {
		            		 image2 = Image.getInstance(imageFile);
		            	 }
					  
					  tableeq11.addCell(image2);
							  
			    }else if(rs3.getString("parameter").equalsIgnoreCase("Photo 3")) {
			    	
			    		System.out.println("this is Photo 3 url "+rs3.getString("TestValue"));
						  
			    		 if(rs3.getString("TestValue")!=null) {
			            		imageFile= rs3.getString("TestValue");    
			            	}
			    		 System.out.println("this is Photo imageFile  "+imageFile); 
			    		
			    		//Image image1 = Image.getInstance(imageFile); 
			    		 Image image3 = null;
		            	 File file = new File(imageFile);
		            	 if(!file.exists()) {
		            		 
		            		// image3 = Image.getInstance("D:\\NOIMG.png");
		            		 image3 =Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/ProApp/GeneratePDF/NOIMG.png");
		            		 
		            	 }else {
		            		 image3 = Image.getInstance(imageFile);
		            	 }
			    		
			    		tableeq11.addCell(image3);
						 
				  }else if(rs3.getString("parameter").equalsIgnoreCase("Photo 4")) {
						  
						  System.out.println("this is Photo 4 url "+rs3.getString("TestValue"));
						  
						  if(rs3.getString("TestValue")!=null) {
			            		imageFile= rs3.getString("TestValue");    
			            	}
						  System.out.println("this is Photo imageFile  "+imageFile); 
						 // Image image1 = Image.getInstance(imageFile); 
						  Image image4 = null;
			            	 File file = new File(imageFile);
			            	 if(!file.exists()) {
			            		 
			            	//	 image4 = Image.getInstance("D:\\NOIMG.png");
			            		 image4 = Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/ProApp/GeneratePDF/NOIMG.png");
			            		 
			            	 }else {
			            		 image4 = Image.getInstance(imageFile);
			            	 }
						  
						  tableeq11.addCell(image4); 
				}else if(rs3.getString("parameter").equalsIgnoreCase("Photo 5")) {
					  
					  System.out.println("this is Photo 5 url "+rs3.getString("TestValue"));
					  
					  if(rs3.getString("TestValue")!=null) {
		            		imageFile= rs3.getString("TestValue");    
		            	}
					  System.out.println("this is Photo imageFile  "+imageFile);
					  //Image image1 =  Image.getInstance(imageFile); 
					  
					  Image image5 = null;
		            	 File file = new File(imageFile);
		            	 if(!file.exists()) {
		            		
		            		// image5 = Image.getInstance("D:\\NOIMG.png");
		            		 image5 = Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/ProApp/GeneratePDF/NOIMG.png");
		            		 
		            	 }else {
		            		 image5 = Image.getInstance(imageFile);
		            	 }
					  tableeq11.addCell(image5); 
			}else if(rs3.getString("parameter").equalsIgnoreCase("Photo 6")) {
				  
				  System.out.println("this is Photo 5 url "+rs3.getString("TestValue"));
				  
				  if(rs3.getString("TestValue")!=null) {
	            		imageFile= rs3.getString("TestValue");    
	            	}
				  System.out.println("this is Photo imageFile  "+imageFile);
				 // Image image1 =  Image.getInstance(imageFile); 
				  
				  Image image6 = null;
	            	 File file = new File(imageFile);
	            	 if(!file.exists()) {
	            		 
	            		// image6 = Image.getInstance("D:\\NOIMG.png");
	            		 image6 = Image.getInstance("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/ProApp/GeneratePDF/NOIMG.png");
	            		 
	            	 }else {
	            		 image6 = Image.getInstance(imageFile);
	            	 }
				  
				  
				  
				  tableeq11.addCell(image6); 
				}
				  else 
						  
						  {
							/*
							 * File file = new File(rs3.getString("TestValue")); // Image image1=null;
							 * 
							 * if(!file.exists()) { tableeq11.addCell(
							 * getCellH("C:\\Program Files\\Apache Software Foundation\\Tomcat 9.0/webapps/ProApp/GeneratePDF/NOIMG.png"
							 * , 1)); }else { tableeq11.addCell(getCellH(rs3.getString("TestValue"), 1)); }
							 */
						String testVal=""; 
						if(rs3.getString("TestValue")!=null) {
							testVal= rs3.getString("TestValue");    
		            	}
						
						  tableeq11.addCell(getCellH(testVal, 1));
						  
						  }
						 
            
            
            
          }
        }
        

       // document.add(rect);
      }
      
      tableeq11.setSpacingAfter(50.0F);
      document.add(tableeq11);
          document.close();
      System.out.println(" ---------DEST----------- " + DEST);
      

      System.out.println("---------end-----");
    }
    catch (Exception e)
    {
      e.printStackTrace();
      filePath = "no file";
    }
    
    return filePath;
  }
  

  private PdfPCell getCellh(String string, int alignCenter)
  {
    float fntSize = 15.0F;
    float lineSpacing = 10.0F;
    

    PdfPCell cell = new PdfPCell(new Phrase(new Phrase(new Phrase(lineSpacing, string, FontFactory.getFont("Courier", fntSize, 1)))));
    cell.setPadding(20.0F);
    cell.setColspan(2);
    cell.setHorizontalAlignment(alignCenter);
    cell.setBorder(0);
    return cell;
  }
  
  public PdfPCell getCell(String text, int alignment)
  {
    float fntSize = 15.0F;
    float lineSpacing = 10.0F;
    
    PdfPCell cell = new PdfPCell(new Phrase(new Phrase(lineSpacing, text, FontFactory.getFont("Courier", fntSize, 1, BaseColor.BLUE))));
    cell.setPadding(5.0F);
    cell.setHorizontalAlignment(alignment);
    cell.setBorder(0);
    

    return cell;
  }
  
  public static PdfPCell createImageCell(String path) throws DocumentException, IOException { Image img = Image.getInstance(path);
    PdfPCell cell = new PdfPCell(img, true);
    img.scaleToFit(100.0F, 100.0F);
    cell.setBorder(0);
    return cell;
  }
  
  public PdfPCell getCellB(String text, int alignment) { PdfPCell cell = new PdfPCell(new Phrase(text));
    cell.setPadding(7.0F);
    cell.setHorizontalAlignment(alignment);
    return cell;
  }
  
  public PdfPCell getCellH(String value, int alignment) {
    PdfPCell cell = new PdfPCell();
    
    cell.setUseAscender(true);
    cell.setUseDescender(true);
    cell.setBackgroundColor(BaseColor.WHITE);
    

    Paragraph p = new Paragraph(value);
    p.setAlignment(alignment);
    cell.addElement(p);
    cell.setPadding(5.0F);
    return cell;
  }
  
  public PdfPCell getCellC(String value, int alignment) {
    PdfPCell cell = new PdfPCell();
    cell.setUseAscender(true);
    cell.setUseDescender(true);
    cell.setBackgroundColor(BaseColor.GRAY);
    
    Paragraph p = new Paragraph(value);
    p.setAlignment(alignment);
    cell.setPadding(5.0F);
    float fntSize = 20.0F;
    float lineSpacing = 15.0F;
    cell.setPadding(5.0F);
    cell.addElement(p);
    return cell;
  }
  
  public PdfPCell getCellBP(String text, int alignment)
  {
    float fntSize = 20.0F;
    float lineSpacing = 15.0F;
    


    PdfPCell cell = new PdfPCell(new Phrase(new Phrase(lineSpacing, text, FontFactory.getFont("Courier", fntSize, 1, BaseColor.BLUE))));
    cell.setPaddingTop(20.0F);
    cell.setPadding(25.0F);
    cell.setColspan(2);
    
    cell.setHorizontalAlignment(alignment);
    return cell;
  }
  

  public PdfPCell getCellM(String text, int alignment)
  {
    PdfPCell cell = new PdfPCell(new Phrase(new Phrase(text)));
    cell.setPadding(5.0F);
    cell.setHorizontalAlignment(alignment);
    cell.setBorder(0);
    return cell;
  }
}
 