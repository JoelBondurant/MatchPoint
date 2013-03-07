package com.analyticobjects.matchpoint.fileio;

import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import com.analyticobjects.matchpoint.numerics.*;

/**
 *
 * @author Joel
 * @version 1.0, 10/14/2006
 */
public class CSVFileIO extends DataFileIO
{
    
    public static final String extension = ".csv";
    private static final byte[] delimiter = ",".getBytes();
    private static final byte[] newlineCharacter = "\n".getBytes();
    private static final String blankValue = "\"\"";

    /** Creates a new instance of CSVFileIO */
    public CSVFileIO()
    {
    }
    
    public String getFileExtension(){return extension;}

    public boolean readFile(File aFile, JTable table)
    {
        if(!aFile.exists()){return false;}
        if(!aFile.isFile()){return false;}
        FileOutputStream inputStream = null;
        try
        {
            inputStream = new FileOutputStream(aFile, false);
        }
        catch(IOException ioe)
        {
            return false;
        }
        return true;
    }

    public boolean writeFile(File aFile, JTable table)
    {
       if(aFile == null){return false;}
        FileOutputStream outputStream = null;
        try
        {
            outputStream = new FileOutputStream(aFile, false);
        }
        catch(IOException ioe)
        {
            return false;
        }
        FileChannel outputChannel = outputStream.getChannel();
        int numColumns = table.getColumnCount();
        int numRows = DataStructure.getInstance().maximumVariableSize();
        int bufferCapacity = Math.max(1024,2*64*numColumns);
        ByteBuffer buffer = ByteBuffer.allocate(bufferCapacity);
        Object pvalue;
        String value;
        try
        {
            for(int i = 0; i<numRows; i++)
            {
                for(int j = 0; j<numColumns; j++)
                {
                    try
                    {
                        pvalue = table.getValueAt(i,j);
                    }
                    catch(Exception e)
                    {
                        pvalue = null;
                    }
                    if(pvalue == null)
                    {
                        value = blankValue;
                    }
                    else
                    {
                        value = pvalue.toString();
                    }
                    if(value.length() == 0)
                    {
                        value = blankValue;
                    }
                    buffer.put(value.getBytes());
                    if(j == numColumns - 1)
                    {
                        if(i != numRows - 1)
                        {
                            buffer.put(newlineCharacter);
                        }
                    }
                    else
                    {
                        buffer.put(delimiter);
                    }
                    buffer.flip();
                    outputChannel.write(buffer);
                    buffer.clear();
                }
            }
            outputStream.close();
        }
        catch(IOException ioe)
        {
            return false;
        }
        return true;
    }
    
    public static javax.swing.filechooser.FileFilter getFileFilter()
    {
        fileFilter = new CSVFileIO.CSVFileFilter();
        return fileFilter;
    }
    
    public static class CSVFileFilter extends javax.swing.filechooser.FileFilter
    {
        private static final String description = "Comma Separated Values (*.csv)";
        public String getDescription()
        {
            return this.description;
        }
        
        public boolean accept(File aFile)
        {
            if(aFile.isDirectory()){return true;}
            if(aFile.getName().toLowerCase().endsWith(CSVFileIO.extension)){return true;}
            return false;
        }
    }
    
}
