package sample.FileTreeTraversalAndCopy;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFiles extends SimpleFileVisitor<Path>
{
	Path srcRoot;
	Path dstRoot;
	
	public CopyFiles(Path src, Path dst)
	{
		this.srcRoot = src;
		this.dstRoot = dst;
	}

	@Override
	public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException 
	{
		Path relativeSrcPath = srcRoot.relativize(dir);
		
		Path targetPath = dstRoot.resolve(relativeSrcPath);
		
		System.out.println("Source Path :: " + relativeSrcPath);
		System.out.println("Relative Path :: " + targetPath);
		
		Files.copy(dir, targetPath);
		
		return FileVisitResult.CONTINUE;
	}

	@Override
	public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException 
	{
		Path relativeSrcPath = srcRoot.relativize(file);
		
		Path targetPath = dstRoot.resolve(relativeSrcPath);
		
		System.out.println("Source Path :: " + relativeSrcPath);
		System.out.println("Relative Path :: " + targetPath);
		
		Files.copy(file, targetPath);
		
		return FileVisitResult.CONTINUE;
	}
	
	
}
