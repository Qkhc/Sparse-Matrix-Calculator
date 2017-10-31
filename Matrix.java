/*
 * Robert Mushkot
 * rmushkot
 * 1543374
 * Matrix.java
 * pa3
 */
 
public class Matrix{
	
	class Entry{
		//Fields
		int col;
		double data;
		
		//constructor
		Entry(int col, double data){
			this.col = col;
			this.data = data;
		}
		int getCol(){
			return this.col;
		}

		public boolean equals(Object x){
			boolean eq = false;
			Entry that;
			if(x instanceof Entry){
				that = (Entry) x;
				eq = (this.data == that.data && this.col == that.col);
			}
			return eq;
		}

		public String toString(){
			return "(" + String.valueOf(col) + ", " + String.valueOf(data) + ")";
		}


	}
	
	//Fields
	int n;
	List[] row;
	
	//Constructor
	Matrix(int n){ // Makes a new n x n Matrix. pre: n>=1
		if(n >=1){
			this.n = n;
			row = new List[n+1];
			for (int i = 1; i < n+1; i++){
				row[i] = new List();
			}
		}

		else{
			System.err.println("Called Matrix() with an empty matrix");
			System.exit(1);
		}
	}


	//Access functions

	// getSize()
	// Returns n, the number of rows and columns of this Matrix
	int getSize(){ 
		return n;
	}


	// getNNZ()
	// Returns the number of non-zero entries in this Matrix
	int getNNZ(){ 
		int count = 0;
		for(int i =1; i<=n; i++){
                        if(row[i].length() != 0 && row[i] != null)
                                count += row[i].length();

                        else continue;
                }
		return count;
	}


	// equals()
	// overrides Objects's equals() method 
	public boolean equals(Object x){
		boolean eq = false;
		Matrix that;

		if(x instanceof Matrix){
			that = (Matrix) x;
			
			if(this.getSize() != that.getSize())
				return false;

			for(int i = 1; i<=n; i++){
					if(!(this.row[i].equals(that.row[i])))
						return false;
			}
			
		}
		return true;
	}	
	

	// Manipulation procedures


	// makeZero()	
	// sets this Matrix to the zero state
	void makeZero(){
		for(int i  =1; i<=n; i++){
			row[i].clear();
		}
	}


	// copy()
	// returns a new Matrix having the same entries as this Matrix
	Matrix copy(){ 
		Matrix copy  = new Matrix(getSize());
		for( int i = 1; i<=n; i++){
			copy.row[i] = row[i];
		}
		return copy;
	}


	//changeEntry()	
	//changes the ith row, jth column of this matrix to x
	//pre: 1<=i<=getSize(), 1<=j<=getSize()
	void changeEntry(int i, int j, double x){
		Entry E;
		if( i >=1 && i<= getSize() && j >=1 && j<= getSize()){			
			
			
			if(row[i].length() == 0 && x != 0){  // Inserts the first Entry into the empty matrix 
			
				row[i].append(new Entry(j,x));
			
			}
			
		
			else if( x != 0){ // inserts non-zero entries 

				row[i].moveFront();

				while(j > ((Entry) row[i].get()).col){  // This helps put the columns into sorted order.
					
					if(row[i].length() == row[i].index() + 1){ //if a the end of the row and you havent inserted, insert at the end.
						row[i].append(new Entry(j,x));
					}

					row[i].moveNext();
				}

				if( j == ((Entry) row[i].get()).col ){ //if you find another entry at the same column, override and delete the previous entry. 
					
					row[i].insertBefore(new Entry(j,x));
					row[i].delete();
				
				}				
				else{  //(j < ((Entry) row[i].get()).col){

					row[i].insertBefore(new Entry(j,x));
				}
			}
		
			else if(x == 0){ // handles the zero-entries
				if( row[i].length() >0){
					
					row[i].moveFront();

					while(j > ((Entry) row[i].get()).col  && row[i].length() != row[i].index() + 1){
						row[i].moveNext();
					}
					
					if(j == ((Entry) row[i].get()).col ){
						row[i].delete();
					}
				}
				return;
			}				

		}
		
		else{
			System.err.println("Error occured on the size of change entry");
			System.exit(1);
		}
	}

	// scalarMult()
	// returns a new Matrix that is the scalar product of this Matrix with x
	Matrix scalarMult(double x){ 
		Matrix copy = new Matrix(getSize());
		
		for(int i = 1; i<=n; i++){

			if(row[i].length() > 0){
				
				row[i].moveFront();
			
				while(row[i].index() >= 0){
				
					Entry E = (Entry) row[i].get();
					
					copy.changeEntry(i, E.col, E.data* x);
					
					row[i].moveNext();
				}
			}
		}
		return copy;
	}

	// add()
	// returns a new Matrix that is the sum of this Matrix with M
	// Pre: getSize() = M.getSize()
	Matrix add(Matrix M){
				
		Matrix sum = new Matrix(getSize());
		
		if(copy().equals(M)) return scalarMult(2);
		
		
		if(getSize() == M.getSize()){

			for( int i = 1; i<=n;i++){
				
				row[i].moveFront();
				M.row[i].moveFront();
				
				while(M.row[i].index()>=0 && row[i].index()>=0){// while the cursor is not null
					
					if(((Entry) row[i].get()).col == ((Entry) M.row[i].get()).col){ //Checks if 2 matrices have the same col and adds them.
						
						sum.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).data + ((Entry) M.row[i].get()).data);
						row[i].moveNext();
						M.row[i].moveNext();
					
					}
					
			
					while(M.row[i].index()>=0 && row[i].index()>=0 && ((Entry) row[i].get()).col < ((Entry) M.row[i].get()).col ){ // While the original matrix cursor is less than M's cursor adds the appropriate entries
							
							sum.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).data);
							row[i].moveNext();
					
						}
					
					while(M.row[i].index()>=0 && row[i].index()>=0 && (((Entry) M.row[i].get()).col <  ((Entry) row[i].get()).col)){ // While M cursor is less than the orignals cursor: adds the new entries.
								
							sum.changeEntry(i, ((Entry) M.row[i].get()).col, ((Entry) M.row[i].get()).data);
							M.row[i].moveNext();
					
					}
				}
				
				
				//edge cases for when one cursor reaches the end of its respective row
				while(row[i].index() >= 0 ){

					sum.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).data);
					row[i].moveNext();
				
				}
				
				while(M.row[i].index() >= 0 ){
				
					sum.changeEntry(i, ((Entry) M.row[i].get()).col, ((Entry) M.row[i].get()).data);
					M.row[i].moveNext();
				
				}
			}			
		}
		return sum;
	}
	
	// sub()
	// returns a new Matrix that is the difference of this Matrix with M
        // Pre: getSize() = M.getSize()
	Matrix sub(Matrix M){ 
                                
		Matrix sum = new Matrix(getSize());
		
		
		if(getSize() == M.getSize()){

			for( int i = 1; i<=n;i++){
				
				row[i].moveFront();
				M.row[i].moveFront();
				
				while(M.row[i].index()>=0 && row[i].index()>=0){ // while the cursor is not null
					
					if(((Entry) row[i].get()).col == ((Entry) M.row[i].get()).col){ //Checks if 2 matrices have the same col and adds them.
						
						sum.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).data - ((Entry) M.row[i].get()).data);
						row[i].moveNext();
						M.row[i].moveNext();
					
					}
					
			
					while(M.row[i].index()>=0 && row[i].index()>=0 && ((Entry) row[i].get()).col < ((Entry) M.row[i].get()).col ){ // While the original matrix cursor is less than M's cursor adds the appropriate entries
							
							sum.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).data);
							row[i].moveNext();
					
						}
					
					while(M.row[i].index()>=0 && row[i].index()>=0 && (((Entry) M.row[i].get()).col <  ((Entry) row[i].get()).col)){  // While M cursor is less than the orignals cursor: adds the new entries.
								
							sum.changeEntry(i, ((Entry) M.row[i].get()).col, -((Entry) M.row[i].get()).data);
							M.row[i].moveNext();
					
						}
				}
				
				//edge cases for when one cursor reaches the end of its respective row
				while(row[i].index() >= 0 ){ 
					
					sum.changeEntry(i, ((Entry) row[i].get()).col, ((Entry) row[i].get()).data);
					row[i].moveNext();
				
				}
				
				while(M.row[i].index() >= 0 ){
				
					sum.changeEntry(i, ((Entry) M.row[i].get()).col, -((Entry) M.row[i].get()).data);
					M.row[i].moveNext();
				
				}
			}			
		}
		return sum;
	}


	//transpose()
	//returns a new Matrix that is the transpose of this Matrix
	Matrix transpose(){ 
		Matrix tp = new Matrix(getSize());
		
		for(int i = 1; i<=n; i++){
		
			if(row[i].length() > 0){
				row[i].moveFront();
				
				while(row[i].index() >= 0){
				
					tp.changeEntry(((Entry) row[i].get()).col, i, ((Entry) row[i].get()).data);
					row[i].moveNext();
				
				}
			}
		}
		return tp;
	}


	// mult()
	// returns a new Matrix that is the product of this Matrix with M, n^2 run time
        // Pre: getSize() == M.getSize
	Matrix mult(Matrix M){
          
		Matrix prod = new Matrix(getSize());
		Matrix Mt = M.transpose();

		if(getSize() == M.getSize()){
			
			for(int i =1; i<=n; i++){

				for(int j = 1; j<=n; j++){

					prod.changeEntry(i,j,dot(row[i],Mt.row[j]));

				}
			}
		}
		else{
			System.err.println("Mult: Matrix sizes arent equal");
			System.exit(1);
		}
		
		  return prod;

	  }


	//Other Functions


	//toString()
	//overrides Objects's toString() method
	public String toString(){ 
		String str=  "";
		
		for(int i =1; i<=n; i++){
		
			if(row[i].length() != 0)
			
				str += i + ": " + row[i];
			
			else continue;
			
			str += "\n";
		} 
		return str;
	}

	// dot()
	// returns the dot product of two lists. 
	private static double dot(List P, List Q){
		double prod = 0;
		P.moveFront();
		Q.moveFront();

		while(P.index()>=0 && Q.index() >=0){
			if(((Entry) P.get()).col ==((Entry)  Q.get()).col){

				prod += ((Entry) Q.get()).data * ((Entry) P.get()).data;
				P.moveNext();
				Q.moveNext();

			}
                 

			while(P.index()>=0 && Q.index() >=0 && ((Entry)P.get()).col  < ((Entry)  Q.get()).col){
				P.moveNext();
			}


			while(P.index()>=0 && Q.index() >=0 && ((Entry)P.get()).col  > ((Entry)  Q.get()).col){
				Q.moveNext();
			}
		}

		return prod;
	}





}
