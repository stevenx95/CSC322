package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;

import org.guccigang.mini_google_docs.UILocation;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.VisitorComplaintFormController;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.GuiUtil;



public class VisitorTextEditorController {
    private DocumentFile selectedDocument;

    @FXML
    private TextArea areaText;


    @FXML
    private void onSave(){

    }
    @FXML
    private void handleComplaint(ActionEvent event){
        try {
            VisitorComplaintFormController controller = new VisitorComplaintFormController(selectedDocument);
            GuiUtil.createWindow(event, UILocation.VISITOR_COMPLAINT_FORM_VIEW, "Complaint Form", controller);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @FXML
    private void onClose(){


    }

    @FXML
    private void onAbout(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setTitle("About");
        alert.setContentText("This is a simple text editor");
        alert.show();
    }
    @FXML
    private void initialize(){
        this.areaText.setEditable(false);
    }
    public void setSelectedDocument(DocumentFile selectedDocument){
        this.selectedDocument = selectedDocument;
    }
    public void setAreaText() {
        this.areaText.setText(selectedDocument.getContent());
    }

}
