import java.io.*;

public class Solver 
{
	public static void main(String args[])
	{	
		String salted_password = "deBIa3tgJkioA";

		String salt = salted_password.substring(0,2);

		String stored_password =  salted_password.substring(2);

		// step through all words in the dictionary		
		try
		{
			BufferedReader dict_br = new BufferedReader(new InputStreamReader(new FileInputStream("dictionary")));
			String word;
			String word_encrypted;

			boolean found = false;

			while ( (word = dict_br.readLine() ) != null && found == false)   
			{
				word_encrypted = JCrypt.crypt(salt, word);

				// Print the content on the console
				if (word_encrypted.equals(salt + stored_password))
				{
					found = true;
					System.out.println(word + " found!");
				}
			}
			dict_br.close();
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
}