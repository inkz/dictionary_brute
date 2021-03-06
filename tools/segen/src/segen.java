import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class segen {

	static String[] line = { "`1234567890-=", "qwertyuiop[]", "asdfghjkl;'", "zxcvbnm,./" };

	static String[] upper = { "1qaz", "2wsx", "3edc", "4rfv", "5tgb", "6yhn", "7ujm", "8ik,", "9ol.", "0p;/", "-['",
			"2qaz", "3wsx", "4edc", "5rfv", "6tgb", "7yhn", "8ujm", "9ik,", "0ol.", "-p;/", "=]'/", "-[;.", "0pl,",
			"9okm", "8ijn", "7uhb", "6ygv", "5tfc", "4rdx", "3esz" };

	private static HashSet<String> merged;
	private static HashSet<Part> passwords;
	final static int max_size = 10000000;
	static PrintWriter writer;
	static String temp_name = "temp_brute_file";
	static int size = 0;

	static String exec_path = "";
	static Boolean runExternal = false;
	static Boolean debug = false;
	static Boolean policy = false;
	static Boolean reverse = false;
	static Boolean optimization = false;
	static String lowercase = "qazwsxedcrfvtgbyhnujmikolp";
	static String uppercase = "QAZWSXEDCRFVTGBYHNUJMIKOLP";
	static String digits = "1234567890";
	static Integer count = 0;
	static Map<String, HashSet<String>> links = new HashMap<String, HashSet<String>>();

	static boolean canBeCombined(String combination, String part) {

		String start = part.substring(0, 1);
		String end = part.substring(part.length() - 1, part.length());

		for (int i = 0; i < combination.length(); i++) {

			if (links.get(combination.substring(i, i + 1)).contains(start)
					|| links.get(combination.substring(i, i + 1)).contains(end))
				return true;

		}

		return false;
	}

	static void add(String key, String value) {
		value = value + value.toUpperCase();
		String tmp;

		for (int i = 0; i < value.length(); i++) {
			tmp = value.substring(i, i + 1);
			if (!links.containsKey(key))
				links.put(key, new HashSet<String>());
			if (!links.containsKey(key.toUpperCase()))
				links.put(key.toUpperCase(), new HashSet<String>());

			links.get(key).add(tmp);
			links.get(key.toUpperCase()).add(tmp);

		}

	}

	static void init(int type, int size) {

		add("`", "`~1!");
		add("~", "`~1!");

		add("0", "_-po90");
		add("1", "12q!@Q");
		add("2", "123wq!@#WQ");
		add("3", "34ew2@#$EW");
		add("4", "345re#$%RE");
		add("5", "56tr4%^TR$");
		add("6", "67yt5%^&YT");
		add("7", "78uy6&*UY^");
		add("8", "89iu7*(IU&");
		add("9", "90oi8()OI*");
		add("0", "0-po9)_PO(");
		add("-", "-=[p0_+{P)");
		add("=", "=][-+}{_");
		add(")", "_-po90");
		add("!", "12q!@Q");
		add("@", "123wq!@#WQ");
		add("#", "34ew2@#$EW");
		add("$", "345re#$%RE");
		add("%", "56tr4%^TR$");
		add("^", "67yt5%^&YT");
		add("&", "78uy6&*UY^");
		add("*", "89iu7*(IU&");
		add("(", "90oi8()OI*");
		add(")", "0-po9)_PO(");
		add("_", "-=[p0_+{P)");
		add("+", "=][-+}{_");
		add("q", "12waq!@WAQ");
		add("w", "23esaqw@#ESAQW");
		add("e", "34rdswe#$RDSWE");
		add("r", "45tfder$%TFDER");
		add("t", "56ygfrt%^YGFRT");
		add("y", "67uhgty^&UHGTY");
		add("u", "78ijhyu&*IJHYU");
		add("i", "89okjui*(OKJUI");
		add("o", "90plkio()PLKIO");
		add("p", "0-[;lop)_{:LOP");
		add("[", "-=]';p[_+}\":P{");
		add("]", "=[']+{\"}");
		add("a", "aAqwszQWSZ");
		add("s", "wedxzasWEDXZAS");
		add("d", "erfcxsdERFCXSD");
		add("f", "rtgvcdfRTGVCDF");
		add("g", "tyhbvfgTYHBVFG");
		add("h", "yujnbghYUJNBGH");
		add("j", "uikmnhjUIKMNHJ");
		add("k", "iol,mjkIOL<MJK");
		add("l", "op;.,klOP:><KL");
		add(";", "p['/.l;P{\"?>L:");
		add("'", "][;/'}{:?\"");
		add("z", "asxzASXZ");
		add("x", "zsdcxZSDCX");
		add("c", "xdfvcXDFVC");
		add("v", "cfgbvCFGBV");
		add("b", "vghnbVGHNB");
		add("n", "bhjmnBHJMN");
		add("m", "njk,mNJK<M");
		add(",", "mkl.,MKL><");
		add(".", ",l;/.<L:?>");
		add("/", "';./\":>?");
		add("{", "-=]';p[_+}\":P{");
		add("}", "=[']+{\"}");
		add("<", "mkl.,MKL><");
		add(">", ",l;/.<L:?>");
		add("?", "';./\":>?");
		add(":", "p['/.l;P{\"?>L:");
		add("\"", "][;/'}{:?\"");

		
		List<String> init = new ArrayList();

		if (type == 1) {
			for (String tmp : line)
				init.add(tmp);
		}

		if (type == 0) {
			for (String tmp : upper)
				init.add(tmp);
		}
		if (type == 2) {
			for (String tmp : upper)
				init.add(tmp);
			for (String tmp : line)
				init.add(tmp);
		}



		for (String tmp : init) {
			for (int i = 0; i <= tmp.length() - size; i++) {

				merged.add(tmp.substring(i, i + size));
			}
		}
		for (String tmp : init) {
			for (int i = 0; i <= tmp.length() - size; i++) {
				merged.add(utils.capsed(tmp.substring(i, i + size)));// capslock
			}
		}
		for (String tmp : init) {
			for (int i = 0; i <= tmp.length() - size; i++) {
				merged.add(utils.shifted(tmp.substring(i, i + size)));// shift
																		// pressed
			}
		}

		if (reverse == true)
			reverse();

		for (String tmp : init) {
			for (int i = 0; i <= tmp.length() - size; i++) {

				merged.add(utils.upperfirst(tmp.substring(i, i + size)));// upperfirts
			}
		}

	}

	static void runExternal() throws IOException, InterruptedException {
		System.out.println(exec_path + " " + temp_name);
		// hacked external program run
		Process p = Runtime.getRuntime().exec(exec_path + " " + temp_name);
		System.out.println("Waiting for batch file ...");
		BufferedReader stdOut = new BufferedReader(new InputStreamReader(p.getInputStream()));

		while ((stdOut.readLine()) != null)
			;

		System.out.println("Batch file done.");
	}

	static void write_to_file(String temp) {
		if (size > max_size) {
			System.out.println("Created");
			writer.close();

			try {
				runExternal();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			size = 0;
			(new File(temp_name)).delete();
			try {
				writer = new PrintWriter(temp_name, "UTF-8");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			size++;
			writer.println(temp);
		}
	}

	static void checkFileEnd() {

		try {
			runExternal();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		(new File(temp_name)).delete();

	}

	static void reverse() {

		HashSet<String> rev = new HashSet<String>();
		for (String tmp : merged) {
			rev.add(new StringBuilder(tmp).reverse().toString());
		}
		for (String tmp : merged) {

			rev.add(utils.upperfirst(new StringBuilder(tmp).reverse().toString()));// upperfirts
		}

		for (String tmp : rev) {
			merged.add(tmp);
		}
	}

	static void generate(HashSet<Part> iterate, Part start, int depth) {

		for (Part pass : iterate) {
			Part tmp = new Part();

			if (optimization == true)
				if (start.getPassword().length() > 0)
					if (!canBeCombined(start.getPassword(), pass.getPassword()))
						continue;
			tmp.append(start);
			tmp.append(pass);

			if (depth != 0) {
				generate(iterate, tmp, depth - 1);
			} else {

				if (policy == true)
					if (tmp.policyCheck() != false)
						continue;

				if (runExternal == true) {
					write_to_file(tmp.getPassword());

				} else {
					System.out.println(tmp.getPassword());
				}

			}

		}

	}

	static void help() {
		System.out.println("-e , --exec  executable path with options");
		System.out.println("Sample: -e \"D:\\hashcat\\hashcat-cli64.exe -m 0 test.txt\"");
		System.out.println("-t , --type 0|1");
		System.out.println("0 - upper");
		System.out.println("1 - line(default)");
		System.out.println("-p , --parts number");
		System.out.println("Sample qwe,asd,zxc, qwer...");
		System.out.println("-l , --length number");
		System.out.println("Sample qweasd,zxcqwe...");
		System.out.println("--policy use windows policy checker (false)");
		System.out.println("--reverse add reverse parts (false)");
	}

	public static void main(String[] args) {

		Integer type = 0;
		Integer parts = 3;
		Integer length = 3;
		Integer partsmax=0;
		merged = new HashSet<String>();
		for (int i = 0; i < args.length; i++) {
			if (args[i].equals("-e") || args[i].equals("--exec"))
				if (i + 1 < args.length) {
					exec_path = args[i + 1];
					runExternal = true;
				}
			if (args[i].equals("-d") || args[i].equals("--debug"))
				if (i + 1 < args.length)
					debug = Boolean.valueOf(args[i + 1]);

			if (args[i].equals("-t") || args[i].equals("--type"))
				if (i + 1 < args.length)
					type = Integer.parseInt(args[i + 1]);
			if (args[i].equals("-p") || args[i].equals("--parts"))
				if (i + 1 < args.length)
					parts = Integer.parseInt(args[i + 1]);
			if (args[i].equals("-l") || args[i].equals("--length"))
				if (i + 1 < args.length)
					length = Integer.parseInt(args[i + 1]);
			
			if (args[i].equals("-pm") || args[i].equals("--part-length-max"))
				if (i + 1 < args.length)
					partsmax = Integer.parseInt(args[i + 1]);
			
			
			if (args[i].equals("--policy"))
				policy = true;
			if (args[i].equals("--reverse"))
				reverse = true;
			if (args[i].equals("-h") || args[i].equals("--help"))
				help();
			if (args[i].equals("-o") || args[i].equals("--opt"))
				optimization = true;
		}

		temp_name = UUID.randomUUID().toString().replace("-", "");
		try {
			if (runExternal == true)
				writer = new PrintWriter(temp_name, "UTF-8");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(partsmax!=0)
		{
			
			for(int i=parts;i<=partsmax;i++)
			{
				System.out.println(i);
				init(type, i);
				System.out.println(merged.size());
			}
		}
		else
		{
			init(type, parts);
		}
		

		length = length - 1;// Human readable
		passwords = new HashSet<Part>();
		System.out.println(merged.size());
		for (String tmp : merged) {
			passwords.add(new Part(tmp));
		}
		merged.clear();
		generate(passwords, new Part(), length);
		if (runExternal == true)
			checkFileEnd();

	}
}