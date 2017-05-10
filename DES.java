import java.math.BigInteger;
import java.util.Scanner;


public class DES {
	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("Enter the 64 bit plain text in hexdec");
		String ip_hex=sc.next();
		int[] ip=new int[64];
		String temp=hexToBinary(ip_hex);
		//System.out.println("binary value "+temp);
		while(temp.length()!=64)
			temp="0"+temp;
		System.out.println("binary value "+temp);

		for(int i=0;i<64;i++) {
		ip[i] = Integer.parseInt(String.valueOf(temp.charAt(i)));
		  }
		//initial permutation
		int a_ip[]=permutation(ip, 'i');
		
		//key generation
		
		System.out.println("Enter the 64 bit Main key in hexdec");
		String key_hex=sc.next();
		int[] m_key=new int[64];
		String temp1=hexToBinary(key_hex);
		//System.out.println("binary value "+temp1);
		while(temp1.length()!=64)
			temp1="0"+temp1;
		System.out.println("binary value "+temp1);

		for(int i=0;i<64;i++) {
		m_key[i] = Integer.parseInt(String.valueOf(temp1.charAt(i)));
		  }
			int[] R0=paritydrop(m_key);
		display(R0);	
		int[][] subkey=new int[16][48];
		subkey=key_generation(R0);
	

		//16 rounds OF ENCRYPION
		
		int[][] enc_pt=new int[16][64];
		int[] LE0=new int[a_ip.length/2];
		int[] RE0=new int[a_ip.length/2];
		for(int i=0;i<a_ip.length/2;i++) {
			LE0[i]=a_ip[i];
		}
		int j=0;
		for(int i=a_ip.length/2;i<a_ip.length;i++) {
			RE0[j]=a_ip[i];
			j++;
		}
		
		for(int i=1;i<=16;i++){
			int[] E_LE=permutation(LE0, 'e');
			
			int[] X_LE=xor(E_LE,subkey[i-1]);
			System.out.println("aferxor");
			display(X_LE);
			int[] S_LE=sbox(X_LE);
			int[] st_LE=permutation(S_LE, 's');
			int[] RE_tmp=xor(RE0,st_LE);
			for(int i1=0;i1<a_ip.length/2;i1++) {
				enc_pt[i-1][i1]=LE0[i1];
			}
			int j1=0;
			for(int i1=a_ip.length/2;i1<a_ip.length;i1++) {
				enc_pt[i-1][i1]=RE_tmp[j1];

				j1++;
			}
			
		}
		System.out.println("after encryption");
		for(int i=0;i<16;i++){
			int j1=i+1;
			System.out.println("Round "+j1+" ciphertext is");
			display(enc_pt[i]);
		}
		
		//FINAL PERMUTATION
		System.out.println("After final permutaion enc PT is ");
		display(permutation(enc_pt[15], 'f'));
		
	}
	public static String hexToBinary(String hex) {
	return new BigInteger(hex, 16).toString(2);
	}
	
	public static int[] permutation(int[] inp, char ch) {
		int key_len = 0;
		int[] key = null;
		switch(ch){
		case 'i':
		{
			int ip[]={58,50,42,34,26,18,10,02,60,52,44,36,28,20,12,04,62,54,46,38,30,22,14,06,64,56,48,40,32,24,16,8,57,49,41,33,25,17,9,01,59,51,43,35,27,19,11,03,61,53,45,37,29,21,13,05,63,55,47,39,31,23,15,07};
			key=ip;
			key_len=ip.length;
			break;
		}
		case 'f':
		{
			int fp[]={40,8,48,16,56,24,64,32,39,07,47,15,55,23,63,31,38,06,46,14,54,22,62,30,37,05,45,13,53,21,61,29,36,04,44,12,52,20,60,28,35,03,43,11,51,19,59,27,34,02,42,10,50,18,58,26,33,01,41,9,49,17,57,25};
			key=fp;
			key_len=fp.length;
			break;
		}
		case 'e':
		{
			int ep[]={32,01,02,03,04,05,04,05,06,07,8,9,8,9,10,11,12,13,12,13,14,15,16,17,16,17,18,19,20,21,20,21,22,23,24,25,24,25,26,27,28,29,28,29,31,31,32,01};
			key=ep;
			key_len=ep.length;
			break;
		}
		case 's':
		{
			int sp[]={16,07,20,21,29,12,28,17,01,15,23,26,05,18,31,10,02,8,24,14,32,27,03,9,19,13,30,06,22,11,04,25};
			key=sp;
			key_len=sp.length;
			break;
		}
		default:
			System.out.println("Enter proper choice");
		}
		int index;
		int[] permutatedValue=new int[key_len];
		for(int i=0;i<key_len;i++){
			index = key[i]-1;
			permutatedValue[i]=inp[index];
		//	System.out.println("key "+index+" perm "+inp[index]);
		}
		return permutatedValue;
	}
	public static int[] paritydrop(int[] key) {
		int parity_len = 0;
		int[] parity = null;
		int pdropt[]={57,49,41,33,25,17,19,1,58,50,42,34,26,18,10,2,59,51,43,35,27,19,11,3,60,52,44,36,63,55,47,39,31,23,15,7,62,54,46,38,30,22,14,6,61,53,45,37,29,21,13,5,28,20,12,4};
	parity=pdropt;
	parity_len=pdropt.length;
	int i;

		int[] paritydrop=new int[parity_len];

		for(int j=0;j<parity_len;j++){
			i = parity[j]-1;
			paritydrop[j]=key[i];
				}
return paritydrop;
	}
public static void display(int[] x){
	String binaryStr="";

	System.out.println("");
	for(int i=0;i<x.length;i++){
		System.out.print(x[i]);
		binaryStr+=x[i];
	}
	
	System.out.println();
	System.out.println("hex value is "+new BigInteger(binaryStr, 2).toString(16));
}
public static int[][] key_generation(int[] y){
	int[][] key=new int[16][48];
	int[] L_temp1 = new int[y.length/2],L_temp2 = new int[y.length/2],R_temp1 = new int[y.length/2],R_temp2=new int[y.length/2];
	int[] temp=new int[y.length];
	int r=1;
	for( r=1;r<=16;r++){
	for(int i=0;i<y.length/2;i++) {
		L_temp1[i]=y[i];
		}
	L_temp2=leftShift(L_temp1, r);
	//System.out.println("lls");
	//display(L_temp2);
	int j=0;
	for(int i=y.length/2;i<y.length;i++) {
		R_temp1[j]=y[i];
		j++;
	}
	R_temp2=leftShift(R_temp1, r);
	//System.out.println("rls");
	//display(R_temp2);
	for(int i=0;i<y.length/2;i++) {
temp[i]=L_temp2[i];	}
	int t=0;
	for(int i=y.length/2;i<y.length;i++) {
		temp[i]=R_temp2[t];	
		t++;
		}
	key[r-1]=dbox(temp);
	System.out.println("Sub key value for round "+r);
	display(dbox(temp));
	y=temp;
	}
	return key; 
}

public static int[] leftShift(int[] z,int r){
	if(r==1||r==2||r==9||r==16){
for (int i = 0; i < 1; i++) {
int first = z[0];
System.arraycopy( z, 1, z, 0, z.length - 1);
z[z.length - 1] = first;
        }
        }
else{

for (int i = 0; i < 2; i++) {
int first = z[0];
System.arraycopy( z, 1, z, 0, z.length - 1);
z[z.length - 1] = first;
        }
        }
	return z;
	
}
public static int[] dbox(int[] ip) {
int comp_len=0;
int[] comp=null;
int[] compresst={14,17,11,24,1,5,3,28,15,6,21,10,23,19,12,4,26,8,16,7,27,20,13,2,41,52,31,37,47,55,30,40,51,45,33,48,44,49,39,56,34,53,46,42,50,36,29,32};
comp = compresst;
comp_len=compresst.length;
int i;
int[] dbox=new int[comp_len];
for(int j=0;j<comp_len;j++){

   i=comp[j]-1;
dbox[j]=ip[i];

}
return dbox;
}
public static int[] xor(int[] pt,int[] sk){
	int[] xor=new int[pt.length];
	for(int i=0;i<pt.length;i++){
		if(pt[i]==sk[i])
			xor[i]=0;
		else
			xor[i]=1;
		
		}
	return xor;
	
}
public static int[] sbox(int[] ip){
	String sbox_s="";
	final int[] S1 =  {
		14, 4,  13, 1,  2,  15, 11, 8,  3,  10, 6,  12, 5,  9,  0,  7,
		0,  15, 7,  4,  14, 2,  13, 1,  10, 6,  12, 11, 9,  5,  3,  8,
		4,  1,  14, 8,  13, 6,  2,  11, 15, 12, 9,  7,  3,  10, 5,  0,
		15, 12, 8,  2,  4,  9,  1,  7,  5,  11, 3,  14, 10, 0,  6,  13
	};
	final int[] S2 =  {
		15, 1,  8,  14, 6,  11, 3,  4,  9,  7,  2,  13, 12, 0,  5,  10,
		3,  13, 4,  7,  15, 2,  8,  14, 12, 0,  1,  10, 6,  9,  11, 5,
		0,  14, 7,  11, 10, 4,  13, 1,  5,  8,  12, 6,  9,  3,  2,  15,
		13, 8,  10, 1,  3,  15, 4,  2,  11, 6,  7,  12, 0,  5,  14, 9
	};
	final int[] S3 =  {

		10, 0,  9,  14, 6,  3,  15, 5,  1,  13, 12, 7,  11, 4,  2,  8,
		13, 7,  0,  9,  3,  4,  6,  10, 2,  8,  5,  14, 12, 11, 15, 1,
		13, 6,  4,  9,  8,  15, 3,  0,  11, 1,  2,  12, 5,  10, 14, 7,
		1,  10, 13, 0,  6,  9,  8,  7,  4,  15, 14, 3,  11, 5,  2,  12
	};
	final int[] S4 =  {

		7,  13, 14, 3,  0,  6,  9,  10, 1,  2,  8,  5,  11, 12, 4,  15,
		13, 8,  11, 5,  6,  15, 0,  3,  4,  7,  2,  12, 1,  10, 14, 9,
		10, 6,  9,  0,  12, 11, 7,  13, 15, 1,  3,  14, 5,  2,  8,  4,
		3,  15, 0,  6,  10, 1,  13, 8,  9,  4,  5,  11, 12, 7,  2,  14
	};		final int[] S5 =  {

		2,  12, 4,  1,  7,  10, 11, 6,  8,  5,  3,  15, 13, 0,  14, 9,
		14, 11, 2,  12, 4,  7,  13, 1,  5,  0,  15, 10, 3,  9,  8,  6,
		4,  2,  1,  11, 10, 13, 7,  8,  15, 9,  12, 5,  6,  3,  0,  14,
		11, 8,  12, 7,  1,  14, 2,  13, 6,  15, 0,  9,  10, 4,  5,  3
	};
	final int[] S6 =  {

		12, 1,  10, 15, 9,  2,  6,  8,  0,  13, 3,  4,  14, 7,  5,  11,
		10, 15, 4,  2,  7,  12, 9,  5,  6,  1,  13, 14, 0,  11, 3,  8,
		9,  14, 15, 5,  2,  8,  12, 3,  7,  0,  4,  10, 1,  13, 11, 6,
		4,  3,  2,  12, 9,  5,  15, 10, 11, 14, 1,  7,  6,  0,  8,  13
	};
	final int[] S7 =  {

		4,  11, 2,  14, 15, 0,  8,  13, 3,  12, 9,  7,  5,  10, 6,  1,
		13, 0,  11, 7,  4,  9,  1,  10, 14, 3,  5,  12, 2,  15, 8,  6,
		1,  4,  11, 13, 12, 3,  7,  14, 10, 15, 6,  8,  0,  5,  9,  2,
		6,  11, 13, 8,  1,  4,  10, 7,  9,  5,  0,  15, 14, 2,  3,  12
	};
	final int[] S8 =  {

		13, 2,  8,  4,  6,  15, 11, 1,  10, 9,  3,  14, 5,  0,  12, 7,
		1,  15, 13, 8,  10, 3,  7,  4,  12, 5,  6,  11, 0,  14, 9,  2,
		7,  11, 4,  1,  9,  12, 14, 2,  0,  6,  10, 13, 15, 3,  5,  8,
		2,  1,  14, 7,  4,  10, 8,  13, 15, 12, 9,  0,  3,  5,  6,  11
	 };
	
	int[] sb=new int[32];
	int i =0;
	String fs="";
	String ls="";
	int x=1;
	for(;i<ip.length;i++){
		if(i%6==0)
			fs=Integer.toString(ip[i]);
		if(i%6==1||i%6==2||i%6==3||i%6==4)
			ls+=Integer.toString(ip[i]);
		if(i%6==5){
			fs+=Integer.toString(ip[i]);
			int iRow = Integer.parseInt(fs, 2);
			int iColumn = Integer.parseInt(ls, 2);	
			System.out.println("row "+iRow+" col "+iColumn);
			fs="";
			ls="";
		int val=0;
		if(x==1)
			val=S1[((iRow)*16)+iColumn];
		if(x==2)
			val=S2[((iRow)*16)+iColumn];
		if(x==3)
			val=S3[((iRow)*16)+iColumn];
		if(x==4)
			val=S4[((iRow)*16)+iColumn];
		if(x==5)
			val=S5[((iRow)*16)+iColumn];
		if(x==6)
			val=S6[((iRow)*16)+iColumn];
		if(x==7)
			val=S7[((iRow)*16)+iColumn];
		if(x==8)
			val=S8[((iRow)*16)+iColumn];
		
		String s = Integer.toBinaryString(val);
		
		while(s.length() < 4) {
			s = "0" + s;
		}
		System.out.println("x value: "+x+" val "+val+" s "+s);
		x++;
sbox_s+=s;
		}
		
	}
for(int i1=0;i1<sbox_s.length();i1++){
	String temp=Character.toString(sbox_s.charAt(i1));
	sb[i1]=Integer.parseInt(temp);
}
	return sb;
	
}

}
