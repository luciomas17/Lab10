package it.polito.tdp.porto;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.porto.model.Author;
import it.polito.tdp.porto.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class PortoController {
	
	Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<Author> boxPrimo;

    @FXML
    private ComboBox<?> boxSecondo;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleCoautori(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Author a;
    	if(this.boxPrimo.getSelectionModel().isEmpty()) {
    		this.txtResult.appendText("Errore: selezionare un autore!");
    		return;
    	} else
    		a = this.boxPrimo.getSelectionModel().getSelectedItem();
    	
    	List<Author> coAuthors = model.getNeighbors(a);
    	if(coAuthors == null) {
    		this.txtResult.appendText("Nessun co-autore trovato per " + a);
    		return;
    	}
    	
    	this.txtResult.appendText("Co-autori di " + a + ":\n\n");
    	for(Author ca : coAuthors)
    		this.txtResult.appendText(ca + "\n");
    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxPrimo != null : "fx:id=\"boxPrimo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert boxSecondo != null : "fx:id=\"boxSecondo\" was not injected: check your FXML file 'Porto.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Porto.fxml'.";

    }
    
	public void setModel(Model model) {
		this.model = model;
		createGraph();
		populateComboBox(this.boxPrimo);
	}

	private void createGraph() {
		this.model.createGraph();
	}
	
	private void populateComboBox(ComboBox<Author> boxPrimo) {
		boxPrimo.getItems().addAll(model.getAuthors());
	}
	
}
