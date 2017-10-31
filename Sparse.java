/*////////////////////////////////
 *				//
 * Name:    Robert Mushkot      //
 * Cruz ID: rmushkot		//
 * Assignment: pa3 		//
 * Sparse.java			//
 *				//
 * ///////////////////////////////
*/


import java.io.*;
import java.util.Scanner;
   
class Sparse{
	public static void main(String[] args) throws IOException{
		Scanner in = null;
		PrintWriter out = null;
		String line = null;
		int lineNumber = 0;
	

		if(args.length < 2){
			System.err.println("Usage:Lex infile outfile");
			System.exit(1);
		}
      
	
		in = new Scanner(new File(args[0]));
		Scanner in2 = new Scanner(new File(args[0]));
		out = new PrintWriter(new FileWriter(args[1]));

		Matrix A = null;
		Matrix B = null;

		int n = 0;
		int a = 0;
		int b = 0 ;

			lineNumber++;
		
		
			if(lineNumber == 1){ // gets the first line from the input file and assigns the values to n,a,b

				line = in.nextLine().trim() + " ";
				String[] token = line.split("\\s+");

				
				n = Integer.parseInt(token[0]);
				a = Integer.parseInt(token[1]);
				b = Integer.parseInt(token[2]);

				A = new Matrix(n);
				B = new Matrix(n);
			}
			in.nextLine();
			for(int k = 0; k < a; k++){ //inputs the entries from the file, a times into A matrix

				line = in.nextLine().trim() + " ";
				String[] token = line.split("\\s+");
				lineNumber++;
	
				
				int i = Integer.parseInt(token[0]);
                        	int j = Integer.parseInt(token[1]);
                        	double d = Double.parseDouble(token[2]);

				
				A.changeEntry(i,j,d);

			}

			in.nextLine();
			for(int k = 0; k < b; k++){ // inputs the entires from the file. b times into B matrix
	
				line = in.nextLine().trim() + " ";
                        	String[] token = line.split("\\s+");

                        	int i = Integer.parseInt(token[0]);
                        	int j = Integer.parseInt(token[1]);
                        	double d = Double.parseDouble(token[2]);


                        	B.changeEntry(i,j,d);

			}


		out.println("A has " + A.getNNZ() + " non-zero entries:");
		out.println(A);

		out.println("B has " + B.getNNZ() + " non-zero entries:");
		out.println(B);

		out.println("(1.5)*A =");
		out.println(A.scalarMult(1.5));

		out.println("A+B =");
		out.println(A.add(B));

                out.println("A+A =");
                out.println(A.add(A));

                out.println("B-A =");
                out.println(B.sub(A));

		out.println("A-A =");
                out.println(A.sub(A));

                out.println("Transpose(A) =");
                out.println(A.transpose());

		out.println("A*B =");
                out.println(A.mult(B));

		out.println("B*B =");
                out.println(B.mult(B));



		
	in.close();
	out.close();

	}
}			 
