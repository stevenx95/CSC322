package org.guccigang.mini_google_docs;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.StringTokenizer;

public class Parser {
    private Scanner myScanner;
    private StringTokenizer tokens;

    public String readFile(File file, String object)
    {
        String obj;
        String val = null;
        try {
            myScanner = new Scanner(file);
        }
        catch (FileNotFoundException e)
        {
            return val;
        }
        myScanner.useDelimiter(";");
        while(myScanner.hasNext())
        {
            tokens = new StringTokenizer(myScanner.next(), "=");
            obj = tokens.nextToken();
            val = tokens.nextToken();
            if(object.equals(obj))
                return val;
        }
        return val;
    }
}
