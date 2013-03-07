package com.analyticobjects.matchpoint.fileio;

import java.io.File;
import javax.swing.JTable;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Joel
 * @version 1.0, 10/14/2006
 */
public abstract class DataFileIO
{  
    protected static FileFilter fileFilter;
    
    public abstract boolean readFile(File aFile, JTable table);
    public abstract boolean writeFile(File aFile, JTable table);
    public abstract String getFileExtension();
    public static FileFilter getFileFilter(){return fileFilter;}
}
