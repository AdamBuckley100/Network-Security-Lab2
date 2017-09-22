import java.io.*;

public class MySolver 
{
	public static void main(String args[])
	{

		try
		{
			BufferedReader myPasswords = new BufferedReader(new InputStreamReader(new FileInputStream("ab_passwd")));

			String password;

			BufferedReader dict_br = new BufferedReader(new InputStreamReader(new FileInputStream("dictionary")));

			while ( (password = myPasswords.readLine() ) != null)  
			{
				//Now looking at 1 passwords and every dict word

				//now compare this one FULL (encrypted and salted) password (password) with all dict words

				String word; //the single dict word variable holder

				//Now extract out of password line: salt and stored_password

				String salted_password = password; //salted password is FULL password

				String salt = salted_password.substring(0,2); // just the salt (first 2 chars) of full password

				String stored_password = salted_password.substring(2); // just the encrypted password, not the salt, i.e. last 11 chars

				String word_encrypted; // this will be what is given back from JCrypt's equals method. (which will be A full password).

				boolean foundADictWordThatMatchedForThisPass = false;

				while ( (word = dict_br.readLine() ) != null && foundADictWordThatMatchedForThisPass == false)   
				{
					word_encrypted = JCrypt.crypt(salt, word); // word is just dict. word, salt is first 2 chars of FULL password we're looking at. A full pass is handed back.

					// Print the content on the console
					if (word_encrypted.equals(salt + stored_password)) // salt + stored_password is just full salted + encrypted password.
					{
						foundADictWordThatMatchedForThisPass = true;
						System.out.println("The dictinary word for " + salted_password + " is " + word); // salted_password is full pass
					}
				}
			}
			myPasswords.close();
			dict_br.close();
		}
		catch (Exception e)
		{
			System.out.println("Error: Program stopped");
			System.err.println("Error: " + e.getMessage());
		}
	}
}