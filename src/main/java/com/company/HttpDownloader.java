package com.company;

import org.w3c.dom.ranges.Range;

import java.awt.font.NumericShaper;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.http.HttpRequest;

public class HttpDownloader implements Runnable{
    String link;
    String name;
    String str;
    long start;
    long end;


    public HttpDownloader(String link ,String name,long start,long end,String str)
    {
        this.link=link;
        this.name=name;
        this.start= start;
        this.end=end;
        this.str=str;

    }
    @Override
    public void run() {
        try {
            URL url= new URL(link);
            HttpURLConnection http=(HttpURLConnection)url.openConnection();

            String downloadRange = "bytes="+start+"-"+end;
            http.setRequestProperty("Range",downloadRange);
            int filerange= (int)http.getContentLengthLong();
            String filetype=(String)http.getContentType()+".";
            File out = new File(name+"."+str);
            BufferedInputStream in=new BufferedInputStream(http.getInputStream());
            FileOutputStream fos=new FileOutputStream(out);

            System.out.println("File "+name+" has started downloading");
            System.out.println("File range starts from "+start +" and ends at "+end+".");
            BufferedOutputStream bout=new BufferedOutputStream(fos,1);
            byte[] buffer=new byte[1];

            int read=0;
            while((read=in.read(buffer,0, 1))>=0){
                bout.write(buffer,0,read);
            }
            fos.close();
            System.out.println("File "+name+" has been downloaded successfully...");
            System.out.println("**********************************************");
        }

        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
