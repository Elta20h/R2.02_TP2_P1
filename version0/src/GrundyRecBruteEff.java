import java.util.ArrayList;

/**
 * Grundy Game - Version 0
 *
 * @author  YONEL Denis && TARDY Elie
 */


/**
 * Main class
 */
class GrundyRecBruteEff {

	/**
	 * Internal operations counter
	 */
	long cpt;

    /**
     * Main entry point
     */
    void principal() {
        // testJouerGagnant();
		// testPremier();
		// testSuivant();
        // testDisplay();
        // testGetPlayableLines();
        // testIsPlayable();
        // testUpdateMatches();

        testEstGagnanteEfficacite();

        //game();
    }

	/**
	 * Main game method containing the game loop
	 */
	void game() {

		System.out.println("Grundy game :");

		String playerName = SimpleInput.getString("Please enter your name : ");

		int nbOfMatches = 0;

		do {

			nbOfMatches = SimpleInput.getInt("Please enter the number of matches : ");

		} while (nbOfMatches < 3);

		ArrayList<Integer> matches = new ArrayList<Integer>();

		matches.add(nbOfMatches);

		int currentPlayer = 0;

		do {

			currentPlayer = SimpleInput.getInt("Who plays first : \n1. " + playerName + "\n2. Computer\n> ");

		} while (currentPlayer < 1 || currentPlayer > 2);

		ArrayList<Integer> playableLines = getPlayableLines(matches);

		display(matches);

		while (getPlayableLines(matches).size() > 0) {

			if (currentPlayer == 1) {

				System.out.println("It's your turn " + playerName + " !");

				playableLines = getPlayableLines(matches);

				int line = -1;

				if (playableLines.size() == 1) {

					line = playableLines.get(0);

					System.out.println("You can only remove matches from line " + line + " !");

				} else {

					do {

						line = SimpleInput.getInt("Which line do you want to remove matches from ? ");

					} while (!playableLines.contains(line));

				}

				int matchesToRemove = 0;

				do {

					matchesToRemove = SimpleInput.getInt("How many matches do you want to remove ? ");

				} while (!isPlayable(matches, line, matchesToRemove));

				updateMatches(matches, line, matchesToRemove);

				display(matches);

				currentPlayer = 2;

			} else {

				System.out.println("It's the computer turn !");

				if (estGagnante(matches)) {

					jouerGagnant(matches);


				} else {

					System.out.println("The computer is playing randomly !");

					playableLines = getPlayableLines(matches);

                    int randomLine;

                    if (playableLines.size() == 1) {

                        randomLine = playableLines.get(0);

                    } else {

                        randomLine = (int) (Math.random() * (playableLines.size() - 1));

                    }

					System.out.println("The computer plays line " + randomLine + " !");

					int matchesToRemove = (int) (Math.random() * matches.get(randomLine));

					while (!isPlayable(matches, randomLine, matchesToRemove)) {

						matchesToRemove = (int) (Math.random() * matches.get(randomLine));

					}

					System.out.println("The computer removes " + matchesToRemove + " matches !");

					updateMatches(matches, randomLine, matchesToRemove);

				}

				display(matches);

				currentPlayer = 1;

			}

		}

		System.out.println("Game over !");

		System.out.print("The winner is ");

        if (currentPlayer == 1) {

            System.out.println("the computer !");

        } else {

            System.out.println(playerName + " !");

        }

	}

	/**
	 * Displays the matches in the current game state 
	 * 
	 * @param matches the current game state
	 */
	void display(ArrayList<Integer> matches) {

		System.out.println();

        if (matches != null) {

            for (int i = 0; i < matches.size(); i++) {

                System.out.print("Line " + i + " : ");
    
                for (int j = 0; j < matches.get(i); j++) {
    
                    System.out.print(" | ");
    
                }
    
                System.out.println();
    
            }

        }

		else {
            
            System.out.println("null");

        }

		System.out.println();

	}

    /**
     * Tests the display method
     */
    void testDisplay () {

        System.out.println("Testing display method : \n");

        int[] test1 = {3, 4, 5};

        String expected1 = "Line 0 :  |  |  | \nLine 1 :  |  |  |  | \nLine 2 :  |  |  |  |  | \n";

        testCaseDisplay(test1, expected1);

        int[] test2 = {1, 2, 3, 4, 5};

        String expected2 = "Line 0 :  | \nLine 1 :  |  | \nLine 2 :  |  |  | \nLine 3 :  |  |  |  | \nLine 4 :  |  |  |  |  | \n";

        testCaseDisplay(test2, expected2);

        int[] test3 = {0};

        String expected3 = "Line 0 : \n";

        testCaseDisplay(test3, expected3);

        int[] test4 = {};

        String expected4 = "";

        testCaseDisplay(test4, expected4);

        int[] test5 = null;

        String expected5 = "null";

        testCaseDisplay(test5, expected5);

        int[] test6 = {1};

        String expected6 = "Line 0 :  | \n";

        testCaseDisplay(test6, expected6);

    }

    /**
     * Tests the display method
     * 
     * @param matchesTable the current game state
     * @param expected the expected output
     */
    void testCaseDisplay (int[] matchesTable, String expected) {
        
        ArrayList<Integer> matches = new ArrayList<Integer>();

        if (matchesTable != null) {

            for (int i = 0; i < matchesTable.length; i++) {

                matches.add(matchesTable[i]);
    
            }

        }

        else {

            matches = null;

        }

        System.out.print("Test :");

        display(matches);

        System.out.println("Expected : \n" + expected + "\n");

    }

	/**
	 * Returns an ArrayList containing the indexes of the lines that can be played
	 * 
	 * @param matches the current game state
	 * @return an ArrayList containing the indexes of the lines that can be played
	 */
	ArrayList<Integer> getPlayableLines(ArrayList<Integer> matches) { 

		ArrayList<Integer> playableLines = new ArrayList<Integer>();

        if (matches == null) {

            playableLines = null;

        }

        else {

            for (int i = 0; i < matches.size(); i++) {

                if (matches.get(i) > 2) {
    
                    playableLines.add(i);
    
                }
    
            }

        }

		return playableLines;

	}

    /**
     * Tests the getPlayableLines method
     */
    void testGetPlayableLines() {

        System.out.println("\nTests getPlayableLines method : \n");

        int[] test1 = {3, 4, 5};

        int[] expected1 = {0, 1, 2};

        testCaseTestGetPlayableLines(test1, expected1);

        int[] test2 = {1, 2, 3, 4, 5};

        int[] expected2 = {2, 3, 4};

        testCaseTestGetPlayableLines(test2, expected2);

        int[] test3 = {0};

        int[] expected3 = {};

        testCaseTestGetPlayableLines(test3, expected3);

        int[] test4 = {1};

        int[] expected4 = {};

        testCaseTestGetPlayableLines(test4, expected4);

        int[] test5 = {2};

        int[] expected5 = {};

        testCaseTestGetPlayableLines(test5, expected5);

        int[] test6 = {3};

        int[] expected6 = {0};

        testCaseTestGetPlayableLines(test6, expected6);

        int[] test7 = {};

        int[] expected7 = {};

        testCaseTestGetPlayableLines(test7, expected7);

        int[] test8 = null;

        int[] expected8 = null;

        testCaseTestGetPlayableLines(test8, expected8);

    }

    /**
     * Tests the getPlayableLines method
     * 
     * @param matchesTable the current game state
     * @param expected the expected output
     */
    void testCaseTestGetPlayableLines(int[] matchesTable, int[] expected) {

        ArrayList<Integer> matches = new ArrayList<Integer>();

        if (matchesTable != null) {

            for (int i = 0; i < matchesTable.length; i++) {

                matches.add(matchesTable[i]);

            }

        }

        else {

            matches = null;

        }

        ArrayList<Integer> expentedTable = new ArrayList<Integer>();

        if (expected != null) {

            for (int i = 0; i < expected.length; i++) {

                expentedTable.add(expected[i]);
    
            }

        }
            
        else {

            expentedTable = null;

        }

        ArrayList<Integer> playableLines = getPlayableLines(matches);

        System.out.println("test for this matche table : " + matches);

        if (playableLines == null && expentedTable == null) {

            System.out.println("OK, they are null");

        }

        else if (playableLines != null && expentedTable == null ||
        playableLines == null && expentedTable != null) {

            System.out.println("error, expected : " + expentedTable +
            " but was : " + playableLines);

        }

        else if (playableLines.equals(expentedTable)) {

            System.out.println("OK");

        }

        else {

            System.out.println("erreur, expected : " + expentedTable +
            " but was : " + playableLines);

        }

        System.out.println("\n");

    }

	/**
	 * Returns true if the player can play the line with the number of matches he wants to remove
	 * 
	 * @param matches the current game state
	 * @param line the line the player wants to play
	 * @param matchesToRemove the number of matches the player wants to remove
	 * @return true if the player can play the line with the number of matches he wants to remove
	 */
	boolean isPlayable(ArrayList<Integer> matches, int line, int matchesToRemove) {

		boolean playable = true;

        if (matches == null || matches.size() == 0) {

            playable = false;

        }

        else {

            if (playable && matches.get(line) % 2 == 0) {

                if (matchesToRemove == (matches.get(line) / 2)) {
    
                    playable = false;
    
                }
    
            }
        
            if (matchesToRemove < 1 || matchesToRemove >= matches.get(line)) {
    
                playable = false;
    
            }

        }

		return playable;

	}

    /**
     * Tests the isPlayable method
     */
    void testIsPlayable() {

        System.out.println("Tests isPlayable method : \n");

        int[] test1 = {3, 4, 5};

        int line1 = 2;

        int matchesToRemove1 = 3;

        testCaseIsPlayable(test1, line1, matchesToRemove1, true);

        int line2 = 2;

        int matchesToRemove2 = 2;

        testCaseIsPlayable(test1, line2, matchesToRemove2, true);

        int line3 = 2;

        int matchesToRemove3 = 1;

        testCaseIsPlayable(test1, line3, matchesToRemove3, true);

        int line4 = 2;

        int matchesToRemove4 = 0;

        testCaseIsPlayable(test1, line4, matchesToRemove4, false);

        int line5 = 2;

        int matchesToRemove5 = 4;

        testCaseIsPlayable(test1, line5, matchesToRemove5, true);

        int line6 = 2;

        int matchesToRemove6 = 5;

        testCaseIsPlayable(test1, line6, matchesToRemove6, false);

        int line7 = 2;

        int matchesToRemove7 = 6;

        testCaseIsPlayable(test1, line7, matchesToRemove7, false);

        int line8 = 1;

        int matchesToRemove8 = 1;

        testCaseIsPlayable(test1, line8, matchesToRemove8, true);

        int line9 = 1;

        int matchesToRemove9 = 2;

        testCaseIsPlayable(test1, line9, matchesToRemove9, false);

        int line10 = 1;

        int matchesToRemove10 = 3;

        testCaseIsPlayable(test1, line10, matchesToRemove10, true);

        int line11 = 1;

        int matchesToRemove11 = 4;

        testCaseIsPlayable(test1, line11, matchesToRemove11, false);
        
        int[] test2 = {};

        int line12 = 0;

        int matchesToRemove12 = 4;

        testCaseIsPlayable(test2, line12, matchesToRemove12,  false);

        int[] test3 = null;

        int line13 = 1;

        int matchesToRemove13 = 4;

        testCaseIsPlayable(test3, line13, matchesToRemove13, false);
        
    }

    /**
     * Tests the isPlayable method
     * 
     * @param matchesTable the current game state
     * @param line the line the player wants to play
     * @param matchesToRemove the number of matches the player wants to remove
     * @param expected the expected result
     */
    void testCaseIsPlayable(int[] matchesTable, int line, int matchesToRemove, boolean expected) {

        ArrayList<Integer> matches = new ArrayList<Integer>();

        if (matchesTable != null) {

            for (int i = 0; i < matchesTable.length; i++) {

                matches.add(matchesTable[i]);
    
            }

        }

        boolean playable = isPlayable(matches, line, matchesToRemove);
         
        System.out.println("test for this matche table : " + matches);

        if (playable == expected) {

            System.out.println("OK");

        }

        else {

            System.out.println("error, expected : " + expected +
            " but was : " + playable);

        }

        System.out.println("\n");

    }

	/**
	 * Updates the matches ArrayList by removing the number of matches the player wants to remove
	 * 
	 * @param matches the current game state
	 * @param line the line the player wants to play
	 * @param matchesToRemove the number of matches the player wants to remove
	 */
	void updateMatches(ArrayList<Integer> matches, int line, int matchesToRemove) {

        if (matches != null && matches.size() > 0) {

            int temp = matches.get(line);

            matches.set(line, matchesToRemove);

            matches.add(temp - matchesToRemove);

        }

	}

    /**
     * Tests the updateMatches method
     */
    void testUpdateMatches() {

        System.out.println("Testing updateMatches method : \n");

        int[] test1 = {3, 4, 5};

        int[] expected1 = {3, 4, 3, 2};

        int line1 = 2;

        int matchesToRemove1 = 3;

        testCaseUpdateMatches(test1, expected1, line1, matchesToRemove1);

        int[] test2 = {4, 5};

        int[] expected2 = {4, 4, 1};

        int line2 = 1;

        int matchesToRemove2 = 4;

        testCaseUpdateMatches(test2, expected2, line2, matchesToRemove2);

        int[] test3 = {4, 5};

        int[] expected3 = {4, 5};

        int line3 = 1;

        int matchesToRemove3 = 5;

        testCaseUpdateMatches(test3, expected3, line3, matchesToRemove3);

        int[] test4 = {};

        int[] expected4 = {};

        int line4 = 0;

        int matchesToRemove4 = 0;

        testCaseUpdateMatches(test4, expected4, line4, matchesToRemove4);

        int[] test5 = null;

        int[] expected5 = null;

        int line5 = 0;

        int matchesToRemove5 = 0;

        testCaseUpdateMatches(test5, expected5, line5, matchesToRemove5);

    }

    /**
     * Tests the updateMatches method
     * 
     * @param matchesTable the current game state
     * @param expectedTable the expected result
     * @param line the line the player wants to play
     * @param matchesToRemove the number of matches the player wants to remove
     */
    void testCaseUpdateMatches(int[] matchesTable, int[] expectedTable, int line, int matchesToRemove) {

        ArrayList<Integer> matches = new ArrayList<Integer>();

        if (matchesTable != null) {

            for (int i = 0; i < matchesTable.length; i++) {

                matches.add(matchesTable[i]);

            }

        }

        ArrayList<Integer> expected = new ArrayList<Integer>();

        if (expectedTable != null) {

            for (int i = 0; i < expectedTable.length; i++) {

                expected.add(expectedTable[i]);

            }

        }

        System.out.print("Test :");

        updateMatches(matches, line, matchesToRemove);

        display(matches);

        System.out.print("Expected :");

        display(expected);

        System.out.println();

    }

    /**
     * Tests the efficiency of the estGagnante method
     */
    void testEstGagnanteEfficacite() {

        System.out.println("\n*** testEstGagnanteEfficacite : ***" );

        double t1,t2,diffT;
        int n = 3;

        while(n < 24){

            ArrayList<Integer>jeu = new ArrayList<Integer>();

            jeu.add(n);

            cpt = 0;

            t1 = System.nanoTime();

            estGagnante(jeu);

            t2 = System.nanoTime();

            diffT = (t2 - t1)/1000000;

            System.out.println("\nExpérience n° " + (n - 2) + " : ");
            System.out.println("\tn : " + n);
            System.out.println("\tTps : " + diffT + " ms");
            System.out.println("\tCpt : " + cpt);
                        
            n++;

        }

    }
	
    /**
     * Joue le coup gagnant s'il existe
     * 
     * @param jeu plateau de jeu
     * @return vrai s'il y a un coup gagnant, faux sinon
     */
    boolean jouerGagnant(ArrayList<Integer> jeu) {
        boolean gagnant = false;
        if (jeu == null) {
            System.err.println("suivant(): le paramètre jeu est null");
        } else {
            ArrayList<Integer> essai = new ArrayList<Integer>();
            int ligne = premier(jeu, essai);
			// mise en oeuvre de la règle numéro2
			// Une situation (ou position) est dite gagnante pour la machine (ou le joueur, peu importe), s’il existe AU MOINS UNE 
			// décomposition (c-à-d UNE action qui consiste à décomposer un tas en 2 tas inégaux) perdante pour l’adversaire.
            while (ligne != -1 && !gagnant) {
                if (estPerdante(essai)) {
                    jeu.clear();
                    gagnant = true;
                    for (int i = 0; i < essai.size(); i++) {
                        jeu.add(essai.get(i));
                    }
                } else {
                    ligne = suivant(jeu, essai, ligne);
                }
            }
        }
		
        return gagnant;
    }
	
	/**
     * Méthode RECURSIVE qui indique si la configuration (du jeu actuel ou jeu d'essai) est perdante
     * 
     * @param jeu plateau de jeu actuel (l'état du jeu à un certain moment au cours de la partie)
     * @return vrai si la configuration (du jeu) est perdante, faux sinon
     */
    boolean estPerdante(ArrayList<Integer> jeu) {
	
        boolean ret = true; // par défaut la configuration est perdante
		
        if (jeu == null) {
            System.err.println("estPerdante(): le paramètre jeu est null");
        }
		
		else {
			// si il n'y a plus que des tas de 1 ou 2 allumettes dans le plateau de jeu
			// alors la situation est forcément perdante (ret=true) = FIN de la récursivité
            if ( !estPossible(jeu) ) {

                ret = true;
                
            }
			
			else {
				// création d'un jeu d'essais qui va examiner toutes les décompositions
				// possibles à partir de jeu
                ArrayList<Integer> essai = new ArrayList<Integer>(); // size = 0
				
				// toute première décomposition : enlever 1 allumette au premier tas qui possède
				// au moins 3 allumettes, ligne = -1 signifie qu'il n'y a plus de tas d'au moins 3 allumettes
                int ligne = premier(jeu, essai);

                while ( (ligne != -1) && ret) {

					cpt++;
					// mise en oeuvre de la règle numéro1
					// Une situation (ou position) est dite perdante pour la machine (ou le joueur, peu importe) si et seulement si TOUTES 
					// ses décompositions possibles (c-à-d TOUTES les actions qui consistent à décomposer un tas en 2 tas inégaux) sont 
					// TOUTES gagnantes pour l’adversaire.
					// Si UNE SEULE décomposition (à partir du jeu) est perdante (pour l'adversaire) alors la configuration n'EST PAS perdante.
					// Ici l'appel à "estPerdante" est RECURSIF.
                    if (estPerdante(essai) == true) {
					
                        ret = false;
						
                    } else {
						// génère la configuration d'essai suivante (c'est-à-dire UNE décomposition possible)
						// à partir du jeu, si ligne = -1 il n'y a plus de décomposition possible
                        ligne = suivant(jeu, essai, ligne);
                    }
                }
            }
        }
		
        return ret;
    }
	
	/**
     * Indique si la configuration est gagnante.
	 * Méthode qui appelle simplement "estPerdante".
     * 
     * @param jeu plateau de jeu
     * @return vrai si la configuration est gagnante, faux sinon
     */
    boolean estGagnante(ArrayList<Integer> jeu) {
        boolean ret = false;
        if (jeu == null) {
            System.err.println("estGagnante(): le paramètre jeu est null");
        } else {
            ret = !estPerdante(jeu);
        }
        return ret;
    }

    /**
     * Tests succincts de la méthode joueurGagnant()
     */
    void testJouerGagnant() {
        System.out.println();
        System.out.println("*** testJouerGagnant() ***");

        System.out.println("Test des cas normaux");
        ArrayList<Integer> jeu1 = new ArrayList<Integer>();
        jeu1.add(6);
        ArrayList<Integer> resJeu1 = new ArrayList<Integer>();
        resJeu1.add(4);
        resJeu1.add(2);
		
        testCasJouerGagnant(jeu1, resJeu1, true);
        
    }

    /**
     * Test d'un cas de la méthode jouerGagnant()
	 *
	 * @param jeu le plateau de jeu
	 * @param resJeu le plateau de jeu après avoir joué gagnant
	 * @param res le résultat attendu par jouerGagnant
     */
    void testCasJouerGagnant(ArrayList<Integer> jeu, ArrayList<Integer> resJeu, boolean res) {
        // Arrange
        System.out.print("jouerGagnant (" + jeu.toString() + ") : ");

        // Act
        boolean resExec = jouerGagnant(jeu);

        // Assert
        System.out.print(jeu.toString() + " " + resExec + " : ");
        if (jeu.equals(resJeu) && res == resExec) {
            System.out.println("OK\n");
        } else {
            System.err.println("ERREUR\n");
        }
    }	

    /**
     * Divise en deux tas les alumettes d'une ligne de jeu (1 ligne = 1 tas)
     * 
     * @param jeu   tableau des alumettes par ligne
     * @param ligne ligne (tas) sur laquelle les alumettes doivent être séparées
     * @param nb    nombre d'alumettes RETIREE de la ligne après séparation
     */
    void enlever ( ArrayList<Integer> jeu, int ligne, int nb ) {
		// traitement des erreurs
        if (jeu == null) {
            System.err.println("enlever() : le paramètre jeu est null");
        } else if (ligne >= jeu.size()) {
            System.err.println("enlever() : le numéro de ligne est trop grand");
        } else if (nb >= jeu.get(ligne)) {
            System.err.println("enlever() : le nb d'allumettes à retirer est trop grand");
        } else if (nb <= 0) {
            System.err.println("enlever() : le nb d'allumettes à retirer est trop petit");
        } else if (2 * nb == jeu.get(ligne)) {
            System.err.println("enlever() : le nb d'allumettes à retirer est la moitié");
        } else {
			// nouveau tas (case) ajouté au jeu (nécessairement en fin de tableau)
			// ce nouveau tas contient le nbre d'allumettes retirées (nb) du tas à séparer			
            jeu.add(nb);
			// le tas restant avec "nb" allumettes en moins
            jeu.set(ligne, jeu.get(ligne) - nb);
        }
    }

    /**
     * Teste s'il est possible de séparer un des tas
     * 
     * @param jeu      plateau de jeu
     * @return vrai s'il existe au moins un tas de 3 allumettes ou plus, faux sinon
     */
    boolean estPossible(ArrayList<Integer> jeu) {
        boolean ret = false;
        if (jeu == null) {
            System.err.println("estPossible(): le paramètre jeu est null");
        } else {
            int i = 0;
            while (i < jeu.size() && !ret) {
                if (jeu.get(i) > 2) {
                    ret = true;
                }
                i = i + 1;
            }
        }
        return ret;
    }

    /**
     * Crée une toute première configuration d'essai à partir du jeu
     * 
     * @param jeu      plateau de jeu
     * @param jeuEssai nouvelle configuration du jeu
     * @return le numéro du tas divisé en deux ou (-1) si il n'y a pas de tas d'au moins 3 allumettes
     */
    int premier(ArrayList<Integer> jeu, ArrayList<Integer> jeuEssai) {
	
        int numTas = -1; // pas de tas à séparer par défaut
		int i;
		
        if (jeu == null) {
            System.err.println("premier(): le paramètre jeu est null");
        } else if (!estPossible((jeu)) ){
            System.err.println("premier(): aucun tas n'est divisible");
        } else if (jeuEssai == null) {
            System.err.println("estPossible(): le paramètre jeuEssai est null");
        } else {
            // avant la copie du jeu dans jeuEssai il y a un reset de jeuEssai 
            jeuEssai.clear();
            i = 0;
			
			// recopie case par case
			// jeuEssai est le même que le jeu au départ
            while (i < jeu.size()) {
                jeuEssai.add(jeu.get(i));
                i = i + 1;
            }
			
            i = 0;
			// rechercher un tas d'allumettes d'au moins 3 allumettes dans le jeu
			// sinon numTas = -1
			boolean trouve = false;
            while ( (i < jeu.size()) && !trouve) {
				
				// si on trouve un tas d'au moins 3 allumettes
				if ( jeuEssai.get(i) >= 3 ) {
					trouve = true;
					numTas = i;
				}
				
				i = i + 1;
            }
			
			// sépare le tas (case numTas) en un tas d'UNE SEULE allumette à la fin du tableau 
			// le tas en case numTas a diminué d'une allumette (retrait d'une allumette)
			// jeuEssai est le plateau de jeu qui fait apparaître cette séparation
            if ( numTas != -1 ) enlever ( jeuEssai, numTas, 1 );
        }
		
        return numTas;
    }

    /**
     * Tests succincts de la méthode premier()
     */
    void testPremier() {
        System.out.println();
        System.out.println("*** testPremier()");

        ArrayList<Integer> jeu1 = new ArrayList<Integer>();
        jeu1.add(10);
        jeu1.add(11);
        int ligne1 = 0;
        ArrayList<Integer> res1 = new ArrayList<Integer>();
        res1.add(9);
        res1.add(11);
        res1.add(1);
        testCasPremier(jeu1, ligne1, res1);
    }

    /**
     * Test un cas de la méthode testPremier
	 * @param jeu le plateau de jeu
	 * @param ligne le numéro du tas séparé en premier
	 * @param res le plateau de jeu après une première séparation
     */
    void testCasPremier(ArrayList<Integer> jeu, int ligne, ArrayList<Integer> res) {
        // Arrange
        System.out.print("premier (" + jeu.toString() + ") : ");
        ArrayList<Integer> jeuEssai = new ArrayList<Integer>();
        // Act
        int noLigne = premier(jeu, jeuEssai);
        // Assert
        System.out.println("\nnoLigne = " + noLigne + " jeuEssai = " + jeuEssai.toString());
        if (jeuEssai.equals(res) && noLigne == ligne) {
            System.out.println("OK\n");
        } else {
            System.err.println("ERREUR\n");
        }
    }

    /**
     * Génère la configuration d'essai suivante (c'est-à-dire UNE décomposition possible)
     * 
     * @param jeu      plateau de jeu
     * @param jeuEssai configuration d'essai du jeu après séparation
     * @param ligne    le numéro du tas qui est le dernier à avoir été séparé
     * @return le numéro du tas divisé en deux pour la nouvelle configuration, -1 si plus aucune décomposition n'est possible
     */
    int suivant(ArrayList<Integer> jeu, ArrayList<Integer> jeuEssai, int ligne) {
	
        // System.out.println("suivant(" + jeu.toString() + ", " +jeuEssai.toString() +
        // ", " + ligne + ") = ");
		
		int numTas = -1; // par défaut il n'y a plus de décomposition possible
		
        int i = 0;
		// traitement des erreurs
        if (jeu == null) {
            System.err.println("suivant(): le paramètre jeu est null");
        } else if (jeuEssai == null) {
            System.err.println("suivant() : le paramètre jeuEssai est null");
        } else if (ligne >= jeu.size()) {
            System.err.println("estPossible(): le paramètre ligne est trop grand");
        }
		
		else {
		
			int nbAllumEnLigne = jeuEssai.get(ligne);
			int nbAllDernCase = jeuEssai.get(jeuEssai.size() - 1);
			
			// si sur la même ligne (passée en paramètre) on peut encore retirer des allumettes,
			// c-à-d si l'écart entre le nombre d'allumettes sur cette ligne et
			// le nombre d'allumettes en fin de tableau est > 2, alors on retire encore
			// 1 allumette sur cette ligne et on ajoute 1 allumette en dernière case		
            if ( (nbAllumEnLigne - nbAllDernCase) > 2 ) {
                jeuEssai.set ( ligne, (nbAllumEnLigne - 1) );
                jeuEssai.set ( jeuEssai.size() - 1, (nbAllDernCase + 1) );
                numTas = ligne;
            } 
			
			// sinon il faut examiner le tas (ligne) suivant du jeu pour éventuellement le décomposer
			// on recrée une nouvelle configuration d'essai identique au plateau de jeu
			else {
                // copie du jeu dans JeuEssai
                jeuEssai.clear();
                for (i = 0; i < jeu.size(); i++) {
                    jeuEssai.add(jeu.get(i));
                }
				
                boolean separation = false;
                i = ligne + 1; // tas suivant
				// si il y a encore un tas et qu'il contient au moins 3 allumettes
				// alors on effectue une première séparation en enlevant 1 allumette
                while ( i < jeuEssai.size() && !separation ) {
					// le tas doit faire minimum 3 allumettes
                    if ( jeu.get(i) > 2 ) {
                        separation = true;
						// on commence par enlever 1 allumette à ce tas
                        enlever(jeuEssai, i, 1);
						numTas = i;
                    } else {
                        i = i + 1;
                    }
                }				
            }
        }
		
        return numTas;
    }

    /**
     * Tests succincts de la méthode suivant()
     */
    void testSuivant() {
        System.out.println();
        System.out.println("*** testSuivant() ****");

        int ligne1 = 0;
        int resLigne1 = 0;
        ArrayList<Integer> jeu1 = new ArrayList<Integer>();
        jeu1.add(10);
        ArrayList<Integer> jeuEssai1 = new ArrayList<Integer>();
        jeuEssai1.add(9);
        jeuEssai1.add(1);
        ArrayList<Integer> res1 = new ArrayList<Integer>();
        res1.add(8);
        res1.add(2);
        testCasSuivant(jeu1, jeuEssai1, ligne1, res1, resLigne1);

        int ligne2 = 0;
        int resLigne2 = -1;
        ArrayList<Integer> jeu2 = new ArrayList<Integer>();
        jeu2.add(10);
        ArrayList<Integer> jeuEssai2 = new ArrayList<Integer>();
        jeuEssai2.add(6);
        jeuEssai2.add(4);
        ArrayList<Integer> res2 = new ArrayList<Integer>();
        res2.add(10);
        testCasSuivant(jeu2, jeuEssai2, ligne2, res2, resLigne2);

        int ligne3 = 1;
        int resLigne3 = 1;
        ArrayList<Integer> jeu3 = new ArrayList<Integer>();
        jeu3.add(4);
        jeu3.add(6);
        jeu3.add(3);
        ArrayList<Integer> jeuEssai3 = new ArrayList<Integer>();
        jeuEssai3.add(4);
        jeuEssai3.add(5);
        jeuEssai3.add(3);
        jeuEssai3.add(1);
        ArrayList<Integer> res3 = new ArrayList<Integer>();
        res3.add(4);
        res3.add(4);
        res3.add(3);
        res3.add(2);
        testCasSuivant(jeu3, jeuEssai3, ligne3, res3, resLigne3);

    }

    /**
     * Test un cas de la méthode suivant
	 * 
	 * @param jeu le plateau de jeu
	 * @param jeuEssai le plateau de jeu obtenu après avoir séparé un tas
	 * @param ligne le numéro du tas qui est le dernier à avoir été séparé
	 * @param resJeu est le jeuEssai attendu après séparation
	 * @param resLigne est le numéro attendu du tas qui est séparé
     */
    void testCasSuivant(ArrayList<Integer> jeu, ArrayList<Integer> jeuEssai, int ligne, ArrayList<Integer> resJeu, int resLigne) {
        // Arrange
        System.out.print("suivant (" + jeu.toString() + ", " + jeuEssai.toString() + ", " + ligne + ") : ");
        // Act
        int noLigne = suivant(jeu, jeuEssai, ligne);
        // Assert
        System.out.println("\nnoLigne = " + noLigne + " jeuEssai = " + jeuEssai.toString());
        if (jeuEssai.equals(resJeu) && noLigne == resLigne) {
            System.out.println("OK\n");
        } else {
            System.err.println("ERREUR\n");
        }
    }

}