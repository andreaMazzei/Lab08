package it.polito.tdp.dizionariograph;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.dizionariograph.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class DizionarioGraphController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField txtNumeroLettere;

    @FXML
    private TextField txtParolaDaCercare;

    @FXML
    private Button btnGeneraGrafo;

    @FXML
    private Button btnTrovaVicini;

    @FXML
    private Button btnTrovaGradoMax;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnReset;

    @FXML
    void doGeneraGrafo(ActionEvent event) {
    	txtResult.clear();
    	txtParolaDaCercare.clear();
    	
    	try {
    		int numeroLettere = Integer.parseInt(txtNumeroLettere.getText());
        	List<String> paroleTrovate = model.createGraph(numeroLettere);
        	
        	if(paroleTrovate != null)
        		txtResult.setText("Trovate " + paroleTrovate.size() + " parole di lunghezza " + numeroLettere);
        	else
        		txtResult.setText("Trovate 0 parole di lunghezza " + numeroLettere);
        	
    	}catch(NumberFormatException ne) {
    		txtResult.setText("Inserire un numero!");
    		return;
    	}catch(RuntimeException re) {
    		txtResult.setText(re.getMessage());
    	}
    	
    	txtNumeroLettere.setDisable(true);
    	btnGeneraGrafo.setDisable(true);
    	txtParolaDaCercare.setDisable(false);
    	btnTrovaVicini.setDisable(false);
    	btnTrovaGradoMax.setDisable(false);
    }

    @FXML
    void doReset(ActionEvent event) {
    	txtNumeroLettere.clear();
    	txtParolaDaCercare.clear();
    	txtResult.clear();
    	txtNumeroLettere.setDisable(false);
    	btnGeneraGrafo.setDisable(false);
    	txtParolaDaCercare.setDisable(true);
    	btnTrovaVicini.setDisable(true);
    	btnTrovaGradoMax.setDisable(true);
    }

    @FXML
    void doTrovaGradoMax(ActionEvent event) {
    	try {
        	txtResult.setText(model.findMaxDegree());
        	
    	}catch(RuntimeException re) {
    		txtResult.setText(re.getMessage());
    	}
    }

    @FXML
    void doTrovaVicini(ActionEvent event) {
    	try {
    		
    		String parola = txtParolaDaCercare.getText();
        	if(parola == null || parola.length() == 0) {
        		txtResult.setText("Inserire una parola!");
        		return;
        	}
        	List<String> vicini = model.displayNeighbours(parola);
        	if(vicini.size() == 0)
        		txtResult.setText("Non è stato trovato nessun vicino");
        	else {
        		txtResult.setText("Trovati " + vicini.size() + " vicini:\n");
        		for(String s : vicini)
        			txtResult.appendText("- "+s+"\n");
        	}
        	
    	}catch(RuntimeException re) {
    		txtResult.setText(re.getMessage());
    	}
    }

    @FXML
    void initialize() {
        assert txtNumeroLettere != null : "fx:id=\"txtNumeroLettere\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtParolaDaCercare != null : "fx:id=\"txtParolaDaCercare\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnGeneraGrafo != null : "fx:id=\"btnGeneraGrafo\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnTrovaVicini != null : "fx:id=\"btnTrovaVicini\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnTrovaGradoMax != null : "fx:id=\"btnTrovaGradoMax\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";
        assert btnReset != null : "fx:id=\"btnReset\" was not injected: check your FXML file 'DizionarioGraph.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
	}
}
