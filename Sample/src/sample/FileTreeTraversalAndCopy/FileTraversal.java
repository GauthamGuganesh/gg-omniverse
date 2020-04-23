package sample.FileTreeTraversalAndCopy;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileTraversal extends SimpleFileVisitor<Path>
{

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException 
	{
		System.out.println("Visiting Directory :: " + dir.toAbsolutePath());
		return super.preVisitDirectory(dir, attrs);
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException 
	{
		System.out.println("Visiting File :: " + file.toAbsolutePath());
		return super.visitFile(file, attrs);
	}
	
}
