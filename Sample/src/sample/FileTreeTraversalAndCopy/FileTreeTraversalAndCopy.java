package sample.FileTreeTraversalAndCopy;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

public class FileTreeTraversalAndCopy 
{
	public static void main(String[] args) throws IOException 
	{
		FileTraversal traversal = new FileTraversal();
		Path startPath = Paths.get("dir1");
		BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(new FileInputStream("dir1/dir2/dir3/file1.txt"))));
		BufferedWriter brw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("")));
		
		BufferedInputStream bri = new BufferedInputStream(new FileInputStream(""));
		InputStreamReader isr = new InputStreamReader(new FileInputStream(""));
		
		RandomAccessFile raf = new RandomAccessFile("", "");
		
		BufferedOutputStream bro = new BufferedOutputStream(new FileOutputStream(""));
		OutputStreamWriter osr = new OutputStreamWriter(new FileOutputStream(""));
		
		DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream("")));
		
		BufferedReader brr = new BufferedReader(new FileReader(""));
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(""));
		
		
		Files.walkFileTree(startPath, traversal);
		
		Path src = FileSystems.getDefault().getPath("dir1");
		
		Path copyDst = FileSystems.getDefault().getPath("dir4" + File.separator + "dir2Copy");
		
		CopyFiles copyFiles = new CopyFiles(src, copyDst);
		
		Files.walkFileTree(src, copyFiles);
	}
}
