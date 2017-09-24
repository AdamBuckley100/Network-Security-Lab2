import java.io.*;

/**
 * 
 * @author Adam Buckley
 * references: JCrypt class author Eric Young and Solver.java class author Jimmy McGibney
 *
 */
public class MySolver 
{
	public static void main(String args[])
	{
		try
		{
			// Read in in ab_password file.
			BufferedReader myPasswords = new BufferedReader(new InputStreamReader(new FileInputStream("ab_passwd")));

			// Initialize password variable: this will hold the passwords in ab_passwd.
			String password;

			// While there's I haven't reached the end of the ab_passwd file
			while ( (password = myPasswords.readLine() ) != null)  
			{
				// Read in dictionary file.
				BufferedReader dict_br = new BufferedReader(new InputStreamReader(new FileInputStream("dictionary")));

				//now compare this one FULL (encrypted and salted) password (password) with all dict words

				String word; //the single dict word variable holder

				//Now extract out of password line: salt and stored_password

				String salted_password = password; //salted password is FULL password

				String salt = salted_password.substring(0,2); // just the salt (first 2 chars) of full password

				String stored_password = salted_password.substring(2); // just the encrypted password, not the salt, i.e. last 11 chars

				String word_encrypted; // this will be what is given back from JCrypt's equals method. (which will be A full password).

				// Has a word been found that matched for the password we are looking at? initialized false
				boolean foundADictWordThatMatchedForThisPass = false;

				// Number of times iterared through dictionary file
				int noTimeIterated = 0;

				// While I haven't reach end of dictionary file and I haven't found a match yet
				while ( (word = dict_br.readLine() ) != null && foundADictWordThatMatchedForThisPass == false )   
				{
					noTimeIterated++;
					word_encrypted = JCrypt.crypt(salt, word); // word is just dict. word, salt is first 2 chars of FULL password we're looking at. A full pass is handed back.

					// Print the contents on the console
					if (word_encrypted.equals(salt + stored_password)) // salt + stored_password is just full salted, encrypted password.
					{
						foundADictWordThatMatchedForThisPass = true;
						System.out.println("The dictinary word (aka actual password) for " + salted_password + " is: => " + word); // salted_password is full pass
					}
				}
				System.out.println(salted_password + "'s salt is " + salt);
				System.out.println("No. of iterations that were required to find " + salted_password + "'s dictionary word (password) was " + noTimeIterated);

				// close the dictionary file now
				dict_br.close();
			}
			System.out.println("Done: Now closing");
			// Close passwords file now
			myPasswords.close();
		}
		catch (Exception e)
		{
			System.out.println("Error: Program stopped");
			System.err.println("Error: " + e.getMessage());
		}
	}
}