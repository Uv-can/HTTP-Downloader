package com.company;


import java.io.*;

public class MergeFile implements Runnable {
    String str;
    int n;

    public MergeFile(String str,int n) throws FileNotFoundException, IOException {
        this.str=str;
        this.n=n;

    }
    @Override
    public void run() {
        System.out.println("************************************************");
        System.out.println("File merging has been started..");
        for(int i=0;i<n;i++)
            try {
                Thread.sleep(1000);
                System.out.println("File part"+i+" merging into final file..");
                File out = new File("Final_Merged_object" + "." + str);
                File input = new File("part" + i + "." + str);
                FileInputStream ind = new FileInputStream(input);
                BufferedInputStream ins = new BufferedInputStream(ind);
                FileOutputStream fs = new FileOutputStream(out, true);
                BufferedOutputStream bpout = new BufferedOutputStream(fs,1 );
                byte[] buffer = new byte[1];
                int read = 0;
                while ((read = ins.read(buffer, 0, 1)) >= 0) {
                    bpout.write(buffer,0, read);
                }
                System.out.println("File part"+i+" merging completed.");
                System.out.println("**********************************");
            } catch (InterruptedException | FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

}
