package it.polito.tdp.seriea;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.seriea.model.Model;
import it.polito.tdp.seriea.model.Season;
import it.polito.tdp.seriea.model.SquadraNumero;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;

//controller turno A --> switchare al branch master_turnoB o master_turnoC per turno B o C

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ChoiceBox<String> boxSquadra;

    @FXML
    private ChoiceBox<Season> boxStagione;

    @FXML
    private Button btnCalcolaConnessioniSquadra;

    @FXML
    private Button btnSimulaTifosi;

    @FXML
    private Button btnAnalizzaSquadre;

    @FXML
    private TextArea txtResult;

    @FXML
    void doAnalizzaSquadre(ActionEvent event) {
    	this.txtResult.clear();
    	this.model.generateGraph();
    	this.txtResult.appendText("Il grafo è stato generato con successo!\n");
    	this.txtResult.appendText("Il numero di vertici è pari a: "+this.model.getNumVertici()+"\n");
    	this.txtResult.appendText("Il numero di archi è pari a: "+this.model.getNumArchi()+"\n");
    	
    	this.boxSquadra.getItems().setAll(this.model.getSquadre());
    	this.btnCalcolaConnessioniSquadra.setDisable(false);
    }

    @FXML
    void doCalcolaConnessioniSquadra(ActionEvent event) {
    	this.txtResult.clear();
    	String squadra = this.boxSquadra.getValue();
    	if(squadra.equals(null)) {
    		this.txtResult.appendText("Scegliere una squadra dal menu a tendina!\n");
    		return;
    	}
    	
    	this.txtResult.appendText("Le connessioni per la squadra selezionata sono:\n");
    	List<SquadraNumero> sn = this.model.getSquadreVicine(squadra);
    	for(SquadraNumero s : sn) {
    		this.txtResult.appendText(s.toString()+"\n");
    	}
    	
    	this.boxStagione.getItems().setAll(this.model.getAllSeason());
    	this.btnSimulaTifosi.setDisable(false);
    }

    @FXML
    void doSimulaTifosi(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Season stagione = this.boxStagione.getValue();
    	if(stagione.equals(null)) {
    		this.txtResult.appendText("Scegliere una stagione dal menu a tendina!\n");
    		return;
    	}
    	
    	this.model.simula(stagione);
    	List<SquadraNumero> tifosi = this.model.getTifosi();
    	List<SquadraNumero> punti = this.model.getPunti();
    	this.txtResult.appendText("Il numero di tifosi per ogni squadra è:\n\n");
    	
    	for(SquadraNumero sn : tifosi) {
    		this.txtResult.appendText(sn.toString()+"\n");
    	}
    	
    	this.txtResult.appendText("\nIl numero di punti per ogni squadra è:\n\n");
    	for(SquadraNumero sn : punti) {
    		this.txtResult.appendText(sn.toString()+"\n");
    	}
    }

    @FXML
    void initialize() {
        assert boxSquadra != null : "fx:id=\"boxSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert boxStagione != null : "fx:id=\"boxStagione\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnCalcolaConnessioniSquadra != null : "fx:id=\"btnCalcolaConnessioniSquadra\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnSimulaTifosi != null : "fx:id=\"btnSimulaTifosi\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert btnAnalizzaSquadre != null : "fx:id=\"btnAnalizzaSquadre\" was not injected: check your FXML file 'SerieA.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'SerieA.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.btnCalcolaConnessioniSquadra.setDisable(true);
		this.btnSimulaTifosi.setDisable(true);
	}
}