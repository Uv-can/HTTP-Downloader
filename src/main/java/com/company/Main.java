package com.company;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {


    public static void main(String[] args) throws IOException, InterruptedException {
        String link = args[0];
        String connections = args[1];
        int n=Integer.parseInt(connections);
        System.out.println();
        String name = null;
        URL url = new URL(link);
        // load is object which stores http connection to link
        HttpURLConnection load = (HttpURLConnection) url.openConnection();
        // @parameter filesize is size of the original file to be downloaded.
        long filesize = (long) load.getContentLengthLong();
        String filetype = (String) load.getContentType() + ".";
        int a = filetype.indexOf('/');
        int b = filetype.lastIndexOf('.');
        // @parameter str stores the extension of the file used for storing the number parts.
        String str = filetype.substring(a + 1, b);
        long reqfilesize = filesize;
        System.out.println("The Type of file downloading is "+str+" and it has size of "+filesize+" bytes.");
        long lastrange = reqfilesize % (n);
        long exptlast = reqfilesize - lastrange;
        long rangesize = exptlast / (n);
        // for loop creates the input of starting byte range and ending byte range to pass "Range" request.
        // In the same loop number of threads will be created to download the file number of parts.
        // @parameter start stores start byte for Range.
        // @parameter end stores end byte for range.
        // httpdownloader will download the file in n number of parts.
        for (long i = 0; i <= n-1 ; i++) {
            long end;
            if (i == n - 1)
                end = exptlast - 1;
            else
                end = (i + 1) * rangesize - 1;
            long start = i * rangesize;
            name = ("part" + i);
            new Thread(new HttpDownloader(link, name, start, end, str)).start();
            if (end == exptlast - 1 && exptlast != filesize) {
                start = exptlast;
                end = filesize;
                name = ("part" + (i + 1));
                System.out.println("File size is not divisible by " + n + " so it will download in " + (n + 1) + " parts");
                new Thread(new HttpDownloader(link, name, start, end, str)).start();

            }

        }
        // In case if filesize is not divisible by input number then we need to download a extra file
        //if statement will increment the n by 1 for merging n+1 files in single file.
        if(lastrange!=0){
            n++;
        }
        //Thread mergefile will merge all downloaded file in single file
        new Thread(new MergeFile(str,n)).start();
    }
}

