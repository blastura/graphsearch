import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Startklass för sökalgoritmen. 
 * 
 * SearchInterface kan anropas med tre eller fem parametrar. 
 * De två första parametrarna anger namnet på er sökklass respektive kartan som ska sökas. 
 * Parameter tre anger sökmetod, B, D, Ad, At eller G vilket motsvarar bredden
 * först, djupet först, A* med kortaste avstånd, A* med kortaste tid,
 * respektive Greedy Search.
 * 
 * Parameter fyra och fem anger start och mål för sökningen. Om dessa utelämnas kommer programmet 
 * att fråga efter dem.  
 * 
 * Exempel: java SearchInterface MySearcher umea_map.xml B Teg Begbilar
 * 
 * @author billing
 */
public class SearchInterface {
	
	public static void main(String[] args) {
		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		
		try {
			if (args.length < 3) {
				System.err.println("Sökklass, karta och sökmetod måste anges.");
			} else {
				Class c = Class.forName(args[0]); // Importerar sökklassen
				MapSearcher searcher = (MapSearcher)c.newInstance(); // Instansierar
				searcher.setMap(new File(args[1])); // Anger kartan
				
				String from;
				String to;
				if (args.length > 3) {
					from=args[3];
				} else {
					System.out.print("Ange startnod: ");
					from = stdin.readLine();
				}
				if (args.length > 4) {
					to=args[4];
				} else {
					System.out.print("Ange målnod: ");
					to = stdin.readLine();
				}
				
				System.out.print(args[0]+" ");
				if (args[2].equals("B")) {
					System.out.print("söker med bredden först... ");
					System.out.println("klar.\n"+searcher.breadthFirst(from,to)); 
				} else if (args[2].equals("D")) {
					System.out.print("söker med djupet först... ");
					System.out.println("klar.\n"+searcher.depthFirst(from,to)); 
				} else if (args[2].equals("Ad")) {
					System.out.print("söker med A* (kortaste vägen)... ");
					System.out.println("klar.\n"+searcher.aStar(from,to,false)); 
				} else if (args[2].equals("At")) {
					System.out.print("söker med A* (snabbaste vägen)... ");
					System.out.println("klar.\n"+searcher.aStar(from,to,true)); 
				} else if (args[2].equals("G")) {
					System.out.print("söker med Greedy Search... ");
					System.out.println("klar.\n"+searcher.greedySearch(from,to)); 
				}
				
				
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
