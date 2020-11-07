package eu.lestard.snakefx.view.menu;

import de.saxsys.mvvmfx.FxmlView;
import de.saxsys.mvvmfx.InjectViewModel;
import eu.lestard.snakefx.util.TriggerablePopup;
import eu.lestard.snakefx.view.about.AboutView;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class MenuView implements FxmlView<MenuViewModel>, Initializable {

    @InjectViewModel
    private MenuViewModel viewModel;

    @FXML
    private AnchorPane menuViewPane;

    private TriggerablePopup aboutPopup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        menuViewPane.sceneProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                final Window window = menuViewPane.getScene().getWindow();
                aboutPopup = new TriggerablePopup(AboutView.class, (Stage) window);
                aboutPopup.trigger().bindBidirectional(viewModel.aboutPopupVisible());
            }
        });
    }

    @FXML
    public void newGame() {

        ChoiceDialog<Integer> sizeChoiceDialog = new ChoiceDialog<>();

        sizeChoiceDialog.setHeaderText("Enter the Size of the new Game");
        sizeChoiceDialog.setTitle("New Game");
        sizeChoiceDialog.setContentText("Size:");

        sizeChoiceDialog.getItems().addAll(viewModel.sizeOptions());
        sizeChoiceDialog.setSelectedItem(viewModel.newSize().get());

        final Optional<Integer> newSizeOptional = sizeChoiceDialog.showAndWait();

        newSizeOptional.ifPresent(viewModel.newSize()::setValue);

        viewModel.newGame();
    }

    @FXML
    public void showHighscores() {
        viewModel.showHighscores();
    }

    @FXML
    public void about() {
        viewModel.about();
    }

    @FXML
    public void exit() {
        viewModel.exit();
    }

}
