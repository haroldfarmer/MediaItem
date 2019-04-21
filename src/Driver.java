import java.io.ObjectInputStream;

import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

//Author: Harold Farmer
//Any constructive criticism is appreciated
//this is a work in progress
public class Driver extends Application {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		Application.launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
        // Creats the arraylist to be used later
		ArrayList<MediaItem> mediaItem = new ArrayList<MediaItem>();
		

		File accountFile = new File("mediaItem.dat");

		// Checks to see if the file exist and if it does then it reads in the file
		if (accountFile.exists()) {
			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File("mediaItem.dat")));
				mediaItem = (ArrayList<MediaItem>) ois.readObject();
				ois.close();
			} catch (Exception e) {
				System.out.println("Could not read file");
			}

		}
		
		// writes the file out

		BorderPane root = new BorderPane();

		primaryStage.setTitle("Media Collection");

		ObservableList<MediaItem> items = FXCollections.observableList(mediaItem);
		ListView<MediaItem> listView = new ListView<MediaItem>(items);

		listView.setItems(items);
		listView.refresh();

		VBox box = new VBox();

		VBox list = new VBox();
		list.prefWidthProperty().multiply(.80);

		GridPane grid1 = new GridPane();
		grid1.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		grid1.setHgap(5.5);
		grid1.setVgap(5.5);
		Button add = new Button("Add");
		TextField t = new TextField();
		TextField f = new TextField();
		grid1.add(new Label("Title: "), 0, 0);
		grid1.add(t, 1, 0);
		grid1.add(new Label("Format: "), 0, 1);
		grid1.add(f, 1, 1);
		grid1.add(add, 0, 2);
		ArrayList<MediaItem> b = mediaItem;
		
        //add button
		add.setOnAction(e -> {
			boolean check = false;
			// adds item to list
			String title = t.getText();
			String format = f.getText();
			// makes sure the format and title has been entered
			if (title.isEmpty() || format.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Must enter both title and format!");
			} else {
				for (int i = 0; i < b.size(); i++) {
					if (b.get(i).getTitle().equalsIgnoreCase(title)) {
						check = true;
					}
				}
				if (check == false) {
					MediaItem a = new MediaItem(title, format);
					items.add(a);
				} else {
					JOptionPane.showMessageDialog(null, "Item already exist!");
				}

			}

		});

		grid1.setStyle("-fx-border-style: solid;" + "-fx-border-color: black;");

		GridPane grid2 = new GridPane();
		grid2.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		grid2.setHgap(5.5);
		grid2.setVgap(10.5);
		Button remove = new Button("Remove");
		Button retu = new Button("Return");
		grid2.add(remove, 0, 0);
		grid2.add(retu, 0, 1);
        
		//remove button
		remove.setOnAction(e -> {
			Boolean check = false;
			String title = t.getText();
			String format = f.getText();
			
			// checks to see if title exist
			for (int i = 0; i < b.size(); i++) {
				if (b.get(i).getTitle().equalsIgnoreCase(title)) {
					b.remove(i);
					check = true;
				}
			}
			
			if (check == false) {
				JOptionPane.showMessageDialog(null, "Item doesn't exist!");
			}
			listView.refresh();
		});
		
		//return button
		retu.setOnAction(e -> {
			String title = t.getText();
			String format = f.getText();
			System.out.println("This is the second title" + title);
			for (int i = 0; i < b.size(); i++) {
				if (b.get(i).getTitle().equalsIgnoreCase(title)) {
					b.get(i).setReturn(title);

				} else {
					JOptionPane.showMessageDialog(null, "Item doesn't exist!");
				}
			}
			listView.refresh();
		});

		GridPane grid3 = new GridPane();
		grid3.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		grid3.setHgap(5.5);
		grid3.setVgap(5.5);
		TextField loanedTo = new TextField();
		TextField loanedOn = new TextField();
		Button loan = new Button("Loan");
		grid3.add(new Label("Loaned To: "), 0, 0);
		grid3.add(loanedTo, 1, 0);
		grid3.add(new Label("Loaned On: "), 0, 1);
		grid3.add(loanedOn, 1, 1);
		grid3.add(loan, 0, 2);
        
		//loan button
		loan.setOnAction(e -> {
			String loanTo = loanedTo.getText();
			String loanDate = loanedOn.getText();
			String title = t.getText();
           
			for (int i = 0; i < b.size(); i++) {

				if (b.get(i).getTitle().equalsIgnoreCase(title)) {
					b.get(i).setOnLoan(loanTo, loanDate);
				} else {
					JOptionPane.showMessageDialog(null, "Item doesn't exist");
				}
			}

			listView.refresh();
		});
		grid3.setStyle("-fx-border-style: solid;" + "-fx-border-color: black;");
		System.out.println(mediaItem);

		GridPane grid4 = new GridPane();
		grid4.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
		grid4.setHgap(5.5);
		grid4.setVgap(10.5);
		grid4.add(new Label("Sort"), 0, 0);
		RadioButton byTitle = new RadioButton("By Title");
		RadioButton byLoanedDate = new RadioButton("By Date");
		grid4.add(byTitle, 0, 1);
		grid4.add(byLoanedDate, 0, 2);

		ToggleGroup group = new ToggleGroup();
		RadioButton[] radioButtons = { byTitle, byLoanedDate };
		for (RadioButton r : radioButtons) {
			r.setToggleGroup(group);
			r.setOnAction(e -> {
				if (byTitle.isSelected()) {

				}
			});
		}

		list.getChildren().addAll(listView);
		box.getChildren().addAll(grid1, grid2, grid3, grid4);
		// root.getChildren().addAll(list, box);
		root.setLeft(list);
		root.setRight(box);

		primaryStage.setScene(new Scene(root, 600, 600));
		primaryStage.setOnCloseRequest(e -> {
			try {
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File("mediaItem.dat")));
				oos.writeObject(b);
				oos.close();
			} catch (IOException e2) {
				System.out.println("Haha! you just dunked boiii!");
			}

		});
		primaryStage.show();

	}

}
