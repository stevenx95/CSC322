package org.guccigang.mini_google_docs.controller.DocumentControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

import org.guccigang.mini_google_docs.model.UILocation;
import org.guccigang.mini_google_docs.controller.ComplaintControllers.VisitorComplaintFormController;
import org.guccigang.mini_google_docs.model.DocumentFile;
import org.guccigang.mini_google_docs.model.GuiUtil;

import java.net.URL;
import java.util.ResourceBundle;


public class VisitorTextEditorController  implements Initializable {
    private DocumentFile selectedDocument;

    @FXML
    private TextArea areaText;



    public VisitorTextEditorController(DocumentFile selectedDocument) {
        this.selectedDocument = selectedDocument;
    }

    public VisitorTextEditorController() {
        this(null);
    }
    @FXML
    private void onSave(){

    }
    @FXML
    private void handleComplaint(){
        try {
            VisitorComplaintFormController controller = new VisitorComplaintFormController(selectedDocument);
            GuiUtil.createWindow(UILocation.VISITOR_COMPLAINT_FORM_VIEW, "Complaint Form", controller);
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

    public void setSelectedDocument(DocumentFile selectedDocument){
        this.selectedDocument = selectedDocument;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.areaText.setText(selectedDocument.getContent());
        this.areaText.setEditable(false);
    }
}
