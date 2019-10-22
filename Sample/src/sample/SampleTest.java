package sample;

public class SampleTest {
	
	
 class Sample
 {
	 public int a;
	 
	 public Sample(int n)
	 {
		 a = n;
	 }
	 
	 public void printSample()
	 {
		 System.out.println("printing argument " + a);
	 }
	 
	 public void setA(int bb)
	 {
		 a = bb;
	 }
 }

	public static void main(String[] args) 
	{	
		SampleTest st = new SampleTest();
		SampleTest.Sample s = st.new Sample(12);
		
		SampleTest.Sample s1 = s;
		s1.setA(14);
		
		s.printSample();
		s1.printSample();
		
		s1 = st.new Sample(16);
		s.printSample();
		s1.printSample();
		
		String a = "Cabbage";
		String b = "Tomato";
		
		System.out.println(a.compareTo(b));
		
	}

}
